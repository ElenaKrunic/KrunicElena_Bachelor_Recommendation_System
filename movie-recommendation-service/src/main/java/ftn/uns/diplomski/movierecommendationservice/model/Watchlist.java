package ftn.uns.diplomski.movierecommendationservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="watchlists")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Watchlist {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchlist_id", unique = true, nullable = false)
	private Long watchlistId;

	@Column(name = "makeItPublic")
	private boolean makeItPublic; 
	
	@Column(name = "comment")
	private String comment;
	
	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "watchlist_movies",
			joinColumns = {@JoinColumn (name = "watchlist_id")},
			inverseJoinColumns = {@JoinColumn(name = "movie_id")})
	@JsonIgnore
	private Set<Movie> movies = new HashSet<>(); 
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user; 
	
	public void addMovie(Movie movie) {
		this.movies.add(movie);
		movie.getWatchlists().add(this);
	}
	
	public void removeMovie(Long movieId) {
		Movie movie = this.movies.stream().filter(m -> m.getMovieId() == movieId).findFirst().orElse(null);
		if(movie != null) {
			this.movies.remove(movie);
			movie.getWatchlists().remove(this);
		}
	}
}
