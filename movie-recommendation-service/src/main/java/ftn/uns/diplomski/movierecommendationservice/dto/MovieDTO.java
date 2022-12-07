package ftn.uns.diplomski.movierecommendationservice.dto;

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
	private int Year; 
	private String Runtime; 
	private String Genre; 
	private String Director; 
	private String Writer;
	private String Actors; 
	private String Language; 
	private String Country; 
	private String Awards; 
	private String Poster; 
	
}
