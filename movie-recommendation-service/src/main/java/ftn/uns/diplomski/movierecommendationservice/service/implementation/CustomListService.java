package ftn.uns.diplomski.movierecommendationservice.service.implementation;

import ftn.uns.diplomski.movierecommendationservice.dto.CustomListDTO;
import ftn.uns.diplomski.movierecommendationservice.model.CustomList;
import ftn.uns.diplomski.movierecommendationservice.model.User;
import ftn.uns.diplomski.movierecommendationservice.repository.CustomListRepository;
import ftn.uns.diplomski.movierecommendationservice.repository.UserRepository;
import ftn.uns.diplomski.movierecommendationservice.service.CustomListInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomListService implements CustomListInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomListRepository customListRepository;

    @Override
    public List<CustomListDTO> getCustomListWithPrincipal(String username) throws Exception {
        User user = userRepository.findOneByUsername(username);

        if(user == null) {
            throw new Exception("User does not exist!");
        }

        List<CustomListDTO> customListDtos = new ArrayList<>();
        List<CustomList> customLists = customListRepository.findAllByUser(user);

        for(CustomList customList : customLists) {
            CustomListDTO customListDto = new CustomListDTO();

            customListDto.setCustomListId(customList.getCustomListId());
            customListDto.setDescription(customList.getDescription());
            customListDto.setMakeItPublic(customList.isMakeItPublic());
            customListDto.setName(customList.getName());

            customListDtos.add(customListDto);

        }

        return customListDtos;
    }
}
