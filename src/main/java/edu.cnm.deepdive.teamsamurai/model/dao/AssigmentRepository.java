package edu.cnm.deepdive.teamsamurai.model.dao;

import edu.cnm.deepdive.teamsamurai.model.entity.Assigment;
import edu.cnm.deepdive.teamsamurai.model.entity.User;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * Declares database operations that can be performed on {@link Assigment} entity instances.
 */
public interface AssigmentRepository extends CrudRepository<Assigment, UUID> {

  /**
   * Selects and returns all {@link Assigment} instances, sorted in alphabetical order.
   *
   * @return {@link Iterable} sequence of {@link Assigment} instances.
   */
  Iterable<Assigment> findAllByOrderByNameAsc();

  /**
   * Selects and returns all {@link Assigment} instances containing the specified text fragment, in
   * alphabetical order.
   *
   * @param fragment filter text content.
   * @return {@link Iterable} sequence of {@link Assigment} instances.
   */
  Iterable<Assigment> findAllByNameContainingOrderByNameAsc(String fragment);

  Iterable<Assigment> findAllByTeacherOrderByCreatedDesc(User teacher);
}
