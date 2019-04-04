package edu.cnm.deepdive.teamsamurai.view;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

/**
 * Declares the getters (and thus the JSON properties) of a source for serialization, excluding
 * references to other objects that could result in stack or buffer overflow on serialization.
 */
public interface FlatAssigment {
  /**
   * Returns the universally unique ID (UUID) of a source resource.
   *
   * @return quote UUID.
   */
  UUID getId();

  /**
   * Returns the date-time stamp recorded when a source resource is first written to the database.
   *
   * @return creation timestamp.
   */
  Date getCreated();

  /**
   * Returns the name of the source.
   *
   * @return source name.
   */
  String getName();

  int getValue();

  /**
   * Returns a URL referencing the source resource.
   *
   * @return source URL.
   */
  URI getHref();
}
