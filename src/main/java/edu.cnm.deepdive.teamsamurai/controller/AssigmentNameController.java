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


import edu.cnm.deepdive.teamsamurai.model.dao.PointsRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.AssigmentNameRepository;
import edu.cnm.deepdive.teamsamurai.model.entity.Points;
import edu.cnm.deepdive.teamsamurai.model.entity.AssigmentName;
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
 * Defines REST endpoints for servicing requests on {@link AssigmentName} resources, invoking {@link
 * AssigmentNameRepository} methods to perform the required operations.
 */
@RestController
@ExposesResourceFor(AssigmentName.class)
@RequestMapping("/sources")
public class AssigmentNameController {

  private AssigmentNameRepository mAssigmentNameRepository;
  private PointsRepository mPointsRepository;

  /**
   * Initializes this instance, injecting an instance of {@link AssigmentNameRepository} and an instance of
   * {@link PointsRepository}.
   *
   * @param assigmentNameRepository repository used for operations on {@link AssigmentName} entity instances.
   * @param pointsRepository repository used for operations on {@link Points} entity instances.
   */
  @Autowired
  public AssigmentNameController(AssigmentNameRepository assigmentNameRepository, PointsRepository pointsRepository) {
    this.mAssigmentNameRepository = assigmentNameRepository;
    this.mPointsRepository = pointsRepository;
  }

  /**
   * Returns a sequence of {@link AssigmentName} resources, containing the specified text.
   *
   * @param fragment text to match (case-insensitive).
   * @return sequence of {@link AssigmentName} resources.
   */
  @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<AssigmentName> search(@RequestParam("q") String fragment) {
    return mAssigmentNameRepository.findAllByNameContainingOrderByNameAsc(fragment);
  }

  /**
   * Returns a sequence of all the {@link AssigmentName} resources in the database.
   *
   * @return sequence of {@link AssigmentName} resources.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<AssigmentName> get() {
    return mAssigmentNameRepository.findAllByOrderByNameAsc();
  }

  /**
   * Adds the provided {@link AssigmentName} resource to the database and returns the completed resource,
   * including timestamp &amp; ID. The provided resource is only required to contain a
   * <code>name</code> property, with a non-<code>null</code> value.
   *
   * @param source partial {@link AssigmentName} resource.
   * @return completed {@link AssigmentName} resource.
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<AssigmentName> post(@RequestBody AssigmentName source) {
    mAssigmentNameRepository.save(source);
    return ResponseEntity.created(source.getHref()).body(source);
  }

  /**
   * Retrieves and returns the {@link AssigmentName} resource with the specified ID.
   *
   * @param sourceId source {@link UUID}.
   * @return retrieved {@link AssigmentName} resource.
   */
  @GetMapping(value = "{sourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public AssigmentName get(@PathVariable("sourceId") UUID sourceId) {
    return mAssigmentNameRepository.findById(sourceId).get();
  }

  /**
   * Replaces the name (which is the only consumer-specifiable property) of the referenced existing
   * {@link AssigmentName} resource with the name from the provided resource.
   *
   * @param sourceId source {@link UUID}.
   * @param update {@link AssigmentName} resource to use to replace contents of existing source.
   */
  @PutMapping(value = "{sourceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void put(@PathVariable("sourceId") UUID sourceId, @RequestBody AssigmentName update) {
    AssigmentName source = mAssigmentNameRepository.findById(sourceId).get();
    source.setName(update.getName());
    mAssigmentNameRepository.save(source);
  }

  /**
   * Deletes the specified {@link AssigmentName} resource from the database.
   *
   * @param sourceId source {@link UUID}.
   */
  @Transactional
  @DeleteMapping(value = "{sourceId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("sourceId") UUID sourceId) {
    AssigmentName source = get(sourceId);
    Set<Points> quotes = source.getQuotes();
    for (Points quote : quotes) {
      quote.getSources().remove(source);
    }
    mPointsRepository.saveAll(quotes);
    mAssigmentNameRepository.delete(source);
  }

  /**
   * Associates the {@link Points} specified in the request body with the {@link AssigmentName} referenced
   * by the path parameter. Only the <code>id</code> property of the {@link Points} must be
   * specified.
   *
   * @param sourceId {@link UUID} of {@link AssigmentName} resource.
   * @param quote {@link Points} to be associated with referenced {@link AssigmentName}.
   * @return updated {@link AssigmentName} resource.
   */
  @PostMapping(value = "{sourceId}/quotes",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public AssigmentName attach(@PathVariable("sourceId") UUID sourceId, @RequestBody Points quote) {
    return attach(sourceId, quote.getId());
  }

  /**
   * Associates the {@link Points} referenced in the path with the {@link AssigmentName}, also referenced by
   * a path parameter.
   *
   * @param sourceId {@link UUID} of {@link AssigmentName} resource.
   * @param quoteId {@link UUID} of {@link Points} to be associated with referenced {@link AssigmentName}.
   * @return updated {@link AssigmentName} resource.
   */
  @PutMapping(value = "{sourceId}/quotes/{quoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public AssigmentName attach(
      @PathVariable("sourceId") UUID sourceId, @PathVariable("quoteId") UUID quoteId) {
    Points quote = mPointsRepository.findById(quoteId).get();
    AssigmentName source = get(sourceId);
    quote.getSources().add(source);
    mPointsRepository.save(quote);
    return source;
  }

  /**
   * Retrieves and returns the referenced {@link Points} resource associated with the referenced
   * {@link AssigmentName} resource. If either does not exist, or if the referenced quote is not associated
   * with the source, this method will fail.
   *
   * @param sourceId {@link UUID} of {@link AssigmentName} resource.
   * @param quoteId {@link UUID} of {@link Points} associated with referenced {@link AssigmentName}.
   * @return referenced {@link Points} resource.
   */
  @GetMapping(value = "{sourceId}/quotes/{quoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Points get(@PathVariable("sourceId") UUID sourceId, @PathVariable("quoteId") UUID quoteId) {
    AssigmentName source = get(sourceId);
    Points quote = mPointsRepository.findById(quoteId).get();
    if (!source.getQuotes().contains(quote)) {
      throw new NoSuchElementException();
    }
    return quote;
  }

  /**
   * Removes the association between the referenced {@link AssigmentName} resource and the referenced
   * {@link Points} resource. If either does not exist, or if the referenced source is not associated
   * with the quote, this method will fail.
   *
   * @param sourceId {@link UUID} of {@link AssigmentName} resource.
   * @param quoteId {@link UUID} of {@link Points} to be disassociated from the referenced {@link
   * AssigmentName}.
   */
  @DeleteMapping(value = "{sourceId}/quotes/{quoteId}")
  public void detach(@PathVariable("sourceId") UUID sourceId, @PathVariable("quoteId") UUID quoteId) {
    AssigmentName source = get(sourceId);
    Points quote = get(sourceId, quoteId);
    quote.getSources().remove(source);
    mPointsRepository.save(quote);
  }

  /**
   * Maps (via annotation) a {@link NoSuchElementException} to a response status code of {@link
   * HttpStatus#NOT_FOUND}.
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}
  
}
