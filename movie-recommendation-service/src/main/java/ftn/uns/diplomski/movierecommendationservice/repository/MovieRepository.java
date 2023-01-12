package ftn.uns.diplomski.movierecommendationservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.dto.BasicMovieInfoDTO;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.model.Watchlist;
import ftn.uns.diplomski.movierecommendationservice.resource.MovieResource;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	List<BasicMovieInfoDTO> findMoviesByWatchlistsWatchlistId(Long watchlistId);
	
    List<Movie> findMoviesByCustomListsCustomListId(Long customlistId);

	List<BasicMovieInfoDTO> findMoviesByWatchlists(Watchlist watchlist);

	List<Movie> findByUser(User user);
	
	Page<Movie> findByTitleContaining(String title, Pageable pageable);
	
	

}
