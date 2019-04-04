package edu.cnm.deepdive.teamsamurai.model.dao;

import edu.cnm.deepdive.teamsamurai.model.entity.AssigmentName;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * Declares database operations that can be performed on {@link AssigmentName} entity instances.
 */
public interface AssigmentNameRepository extends CrudRepository<AssigmentName, UUID> {

  /**
   * Selects and returns all {@link AssigmentName} instances, sorted in alphabetical order.
   *
   * @return {@link Iterable} sequence of {@link AssigmentName} instances.
   */
  Iterable<AssigmentName> findAllByOrderByNameAsc();

  /**
   * Selects and returns all {@link AssigmentName} instances containing the specified text fragment, in
   * alphabetical order.
   *
   * @param fragment filter text content.
   * @return {@link Iterable} sequence of {@link AssigmentName} instances.
   */
  Iterable<AssigmentName> findAllByNameContainingOrderByNameAsc(String fragment);

}
