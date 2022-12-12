package ftn.uns.diplomski.movierecommendationservice.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

	private String username; 
	
	@NotBlank
	private String password; 
}
