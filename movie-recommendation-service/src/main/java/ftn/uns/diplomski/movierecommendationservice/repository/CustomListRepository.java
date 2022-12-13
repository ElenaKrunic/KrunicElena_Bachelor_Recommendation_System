package ftn.uns.diplomski.movierecommendationservice.repository;

import ftn.uns.diplomski.movierecommendationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.CustomList;

import java.util.List;

@Repository
public interface CustomListRepository extends JpaRepository<CustomList, Long> {

    Iterable<Object> findByNameContaining(String name);

    List<CustomList> findAllByUser(User user);
}
