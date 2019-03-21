package edu.cnm.deepdive.teamsamurai.progressionsystem.model;


import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class Assignments {


  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "assignments_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID assignemnt_id;

  @Column
  private long fromSource;//from teacher

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String assignment_name;

  @Column
  private long toSource;//to student

  @Column
  private long points_possible;

  @Column
  private long points_earned;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  public UUID getAssignemnt_id() {
    return assignemnt_id;
  }

  public void setAssignemnt_id(UUID assignemnt_id) {
    this.assignemnt_id = assignemnt_id;
  }

  public long getFromSource() {
    return fromSource;
  }

  public void setFromSource(long fromSource) {
    this.fromSource = fromSource;
  }

  public String getAssignment_name() {
    return assignment_name;
  }

  public void setAssignment_name(String assignment_name) {
    this.assignment_name = assignment_name;
  }

  public long getToSource() {
    return toSource;
  }

  public void setToSource(long toSource) {
    this.toSource = toSource;
  }

  public long getPoints_possible() {
    return points_possible;
  }

  public void setPoints_possible(long points_possible) {
    this.points_possible = points_possible;
  }

  public long getPoints_earned() {
    return points_earned;
  }

  public void setPoints_earned(long points_earned) {
    this.points_earned = points_earned;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }
}
