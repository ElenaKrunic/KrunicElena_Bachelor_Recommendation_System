package ftn.uns.diplomski.movierecommendationservice.dto;

import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

	private String Title;
	private String Plot;
	private Integer Year; 
	private String Runtime; 
	private String Genre; 
	private String Director; 
	private String Writer;
	private String Actors; 
	private String Language; 
	private String Country; 
	private String Awards; 
	private String Poster; 
	
	public MovieDTO(Movie movie) {
		this.Title = movie.getTitle();
		this.Plot = movie.getPlot();
		this.Year = movie.getYear();
		this.Runtime = movie.getRuntime();
		this.Genre = movie.getGenre();
		this.Director = movie.getDirector();
		this.Writer = movie.getWriter();
		this.Actors = movie.getActors();
		this.Language = movie.getLanguage();
		this.Country = movie.getCountry();
		this.Awards = movie.getAwards();
		this.Poster = movie.getPoster();
	}
	
}
