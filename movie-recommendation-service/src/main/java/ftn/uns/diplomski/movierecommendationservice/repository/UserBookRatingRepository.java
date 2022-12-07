package ftn.uns.diplomski.movierecommendationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.UserBookRating;


/* The interface User Book Rate repository.
 *
 * @author Sefa Oduncuoglu
 */
@Repository
public interface UserBookRatingRepository extends JpaRepository<UserBookRating, Long> {
}