package ftn.uns.diplomski.movierecommendationservice.controller.datasetcontroller;

public class DatasetController {

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
