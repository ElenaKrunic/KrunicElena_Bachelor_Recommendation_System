package ftn.uns.diplomski.movierecommendationservice;

import ftn.uns.diplomski.movierecommendationservice.controller.datasetcontroller.DatasetController;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MovieRecommendationServiceApplication implements CommandLineRunner {

    @Autowired 
    private MovieRepository movieRepository; 

    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
      
        DatasetController datasetController = new DatasetController();
      
        List<Movie> movieList = new ArrayList<>();
        
        datasetController.getMovies().getMovies().forEach((key, movie) -> {
        	if (!movieRepository.existsById(key)) {
        		movieList.add(movie);
        	}
        });

      
        movieRepository.saveAll(movieList);
      
    }
}
