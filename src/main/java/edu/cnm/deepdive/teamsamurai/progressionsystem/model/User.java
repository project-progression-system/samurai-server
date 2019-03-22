package edu.cnm.deepdive.teamsamurai.progressionsystem.model;


import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class User {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "user_google_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID userId;

  @NonNull
  @Column(name = "user_name", length = 1024, nullable = false, unique = true)
  private String userName;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String access;

  @NonNull
  @Column(name = "unlock_name", length = 1024, nullable = false, unique = true)
  private String unlockableName;//avatars they can unlock.

  @Column
  private long level;

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public String getUnlockableName() {
    return unlockableName;
  }

  public void setUnlockableName(String unlockableName) {
    this.unlockableName = unlockableName;
  }

  public long getLevel() {
    return level;
  }

  public void setLevel(long level) {
    this.level = level;
  }

}
