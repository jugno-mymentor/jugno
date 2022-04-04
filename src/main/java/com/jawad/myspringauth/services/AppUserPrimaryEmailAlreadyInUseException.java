package com.jawad.myspringauth.services;

import com.jawad.myspringauth.model.AppUser;

public class AppUserPrimaryEmailAlreadyInUseException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final AppUser appUser;

  private AppUserPrimaryEmailAlreadyInUseException(AppUser appUser) {
    super("The 'primary email address': " + appUser.getPrimaryEmailAddress() + "' is already in use!");
    this.appUser = appUser;
  }

  public static AppUserPrimaryEmailAlreadyInUseException create(AppUser appUser) {
    return new AppUserPrimaryEmailAlreadyInUseException(appUser);
  }

}
