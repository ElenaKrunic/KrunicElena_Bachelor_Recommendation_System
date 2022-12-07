package ftn.uns.diplomski.movierecommendationservice.service;

import ftn.uns.diplomski.movierecommendationservice.dto.MovieDTO;

public interface MovieInteface {

	public MovieDTO getMovieByTitleFromApi(String title);
	public MovieDTO getMovieByIdFromApi(String id);

}
