package edu.cnm.deepdive.teamsamurai.progressionsystem.model;


import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class Assignment {


  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "assignment_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID assignmentId;


  @Column(name = "from_source")
  @JoinColumn(name = "user_google_id", nullable = false, updatable = false) //might have to change
  private long fromSource;//from teacher

  @NonNull
  @Column(name = "assignment_name", length = 1024, nullable = false, unique = true)
  private String assignmentName;

  @Column(name = "to_source")
  @JoinColumn(name = "user_google_id", nullable = false, updatable = false) //might have to change
  private long toSource;//to student

  @Column(name = "points_possible")
  private long pointsPossible;

  @Column(name = "points_earned")
  private long pointsEarned;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  public UUID getAssignmentId() {
    return assignmentId;
  }

  public void setAssignmentId(UUID assignmentId) {
    this.assignmentId = assignmentId;
  }

  public long getFromSource() {
    return fromSource;
  }

  public void setFromSource(long fromSource) {
    this.fromSource = fromSource;
  }

  public String getAssignmentName() {
    return assignmentName;
  }

  public void setAssignmentName(String assignmentName) {
    this.assignmentName = assignmentName;
  }

  public long getToSource() {
    return toSource;
  }

  public void setToSource(long toSource) {
    this.toSource = toSource;
  }

  public long getPointsPossible() {
    return pointsPossible;
  }

  public void setPointsPossible(long pointsPossible) {
    this.pointsPossible = pointsPossible;
  }

  public long getPointsEarned() {
    return pointsEarned;
  }

  public void setPointsEarned(long pointsEarned) {
    this.pointsEarned = pointsEarned;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }
}
