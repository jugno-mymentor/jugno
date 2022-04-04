package com.jawad.myspringauth.utils;

public class InternalApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InternalApplicationException(Throwable cause) {
    this(null, cause);
  }

  public InternalApplicationException(String message, Throwable cause) {
    super(message, cause);
  }

}
