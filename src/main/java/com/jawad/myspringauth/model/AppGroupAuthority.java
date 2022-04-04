package com.jawad.myspringauth.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Access(AccessType.FIELD)
@Entity(name = "AppGroupAuthority")
@Table(name = "group_authorities")
public class AppGroupAuthority implements Serializable, Comparable<AppGroupAuthority> {

  @Id
  @ManyToOne
  @JoinColumn(name="groupId")
  private AppGroup group;

  @Id
  @ManyToOne
  @JoinColumn(name="authorityId")
  private AppAuthority authority;

  //TODO-JPA: Can we add recordVersionId in these link tables?

  /**
   * For JPA use only.
   */
  protected AppGroupAuthority() {
  }

  public AppGroupAuthority(AppGroup group, AppAuthority authority) {
    this.group = group;
    this.authority = authority;
  }

  public AppGroup getGroup() {
    return group;
  }

  public AppAuthority getAuthority() {
    return authority;
  }

  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof AppGroupAuthority) {
      return compareTo((AppGroupAuthority) obj) == 0;
    } else {
      return false;
    }
  }

  @Override
  public final int compareTo(AppGroupAuthority o) {
    int comparisonResult = group.compareTo(o.group);
    if (comparisonResult == 0) {
      comparisonResult = authority.compareTo(o.authority);
    }
    return comparisonResult;
  }

  @Override
  public int hashCode() {
    return Objects.hash(group, authority);
  }

}
