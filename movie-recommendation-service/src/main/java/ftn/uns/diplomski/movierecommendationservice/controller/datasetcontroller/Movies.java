package ftn.uns.diplomski.movierecommendationservice.controller.datasetcontroller;

import java.util.HashMap;

import ftn.uns.diplomski.movierecommendationservice.controller.datareadercontroller.MovieReader;
import ftn.uns.diplomski.movierecommendationservice.controller.datareadercontroller.ReaderFactory;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;

public class Movies {

	private HashMap<Long, Movie> theMovies = new HashMap<>();
	
	public Movies(String filename) {
		createMovies(filename);
	}
	
	private void createMovies(String filename) {
		ReaderFactory readerFactory = new ReaderFactory(); 
		MovieReader movieReader = (MovieReader) readerFactory.getReader("movies", filename); 
		
		theMovies = movieReader.getTheMovies(); 
	}
	
	public HashMap<Long, Movie> getMovies() {
		return theMovies;
	}
}
