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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "customList")
@AllArgsConstructor
@NoArgsConstructor
public class CustomList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customList_id", unique = true, nullable = false)
	private Long customListId; 
	
	@Column(name = "name")
	private String name; 
	
	@Column(name = "makeItPublic")
	private boolean makeItPublic;
	
	@Column(name = "description")
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {
				CascadeType.PERSIST,
				CascadeType.MERGE
			})
	@JoinTable(name = "customList_movies",
			joinColumns = {@JoinColumn (name = "customList_id")},
			inverseJoinColumns =  {@JoinColumn(name = "movie_id")})
	@JsonIgnore
	private Set<Movie> movies = new HashSet<>();

	@JsonIgnore
	@ManyToOne
	private User user;
		
	public void addMovie(Movie movie) {
		this.movies.add(movie);
		movie.getCustomLists().add(this);
	}

	public void removeMovie(Long movieId) {
		Movie movie = this.movies.stream().filter(m -> m.getMovieId() == movieId).findFirst().orElse(null);
		if(movie != null) {
			this.movies.remove(movie);
			movie.getCustomLists().remove(this);
		}
	}
	
}
