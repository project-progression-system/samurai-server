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

import edu.cnm.deepdive.teamsamurai.model.dao.CompleteRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.UserRepository;
import edu.cnm.deepdive.teamsamurai.model.entity.Complete;
import edu.cnm.deepdive.teamsamurai.model.entity.User;
import java.util.UUID;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines REST endpoints for servicing requests on {@link User} resources, invoking {@link
 * UserRepository} methods to perform the required operations.
 */
//TODO Restrict end points based on security
@RestController
@ExposesResourceFor(User.class)
@RequestMapping("/users")
public class UserController {

  private UserRepository userRepository;

  /**
   * Initializes this instance, injecting an instance of {@link UserRepository}
   *
   * @param userRepository repository used for operations on {@link User} entity instances.
   */
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Returns a sequence of all the {@link User} resources in the database.
   *
   * @return sequence of {@link User} resources.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> get() {
    return userRepository.findAllByOrderByNameAsc();
  }

  /**
   * Adds the provided {@link User} resource to the database and returns the completed resource,
   * including timestamp &amp; ID. The provided resource is only required to contain a
   * <code>text</code> property, with a non-<code>null</code> value.
   *
   * @param user partial {@link User} resource.
   * @return completed {@link User} resource.
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> post(@RequestBody User user) {
    userRepository.save(user);
    return ResponseEntity.created(user.getHref()).body(user);
  }

  /**
   * Retrieves and returns the {@link User} resource with the specified ID.
   *
   * @param id quote {@link UUID}.
   * @return retrieved {@link User} resource.
   */
  @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable("userId") UUID id) {
    return userRepository.findById(id).get();
  }

  /**
   * Deletes the specified {@link User} resource from the database.
   *
   * @param id user {@link UUID}.
   */
  @DeleteMapping(value = "{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("userId") UUID id) {
    userRepository.delete(get(id));
  }

}
