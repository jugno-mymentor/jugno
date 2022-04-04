package com.jawad.myspringauth.services;

import com.jawad.myspringauth.model.AppAuthority;
import com.jawad.myspringauth.repositories.AppAuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AppAuthorityManagementService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppUserManagementService.class);

  private final AppAuthorityRepository appAuthorityRepository;

  @Autowired
  public AppAuthorityManagementService(AppAuthorityRepository appAuthorityRepository) {
    this.appAuthorityRepository = appAuthorityRepository;
  }

  @Transactional
  public void createAppAuthority(AppAuthority appAuthority) {
    LOGGER.debug("REST request to create authority : {}", appAuthority);
    Optional<AppAuthority> optionalExistingAppAuthority = appAuthorityRepository.findByAuthorityName(appAuthority.getAuthorityName());
    if (optionalExistingAppAuthority.isPresent()) {
      throw ServiceOperationFailureException.createForAppAuthorityNameAlreadyInUse((appAuthority));
    } else {
      appAuthorityRepository.save(appAuthority);
    }
  }

  @Transactional(readOnly = true)
  public Optional<AppAuthority> getAuthorityByName(String authorityName) {
    Optional<AppAuthority> appAuthority = appAuthorityRepository.findByAuthorityName(authorityName);
    return appAuthority;
  }

}
