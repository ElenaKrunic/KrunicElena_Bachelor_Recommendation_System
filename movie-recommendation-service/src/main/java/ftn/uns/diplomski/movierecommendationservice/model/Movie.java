package ftn.uns.diplomski.movierecommendationservice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="movies")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

	@Id
	@Column(name = "movie_id", unique = true, nullable = false)
	private long movieId; 
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name="genre", nullable = false)
	private String genre;
	
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserMovieRating> userMovieRatings; 
    
    public Movie(long movieId, String title, String genre) {
    	this.movieId = movieId; 
    	this.title = title; 
    	this.genre = genre; 
    }
    
    public Movie(String title, String genre) { 
    	this.title = title; 
    	this.genre = genre; 
    }
    
    
	
}
