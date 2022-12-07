package ftn.uns.diplomski.movierecommendationservice;

import ftn.uns.diplomski.movierecommendationservice.controller.datasetcontroller.DatasetController;
import ftn.uns.diplomski.movierecommendationservice.model.Book;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.repository.BookRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MovieRecommendationServiceApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MovieRecommendationServiceApplication.class);
   
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired 
    private MovieRepository movieRepository; 

    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("BookRecommendationServiceApplication....");

        DatasetController datasetController = new DatasetController();
        //List<Book> bookList = new ArrayList<>();
        List<Movie> movieList = new ArrayList<>();
        
        datasetController.getMovies().getMovies().forEach((key, movie) -> {
        	if (!movieRepository.existsById(key)) {
        		movieList.add(movie);
        	}
        });

        /*
        datasetController.getBooks().getBooks().forEach((key, book) -> {
            if (!bookRepository.existsById(key)) {
                bookList.add(book);
            }
        });

*/
        movieRepository.saveAll(movieList);
        //bookRepository.saveAll(bookList);
    }
}
