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
package edu.cnm.deepdive.teamsamurai.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cnm.deepdive.teamsamurai.view.FlatAssignment;
import edu.cnm.deepdive.teamsamurai.view.FlatUser;
import java.net.URI;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Defines a database entity and REST resource representing the a assignment complete user, and its
 * relationships to zero or more {@link Assignment} resources.
 */
@Entity
@Component
@JsonIgnoreProperties(
    value = {"created", "users", "href"}, allowGetters = true, ignoreUnknown = true)
public class Assignment implements FlatAssignment {

  private static EntityLinks entityLinks;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "assignment_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String name;

  @Column
  private int value;

  @JsonSerialize(as = FlatUser.class)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "teacher_id", updatable = false, nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User teacher;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this <code>AssigmentName</code> instance.
   *
   * @param name quote source name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return
   */
  @Override
  public int getValue() {
    return value;
  }

  /**
   * @param value
   */
  public void setValue(int value) {
    this.value = value;
  }

  /**
   * @return
   */
  public User getTeacher() {
    return teacher;
  }

  /**
   * @param teacher
   */
  public void setTeacher(User teacher) {
    this.teacher = teacher;
  }

  @Override
  public URI getHref() {
    return entityLinks.linkForSingleResource(Assignment.class, id).toUri();
  }

  @PostConstruct
  private void init() {
    String ignore = entityLinks.toString(); // Deliberately ignored.
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Assignment.entityLinks = entityLinks;
  }

  /**
   * Computes and returns a hash value computed from {@link #getName()}, after first converting to
   * uppercase.
   *
   * @return hash value.
   */
  @Override
  public int hashCode() {
    return (name != null) ? name.toUpperCase().hashCode() : 0;
  }

  /**
   * Implements an equality test based on a case-insensitive comparison of the text returned by
   * {@link #getName()}. If the other object is <code>null</code>, or if one (but not both) of the
   * instances' {@link #getName()} values is <code>null</code>, then <code>false</code> is returned;
   * otherwise, the name values are compared using {@link String#equalsIgnoreCase(String)}.
   *
   * @param obj object to which this instance will compare itself, based on {@link #getName()}.
   * @return <code>true</code> if the values are equal, ignoring case; <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    Assignment other = (Assignment) obj;
    return Objects.equals(name, other.name) || (name != null && name.equalsIgnoreCase(other.name));
  }

}
