
package edu.cnm.deepdive.teamsamurai.controller;

import edu.cnm.deepdive.teamsamurai.model.dao.PointsRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.AssigmentNameRepository;
import edu.cnm.deepdive.teamsamurai.model.entity.Points;
import edu.cnm.deepdive.teamsamurai.model.entity.AssigmentName;
import java.util.NoSuchElementException;
import java.util.UUID;
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
 * Defines REST endpoints for servicing requests on {@link Points} resources, invoking {@link
 * PointsRepository} methods to perform the required operations.
 */
@RestController
@ExposesResourceFor(Points.class)
@RequestMapping("/quotes")
public class PointsController {

  private PointsRepository PointsRepository;
  private AssigmentNameRepository mAssigmentNameRepository;

  /**
   * Initializes this instance, injecting an instance of {@link PointsRepository} and an instance of
   * {@link AssigmentNameRepository}.
   *
   * @param pointsRepository repository used for operations on {@link Points} entity instances.
   * @param assigmentNameRepository repository used for operations on {@link AssigmentName} entity instances.
   */
  @Autowired
  public PointsController(PointsRepository pointsRepository, AssigmentNameRepository assigmentNameRepository) {
    this.PointsRepository = pointsRepository;
    this.mAssigmentNameRepository = assigmentNameRepository;
  }

  /**
   * Returns a randomly selected {@link Points} resource, presumably for use in "Quote of the
   * Day"-type applications.
   *
   * @return random {@link Points}.
   */
  @GetMapping(value = "random", produces = MediaType.APPLICATION_JSON_VALUE)
  public Points getRandom() {
    return PointsRepository.findRandom().get();
  }

  /**
   * Returns a sequence of {@link Points} resources, containing the specified text.
   *
   * @param fragment text to match (case-insensitive).
   * @return sequence of {@link Points} resources.
   */
  @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Points> search(@RequestParam("q") String fragment) {
    return PointsRepository.findAllByTextContainingOrderByTextAsc(fragment);
  }

  /**
   * Returns a sequence of all the {@link Points} resources in the database.
   *
   * @return sequence of {@link Points} resources.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Points> get() {
    return PointsRepository.findAllByOrderByTextAsc();
  }

  /**
   * Adds the provided {@link Points} resource to the database and returns the completed resource,
   * including timestamp &amp; ID. The provided resource is only required to contain a
   * <code>text</code> property, with a non-<code>null</code> value.
   *
   * @param quote partial {@link Points} resource.
   * @return completed {@link Points} resource.
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Points> post(@RequestBody Points quote) {
    PointsRepository.save(quote);
    return ResponseEntity.created(quote.getHref()).body(quote);
  }

  /**
   * Retrieves and returns the {@link Points} resource with the specified ID.
   *
   * @param quoteId quote {@link UUID}.
   * @return retrieved {@link Points} resource.
   */
  @GetMapping(value = "{quoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Points get(@PathVariable("quoteId") UUID quoteId) {
    return PointsRepository.findById(quoteId).get();
  }

  /**
   * Replaces the text (which is the only consumer-specifiable property) of the referenced existing
   * {@link Points} resource with the text from the provided resource.
   *
   * @param quoteId source {@link UUID}.
   * @param update {@link Points} resource to use to replace contents of existing quote.
   */
  @PutMapping(value = "{quoteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void put(@PathVariable("quoteId") UUID quoteId, @RequestBody Points update) {
    Points quote = PointsRepository.findById(quoteId).get();
    quote.setText(update.getText());
    PointsRepository.save(quote);
  }

  /**
   * Deletes the specified {@link Points} resource from the database.
   *
   * @param quoteId quote {@link UUID}.
   */
  @DeleteMapping(value = "{quoteId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("quoteId") UUID quoteId) {
    PointsRepository.delete(get(quoteId));
  }

  /**
   * Associates the {@link AssigmentName} specified in the request body with the {@link Points} referenced
   * by the path parameter. Only the <code>id</code> property of the {@link AssigmentName} must be
   * specified.
   *
   * @param quoteId {@link UUID} of {@link Points} resource.
   * @param source {@link AssigmentName} to be associated with referenced {@link Points}.
   * @return updated {@link Points} resource.
   */
  @PostMapping(value = "{quoteId}/sources",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Points attach(@PathVariable("quoteId") UUID quoteId, @RequestBody AssigmentName source) {
    return attach(quoteId, source.getId());
  }

  /**
   * Associates the {@link AssigmentName} referenced in the path with the {@link Points}, also referenced by
   * a path parameter.
   *
   * @param quoteId {@link UUID} of {@link Points} resource.
   * @param sourceId {@link UUID} of {@link AssigmentName} to be associated with referenced {@link Points}.
   * @return updated {@link Points} resource.
   */
  @PutMapping(value = "{quoteId}/sources/{sourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Points attach(@PathVariable("quoteId") UUID quoteId, @PathVariable UUID sourceId) {
    edu.cnm.deepdive.teamsamurai.model.entity.AssigmentName source = mAssigmentNameRepository.findById(sourceId).get();
    Points quote = get(quoteId);
    quote.getSources().add(source);
    PointsRepository.save(quote);
    return quote;
  }

  /**
   * Retrieves and returns the referenced {@link AssigmentName} resource associated with the referenced
   * {@link Points} resource. If either does not exist, or if the referenced source is not associated
   * with the quote, this method will fail.
   *
   * @param quoteId {@link UUID} of {@link Points} resource.
   * @param sourceId {@link UUID} of {@link AssigmentName} associated with referenced {@link Points}.
   * @return referenced {@link AssigmentName} resource.
   */
  @GetMapping(value = "{quoteId}/sources/{sourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public edu.cnm.deepdive.teamsamurai.model.entity.AssigmentName get(
      @PathVariable("quoteId") UUID quoteId, @PathVariable("sourceId") UUID sourceId) {
    Points quote = get(quoteId);
    edu.cnm.deepdive.teamsamurai.model.entity.AssigmentName source = mAssigmentNameRepository.findById(sourceId).get();
    if (!quote.getSources().contains(source)) {
      throw new NoSuchElementException();
    }
    return source;
  }

  /**
   * Removes the association between the referenced {@link Points} resource and the referenced
   * {@link AssigmentName} resource. If either does not exist, or if the referenced source is not
   * associated with the quote, this method will fail.
   *
   * @param quoteId {@link UUID} of {@link Points} resource.
   * @param sourceId {@link UUID} of {@link AssigmentName} to be disassociated from referenced {@link
   * Points}.
   */
  @DeleteMapping(value = "{quoteId}/sources/{sourceId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void detach(
      @PathVariable("quoteId") UUID quoteId, @PathVariable("sourceId") UUID sourceId) {
    Points quote = get(quoteId);
    AssigmentName source = get(quoteId, sourceId);
    quote.getSources().remove(source);
    PointsRepository.save(quote);
  }

  /**
   * Maps (via annotation) a {@link NoSuchElementException} to a response status code of {@link
   * HttpStatus#NOT_FOUND}.
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}

}
