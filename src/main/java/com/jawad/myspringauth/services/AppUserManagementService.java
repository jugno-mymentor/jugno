package com.jawad.myspringauth.services;

import com.jawad.myspringauth.model.AppUser;
import com.jawad.myspringauth.repositories.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserManagementService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppUserManagementService.class);

  private final AppUserRepository appUserRepository;

  @Autowired
  public AppUserManagementService(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  @Transactional
  //-- See: https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth
  public void create(AppUser appUser) {
    LOGGER.debug("REST request to create User : {}", appUser);
    Optional<AppUser> optionalExistingAppUser = appUserRepository.findByUserName(appUser.getUserName());
    if (optionalExistingAppUser.isPresent()) {
      throw ServiceOperationFailureException.createForAppUserNameAlreadyInUse(appUser);
    }
    optionalExistingAppUser = appUserRepository.findByPrimaryEmailAddress(appUser.getPrimaryEmailAddress());
    if (optionalExistingAppUser.isPresent()) {
      throw ServiceOperationFailureException.createForAppUserPrimaryEmailAddressAlreadyInUse(appUser);
    } else {
      appUserRepository.save(appUser);
    }
  }

  @Transactional(readOnly = true)
  public Optional<AppUser> getByUserName(String userName) {
    Optional<AppUser> appUser = appUserRepository.findByUserName(userName);
    return appUser;
  }

//  public void updateAppUser(String appUserName, AppUser appUser) {
//    AppUser appUserToUpdate = appUserRepository.findUserByUserName(appUserName)
//            .orElseThrow(() -> ResourceNotFoundException.createAppUserNotFound(appUserName));
//  }

  @Transactional
  public void update(AppUser appUser) {
    LOGGER.debug("REST request to update user : {}", appUser);
    Optional<AppUser> optionalExistingAppUserWithSameUserName = appUserRepository.findByUserName(appUser.getUserName());
    if (optionalExistingAppUserWithSameUserName.isPresent()
            && (!optionalExistingAppUserWithSameUserName.get().getUserId().equals(appUser.getUserId()))) {
      throw ServiceOperationFailureException.createForAppUserNameAlreadyInUse(appUser);
    }

    Optional<AppUser> optionalExistingAppUserWithSamePrimaryEmailAddress =
            appUserRepository.findByPrimaryEmailAddress(appUser.getPrimaryEmailAddress());
    if (optionalExistingAppUserWithSamePrimaryEmailAddress.isPresent()
            && (!optionalExistingAppUserWithSamePrimaryEmailAddress.get().getUserId().equals(appUser.getUserId()))) {
      throw ServiceOperationFailureException.createForAppUserPrimaryEmailAddressAlreadyInUse(appUser);
    }

    Optional<AppUser> optionalAppUserToUpdate = appUserRepository.findById(appUser.getUserId());
    if (optionalAppUserToUpdate.isPresent()) {
      AppUser appUserToUpdate = optionalAppUserToUpdate.get();
      appUserToUpdate.setPassword(appUser.getPassword());
      appUserToUpdate.setUserName(appUser.getUserName());
      appUserToUpdate.setPrimaryEmailAddress(appUser.getPrimaryEmailAddress());
      appUserToUpdate.setEnabled(appUser.isEnabled());
      appUserToUpdate.getAppUserAuthorities().retainAll(appUser.getAppUserAuthorities());
      appUserToUpdate.getAppUserAuthorities().addAll(appUser.getAppUserAuthorities());
      appUserToUpdate.getAppGroupUsers().retainAll(appUser.getAppGroupUsers());
      appUserToUpdate.getAppGroupUsers().addAll(appUser.getAppGroupUsers());
      appUserRepository.save(appUserToUpdate);
    } else {
      throw ServiceOperationFailureException.createForUserNameNotFound(appUser);
    }
  }

  @Transactional(readOnly = true)
  public Set<AppUser> getAllAppUsers() {
    List<AppUser> appUserList = appUserRepository.findAll();
    Set<AppUser> appUsers = new HashSet<>(appUserList);
    return appUsers;
  }

}
