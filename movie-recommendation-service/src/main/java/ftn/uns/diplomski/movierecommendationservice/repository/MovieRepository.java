package ftn.uns.diplomski.movierecommendationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
    List<Movie> findMoviesByCustomListsCustomListId(Long customlistId);

}
