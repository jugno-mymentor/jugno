package com.jawad.myspringauth.repositories;

import com.jawad.myspringauth.model.AppAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppAuthorityRepository extends JpaRepository<AppAuthority, Integer> {

  @Query("SELECT a FROM AppAuthority a WHERE a.authorityName = :authorityName")
  Optional<AppAuthority> findByAuthorityName(@Param("authorityName") String authorityName);

}
