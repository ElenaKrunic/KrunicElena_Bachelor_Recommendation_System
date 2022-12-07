package ftn.uns.diplomski.movierecommendationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.diplomski.movierecommendationservice.model.Book;

/* The interface Book repository.
 *
 * @author Sefa Oduncuoglu
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}