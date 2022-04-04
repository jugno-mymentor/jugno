package com.jawad.myspringauth.model;


import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Access(AccessType.FIELD)
@Entity(name = "AppGroup")
@Table(name = "groups")
@SequenceGenerator(name = "seq_groups", allocationSize = 1)
public class AppGroup implements Serializable, Comparable<AppGroup> {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_groups")
  @Column(name = "groupId")
  private Integer groupId;

  @NaturalId
  @Column(name = "groupName")
  private String groupName;

  //TODO-nowwww

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parentGroupId")
  private AppGroup parentGroup;

  @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<AppGroup> childGroups = new HashSet<>();

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @SortNatural
  private SortedSet<AppGroupAuthority> appGroupAuthorities = new TreeSet<>();

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @SortNatural
  private SortedSet<AppUserGroup> appUserGroups = new TreeSet<>();

  /**
   * Record version for optimistic locking (i.e. prevention of concurrent modifications).
   */
  @Version
  @Column(name = "recordVersionId")
  private long recordVersionId;

  /**
   * For JPA use only.
   */
  protected AppGroup() {
  }

  public void addAppAuthority(AppAuthority appAuthority) {
    AppGroupAuthority appGroupAuthority = new AppGroupAuthority(this, appAuthority);
    getAppGroupAuthorities().add(appGroupAuthority);
    appAuthority.getAppGroupAuthorities().add(appGroupAuthority);
  }

  public void removeAppAuthority(AppAuthority appAuthority) {
    AppGroupAuthority appGroupAuthority = new AppGroupAuthority(this, appAuthority);
    getAppGroupAuthorities().remove(appGroupAuthority);
    appAuthority.getAppGroupAuthorities().remove(appGroupAuthority);
  }

  public void addAppUser(AppUser appUser) {
    AppUserGroup appUserGroup = new AppUserGroup(this, appUser);
    getAppGroupUsers().add(appUserGroup);
    appUser.getAppGroupUsers().add(appUserGroup);
  }

  public void removeAppUser(AppUser appUser) {
    AppUserGroup appUserGroup = new AppUserGroup(this, appUser);
    getAppGroupUsers().remove(appUserGroup);
    appUser.getAppGroupUsers().remove(appUserGroup);
  }

  public Integer getGroupId() {
    return groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public AppGroup getParentGroup() {
    return parentGroup;
  }

  public SortedSet<AppGroupAuthority> getAppGroupAuthorities() {
    return appGroupAuthorities;
  }

  public SortedSet<AppUserGroup> getAppGroupUsers() {
    return appUserGroups;
  }

  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof AppGroup) {
      return compareTo((AppGroup) obj) == 0;
    } else {
      return false;
    }
  }

  @Override
  public final int compareTo(AppGroup o) {
    int comparisonResult = groupName.compareTo(o.groupName);
    return comparisonResult;
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName);
  }

}