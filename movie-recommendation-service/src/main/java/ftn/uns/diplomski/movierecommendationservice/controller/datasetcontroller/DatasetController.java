package ftn.uns.diplomski.movierecommendationservice.controller.datasetcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatasetController {

    private static final Logger logger = LoggerFactory.getLogger(DatasetController.class);
    private Books books;
    private Movies movies; 
    
    public DatasetController() {
        logger.info("Reading book data...");
        //this.books = new Books("books.txt");
        this.movies = new Movies("movies.txt");
    }
    
    public Movies getMovies() {
    	return movies; 
    }
    
    public void setMovies(Movies movies) {
    	this.movies = movies; 
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

}
