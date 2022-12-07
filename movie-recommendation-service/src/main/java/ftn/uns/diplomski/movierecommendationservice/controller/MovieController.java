package ftn.uns.diplomski.movierecommendationservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.diplomski.movierecommendationservice.exception.ResourceNotFoundException;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.resource.MovieResource;
import ftn.uns.diplomski.movierecommendationservice.service.MovieRecommender;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

	private final MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public MovieController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository; 
	}
	
	@Autowired
	private MovieRecommender movieRecommender; 
	
	@GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllMovies() {
		List<MovieResource> movieResources = new ArrayList<>();
		movieRepository.findAll().forEach(movie -> movieResources.add(getMovieResource(movie)));
		return ResponseEntity.ok().body(movieResources);
	}
	
	
	@GetMapping(value="/recommended" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getRecommendedMoviesByUserId(@RequestParam("userId") Long userId) throws ResourceNotFoundException {
		
		if(!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException("User not found");
		}
		
		String recommendedMovies = movieRecommender.recommendedMovies(userId, userRepository, movieRepository);
		return ResponseEntity.ok().body(recommendedMovies);
	}
	
	private MovieResource getMovieResource(Movie movie) {
		return new MovieResource(movie.getMovieId(),movie.getTitle(), movie.getGenre());
	}
	
	
}
