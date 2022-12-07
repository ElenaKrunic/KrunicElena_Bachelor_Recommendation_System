package ftn.uns.diplomski.movierecommendationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    User findByEmail(String username); 
	
}
