package com.example.UserModule.helper;

public interface UserModuleApiPath {
  String BASE_PATH = Constants.CONTEXT_PATH + "UserController";
  String SIGN_UP = "Sign-Up";
  String SIGN_IN = "Sign-In";
  String RESET_PASSWORD_LINK = "Reset-Password-Link";
  String UPDATE_PASSWORD = "Update-Password";
  String DELETE_USER_ACCOUNT = "Delete-User-Account";
  String DEACTIVATE_USER_ACCOUNT = "Deactivate-User-Account";
}
