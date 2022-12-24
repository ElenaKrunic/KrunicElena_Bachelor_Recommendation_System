package ftn.uns.diplomski.movierecommendationservice.service.implementation;

import ftn.uns.diplomski.movierecommendationservice.dto.CustomListDTO;
import ftn.uns.diplomski.movierecommendationservice.dto.WatchlistDTO;
import ftn.uns.diplomski.movierecommendationservice.model.CustomList;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.model.Watchlist;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.WatchlistRepository;
import ftn.uns.diplomski.movierecommendationservice.service.WatchlistInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchlistService implements WatchlistInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WatchlistRepository watchlistRepository;

    @Override
    public List<WatchlistDTO> getWatchlistWithPrincipal(String username) throws Exception {
        User user = userRepository.findOneByUsername(username);

        if(user == null) {
            throw new Exception("User does not exist!");
        }

        List<WatchlistDTO> watchlistDtos = new ArrayList<>();
        List<Watchlist> watchlists = watchlistRepository.findByUser(user);

        for(Watchlist watchlist : watchlists) {
            WatchlistDTO watchlistDto = new WatchlistDTO();

            watchlistDto.setComment(watchlist.getComment());
            watchlistDto.setMakeItPublic(watchlist.isMakeItPublic());
            watchlistDto.setMovies(watchlist.getMovies());

            watchlistDtos.add(watchlistDto);

        }

        return watchlistDtos;
    }

	public Watchlist getOneWatchlistWithPrincipal(String name) throws Exception {
		User user = userRepository.findOneByUsername(name);
		
		if(user == null) {
			throw new Exception("User does not exist!");
		}
		
		Watchlist watchlist = watchlistRepository.findOneWatchlistByUser(user);
		
		return watchlist;
	}
}
