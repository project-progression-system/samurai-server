
package edu.cnm.deepdive.teamsamurai.model.dao;


import edu.cnm.deepdive.teamsamurai.model.entity.Points;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Declares database operations that can be performed on {@link Points} entity instances.
 */
public interface PointsRepository extends CrudRepository<Points, UUID> {

  /**
   * Selects and returns all {@link Points} instances, sorted in alphabetical order.
   *
   * @return {@link Iterable} sequence of {@link Points} instances.
   */
  Iterable<Points> findAllByOrderByTextAsc();

  /**
   * Selects and returns all {@link Points} instances containing the specified text fragment, in
   * alphabetical order.
   *
   * @param fragment filter text content.
   * @return {@link Iterable} sequence of {@link Points} instances.
   */
  Iterable<Points> findAllByTextContainingOrderByTextAsc(String fragment);

  /**
   * Selects and returns a randomly selected {@link Points} instance. Note that this is currently
   * implemented with Derby-specific SQL, since there is not a JPQL-standard way of sorting on a
   * random value (i.e. shuffling), nor of limiting the number of rows returned.
   *
   * @return random {@link Points} instance.
   */
  @Query(value = "SELECT * FROM sa.quote ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY",
      nativeQuery = true)
  Optional<Points> findRandom();

}
