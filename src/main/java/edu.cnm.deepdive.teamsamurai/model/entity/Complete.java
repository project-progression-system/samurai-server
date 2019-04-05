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

import edu.cnm.deepdive.teamsamurai.view.FlatComplete;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
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
  private String student;

  @Column
  private int points;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "assignment_id", insertable = false, updatable = false)
  private Assignment assignment;

  /**
   * @return
   */
  public static EntityLinks getEntityLinks() {
    return entityLinks;
  }

  /**
   * @param entityLinks
   */
  public static void setEntityLinks(EntityLinks entityLinks) {
    Complete.entityLinks = entityLinks;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  /**
   * @param created
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * @return
   */
  public String getStudent() {
    return student;
  }

  /**
   * @param student
   */
  public void setStudent(String student) {
    this.student = student;
  }

  @Override
  public int getPoints() {
    return points;
  }

  /**
   * @param points
   */
  public void setPoints(int points) {
    this.points = points;
  }

  /**
   * @return
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * @return
   */
  public Assignment getAssignment() {
    return assignment;
  }

  /**
   * @param assignment
   */
  public void setAssignment(Assignment assignment) {
    this.assignment = assignment;
  }

  @Override
  public URI getHref() {
    return entityLinks.linkForSingleResource(Complete.class, id).toUri();
  }
}
