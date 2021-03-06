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
import edu.cnm.deepdive.teamsamurai.model.entity.Complete;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * Declares database operations that can be performed on {@link Complete} entity instances.
 */
public interface CompleteRepository extends CrudRepository<Complete, UUID> {

  /**
   * Selects and returns all {@link Complete} instances containing the specified student fragment, in
   * alphabetical order.
   *
   * @param student fragment filter text content.
   * @return all students.
   */
  Iterable<Complete> findAllByStudentOrderByCreatedDesc(String student);

}
