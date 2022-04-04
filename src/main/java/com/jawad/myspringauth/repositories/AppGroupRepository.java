package com.jawad.myspringauth.repositories;

import com.jawad.myspringauth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppGroupRepository extends JpaRepository<AppUser, Integer> {

}
