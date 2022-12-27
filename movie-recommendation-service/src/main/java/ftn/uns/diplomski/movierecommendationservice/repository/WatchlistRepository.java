package ftn.uns.diplomski.movierecommendationservice.repository;

import java.util.List;

import ftn.uns.diplomski.movierecommendationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.Watchlist;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

	Iterable<Watchlist> findByWatchlistIdContaining(Long id);

	List<Watchlist> findByMakeItPublic(boolean b);

    List<Watchlist> findByUser(User user);

	Watchlist findOneWatchlistByUser(User user);

	Watchlist findWatchlistByUser(User user);

}
