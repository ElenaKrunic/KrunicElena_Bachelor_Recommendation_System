package ftn.uns.diplomski.movierecommendationservice.resource;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResource {
	
	public MovieResource(String string) {
		// TODO Auto-generated constructor stub
	}
	private long movieId; 
	private String title; 
	private String genre; 

}
