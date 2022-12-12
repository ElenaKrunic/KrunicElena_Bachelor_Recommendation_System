package ftn.uns.diplomski.movierecommendationservice.service;

import java.util.List;

import ftn.uns.diplomski.movierecommendationservice.model.User;

public interface UserInterface {
	
	List<User> findAll();

	User findOne(Long id);

	User save(User user);
	
	void remove(Long id);

}
