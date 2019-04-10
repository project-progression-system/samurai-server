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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cnm.deepdive.teamsamurai.view.FlatAssignment;
import edu.cnm.deepdive.teamsamurai.view.FlatComplete;
import edu.cnm.deepdive.teamsamurai.view.FlatUser;
import java.net.URI;
import java.util.Date;
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
 * relationships to zero or more {@link Complete} resources.
 */
@Entity
@Component
public class Complete implements FlatComplete {

  private static EntityLinks entityLinks;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "completion_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @JsonSerialize(as = FlatUser.class)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "student_id", updatable = false, nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User student;

  @Column
  private int points;

  @JsonSerialize(as = FlatAssignment.class)
  @ManyToOne
  @JoinColumn(name = "assignment_id", nullable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Assignment assignment;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  /**
   * returns a student user instance
   *
   * @return student user.
   */
  public User getStudent() {
    return student;
  }

  /**
   * Sets the student user of this <code>Complete</code> instance.
   *
   * @param student complete user student instance
   */
  public void setStudent(User student) {
    this.student = student;
  }

  @Override
  public int getPoints() {
    return points;
  }

  /**
   * Sets the points of this <code>Complete</code> instance.
   *
   * @param points complete instance
   */
  public void setPoints(int points) {
    this.points = points;
  }

  /**
   * returns an Assignment
   *
   * @return assignment
   */
  public Assignment getAssignment() {
    return assignment;
  }

  /**
   * Sets the assignment of this <code>Complete</code> instance.
   *
   * @param assignment complete instance.
   */
  public void setAssignment(Assignment assignment) {
    this.assignment = assignment;
  }

  @Override
  public URI getHref() {
    return entityLinks.linkForSingleResource(Complete.class, id).toUri();
  }

  @PostConstruct
  private void init() {
    String ignore = entityLinks.toString(); // Deliberately ignored.
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Complete.entityLinks = entityLinks;
  }

}
