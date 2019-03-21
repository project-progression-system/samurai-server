package edu.cnm.deepdive.teamsamurai.progressionsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.lang.NonNull;

@Entity
public class Unlock {

  @Column
  private long unlock_id;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String unlock_name;

  @Column
  private long level_unlocked;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String assignment_name;

  public long getUnlock_id() {
    return unlock_id;
  }

  public void setUnlock_id(long unlock_id) {
    this.unlock_id = unlock_id;
  }

  public String getUnlock_name() {
    return unlock_name;
  }

  public void setUnlock_name(String unlock_name) {
    this.unlock_name = unlock_name;
  }

  public long getLevel_unlocked() {
    return level_unlocked;
  }

  public void setLevel_unlocked(long level_unlocked) {
    this.level_unlocked = level_unlocked;
  }

  public String getAssignment_name() {
    return assignment_name;
  }

  public void setAssignment_name(String assignment_name) {
    this.assignment_name = assignment_name;
  }
}
