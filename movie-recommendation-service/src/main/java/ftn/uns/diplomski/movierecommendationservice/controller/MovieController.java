package ftn.uns.diplomski.movierecommendationservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.diplomski.movierecommendationservice.dto.BasicMovieInfoDTO;
import ftn.uns.diplomski.movierecommendationservice.dto.MovieDTO;
import ftn.uns.diplomski.movierecommendationservice.exception.ResourceNotFoundException;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.resource.MovieResource;
import ftn.uns.diplomski.movierecommendationservice.service.MovieRecommender;
import ftn.uns.diplomski.movierecommendationservice.service.implementation.MovieService;

@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {

	private final MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieService movieService; 
	
	public MovieController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository; 
	}
	
	@Autowired
	private MovieRecommender movieRecommender; 
	
	@GetMapping("/{id}")
	public ResponseEntity<BasicMovieInfoDTO> getMovieById(@PathVariable("id") Long id) {
		Movie movie = movieRepository.findById(id).orElseThrow();
		return new ResponseEntity<BasicMovieInfoDTO>(new BasicMovieInfoDTO(movie), HttpStatus.OK);
	}
	
	@GetMapping("/searchTitle/{title}")
	public ResponseEntity<MovieDTO> getMovieByTitle(@PathVariable(name="title") String title) {
		MovieDTO movie = movieService.getMovieByTitleFromApi(title);
		return new ResponseEntity<MovieDTO>(movie, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchByImdbId/{id}")
	public ResponseEntity<MovieDTO> getMovieByImdbId(@PathVariable(name = "id") String id){
		MovieDTO movie = movieService.getMovieByIdFromApi(id);
		return new ResponseEntity<MovieDTO>(movie, HttpStatus.OK);
	}
	
	@PostMapping(value = "/insertMovie")
	public ResponseEntity<BasicMovieInfoDTO> saveMovie(@RequestBody BasicMovieInfoDTO movieDto) {
		Movie movie = new Movie(); 
		
		movie.setTitle(movieDto.getTitle());
		movie.setGenre(movieDto.getGenre());
		
		movie = movieRepository.save(movie);
		
		return new ResponseEntity<BasicMovieInfoDTO>(new BasicMovieInfoDTO(movie), HttpStatus.CREATED);
	}
	
	@GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllMovies() {
		List<MovieResource> movieResources = new ArrayList<>();
		movieRepository.findAll().forEach(movie -> movieResources.add(getMovieResource(movie)));
		return ResponseEntity.ok().body(movieResources);
	}
	
	@SuppressWarnings("rawtypes")
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
