package ftn.uns.diplomski.movierecommendationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.ERole;
import ftn.uns.diplomski.movierecommendationservice.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);


}
