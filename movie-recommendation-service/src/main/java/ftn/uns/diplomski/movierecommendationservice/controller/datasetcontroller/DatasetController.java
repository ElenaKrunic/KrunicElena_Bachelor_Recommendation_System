package ftn.uns.diplomski.movierecommendationservice.controller.datasetcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatasetController {

    private static final Logger logger = LoggerFactory.getLogger(DatasetController.class);

    private Movies movies; 
    
    public DatasetController() {
        this.movies = new Movies("movies.txt");
    }
    
    public Movies getMovies() {
    	return movies; 
    }
    
    public void setMovies(Movies movies) {
    	this.movies = movies; 
    }
}
