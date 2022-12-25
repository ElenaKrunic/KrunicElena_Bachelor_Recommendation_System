package ftn.uns.diplomski.movierecommendationservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import ftn.uns.diplomski.movierecommendationservice.dto.MovieDTO;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.service.MovieInteface;
import ftn.uns.diplomski.movierecommendationservice.utils.OmdbWebServiceClient;

@Service
public class MovieService implements MovieInteface{
	
	@SuppressWarnings("unused")
	@Autowired
	private MovieRepository movieRepository; 

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

	

}
