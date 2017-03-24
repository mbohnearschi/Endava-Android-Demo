package com.endava.androiddemo.utils.validation;

public class ValidationResponse {

  private Boolean success;
  private String failReason;
  private int failCode;

  ValidationResponse() {
  }

  public ValidationResponse(Boolean success, String failReason, int failCode) {
    this.success = success;
    this.failReason = failReason;
    this.failCode = failCode;
  }

  public Boolean getSuccess() {
    return success;
  }

  void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getFailReason() {
    return failReason;
  }

  void setFailReason(String failReason) {
    this.failReason = failReason;
  }

  public int getFailCode() {
    return failCode;
  }

  void setFailCode(int failCode) {
    this.failCode = failCode;
  }
}