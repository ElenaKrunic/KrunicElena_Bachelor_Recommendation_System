package ftn.uns.diplomski.movierecommendationservice.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.service.UserInterface;

@Service
public class UserService implements UserInterface {

	@Autowired
	private UserRepository userRepository; 
		
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findOne(Long id) {
		return userRepository.findById(id).orElseThrow(null);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void remove(Long id) {
		userRepository.deleteById(id);
	}

	

}
