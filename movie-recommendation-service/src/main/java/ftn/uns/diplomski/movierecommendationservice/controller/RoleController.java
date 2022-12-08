package ftn.uns.diplomski.movierecommendationservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.diplomski.movierecommendationservice.dto.RoleDTO;
import ftn.uns.diplomski.movierecommendationservice.model.Role;
import ftn.uns.diplomski.movierecommendationservice.service.implementation.RoleService;

@RestController
@RequestMapping(value="/api/roles")
@CrossOrigin
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(value="/all")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<RoleDTO>> getRoles(){
		List<Role> roles = roleService.findAll();
		List<RoleDTO> rolesDTO = new ArrayList<>();
		for(Role r: roles) {
			rolesDTO.add(new RoleDTO(r)); 
		}
		
		return new ResponseEntity<List<RoleDTO>>(rolesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<RoleDTO> getRole(@PathVariable("id") Long id) {
		Role role = roleService.findOne(id);
		if(role == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RoleDTO>(new RoleDTO(role), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
		Role role = new Role(); 
		role.setName(roleDTO.getName());
		
		role = roleService.save(role); 
		return new ResponseEntity<RoleDTO>(new RoleDTO(role), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO, @PathVariable("id") Long id) {
		 
		Role role = roleService.findOne(id); 
		
		if(role == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.BAD_REQUEST);
		}
		
		role.setName(roleDTO.getName());
		
		role = roleService.save(role);
		
		return new ResponseEntity<>(new RoleDTO(role), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id) {
		Role role = roleService.findOne(id); 
		
		if(role != null) {
			roleService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
