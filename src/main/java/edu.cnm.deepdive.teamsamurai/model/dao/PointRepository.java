
package edu.cnm.deepdive.teamsamurai.model.dao;


import edu.cnm.deepdive.teamsamurai.model.entity.Point;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Declares database operations that can be performed on {@link Point} entity instances.
 */
public interface PointRepository extends CrudRepository<Point, UUID> {

  /**
   * Selects and returns all {@link Point} instances, sorted in alphabetical order.
   *
   * @return {@link Iterable} sequence of {@link Point} instances.
   */
  Iterable<Point> findAllByOrderByTextAsc();

  /**
   * Selects and returns all {@link Point} instances containing the specified text fragment, in
   * alphabetical order.
   *
   * @param fragment filter text content.
   * @return {@link Iterable} sequence of {@link Point} instances.
   */
  Iterable<Point> findAllByTextContainingOrderByTextAsc(String fragment);

  /**
   * Selects and returns a randomly selected {@link Point} instance. Note that this is currently
   * implemented with Derby-specific SQL, since there is not a JPQL-standard way of sorting on a
   * random value (i.e. shuffling), nor of limiting the number of rows returned.
   *
   * @return random {@link Point} instance.
   */
  @Query(value = "SELECT * FROM sa.quote ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY",
      nativeQuery = true)
  Optional<Point> findRandom();

}
