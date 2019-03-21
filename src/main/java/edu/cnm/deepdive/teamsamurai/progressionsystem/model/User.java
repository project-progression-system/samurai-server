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
  @Column(length = 1024, nullable = false, unique = true)
  private String user_name;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String access;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true)
  private String unlockable_name;//avatars they can unlock.

  @Column
  private long level;

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public String getUnlockable_name() {
    return unlockable_name;
  }

  public void setUnlockable_name(String unlockable_name) {
    this.unlockable_name = unlockable_name;
  }

  public long getLevel() {
    return level;
  }

  public void setLevel(long level) {
    this.level = level;
  }
}
