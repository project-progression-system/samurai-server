package edu.cnm.deepdive.teamsamurai.progressionsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.lang.NonNull;

@Entity
public class Unlock {

  @Column(name = "unlock_id")
  private long unlockId;

  @NonNull
  @Column(name = "unlock_name", length = 1024, nullable = false, unique = true)
  private String unlockName;

  @Column(name = "level_unlocked")
  private long levelUnlocked;

  @NonNull
  @Column(name = "assignment_name", length = 1024, nullable = false, unique = true)
  private String assignmentName;

  public long getUnlockId() {
    return unlockId;
  }

  public void setUnlockId(long unlockId) {
    this.unlockId = unlockId;
  }

  public String getUnlockName() {
    return unlockName;
  }

  public void setUnlockName(String unlockName) {
    this.unlockName = unlockName;
  }

  public long getLevelUnlocked() {
    return levelUnlocked;
  }

  public void setLevelUnlocked(long levelUnlocked) {
    this.levelUnlocked = levelUnlocked;
  }

  public String getAssignmentName() {
    return assignmentName;
  }

  public void setAssignmentName(String assignmentName) {
    this.assignmentName = assignmentName;
  }
}
