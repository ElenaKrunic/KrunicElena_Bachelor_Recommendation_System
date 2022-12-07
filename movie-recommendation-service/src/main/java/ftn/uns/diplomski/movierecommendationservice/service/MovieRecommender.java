package ftn.uns.diplomski.movierecommendationservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import ftn.uns.diplomski.movierecommendationservice.repository.MovieRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.utils.Utils;
import ftn.uns.diplomski.movierecommendationservice.utils.ValueComparator;

@Component
public class MovieRecommender {
	
	  private static final int NUM_NEIGHBOURHOODS = 10;
	    
	    private static final int NUM_RECOMMENDATIONS = 20;
	    
	    private static final int MIN_VALUE_RECOMMENDATION = 4;

	    /**
	     * Map with the user id as key and its ratings as value that is a map with book ASIN as key and its rating as value
	     */
	    private Map<Long, Map<Long, Integer>> ratings;

	    /**
	     * Average rating of each user where the key is the user id and the value its average rating
	     */
	    private Map<Long, Double> averageRating;

	    /**
	     * Constructor
	     */
	    public MovieRecommender() {
	        ratings = new HashMap<>();
	        averageRating = new HashMap<>();
	    }

	    public Map<Long, Map<Long, Integer>> getRatings() {
	        return ratings;
	    }

	    private void setRatings(Map<Long, Map<Long, Integer>> ratings) {
	        this.ratings = ratings;
	    }

	    public Map<Long, Double> getAverageRating() {
	        return averageRating;
	    }

	    private void setAverageRating(Map<Long, Double> averageRating) {
	        this.averageRating = averageRating;
	    }
	    
	    private Map<Long, Double> getNeighbourhoods(Map<Long, Integer> userRatings) {
	    	
	        Map<Long, Double> neighbourhoods = new HashMap<>();
	        ValueComparator valueComparator = new ValueComparator(neighbourhoods);
	        Map<Long, Double> sortedNeighbourhoods = new TreeMap<>(valueComparator);

	        double userAverage = getAverage(userRatings);

	        for (long user : ratings.keySet()) {
	            ArrayList<Long> matches = new ArrayList<>();
	            for (long movieId : userRatings.keySet()) {
	                if (ratings.get(user).containsKey(movieId)) {
	                    matches.add(movieId);
	                }
	            }
	            double matchRate;
	            if (matches.size() > 0) {
	                double numerator = 0, userDenominator = 0, otherUserDenominator = 0;
	                for (long movieId : matches) {
	                    double u = userRatings.get(movieId) - userAverage;
	                    double v = ratings.get(user).get(movieId) - averageRating.get(user);

	                    numerator += u * v;
	                    userDenominator += u * u;
	                    otherUserDenominator += v * v;
	                }
	                if (userDenominator == 0 || otherUserDenominator == 0) {
	                    matchRate = 0;
	                } else {
	                    matchRate = numerator / (Math.sqrt(userDenominator) * Math.sqrt(otherUserDenominator));
	                }
	            } else {
	                matchRate = 0;
	            }

	            neighbourhoods.put(user, matchRate);
	        }
	        sortedNeighbourhoods.putAll(neighbourhoods);

	        Map<Long, Double> output = new TreeMap<>();

	        Iterator entries = sortedNeighbourhoods.entrySet().iterator();
	        int i = 0;
	        while (entries.hasNext() && i < NUM_NEIGHBOURHOODS) {
	            Map.Entry entry = (Map.Entry) entries.next();
	            if ((double) entry.getValue() > 0) {
	                output.put((long) entry.getKey(), (double) entry.getValue());
	                i++;
	            }
	        }
	        return output;
	    }
	    
	    private double getAverage(Map<Long, Integer> userRatings) {
	        double userAverage = 0;
	        for (Map.Entry<Long, Integer> longIntegerEntry : userRatings.entrySet()) {
	            userAverage += (int) ((Map.Entry) longIntegerEntry).getValue();
	        }
	        return userAverage / userRatings.size();
	    }
	    
	    private Map<Long, Double> getRecommendations(Map<Long, Integer> userRatings,
                Map<Long, Double> neighbourhoods, Map<Long, String> movies) {

	    	Map<Long, Double> predictedRatings = new HashMap<>();

	    	// r(u)
	    	double userAverage = getAverage(userRatings);

	    	for (Long movieId : movies.keySet()) {
	    		if (!userRatings.containsKey(movieId)) {

	    			// sum(sim(u,v) * (r(v,i) - r(v)))
	    			double numerator = 0;

	    			// sum(abs(sim(u,v)))
	    			double denominator = 0;

	    			for (Long neighbourhood : neighbourhoods.keySet()) {
	    				if (ratings.get(neighbourhood).containsKey(movieId)) {
	    					double matchRate = neighbourhoods.get(neighbourhood);
	    					numerator += matchRate * (ratings.get(neighbourhood).get(movieId) - averageRating.get(neighbourhood));
	    					denominator += Math.abs(matchRate);
	    				}
	    				
	    			}

	    			double predictedRating = 0;
	    			if (denominator > 0) {
	    				predictedRating = userAverage + numerator / denominator;
	    				if (predictedRating > 5) {
	    					predictedRating = 5;
	    				}
	    			}
	    			predictedRatings.put(movieId, predictedRating);
	    		}
	    	}
	    	return predictedRatings;
	    }
	    
	    
	    public String recommendedMovies(Long userId, UserRepository userRepository, MovieRepository movieRepository) {

	        Map<Long, Double> averageRating = new HashMap<>();
	        Map<Long, Map<Long, Integer>> myRatesMap = new TreeMap<>();
	        Map<Long, Map<Long, Integer>> userWithRatesMap = new TreeMap<>();

	        userRepository.findAll().forEach(userItem -> {
	            Long userID = userItem.getUserId();
	            Map<Long, Integer> userRatings = new HashMap<>();
	            
	            userItem.getUserMovieRating().forEach(userMovieRating -> {
	                        if (userMovieRating.getId().getUserId().compareTo(userID) == 0) {
	                            userRatings.put(userMovieRating.getId().getMovieId(), userMovieRating.getRate());
	                        }
	                    }
	            );

	            if (userId.compareTo(userID) == 0) {
	                myRatesMap.put(userID, userRatings);
	            } else {
	                userWithRatesMap.put(userID, userRatings);

	                setRatings(userWithRatesMap);
	                averageRating.put(userID, 0.0);

	                for (Map.Entry<Long, Integer> longIntegerEntry : userRatings.entrySet()) {

	                    if (ratings.containsKey(userID)) {
	                        ratings.get(userID).put(longIntegerEntry.getKey(), longIntegerEntry.getValue());
	                        averageRating.put(userID, averageRating.get(userID) + (double) longIntegerEntry.getValue());
	                    } else {
	                        Map<Long, Integer> movieRating = new HashMap<>();
	                        movieRating.put(longIntegerEntry.getKey(), longIntegerEntry.getValue());
	                        ratings.put(userID, movieRating);
	                        averageRating.put(userID, (double) longIntegerEntry.getValue());
	                    }
	                }
	            }
	        });

	        for (Map.Entry<Long, Double> longDoubleEntry : averageRating.entrySet()) {
	            if (ratings.containsKey(longDoubleEntry.getKey())) {
	                longDoubleEntry.setValue(longDoubleEntry.getValue() / (double) ratings.get(longDoubleEntry.getKey()).size());
	            }
	        }

	        setAverageRating(averageRating);

	        Map<Long, String> movies = new HashMap<>();

	        movieRepository.findAll().forEach(movie -> movies.put(movie.getMovieId(), movie.getTitle()));

	        Map<Long, Double> neighbourhoods = getNeighbourhoods(myRatesMap.get(userId));
	        Map<Long, Double> recommendations = getRecommendations(myRatesMap.get(userId), neighbourhoods, movies);

	        System.out.println(">>>>>>>>>>>>>>>> neighbourhoods size and to string >>>>>>>>>>>>>>" + neighbourhoods.size() + "   " + neighbourhoods.toString());
	        System.out.println(">>>>>>>>>>>>>>>> recommendations size and to string >>>>>>>>>>>>>>" + recommendations.size() + "   " + recommendations.toString());
	        
	        ValueComparator valueComparator = new ValueComparator(recommendations);
	        Map<Long, Double> sortedRecommendations = new TreeMap<>(valueComparator);
	        sortedRecommendations.putAll(recommendations);

	        Iterator<Map.Entry<Long, Double>> sortedREntries = sortedRecommendations.entrySet().iterator();
	        JSONArray recommendedMoviesArray = new JSONArray();

	        int i = 0;
	        while (sortedREntries.hasNext() && i < NUM_RECOMMENDATIONS) {
	            Map.Entry<Long, Double> entry = sortedREntries.next();
	            if (entry.getValue() >= MIN_VALUE_RECOMMENDATION) {
	                JSONObject recommendedMovies = new JSONObject("{}");
	                recommendedMovies.put("Title", movies.get(entry.getKey()));
	                recommendedMovies.put("Rate", Utils.round(entry.getValue(), 1));
	                recommendedMoviesArray.put(recommendedMovies);
	                i++;
	            }
	        }
	        return recommendedMoviesArray.toString();
	    } 

}
