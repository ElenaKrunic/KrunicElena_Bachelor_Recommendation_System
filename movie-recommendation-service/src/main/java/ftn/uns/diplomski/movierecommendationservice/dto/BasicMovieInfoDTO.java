package ftn.uns.diplomski.movierecommendationservice.dto;

import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasicMovieInfoDTO {

	private long movieId; 
	private String title; 
	private String genre;
	
	public BasicMovieInfoDTO(Movie m) {
		this.movieId = m.getMovieId();
		this.title = m.getTitle();
		this.genre = m.getGenre();
	}
}
