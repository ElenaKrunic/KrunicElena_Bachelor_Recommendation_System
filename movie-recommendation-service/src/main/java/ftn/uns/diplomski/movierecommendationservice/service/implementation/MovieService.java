package ftn.uns.diplomski.movierecommendationservice.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import ftn.uns.diplomski.movierecommendationservice.dto.MovieDTO;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.service.MovieInteface;
import ftn.uns.diplomski.movierecommendationservice.utils.OmdbWebServiceClient;

@Service
public class MovieService implements MovieInteface{
	
	@SuppressWarnings("unused")
	@Autowired
	private MovieRepository movieRepository; 
	
	@Autowired
	private UserRepository userRepository; 

	@Override
	public MovieDTO getMovieByTitleFromApi(String title) {
		
		String uri = OmdbWebServiceClient.SEARCH_TITLE_URL + title; 
		
		RestTemplate restTemplate = new RestTemplate(); 
		String result = restTemplate.getForObject(uri, String.class);
		
		System.out.println("result je " + result);
		
		Gson g = new Gson();
		MovieDTO movie = g.fromJson(result, MovieDTO.class);
		
		return movie;
	}

	@Override
	public MovieDTO getMovieByIdFromApi(String id) {
		String uri = OmdbWebServiceClient.SEARCH_IMDb_ID_URL + id; 
		
		RestTemplate restTemplate = new RestTemplate(); 
		String result = restTemplate.getForObject(uri, String.class);
		
		Gson g = new Gson(); 
		MovieDTO movie = g.fromJson(result, MovieDTO.class);
		
		return movie;
	}

	public List<MovieDTO> getMoviesWithPrincipal(String name) throws Exception {
		User user = userRepository.findByUsername(name);
		
		if(user == null) {
			throw new Exception("User does not exist!");
		}
		
		List<MovieDTO> moviesDtos = new ArrayList<>();
		List<Movie> movies = movieRepository.findByUser(user);
		
		for(Movie movie : movies) {
			MovieDTO movieDto = new MovieDTO();
			
			movieDto.setMovieId(movie.getMovieId());
			movieDto.setActors(movie.getActors());
			movieDto.setAwards(movie.getAwards());
			movieDto.setCountry(movie.getCountry());
			movieDto.setDirector(movie.getDirector());
			movieDto.setGenre(movie.getGenre());
			movieDto.setLanguage(movie.getLanguage());
			movieDto.setPlot(movie.getPlot());
			movieDto.setPoster(movie.getPoster());
			movieDto.setRuntime(movie.getRuntime());
			movieDto.setTitle(movie.getTitle());
			movieDto.setWriter(movie.getWriter());
			movieDto.setYear(movie.getYear());
			
			moviesDtos.add(movieDto);
		}
		
		
		return moviesDtos;
	}

	

}
