package ftn.uns.diplomski.movierecommendationservice.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import ftn.uns.diplomski.movierecommendationservice.service.CustomListInterface;
import ftn.uns.diplomski.movierecommendationservice.service.implementation.CustomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ftn.uns.diplomski.movierecommendationservice.dto.CustomListDTO;
import ftn.uns.diplomski.movierecommendationservice.exception.ResourceNotFoundException;
import ftn.uns.diplomski.movierecommendationservice.model.CustomList;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.repository.CustomListRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/customLists")
public class CustomListController {

	@Autowired
	private CustomListService customListService;
	
	@Autowired
	private CustomListRepository customListRepository; 
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired 
	private MovieRepository movieRepository; 
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomList> getCustomListById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        CustomList customList = customListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found custom list with id = " + id));
        return new ResponseEntity<CustomList>(customList, HttpStatus.OK); 
	}

	  @GetMapping("/{customListId}/movies")
	  public ResponseEntity<List<Movie>> getAllMoviesByCustomListId (@PathVariable(value = "customListId") Long customListId) throws ResourceNotFoundException {

		if(!customListRepository.existsById(customListId)) {
			throw new ResourceNotFoundException("Not found custom list with id = " + customListId);
		}

		List<Movie> movies = movieRepository.findMoviesByCustomListsCustomListId(customListId);
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}
	  
	  @PostMapping("addMovieInCustomList/{customListId}/{movieId}")
		public ResponseEntity<HttpStatus> addMovieInCustomList(@PathVariable(value = "customListId") Long customListId, @PathVariable(value = "movieId") Long movieId) throws ResourceNotFoundException {
			CustomList customList = customListRepository.findById(customListId).orElseThrow(() -> new ResourceNotFoundException("Not found custom list with id" + customListId));

			if(movieId != 0L) {
				Movie _movie = movieRepository.findById(movieId)
						.orElseThrow(() -> new ResourceNotFoundException("Not found movie with id =  " + movieId));
				customList.addMovie(_movie);
				customListRepository.save(customList);
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		}

		@DeleteMapping("/deleteMovieFromCustomList/{customListId}/{movieId}")
		public ResponseEntity<HttpStatus> deleteMovieFromCustomList(@PathVariable(value = "customListId") Long customListId, @PathVariable(value = "movieId") Long movieId) throws ResourceNotFoundException {
			CustomList customList = customListRepository.findById(customListId)
					.orElseThrow(() -> new ResourceNotFoundException("Not found custom list with id =" + customListId));
			customList.removeMovie(movieId);
			customListRepository.save(customList);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		//prosiren kod

	@GetMapping("/userCustomList")
	public ResponseEntity<?> userCustomLists(Principal principal) {
		try {

			List<CustomListDTO> dtos = customListService.getCustomListWithPrincipal(principal.getName());
			return new ResponseEntity<>(dtos, HttpStatus.OK);

		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@PostMapping(consumes = "application/json", value = "/createCustomList")
	public ResponseEntity<CustomListDTO> saveCustomListForUser(@RequestBody CustomListDTO customListDto, Principal principal) {

		CustomList customList = new CustomList();

		User user = userRepository.findOneByUsername(principal.getName());
		System.out.println("username korisnika koji dodaje custom list je " + user.getUsername());

		customList.setDescription(customListDto.getDescription());
		customList.setMakeItPublic(customListDto.isMakeItPublic());
		customList.setName(customListDto.getName());
		customList.setUser(user);

		customList = customListRepository.save(customList);

		return new ResponseEntity<CustomListDTO>(new CustomListDTO(customList), HttpStatus.CREATED);
	}

	@PutMapping("/updateCustomList/{id}")
	public ResponseEntity<CustomList> updateCustomList(@PathVariable("id") Long id, @RequestBody CustomList customList) {

		CustomList _customList = customListRepository.findById(id).orElseThrow(null);

		_customList.setDescription(customList.getDescription());
		_customList.setMakeItPublic(customList.isMakeItPublic());
		_customList.setName(customList.getName());

		return new ResponseEntity<CustomList>(customListRepository.save(_customList), HttpStatus.OK);
	}

	@DeleteMapping("/deleteCustomList/{id}")
	public ResponseEntity<HttpStatus> deleteCustomList(@PathVariable("id") Long id) {
		customListRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<HttpStatus> deleteAllCustomLists() {
		try {
			customListRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
