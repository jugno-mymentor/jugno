package com.jawad.myspringauth.services;

public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private ResourceNotFoundException(String message) {
    super(message);
  }

  public static ResourceNotFoundException createAppUserNotFound(String appUserName) {
    return new ResourceNotFoundException("The user having user-name: '" + appUserName + "' was not found!");
  }

}
