package com.jawad.myspringauth.repositories;

import com.jawad.myspringauth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TODO: This is probably not needed. AppUser and AppGroup and AppAuthority should be able to handle crud for this.
@Repository
public interface AppGroupUserRepository extends JpaRepository<AppUser, Integer> {

}
