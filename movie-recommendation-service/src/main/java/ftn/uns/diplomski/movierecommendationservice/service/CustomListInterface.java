package ftn.uns.diplomski.movierecommendationservice.service;

import ftn.uns.diplomski.movierecommendationservice.dto.CustomListDTO;

import java.util.List;

public interface CustomListInterface {

    List<CustomListDTO> getCustomListWithPrincipal(String username) throws Exception;

}
