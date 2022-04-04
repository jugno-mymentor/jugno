package com.jawad.myspringauth.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Access(AccessType.FIELD)
@Entity(name = "AppUserGroup")
@Table(name = "user_groups")
public class AppUserGroup implements Serializable, Comparable<AppUserGroup> {

  @Id
  @ManyToOne
  @JoinColumn(name = "groupId")
  private AppGroup group;

  @Id
  @ManyToOne
  @JoinColumn(name = "userId")
  private AppUser user;

  /**
   * For JPA use only.
   */
  protected AppUserGroup() {
  }

  public AppUserGroup(AppGroup group, AppUser user) {
    this.group = group;
    this.user = user;
  }

  public AppGroup getGroup() {
    return group;
  }

  public AppUser getUser() {
    return user;
  }

  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof AppUserGroup) {
      return compareTo((AppUserGroup) obj) == 0;
    } else {
      return false;
    }
  }

  @Override
  public final int compareTo(AppUserGroup o) {
    int comparisonResult = group.compareTo(o.group);
    if (comparisonResult == 0) {
      comparisonResult = user.compareTo(o.user);
    }
    return comparisonResult;
  }

  @Override
  public int hashCode() {
    return Objects.hash(group, user);
  }

}
