package com.example.UserModule.helper;

public interface ResponseMessages {
  String EXISTING_USER = "User Already Exists !";
  String USER_CREATED = "User Created Successfully !";
  String USER_UNAVAILABLE = "User Not Present !";
  String MAIL_SENT_SUCCESS = "Mail Sent Successfully...";
  String MAIL_SENT_FAILURE = "Error while Sending Mail";
  String ATTEMPTS_EXHAUSTED = "No Attempts Left Try Again After 12 Hours from Current Time";
  String AUTHENTICATION_ERROR = "Unable to Authorize the User Check Again !";
  String INVALID_PASSWORD = "Password Not as per Valid Format";
  String TRY_LOGIN = "Try Login";
  String ACCOUNT_DEACTIVATED = "Account Deactivated";
  String ACCOUNT_ALREADY_DEACTIVATED = "Account Already Deactivated";
  String ACCOUNT_ACTIVATED = "Account Activated";
}
