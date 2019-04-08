/*
 *  Copyright 2019 Lance Zotigh, Alex Rauenzahn, Thomas Herrera & Deep Dive Coding
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cnm.deepdive.teamsamurai.model.dao;

import edu.cnm.deepdive.teamsamurai.model.entity.Assignment;
import edu.cnm.deepdive.teamsamurai.model.entity.User;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * Declares database operations that can be performed on {@link Assignment} entity instances.
 */
public interface AssigmentRepository extends CrudRepository<Assignment, UUID> {

  /**
   * Selects and returns all {@link Assignment} instances, sorted in alphabetical order.
   *
   * @return {@link Iterable} sequence of {@link Assignment} instances.
   */
  Iterable<Assignment> findAllByOrderByNameAsc();

  /**
   * Selects and returns all {@link Assignment} instances containing the specified text fragment, in
   * alphabetical order.
   *
   * @param fragment filter text content.
   * @return {@link Iterable} sequence of {@link Assignment} instances.
   */
  Iterable<Assignment> findAllByNameContainingOrderByNameAsc(String fragment);

  /**
   * Selects and returns all {@link Assignment} instances for a given {@link User} to an assigned teacher, sorted in Selects and returns all {@link Assignment} instances, sorted in alphabetical order. order.
   *
   * @param teacher determins user signature
   * @return {@link Iterable} sequence of {@link Assignment} instances.
   */
  Iterable<Assignment> findAllByTeacherOrderByCreatedDesc(User teacher);
}
