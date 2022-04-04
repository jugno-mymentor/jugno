package com.jawad.myspringauth.services;

import com.jawad.myspringauth.model.AppAuthority;
import com.jawad.myspringauth.model.AppUser;

import java.util.Collections;

public class ServiceOperationFailureException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final ServiceOperationFailureInfo serviceOperationFailureInfo;

  private ServiceOperationFailureException(ServiceOperationFailureInfo serviceOperationFailureInfo) {
    this(serviceOperationFailureInfo, null);
  }

  private ServiceOperationFailureException(ServiceOperationFailureInfo serviceOperationFailureInfo, Throwable cause) {
    super(serviceOperationFailureInfo.getErrorMessage(), cause);
    this.serviceOperationFailureInfo = serviceOperationFailureInfo;
  }

  public ServiceOperationFailureInfo getFailedOperationInfo() {
    return serviceOperationFailureInfo;
  }

  public static ServiceOperationFailureException createForAppUserNameAlreadyInUse(AppUser appUser) {
    ServiceOperationFailureInfo serviceOperationFailureInfo =
            ServiceOperationFailureType.APP_USER_NAME_ALREADY_IN_USE.create(
                    Collections.singletonList(appUser));
    return new ServiceOperationFailureException(serviceOperationFailureInfo);
  }

  public static ServiceOperationFailureException createForAppUserPrimaryEmailAddressAlreadyInUse(AppUser appUser) {
    ServiceOperationFailureInfo serviceOperationFailureInfo =
            ServiceOperationFailureType.APP_USER_PRIMARY_EMAIL_ADDRESS_ALREADY_IN_USE.create(
                    Collections.singletonList(appUser));
    return new ServiceOperationFailureException(serviceOperationFailureInfo);
  }

  public static ServiceOperationFailureException createForAppAuthorityNameAlreadyInUse(AppAuthority appAuthority) {
    ServiceOperationFailureInfo serviceOperationFailureInfo =
            ServiceOperationFailureType.APP_AUTHORITY_NAME_ALREADY_IN_USE.create(
                    Collections.singletonList(appAuthority));
    return new ServiceOperationFailureException(serviceOperationFailureInfo);
  }

  public static ServiceOperationFailureException createForUserIdNotFound(AppUser appUser) {
    ServiceOperationFailureInfo serviceOperationFailureInfo =
            ServiceOperationFailureType.APP_USER_ID_NOT_FOUND.create(
                    Collections.singletonList(appUser));
    return new ServiceOperationFailureException(serviceOperationFailureInfo);
  }

  public static ServiceOperationFailureException createForUserNameNotFound(AppUser appUser) {
    ServiceOperationFailureInfo serviceOperationFailureInfo =
            ServiceOperationFailureType.APP_USER_NAME_NOT_FOUND.create(
                    Collections.singletonList(appUser));
    return new ServiceOperationFailureException(serviceOperationFailureInfo);
  }

}
