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
package edu.cnm.deepdive.teamsamurai.controller;

import edu.cnm.deepdive.teamsamurai.model.dao.AssigmentRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.CompleteRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.UserRepository;
import edu.cnm.deepdive.teamsamurai.model.entity.Assignment;
import edu.cnm.deepdive.teamsamurai.model.entity.Complete;
import edu.cnm.deepdive.teamsamurai.model.entity.User;
import edu.cnm.deepdive.teamsamurai.model.entity.User.Type;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines REST endpoints for servicing requests on {@link Complete} resources, invoking {@link
 * CompleteRepository} methods to perform the required operations.
 */
@RestController
@ExposesResourceFor(Complete.class)
@RequestMapping("/completions")
public class CompleteController {

  private CompleteRepository completeRepository;
  private AssigmentRepository assignmentRepository;
  private UserRepository userRepository;

  /**
   * Initializes this instance, injecting an instance of {@link CompleteRepository} {@link AssigmentRepository}
   * as well as {@link UserRepository}
   *
   * @param completeRepository repository used for operations on {@link Complete} entity instances.
   * @param assignmentRepository repository used for operations on {@link Assignment} entity instances.
   * @param userRepository repository used for operations on {@link User} entity instances.
   */
  @Autowired
  public CompleteController(CompleteRepository completeRepository,
      AssigmentRepository assignmentRepository, UserRepository userRepository) {
    this.completeRepository = completeRepository;
    this.assignmentRepository = assignmentRepository;
    this.userRepository = userRepository;
  }

  /**
   * Returns a sequence of all the {@link Complete} resources in the database.
   *
   * @return sequence of {@link Complete} resources.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Complete> get() {
    return completeRepository.findAll();
  }

  /**
   * Adds the provided {@link Complete} resource to the database and returns the completed resource,
   * including timestamp &amp; ID. The provided resource is only required to contain a
   * <code>name</code> property, with a non-<code>null</code> value.
   *
   * @param complete partial {@link Complete} resource.
   * @return completed {@link Complete} resource.
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<Complete> post(@RequestBody Complete complete) {
    User student = userRepository.findFirstByIdAndType(complete.getStudent().getId(), Type.STUDENT).get();
    Assignment assignment = assignmentRepository.findById(complete.getAssignment().getId()).get();
    complete.setAssignment(assignment);
    complete.setStudent(student);
    completeRepository.save(complete);
    return ResponseEntity.created(complete.getHref()).body(complete);
  }

  /**
   * Associates the {@link Assignment} referenced in the path with the {@link Complete}, also referenced by
   * a path parameter.
   *
   * @param completeId {@link UUID} of {@link Complete} resource.
   * @param assignmentId {@link UUID} of {@link Assignment} to be associated with referenced {@link Complete}.
   * @return updated {@link Complete} resource.
   */
  @PutMapping(value = "{completeId}/assignment/{assignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Complete attach(@PathVariable("completeId") UUID completeId, @PathVariable("assignmentId") UUID assignmentId) {
    Assignment assignment = assignmentRepository.findById(assignmentId).get();
    Complete complete = completeRepository.findById(completeId).get();
    complete.setAssignment(assignment);
    completeRepository.save(complete);
    return complete;
  }


  /**
   * Maps (via annotation) a {@link NoSuchElementException} to a response status code of {@link
   * HttpStatus#NOT_FOUND}.
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}
}
