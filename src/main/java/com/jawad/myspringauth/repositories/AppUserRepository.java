package com.jawad.myspringauth.repositories;

import com.jawad.myspringauth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

  @Query("SELECT u FROM AppUser u WHERE u.userName = :userName")
  Optional<AppUser> findByUserName(@Param("userName") String userName);

  @Query("SELECT u FROM AppUser u WHERE u.primaryEmailAddress = :primaryEmailAddress")
  Optional<AppUser> findByPrimaryEmailAddress(@Param("primaryEmailAddress") String primaryEmailAddress);

}
