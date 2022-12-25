package ftn.uns.diplomski.movierecommendationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.dto.BasicMovieInfoDTO;
import ftn.uns.diplomski.movierecommendationservice.model.Movie;
import ftn.uns.diplomski.movierecommendationservice.model.Watchlist;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	List<BasicMovieInfoDTO> findMoviesByWatchlistsWatchlistId(Long watchlistId);
	
    List<Movie> findMoviesByCustomListsCustomListId(Long customlistId);

	List<BasicMovieInfoDTO> findMoviesByWatchlists(Watchlist watchlist);

}
