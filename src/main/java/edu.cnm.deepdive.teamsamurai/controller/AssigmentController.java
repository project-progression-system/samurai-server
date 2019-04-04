/*
 *  Copyright 2019 Nicholas Bennett & Deep Dive Coding
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


import edu.cnm.deepdive.teamsamurai.model.dao.PointRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.AssigmentRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.UserRepository;
import edu.cnm.deepdive.teamsamurai.model.entity.Point;
import edu.cnm.deepdive.teamsamurai.model.entity.Assigment;
import edu.cnm.deepdive.teamsamurai.model.entity.User;
import edu.cnm.deepdive.teamsamurai.model.entity.User.Type;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines REST endpoints for servicing requests on {@link Assigment} resources, invoking {@link
 * AssigmentRepository} methods to perform the required operations.
 */
@RestController
@ExposesResourceFor(Assigment.class)
@RequestMapping("/assignments")
public class AssigmentController {

  private AssigmentRepository mAssigmentRepository;
  private UserRepository userRepository;

  /**
   * Initializes this instance, injecting an instance of {@link AssigmentRepository} and an instance of
   * {@link PointRepository}.
   *
   * @param assigmentRepository repository used for operations on {@link Assigment} entity instances.
   */
  @Autowired
  public AssigmentController(AssigmentRepository assigmentRepository, UserRepository userRepository) {
    this.mAssigmentRepository = assigmentRepository;
    this.userRepository = userRepository;
  }

  /**
   * Returns a sequence of {@link Assigment} resources, containing the specified text.
   *
   * @param fragment text to match (case-insensitive).
   * @return sequence of {@link Assigment} resources.
   */
  @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Assigment> search(@RequestParam("q") String fragment) {
    return mAssigmentRepository.findAllByNameContainingOrderByNameAsc(fragment);
  }

  /*@GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Assigment> getByTeacher(@RequestParam("teacher") UUID teacherId) {
    User teacher = userRepository.findFirstByIdAndType(teacherId, Type.TEACHER).get();
    return mAssigmentRepository.findAllByTeacherOrderByCreatedDesc(teacher);
  }*/

  /**
   * Returns a sequence of all the {@link Assigment} resources in the database.
   *
   * @return sequence of {@link Assigment} resources.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Assigment> get() {
    return mAssigmentRepository.findAllByOrderByNameAsc();
  }

  /**
   * Adds the provided {@link Assigment} resource to the database and returns the completed resource,
   * including timestamp &amp; ID. The provided resource is only required to contain a
   * <code>name</code> property, with a non-<code>null</code> value.
   *
   * @param assignment partial {@link Assigment} resource.
   * @return completed {@link Assigment} resource.
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<Assigment> post(@RequestBody Assigment assignment) {
    User teacher = userRepository.findFirstByIdAndType(assignment.getTeacher().getId(), Type.TEACHER).get(); // FIXME replace when authentication is in place
    assignment.setTeacher(teacher);
    mAssigmentRepository.save(assignment);
    return ResponseEntity.created(assignment.getHref()).body(assignment);
  }

  /**
   * Retrieves and returns the {@link Assigment} resource with the specified ID.
   *
   * @param assignmentId source {@link UUID}.
   * @return retrieved {@link Assigment} resource.
   */
  @GetMapping(value = "{assignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Assigment get(@PathVariable("assignmentId") UUID assignmentId) {
    return mAssigmentRepository.findById(assignmentId).get();
  }

  /**
   * Deletes the specified {@link Assigment} resource from the database.
   *
   * @param assignmentId source {@link UUID}.
   */
  @DeleteMapping(value = "{assignmentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("assignmentId") UUID assignmentId) {
    Assigment assignment = get(assignmentId);
    mAssigmentRepository.delete(assignment);//TODO Implement security on this
  }

  /**
   * Maps (via annotation) a {@link NoSuchElementException} to a response status code of {@link
   * HttpStatus#NOT_FOUND}.
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}
  
}
