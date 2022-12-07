package ftn.uns.diplomski.movierecommendationservice.controller.datareadercontroller;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import ftn.uns.diplomski.movierecommendationservice.model.Movie;

public class MovieReader implements Reader {
	
	private HashMap<Long, Movie> theMovies; 
	
	public MovieReader(String filename) {
		theMovies = new HashMap<>();
		readFile(filename);
	}

	public HashMap<Long, Movie> getTheMovies() {
		return theMovies; 
	}

	@Override
	public void createObjects(String movieInfo) {
		String[] info = movieInfo.split(";");
		
		  if (!info[0].matches("-?(0|[1-9]\\d*)")) {
	            return;
	        }
		
		long movieId = Long.parseLong(info[0]);
		String title = info[1];
		String genre = info[2];
		
		if(!theMovies.containsKey(movieId)) {
			Movie newMovie = new Movie(movieId, title, genre);
			theMovies.put(movieId, newMovie);
		}

	}
	
	  public void readFile(String file) {
	        try (Scanner n = new Scanner(new File(file), "UTF-8")) {
	            //iterate through all lines in file
	            while (n.hasNextLine()) {
	                String line = n.nextLine();
	                //check if line is correctly formatted, and if so, call createObjects with given line
	                if (line.length() > 0) {
	                    createObjects(line);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
