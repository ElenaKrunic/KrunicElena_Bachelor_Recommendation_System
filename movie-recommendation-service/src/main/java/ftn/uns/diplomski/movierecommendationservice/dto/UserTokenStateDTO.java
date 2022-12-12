package ftn.uns.diplomski.movierecommendationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTokenStateDTO {
	
	private String accessToken;
	private Long expiresIn;
	
	 public UserTokenStateDTO(String accessToken, long expiresIn) {
	        this.accessToken = accessToken;
	        this.expiresIn = expiresIn;
	 }

}
