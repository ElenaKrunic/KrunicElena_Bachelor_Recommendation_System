package ftn.uns.diplomski.movierecommendationservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.diplomski.movierecommendationservice.exception.ResourceNotFoundException;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.model.UserMovieRating;
import ftn.uns.diplomski.movierecommendationservice.model.UserMovieRatingKey;
import ftn.uns.diplomski.movierecommendationservice.model.UserMovieReview;
import ftn.uns.diplomski.movierecommendationservice.model.UserMovieReviewKey;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserMovieRatingRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserMovieReviewRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/movieRatesAndReviews")
public class UserMovieController {

	@Autowired
	private UserMovieRatingRepository userMovieRatingRepository; 
	
	@Autowired
	private UserMovieReviewRepository userMovieReviewRepository; 
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired 
	private MovieRepository movieRepository; 
	
	//ovo je posveceno za rate 
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/rateMovie/{userId}")
	public ResponseEntity rateMovies(@PathVariable("userId") Long userId, @RequestBody List<HashMap> movieRates) throws ResourceNotFoundException{
		
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		List<UserMovieRating> updatedMovieRates = new ArrayList<>(); 
		
		for(HashMap map : movieRates) {
			long movieId = Long.parseLong(map.get("movieId").toString());
			int rate = Integer.parseInt(map.get("rate").toString());
			
			System.out.println("movieId" + movieId);
			System.out.println("rate" + rate);
			
			Movie movie = movieRepository.findById(movieId).orElseThrow(null);
			
			UserMovieRating userMovieRating = new UserMovieRating(user, movie);
			UserMovieRatingKey userMovieRatingKey = new UserMovieRatingKey(userId, movieId);
			
			userMovieRating.setId(userMovieRatingKey);
			userMovieRating.setRate(rate);
			updatedMovieRates.add(userMovieRating);
		}
		
		return ResponseEntity.ok().body(userMovieRatingRepository.saveAll(updatedMovieRates).toString());
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/leaveReview/{userId}")
	public ResponseEntity leaveReview(@PathVariable("userId") Long userId, @RequestBody List<HashMap> movieReviews) throws ResourceNotFoundException {
		
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<UserMovieReview> updatedMovieReviews = new ArrayList<>();
		
		for(HashMap map: movieReviews) {
			long movieId = Long.parseLong(map.get("movieId").toString());
			String review = map.get("review").toString();
			
			Movie movie = movieRepository.findById(movieId).orElseThrow();
			
			UserMovieReview userMovieReview = new UserMovieReview(user, movie);
			UserMovieReviewKey userMovieReviewKey = new UserMovieReviewKey(userId, movieId);
			
			userMovieReview.setId(userMovieReviewKey);
			userMovieReview.setReview(review);
			updatedMovieReviews.add(userMovieReview);
		}
		return ResponseEntity.ok().body(userMovieReviewRepository.saveAll(updatedMovieReviews).toString());
	}
}
