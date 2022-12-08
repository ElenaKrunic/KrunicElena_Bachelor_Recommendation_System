package ftn.uns.diplomski.movierecommendationservice.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.diplomski.movierecommendationservice.model.Role;
import ftn.uns.diplomski.movierecommendationservice.repository.RoleRepository;
import ftn.uns.diplomski.movierecommendationservice.service.RoleInterface;

@Service
public class RoleService implements RoleInterface {
	
	@Autowired
	RoleRepository roleRepository; 

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findOne(Long id) {
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void remove(Long id) {
		roleRepository.deleteById(id);
	}

}
