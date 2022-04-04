package com.jawad.myspringauth.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Access(AccessType.FIELD)
/*
 * The entity name that is to be used if and when writing JPQL queries.
 * Defaults to unqualified class name if not specified.
 */
@Entity(name = "AppUser")

/*
 * The name of the underlying table. By default, it is generated using ImplicitNamingStrategy which is that the table
 * name will be same as the class name.
 */
@Table(name = "users", schema = "schema_jawad")

@SequenceGenerator(name = "seq_users", allocationSize = 1)
public class AppUser implements Serializable, Comparable<AppUser> {

  private static final long serialVersionUID = 1L;

  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
  @Id
//  @Column(name = "userId")
  private Integer userId;

  @NaturalId
//  @Column(name = "userName")
  private String userName;

  //-- If json property name is different from the class property then do this:
  //@JsonProperty("pwd")
  //-- To ignore a class property when serializing.
  //@JsonIgnore
//  @Column(name = "password")
  private String password;

  private String primaryEmailAddress;

  @ColumnDefault(value = "true")
  private boolean isEnabled = true;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<AppUserAuthority> appUserAuthorities = new TreeSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<AppUserGroup> appUserGroups = new TreeSet<>();

  /**
   * Record version for optimistic locking (i.e. prevention of concurrent modifications).
   */
  @Version
  @Column(name = "recordVersionId")
  private long recordVersionId;

  public void addAppAuthority(AppAuthority appAuthority) {
    AppUserAuthority appUserAuthority = new AppUserAuthority(this, appAuthority);
    getAppUserAuthorities().add(appUserAuthority);
    appAuthority.getAppUserAuthorities().add(appUserAuthority);
  }

  public void removeAppAuthority(AppAuthority appAuthority) {
    AppUserAuthority appUserAuthority = new AppUserAuthority(this, appAuthority);
    getAppUserAuthorities().remove(appUserAuthority);
    appAuthority.getAppUserAuthorities().remove(appUserAuthority);
  }

  public void addAppGroup(AppGroup appGroup) {
    AppUserGroup appUserGroup = new AppUserGroup(appGroup, this);
    getAppGroupUsers().add(appUserGroup);
    appGroup.getAppGroupUsers().add(appUserGroup);
  }

  public void removeAppGroup(AppGroup appGroup) {
    AppUserGroup appUserGroup = new AppUserGroup(appGroup, this);
    getAppGroupUsers().remove(appUserGroup);
    appGroup.getAppGroupUsers().remove(appUserGroup);
  }

  public Integer getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getPrimaryEmailAddress() {
    return primaryEmailAddress;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public Set<AppUserAuthority> getAppUserAuthorities() {
    return appUserAuthorities;
  }

  public Set<AppUserGroup> getAppGroupUsers() {
    return appUserGroups;
  }

  public long getRecordVersionId() {
    return recordVersionId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPrimaryEmailAddress(String primaryEmailAddress) {
    this.primaryEmailAddress = primaryEmailAddress;
  }

  public void setEnabled(boolean enabled) {
    this.isEnabled = enabled;
  }

//  public void setAppUserAuthorities(Set<AppUserAuthority> appUserAuthorities) {
//    this.appUserAuthorities = appUserAuthorities;
//  }
//
//  public void setAppGroupUsers(Set<AppUserGroup> appUserGroups) {
//    this.appUserGroups = appUserGroups;
//  }
//
  public void setRecordVersionId(long recordVersionId) {
    this.recordVersionId = recordVersionId;
  }

  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof AppUser) {
      return compareTo((AppUser) obj) == 0;
    } else {
      return false;
    }
  }

  @Override
  public final int compareTo(AppUser o) {
    int comparisonResult = userName.compareTo(o.userName);
    return comparisonResult;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }

}
