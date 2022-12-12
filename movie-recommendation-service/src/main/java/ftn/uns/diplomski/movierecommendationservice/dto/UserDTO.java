package ftn.uns.diplomski.movierecommendationservice.dto;

import java.util.Date;

import ftn.uns.diplomski.movierecommendationservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	 private Long id;
	 private String firstname;
	 private String lastname;
	 private String username;
	 private String password;
	 private String email;
	 private Date createdAt; 
	 private Date updatedAt; 
	 private Integer numberOfMovieRates; 
	 private Integer watchlistId;
	 private Integer numberOfAddedMovies; 
	 private Integer numberOfCustomLists; 

	 public UserDTO(User user) {
		 this.id = user.getUserId();
		 this.firstname = user.getFirstName(); 
		 this.lastname = user.getLastName(); 
		 this.username = user.getUsername(); 
		 this.password = user.getPassword();
		 this.email = user.getEmail();
		 this.createdAt = user.getCreatedAt();
		 this.updatedAt = user.getUpdatedAt();
		 this.numberOfAddedMovies = null;
		 this.watchlistId = null;
		 this.numberOfAddedMovies = null; 
		 this.numberOfCustomLists = null;
	 }

}
