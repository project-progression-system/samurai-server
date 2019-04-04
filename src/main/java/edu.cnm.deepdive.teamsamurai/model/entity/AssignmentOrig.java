//package edu.cnm.deepdive.teamsamurai.model.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import edu.cnm.deepdive.teamsamurai.view.FlatAssignmentOrig;
//import edu.cnm.deepdive.teamsamurai.view.FlatAssigment;
//import java.net.URI;
//import java.util.Date;
//import java.util.LinkedHashSet;
//import java.util.Set;
//import java.util.UUID;
//import javax.annotation.PostConstruct;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.OrderBy;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.GenericGenerator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.EntityLinks;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//
//
//@Entity
//@Component
//@JsonIgnoreProperties(
//    value = {"created", "sources", "href"}, allowGetters = true, ignoreUnknown = true)
//public class Assignment implements FlatAssignmentOrig {
//
//  private static EntityLinks entityLinks;
//
//  @Id
//  @GeneratedValue(generator = "uuid2")
//  @GenericGenerator(name = "uuid", strategy = "uuid2")
//  @Column(name = "assignment_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
//  private UUID id;
//
//
//  @NonNull
//  @CreationTimestamp
//  @Temporal(TemporalType.TIMESTAMP)
//  @Column(nullable = false, updatable = false)
//  private Date created;
//
//  @JsonSerialize(contentAs = FlatAssigment.class)
//  @ManyToMany(fetch = FetchType.LAZY,
//      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//  @JoinTable(joinColumns = @JoinColumn(name = "_id"), //TODO relace with proper id
//      inverseJoinColumns = @JoinColumn(name = "source_id"))
//  @OrderBy("name ASC")
//  private Set<AssigmentName> sources = new LinkedHashSet<>();
//
//  @Column(name = "from_source")
//  @JoinColumn(name = "user_google_id", nullable = false, updatable = false) //might have to change
//  private long fromSource;//from teacher
//
//  @NonNull
//  @Column(name = "assignment_name", length = 1024, nullable = false, unique = true)
//  private String assignmentName;
//
//  @Column(name = "to_source")
//  @JoinColumn(name = "user_google_id", nullable = false, updatable = false) //might have to change
//  private long toSource;//to student
//
//  @Column(name = "points_possible")
//  private long pointsPossible;
//
//  @NonNull
//  @Column(length = 4096, nullable = false, unique = true)
//  private long pointsEarned;//Meat
//
//  public UUID getId() {
//    return id;
//  }
//  @Override
//  public Date getCreated() {
//    return created;
//  }
//
//  @Override
//  public long getPointsEarned() {
//    return pointsEarned;
//  }
//
//  /**
//   * Sets the text content of this <code>Quote</code> instance.
//   *
//   * @param  pointsEarned actual Assignment.
//   */
//  public void setPointsEarned(long pointsEarned) {
//    this.pointsEarned = pointsEarned;
//  }
//
//  /**
//   * Returns a {@link Set} of the {@link AssigmentName} instances related to this <code>Quote</code>.
//   *
//   * @return {@link AssigmentName} set.
//   */
//  public Set<AssigmentName> getSources() {
//    return sources;
//  }
//
//  @Override
//  public URI getHref() {
//    return entityLinks.linkForSingleResource(Assignment.class, id).toUri();
//  }
//
//  @PostConstruct
//  private void init() {
//    String ignore = entityLinks.toString(); // Deliberately ignored.
//  }
//
//  @Autowired
//  private void setEntityLinks(EntityLinks entityLinks) {
//    Assignment.entityLinks = entityLinks;
//  }
//
////  /**
////   * Computes and returns a hash value computed from {@link #getText()}, after first converting to
////   * uppercase.
////   *
////   * @return hash value.
////   */
////  @Override
////  public int hashCode() {
////    return ( pointsEarned != null) ?  pointsEarned.hashCode() : 0;
////  }
////
////  /**
////   * Implements an equality test based on a case-insensitive comparison of the text returned by
////   * {@link #getText()}. If the other object is <code>null</code>, or if one (but not both) of the
////   * instances' {@link #getText()} values is <code>null</code>, then <code>false</code> is returned;
////   * otherwise, the text values are compared using {@link String#equalsIgnoreCase(String)}.
////   *
////   * @param obj object to which this instance will compare itself, based on {@link #getText()}.
////   * @return <code>true</code> if the values are equal, ignoring case; <code>false</code> otherwise.
////   */
////  @Override
////  public boolean equals(Object obj) {
////    if (obj == null || obj.getClass() != getClass()) {
////      return false;
////    }
////    Assigment other = (Assigment) obj;
////    return Objects.equals( pointsEarned, other. pointsEarned) || ( pointsEarned != null &&  pointsEarned.equalsIgnoreCase(other. pointsEarned));
////  }
//
//}
