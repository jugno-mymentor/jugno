package com.jawad.myspringauth.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@Access(AccessType.FIELD)
@Entity(name = "AppAuthority")
@Table(name = "authorities")
@SequenceGenerator(name = "seq_authorities", allocationSize = 1)
public class AppAuthority implements Serializable, Comparable<AppAuthority> {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_authorities")
  @Column(name = "authorityId")
  private Integer authorityId;

  @NaturalId
  @Column(name = "authorityName")
  private String authorityName;

  /*
   Fetch modes:
     SELECT: uses separate queries for parent record and EACH child record (if eager then will run n+1 queries
             otherwise it loads only parent and issues n queries when first child is being retrieved.
     JOIN (the default if not specified) uses single query using left outer join to select children. since it is eager by nature so lazy setting has no effect.
     SUBSELECT:
       Seems always behaves lazy whether setting is eager or lazy. It loads the parent using one query and then when the
       children are accesses it loads all of them using another query that uses a nested query in IN clause to select children)

    SUBSELECT seems best since it will run max two queries and it is always lazy i.e. second query is only run when children are needed.
   */
  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @SortNatural
  private SortedSet<AppGroupAuthority> appGroupAuthorities = new TreeSet<>();

  @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @SortNatural
  private SortedSet<AppUserAuthority> appUserAuthorities = new TreeSet<>();

  /**
   * Record version for optimistic locking (i.e. prevention of concurrent modifications).
   */
  @Version
  @Column(name = "recordVersionId")
  private long recordVersionId;

  /**
   * For JPA use only.
   */
  public AppAuthority() {
  }

  public void addAppGroup(AppGroup appGroup) {
    AppGroupAuthority appGroupAuthority = new AppGroupAuthority(appGroup, this);
    getAppGroupAuthorities().add(appGroupAuthority);
    appGroup.getAppGroupAuthorities().add(appGroupAuthority);
  }

  public void removeAppGroup(AppGroup appGroup) {
    AppGroupAuthority appGroupAuthority = new AppGroupAuthority(appGroup, this);
    getAppGroupAuthorities().remove(appGroupAuthority);
    appGroup.getAppGroupAuthorities().remove(appGroupAuthority);
//    appGroupAuthority.setGroup(null);
//    appGroupAuthority.setAuthority(null);
  }

  public void addAppUser(AppUser appUser) {
    AppUserAuthority appUserAuthority = new AppUserAuthority(appUser, this);
    getAppUserAuthorities().add(appUserAuthority);
    appUser.getAppUserAuthorities().add(appUserAuthority);
  }

  public void removeAppUser(AppUser appUser) {
    AppUserAuthority appUserAuthority = new AppUserAuthority(appUser, this);
    getAppUserAuthorities().remove(appUserAuthority);
    appUser.getAppUserAuthorities().remove(appUserAuthority);
  }

  public Integer getAuthorityId() {
    return authorityId;
  }

  public String getAuthorityName() {
    return authorityName;
  }

  public SortedSet<AppGroupAuthority> getAppGroupAuthorities() {
    return appGroupAuthorities;
  }

  public SortedSet<AppUserAuthority> getAppUserAuthorities() {
    return appUserAuthorities;
  }

  public long getRecordVersionId() {
    return recordVersionId;
  }

  public void setAuthorityName(String authorityName) {
    this.authorityName = authorityName;
  }

  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof AppAuthority) {
      return compareTo((AppAuthority) obj) == 0;
    } else {
      return false;
    }
  }

  @Override
  public final int compareTo(AppAuthority o) {
    int comparisonResult = authorityName.compareTo(o.authorityName);
    return comparisonResult;
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorityName);
  }

}
