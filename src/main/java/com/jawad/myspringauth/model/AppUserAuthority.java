package com.jawad.myspringauth.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Access(AccessType.FIELD)
@Entity(name = "AppUserAuthority")
@Table(name = "user_authorities")
public class AppUserAuthority implements Serializable, Comparable<AppUserAuthority> {

  @Id
  @ManyToOne
  @JoinColumn(name = "authorityId")
  private AppAuthority authority;

  @Id
  @ManyToOne
  @JoinColumn(name = "userId")
  private AppUser user;

  @Id
  @ManyToOne
  @JoinColumn(name = "delegatorUserId")
  private AppUser delegatorUser;

  @ColumnDefault(value = "true")
  private Boolean canDelegate = true;

  /**
   * For JPA use only.
   */
  protected AppUserAuthority() {
  }

  public AppUserAuthority(AppUser user, AppAuthority authority) {
    this.user = user;
    this.authority = authority;
    //TODO-JPA:
    this.delegatorUser = null;
  }

  public AppAuthority getAuthority() {
    return authority;
  }

  public AppUser getDelegatorUser() {
    return delegatorUser;
  }

  public AppUser getUser() {
    return user;
  }

  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof AppUserAuthority) {
      return compareTo((AppUserAuthority) obj) == 0;
    } else {
      return false;
    }
  }

  @Override
  public final int compareTo(AppUserAuthority o) {
    int comparisonResult = user.compareTo(o.user);
    if (comparisonResult == 0) {
      comparisonResult = authority.compareTo(o.authority);
    }
    return comparisonResult;
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, authority);
  }

}
