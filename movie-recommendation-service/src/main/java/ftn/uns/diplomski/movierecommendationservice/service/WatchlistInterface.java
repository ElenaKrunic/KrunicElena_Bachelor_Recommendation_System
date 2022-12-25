package ftn.uns.diplomski.movierecommendationservice.service;

import ftn.uns.diplomski.movierecommendationservice.dto.WatchlistDTO;

import java.util.List;

public interface WatchlistInterface {

    List<WatchlistDTO> getWatchlistWithPrincipal(String username) throws Exception;

}
