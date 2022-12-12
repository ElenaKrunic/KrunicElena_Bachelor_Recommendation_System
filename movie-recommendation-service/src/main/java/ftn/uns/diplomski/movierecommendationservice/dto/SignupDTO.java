package ftn.uns.diplomski.movierecommendationservice.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {

	private String firstName;
	private String lastName;

	//@Size(min = 3, max = 20)
	private String username; 

	//@Size(max = 50)
	@Email
	private String email;
	
	private Set<String> roles; 

	//@Size(min = 6, max = 40)
	private String password;

}
