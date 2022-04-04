package com.jawad.myspringauth.services;

import java.util.ArrayList;
import java.util.List;

public class ServiceOperationFailureInfo {

  private final ServiceOperationFailureType serviceOperationFailureType;
  private final List<Object> errorMessageParams = new ArrayList<>();

  public ServiceOperationFailureInfo(ServiceOperationFailureType serviceOperationFailureType,
                                     List<Object> operationFailureReasonMessageParams) {
    this.serviceOperationFailureType = serviceOperationFailureType;
    if (operationFailureReasonMessageParams != null) {
      this.errorMessageParams.addAll(operationFailureReasonMessageParams);
    }
  }

  String getErrorMessage() {
    return getFailureType().getErrorMessage(getErrorMessageParams());
  }

  public String getGloballyUniqueErrorCode() {
    return getFailureType().getGloballyUniqueErrorCode();
  }

  private ServiceOperationFailureType getFailureType() {
    return serviceOperationFailureType;
  }

  private List<Object> getErrorMessageParams() {
    return errorMessageParams;
  }

}
