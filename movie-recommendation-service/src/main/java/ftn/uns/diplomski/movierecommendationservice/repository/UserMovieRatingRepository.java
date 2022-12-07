package ftn.uns.diplomski.movierecommendationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.UserMovieRating;

@Repository
public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {

}
