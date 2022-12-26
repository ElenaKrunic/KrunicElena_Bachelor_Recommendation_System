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
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "movie_id", unique = true, nullable = false)
	private long movieId; 
	
	@Column(name = "title")
	private String title;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name="plot")
	private String plot; 
	
	@Column(name="year")
	private Integer year;
	
	@Column(name="runtime")
	private String runtime;
	
	@Column(name="director")
	private String director;
	
	@Column(name="writer")
	private String writer;
	
	@Column(name="actors")
	private String actors;
	
	@Column(name="language")
	private String language;
	
	@Column(name="country")
	private String country;
	
	@Column(name="awards")
	private String awards;
	
	@Column(name="poster")
	private String poster; 
	
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
    
    public Movie(String title, String genre, String plot, String runtime,
    		String director, String writer, String actors, String language, String country,
    		String awards, String poster, Integer year) { 
    	this.title = title; 
    	this.genre = genre; 
    	this.plot = plot;
    	this.year = year;
    	this.runtime = runtime;
    	this.director = director;
    	this.writer = writer;
    	this.actors = actors;
    	this.language = language;
    	this.country = country;
    	this.awards = awards;
    	this.poster = poster;
    	
    }
}
