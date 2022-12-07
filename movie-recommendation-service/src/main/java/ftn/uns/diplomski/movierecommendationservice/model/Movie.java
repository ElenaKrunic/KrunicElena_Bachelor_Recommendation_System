package ftn.uns.diplomski.movierecommendationservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
				CascadeType.PERSIST,
				CascadeType.MERGE
			},
			mappedBy = "movies")
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<CustomList> customLists = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "movies")
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Watchlist> watchlists = new HashSet<>();
    
	@JsonIgnore
	@ManyToOne
	private User user;
	
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
