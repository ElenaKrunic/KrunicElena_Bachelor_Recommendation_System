package ftn.uns.diplomski.movierecommendationservice.controller;

import ftn.uns.diplomski.movierecommendationservice.dto.JwtResponseDTO;
import ftn.uns.diplomski.movierecommendationservice.dto.LoginDTO;
import ftn.uns.diplomski.movierecommendationservice.dto.MessageResponse;
import ftn.uns.diplomski.movierecommendationservice.dto.SignupDTO;
import ftn.uns.diplomski.movierecommendationservice.exception.ResourceNotFoundException;
import ftn.uns.diplomski.movierecommendationservice.model.ERole;
import ftn.uns.diplomski.movierecommendationservice.model.Role;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.repository.RoleRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.resource.UserResource;
import ftn.uns.diplomski.movierecommendationservice.security.jwt.JwtUtils;
import ftn.uns.diplomski.movierecommendationservice.security.services.UserDetailsImpl;
import ftn.uns.diplomski.movierecommendationservice.service.implementation.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    AuthenticationManager authenticationManager; 
    
    @Autowired
    UserDetailsService userDetailsService; 
    
    @Autowired 
    UserService userService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository; 
    
    @Autowired
    PasswordEncoder encoder; 
    
    @Autowired
    JwtUtils jwtUtils; 
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponseDTO(jwt, 
												 userDetails.getId().toString(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}
    
    @PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
                            signUpRequest.getFirstName(), signUpRequest.getLastName());

		Set<String> strRoles = signUpRequest.getRoles();
		List<Role> roles = new ArrayList<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_REGISTERED_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_REGISTERED_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					System.out.println(userRole.getName());
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	

     
    /**
     * Gets users.
     *
     * @return the all users
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getAllUsers() {

        List<UserResource> userResources = new ArrayList<>();
        userRepository.findAll().forEach(user -> userResources.add(getUserResource(user)));

        return ResponseEntity.ok().body(userResources);
    }

  
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public ResponseEntity<UserResource> getUserById(@RequestParam("userId") Long userId)
            throws ResourceNotFoundException {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return ResponseEntity.ok().body(getUserResource(user));
    }

   
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody User userData) {
        return ResponseEntity.ok().body(getUserResource(userRepository.save(userData)));
    }

   
    @PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUser(
            @RequestParam("userId") Long userId, @Valid @RequestBody User userDetails)
            throws ResourceNotFoundException {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setUpdatedAt(new Date());

        return ResponseEntity.ok().body(getUserResource(userRepository.save(user)));
    }

 
    @SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam("userId") Long userId) throws Exception {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

    private UserResource getUserResource(User user) {
        return new UserResource(
                user.getUserId()
                , user.getFirstName()
                , user.getLastName()
                , user.getEmail()
                , user.getUsername()
                , user.getCreatedAt()
                , user.getUpdatedAt());
    }
}
