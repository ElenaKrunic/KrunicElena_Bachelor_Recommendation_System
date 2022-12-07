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

import ftn.uns.diplomski.movierecommendationservice.dto.CustomListDTO;
import ftn.uns.diplomski.movierecommendationservice.exception.ResourceNotFoundException;
import ftn.uns.diplomski.movierecommendationservice.model.CustomList;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.repository.CustomListRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;

@RestController
@RequestMapping("/api/customLists")
public class CustomListController {
	
	@Autowired
	private CustomListRepository customListRepository; 
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired 
	private MovieRepository movieRepository; 
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomList> getCustomListById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        CustomList customList = customListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found custom list with id = " + id));
        return new ResponseEntity<CustomList>(customList, HttpStatus.OK); 
	}
	
	 @PostMapping(consumes = "application/json", value = "/createCustomList/{userId}")
	 public ResponseEntity<CustomListDTO> saveCustomList(@RequestBody CustomListDTO dto, @PathVariable("userId") Long userId) {
		 CustomList customList = new CustomList();
		 User user = userRepository.findById(userId).orElseThrow();
	    	
	    customList.setDescription(dto.getDescription());
	    customList.setMakeItPublic(dto.isMakeItPublic());
	    customList.setName(dto.getName());
	    customList.setUser(user);
	    	
	    customList = customListRepository.save(customList);
	    	
	    return new ResponseEntity<CustomListDTO>(new CustomListDTO(customList), HttpStatus.OK);
	  }
	 
	 @PutMapping("/updateCustomList/{id}")
	    public ResponseEntity<CustomList> updateCustomList(@PathVariable("id") Long id, @RequestBody CustomList customList) throws ResourceNotFoundException {
	        CustomList _customList = customListRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Not found custom list with id = " + id));
	        _customList.setName(customList.getName());
	        _customList.setDescription(customList.getDescription());
	        _customList.setMakeItPublic(customList.isMakeItPublic());

	        return new ResponseEntity<>(customListRepository.save(_customList), HttpStatus.OK);
	    }
	
	  @DeleteMapping("/deleteCustomList/{id}")
	    public ResponseEntity<HttpStatus> deleteCustomList(@PathVariable("id") Long id) {
	        customListRepository.deleteById(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	
	  @GetMapping("/{customListId}/movies")
	  public ResponseEntity<List<Movie>> getAllMoviesByCustomListId (@PathVariable(value = "customListId") Long customListId) throws ResourceNotFoundException {

		if(!customListRepository.existsById(customListId)) {
			throw new ResourceNotFoundException("Not found custom list with id = " + customListId);
		}

		List<Movie> movies = movieRepository.findMoviesByCustomListsCustomListId(customListId);
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}
	  
	  @PostMapping("addMovieInCustomList/{customListId}/{movieId}")
		public ResponseEntity<HttpStatus> addMovieInCustomList(@PathVariable(value = "customListId") Long customListId, @PathVariable(value = "movieId") Long movieId) throws ResourceNotFoundException {
			CustomList customList = customListRepository.findById(customListId).orElseThrow(() -> new ResourceNotFoundException("Not found custom list with id" + customListId));

			if(movieId != 0L) {
				Movie _movie = movieRepository.findById(movieId)
						.orElseThrow(() -> new ResourceNotFoundException("Not found movie with id =  " + movieId));
				customList.addMovie(_movie);
				customListRepository.save(customList);
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		}

		@DeleteMapping("/deleteMovieFromCustomList/{customListId}/{movieId}")
		public ResponseEntity<HttpStatus> deleteMovieFromCustomList(@PathVariable(value = "customListId") Long customListId, @PathVariable(value = "movieId") Long movieId) throws ResourceNotFoundException {
			CustomList customList = customListRepository.findById(customListId)
					.orElseThrow(() -> new ResourceNotFoundException("Not found custom list with id =" + customListId));
			customList.removeMovie(movieId);
			customListRepository.save(customList);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}
