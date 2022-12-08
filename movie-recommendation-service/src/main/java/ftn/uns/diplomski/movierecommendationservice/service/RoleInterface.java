package ftn.uns.diplomski.movierecommendationservice.service;

import java.util.List;

import ftn.uns.diplomski.movierecommendationservice.model.Role;

public interface RoleInterface {
	
	List<Role> findAll();
	
	Role findOne(Long id);
	
	Role save(Role role);
	
	void remove(Long id);

}
