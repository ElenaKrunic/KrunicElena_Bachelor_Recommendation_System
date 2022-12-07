package ftn.uns.diplomski.movierecommendationservice.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_movie_rate", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "movie_id"})})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMovieRating {

	@EmbeddedId
	private UserMovieRatingKey id; 
	
	@ManyToOne
	@MapsId("user_id")
	@JoinColumn(name = "user_id")
	private User user; 
	
	@ManyToOne
	@MapsId("movie_id")
	@JoinColumn(name = "movie_id")
	private Movie movie; 
	
	@Column(name = "rate")
	private int rate;
	
	public UserMovieRating(User user, Movie movie) {
		this.user = user; 
		this.movie = movie;
	}
}
