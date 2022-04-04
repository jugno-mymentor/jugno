package com.jawad.myspringauth.services;

import com.jawad.myspringauth.model.AppAuthority;
import com.jawad.myspringauth.model.AppUser;

import java.util.List;
import java.util.function.Function;

/**
 * TODO-Exceptions: Can be used to identify the EXACT reason for this exception as well as a global error code scheme.
 */
public enum ServiceOperationFailureType {

  APP_USER_NAME_ALREADY_IN_USE((params) -> {
    AppUser appUser = (AppUser) params.get(0);
    return "The user name: '" + appUser.getUserName() + "' is already in use! Please choose a different user name.";
  }),
  APP_USER_PRIMARY_EMAIL_ADDRESS_ALREADY_IN_USE((params) -> {
    AppUser appUser = (AppUser) params.get(0);
    return "The 'primary email address': '" + appUser.getPrimaryEmailAddress() + "' is already in use! Please choose a different email address.";
  }),
  APP_AUTHORITY_NAME_ALREADY_IN_USE((params) -> {
    AppAuthority appAuthority = (AppAuthority) params.get(0);
    return "The authority name: '" + appAuthority.getAuthorityName() + "' is already in use! Please choose a different authority name.";
  }),
  APP_USER_ID_NOT_FOUND((params) -> {
    AppUser appUser = (AppUser) params.get(0);
    return "The user having user-id: '" + appUser.getUserId() + "' does not exist!";
  }),
  APP_USER_NAME_NOT_FOUND((params) -> {
    AppUser appUser = (AppUser) params.get(0);
    return "The user having user-name: '" + appUser.getUserName() + "' does not exist!";
  });

  private final Function<List<Object>, String> errorMessageGetter;

  ServiceOperationFailureType(Function<List<Object>, String> errorMessageGetter) {
    this.errorMessageGetter = errorMessageGetter;
  }

  /**
   * Written so that our code is not coupled to this.name() (if it needs to be changed to a different error coding scheme).
   *
   * @return
   */
  String getGloballyUniqueErrorCode() {
    return this.name();
  }

  public String getErrorMessage(List<Object> params) {
    return getErrorMessageGetter().apply(params);
  }

  public ServiceOperationFailureInfo create(List<Object> params) {
    return new ServiceOperationFailureInfo(this, params);
  }

  private Function<List<Object>, String> getErrorMessageGetter() {
    return errorMessageGetter;
  }

}
