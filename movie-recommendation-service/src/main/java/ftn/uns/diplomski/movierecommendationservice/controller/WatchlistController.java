package ftn.uns.diplomski.movierecommendationservice.controller;

import java.security.Principal;
import java.util.List;

import ftn.uns.diplomski.movierecommendationservice.dto.BasicMovieInfoDTO;
import ftn.uns.diplomski.movierecommendationservice.service.implementation.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.diplomski.movierecommendationservice.dto.WatchlistDTO;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.model.Watchlist;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.WatchlistRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {

	@Autowired
	private WatchlistRepository watchlistRepository; 
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private MovieRepository movieRepository;

	@Autowired
	private WatchlistService watchlistService;
	
	@GetMapping("/{watchlistId}")
	public ResponseEntity<Watchlist> getWatchlistById(@PathVariable("watchlistId") Long watchlistId) {
		Watchlist _watchlist = watchlistRepository.findById(watchlistId).orElseThrow(null);
		return new ResponseEntity<>(_watchlist, HttpStatus.OK);
	}
	
	@GetMapping("getWatchlistByUser/{userId}")
	public ResponseEntity<Watchlist> getWatchlistByUserId(@PathVariable("userId") Long userId) {
		
		User user = userRepository.getOne(userId);
		
		if(user == null) {
			return null;  
		}
		
		Watchlist _watchlist = watchlistRepository.findWatchlistByUser(user);
		
		if(_watchlist == null) {
			return null;
		}
		
		return new ResponseEntity<>(_watchlist, HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json", value="/createWatchlist/{userId}")
	public ResponseEntity<WatchlistDTO> saveWatchlist(@RequestBody WatchlistDTO dto, @PathVariable("userId") Long userId) {
		Watchlist watchlist = new Watchlist();
		User user = userRepository.findById(userId).orElseThrow(null);
				
		watchlist.setComment(dto.getComment());
		watchlist.setMakeItPublic(dto.isMakeItPublic());
		watchlist.setUser(user);
		
		watchlist = watchlistRepository.save(watchlist);
		
		return new ResponseEntity<WatchlistDTO>(new WatchlistDTO(watchlist), HttpStatus.CREATED); 
	}
	
	@PutMapping("/updateWatchlist/{watchlistId}")
	public ResponseEntity<Watchlist> updateWatchlist(@PathVariable("watchlistId") Long watchlistId, @RequestBody Watchlist watchlist) {
		Watchlist _watchlist = watchlistRepository.findById(watchlistId)
				.orElseThrow(null);
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
			
	@DeleteMapping("/deleteMovieFromWatchlist/{watchlistId}/{movieId}")
	public ResponseEntity<HttpStatus> deleteMovieFromWatchlist(@PathVariable(value="watchlistId") Long watchlistId, @PathVariable(value="movieId") Long movieId) {

		Watchlist watchlist = watchlistRepository.findById(watchlistId)
				.orElseThrow(null);
		watchlist.removeMovie(movieId);
		watchlistRepository.save(watchlist);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/principal/movies")
	public ResponseEntity<List<BasicMovieInfoDTO>> getAllMoviesByUserWatchlist (Principal principal) throws Exception {
		Watchlist watchlist = watchlistService.getOneWatchlistWithPrincipal(principal.getName());

		List<BasicMovieInfoDTO> movies = movieRepository.findMoviesByWatchlists(watchlist);
		return new ResponseEntity<List<BasicMovieInfoDTO>>(movies, HttpStatus.OK);
	}
	
	@PutMapping("/addMovieInWatchlist")
	public ResponseEntity<?> addMovieInWatchlist(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId) {
		
		try {			
			Watchlist watchlist = watchlistService.getWatchlistByUserId(userId);
			if(watchlist == null) {
				return null;
			} else {
				if(movieId != 0L) {
					Movie _movie = movieRepository.findById(movieId)
							.orElseThrow(null);
					watchlist.addMovie(_movie);
					watchlistRepository.save(watchlist);
				}
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}

	@PostMapping(consumes = "application/json", value = "/createWatchlist")
	public ResponseEntity<WatchlistDTO> saveWatchlistForUser(@RequestBody WatchlistDTO watchlistDto, Principal principal) {

		Watchlist watchlist = new Watchlist();

		User user = userRepository.findOneByUsername(principal.getName());

		watchlist.setComment(watchlistDto.getComment());
		watchlist.setMakeItPublic(watchlistDto.isMakeItPublic());
		watchlist.setUser(user);

		watchlist = watchlistRepository.save(watchlist);

		return new ResponseEntity<WatchlistDTO>(new WatchlistDTO(watchlist), HttpStatus.CREATED);
	}
	
	@GetMapping("/userWatchlist")
	public ResponseEntity<?> userWatchlist(Principal principal) {
		try {
			System.out.println(principal.getName());
			List<WatchlistDTO> dtos = watchlistService.getWatchlistWithPrincipal(principal.getName());
			return new ResponseEntity<>(dtos, HttpStatus.OK);

		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
