
package edu.cnm.deepdive.teamsamurai.controller;

import edu.cnm.deepdive.teamsamurai.model.dao.PointRepository;
import edu.cnm.deepdive.teamsamurai.model.dao.AssigmentRepository;
import edu.cnm.deepdive.teamsamurai.model.entity.Point;
import edu.cnm.deepdive.teamsamurai.model.entity.Assigment;
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
 * Defines REST endpoints for servicing requests on {@link Point} resources, invoking {@link
 * PointRepository} methods to perform the required operations.
 */
//quotes
@RestController
@ExposesResourceFor(Point.class)
@RequestMapping("/points")
public class PointController {

  private PointRepository PointRepository;
  private AssigmentRepository mAssigmentRepository;

  /**
   * Initializes this instance, injecting an instance of {@link PointRepository} and an instance of
   * {@link AssigmentRepository}.
   *
   * @param pointRepository repository used for operations on {@link Point} entity instances.
   * @param assigmentRepository repository used for operations on {@link Assigment} entity instances.
   */
  @Autowired
  public PointController(PointRepository pointRepository, AssigmentRepository assigmentRepository) {
    this.PointRepository = pointRepository;
    this.mAssigmentRepository = assigmentRepository;
  }

  /**
   * Returns a randomly selected {@link Point} resource, presumably for use in "Quote of the
   * Day"-type applications.
   *
   * @return random {@link Point}.
   */
  @GetMapping(value = "random", produces = MediaType.APPLICATION_JSON_VALUE)
  public Point getRandom() {
    return PointRepository.findRandom().get();
  }

  /**
   * Returns a sequence of {@link Point} resources, containing the specified text.
   *
   * @param fragment text to match (case-insensitive).
   * @return sequence of {@link Point} resources.
   */
  @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Point> search(@RequestParam("q") String fragment) {
    return PointRepository.findAllByTextContainingOrderByTextAsc(fragment);
  }

  /**
   * Returns a sequence of all the {@link Point} resources in the database.
   *
   * @return sequence of {@link Point} resources.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Point> get() {
    return PointRepository.findAllByOrderByTextAsc();
  }

  /**
   * Adds the provided {@link Point} resource to the database and returns the completed resource,
   * including timestamp &amp; ID. The provided resource is only required to contain a
   * <code>text</code> property, with a non-<code>null</code> value.
   *
   * @param quote partial {@link Point} resource.
   * @return completed {@link Point} resource.
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Point> post(@RequestBody Point quote) {
    PointRepository.save(quote);
    return ResponseEntity.created(quote.getHref()).body(quote);
  }

  /**
   * Retrieves and returns the {@link Point} resource with the specified ID.
   *
   * @param quoteId quote {@link UUID}.
   * @return retrieved {@link Point} resource.
   */
  @GetMapping(value = "{quoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Point get(@PathVariable("quoteId") UUID quoteId) {
    return PointRepository.findById(quoteId).get();
  }

  /**
   * Replaces the text (which is the only consumer-specifiable property) of the referenced existing
   * {@link Point} resource with the text from the provided resource.
   *
   * @param quoteId source {@link UUID}.
   * @param update {@link Point} resource to use to replace contents of existing quote.
   */
  @PutMapping(value = "{quoteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void put(@PathVariable("quoteId") UUID quoteId, @RequestBody Point update) {
    Point quote = PointRepository.findById(quoteId).get();
    quote.setText(update.getText());
    PointRepository.save(quote);
  }

  /**
   * Deletes the specified {@link Point} resource from the database.
   *
   * @param quoteId quote {@link UUID}.
   */
  @DeleteMapping(value = "{quoteId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("quoteId") UUID quoteId) {
    PointRepository.delete(get(quoteId));
  }

  /**
   * Associates the {@link Assigment} specified in the request body with the {@link Point} referenced
   * by the path parameter. Only the <code>id</code> property of the {@link Assigment} must be
   * specified.
   *
   * @param quoteId {@link UUID} of {@link Point} resource.
   * @param source {@link Assigment} to be associated with referenced {@link Point}.
   * @return updated {@link Point} resource.
   */
  @PostMapping(value = "{quoteId}/sources",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Point attach(@PathVariable("quoteId") UUID quoteId, @RequestBody Assigment source) {
    return attach(quoteId, source.getId());
  }

  /**
   * Associates the {@link Assigment} referenced in the path with the {@link Point}, also referenced by
   * a path parameter.
   *
   * @param quoteId {@link UUID} of {@link Point} resource.
   * @param sourceId {@link UUID} of {@link Assigment} to be associated with referenced {@link Point}.
   * @return updated {@link Point} resource.
   */
  @PutMapping(value = "{quoteId}/sources/{sourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Point attach(@PathVariable("quoteId") UUID quoteId, @PathVariable UUID sourceId) {
    edu.cnm.deepdive.teamsamurai.model.entity.Assigment source = mAssigmentRepository.findById(sourceId).get();
    Point quote = get(quoteId);
    quote.getSources().add(source);
    PointRepository.save(quote);
    return quote;
  }

  /**
   * Retrieves and returns the referenced {@link Assigment} resource associated with the referenced
   * {@link Point} resource. If either does not exist, or if the referenced source is not associated
   * with the quote, this method will fail.
   *
   * @param quoteId {@link UUID} of {@link Point} resource.
   * @param sourceId {@link UUID} of {@link Assigment} associated with referenced {@link Point}.
   * @return referenced {@link Assigment} resource.
   */
  @GetMapping(value = "{quoteId}/sources/{sourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public edu.cnm.deepdive.teamsamurai.model.entity.Assigment get(
      @PathVariable("quoteId") UUID quoteId, @PathVariable("sourceId") UUID sourceId) {
    Point quote = get(quoteId);
    edu.cnm.deepdive.teamsamurai.model.entity.Assigment source = mAssigmentRepository.findById(sourceId).get();
    if (!quote.getSources().contains(source)) {
      throw new NoSuchElementException();
    }
    return source;
  }

  /**
   * Removes the association between the referenced {@link Point} resource and the referenced
   * {@link Assigment} resource. If either does not exist, or if the referenced source is not
   * associated with the quote, this method will fail.
   *
   * @param quoteId {@link UUID} of {@link Point} resource.
   * @param sourceId {@link UUID} of {@link Assigment} to be disassociated from referenced {@link
   * Point}.
   */
  @DeleteMapping(value = "{quoteId}/sources/{sourceId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void detach(
      @PathVariable("quoteId") UUID quoteId, @PathVariable("sourceId") UUID sourceId) {
    Point quote = get(quoteId);
    Assigment source = get(quoteId, sourceId);
    quote.getSources().remove(source);
    PointRepository.save(quote);
  }

  /**
   * Maps (via annotation) a {@link NoSuchElementException} to a response status code of {@link
   * HttpStatus#NOT_FOUND}.
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}

}
