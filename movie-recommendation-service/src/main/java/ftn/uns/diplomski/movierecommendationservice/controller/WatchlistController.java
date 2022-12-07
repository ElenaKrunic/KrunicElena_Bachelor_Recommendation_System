package ftn.uns.diplomski.movierecommendationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.diplomski.movierecommendationservice.dto.WatchlistDTO;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.model.Watchlist;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.WatchlistRepository;

@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {

	@Autowired
	private WatchlistRepository watchlistRepository; 
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private MovieRepository movieRepository; 
	
	@GetMapping("/{watchlistId}")
	public ResponseEntity<Watchlist> getWatchlistById(@PathVariable("watchlistId") Long watchlistId) {
		Watchlist _watchlist = watchlistRepository.findById(watchlistId).orElseThrow();
		return new ResponseEntity<>(_watchlist, HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json", value="/createWatchlist/{userId}")
	public ResponseEntity<WatchlistDTO> saveWatchlist(@RequestBody WatchlistDTO dto, @PathVariable("userId") Long userId) {
		Watchlist watchlist = new Watchlist();
		User user = userRepository.findById(userId).orElseThrow();
				
		watchlist.setComment(dto.getComment());
		watchlist.setMakeItPublic(dto.isMakeItPublic());
		watchlist.setUser(user);
		
		watchlist = watchlistRepository.save(watchlist);
		
		return new ResponseEntity<WatchlistDTO>(new WatchlistDTO(watchlist), HttpStatus.CREATED); 
	}
	
	@PutMapping("/updateWatchlist/{watchlistId}")
	public ResponseEntity<Watchlist> updateWatchlist(@PathVariable("watchlistId") Long watchlistId, @RequestBody Watchlist watchlist) {
		Watchlist _watchlist = watchlistRepository.findById(watchlistId)
				.orElseThrow();
		_watchlist.setComment(watchlist.getComment());
		_watchlist.setMakeItPublic(watchlist.isMakeItPublic());
		
		return new ResponseEntity<>(watchlistRepository.save(_watchlist), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteWatchlist/{watchlistId}")
	public ResponseEntity<HttpStatus> deleteWatchlist(@PathVariable("watchlistId") Long watchlistId) {
		watchlistRepository.deleteById(watchlistId);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/public")
	public ResponseEntity<List<Watchlist>> findPublicWatchlists() {
		List<Watchlist> watchlists = watchlistRepository.findByMakeItPublic(true);
		if(watchlists.isEmpty()) {
			return new ResponseEntity<List<Watchlist>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Watchlist>>(watchlists, HttpStatus.OK);
	}
	
	@GetMapping("/{watchlistId}/movies")
	public ResponseEntity<List<Movie>> getAllMoviesByWatchlistId (@PathVariable(value = "watchlistId") Long watchlistId) {

		if(!watchlistRepository.existsById(watchlistId)) {
			return null;
		}

		List<Movie> movies = movieRepository.findMoviesByWatchlistsWatchlistId(watchlistId);
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}
	
	@PostMapping("/addMovieInWatchlist/{watchlistId}/{movieId}")
	public ResponseEntity<HttpStatus> addMovieInWatchlist(@PathVariable(value = "watchlistId") Long watchlistId, @PathVariable(value = "movieId") Long movieId) {
		Watchlist watchlist = watchlistRepository.findById(watchlistId).orElseThrow();

		if(movieId != 0L) {
			Movie _movie = movieRepository.findById(movieId)
					.orElseThrow();
			watchlist.addMovie(_movie);
			watchlistRepository.save(watchlist);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
		
	@DeleteMapping("/deleteMovieFromWatchlist/{watchlistId}/{movieId}")
	public ResponseEntity<HttpStatus> deleteMovieFromWatchlist(@PathVariable(value="watchlistId") Long watchlistId, @PathVariable(value="movieId") Long movieId) {

		Watchlist watchlist = watchlistRepository.findById(watchlistId)
				.orElseThrow();
		watchlist.removeMovie(movieId);
		watchlistRepository.save(watchlist);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	
}
