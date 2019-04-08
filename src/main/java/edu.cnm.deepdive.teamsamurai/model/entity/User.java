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
import edu.cnm.deepdive.teamsamurai.view.FlatUser;
import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


/**
 * Defines a database entity and REST resource representing the a assignment complete user, and its
 * relationships to zero or more {@link User} resources.
 */
@Component
@Entity(name = "user_info")
public class User implements FlatUser {

  private static EntityLinks entityLinks;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "user_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @Column(updatable = false, unique = true)
  private String subject;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private int level;

  @NonNull
  @Column(nullable = false)
  private Type type;

  @JsonSerialize(contentAs = FlatAssignment.class)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
  @OrderBy("created DESC")
  private List<Assignment> assignments = new LinkedList<>();

  @Override
  public Type getType() {
    return type;
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
   *
   *
   * @return
   */
  public String getSubject() {
    return subject;
  }

  /**
   *
   *
   * @param subject
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   *
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int getLevel() {
    return level;
  }

  /**
   * sets the level of the student
   *
   * @param level int will return a int value representing a level
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * gets a list of assignments
   *
   * @return assigment
   */
  public List<Assignment> getAssignments() {
    return assignments;
  }

  @Override
  public URI getHref(){
    return entityLinks.linkForSingleResource(User.class, id).toUri();
  }

  @PostConstruct
  private void init() {
    String ignore = entityLinks.toString(); // Deliberately ignored.
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    User.entityLinks = entityLinks;
  }

  /**
   * creates an enum for teacher and student
   */
  public enum Type {
    STUDENT, TEACHER;
  }

}
