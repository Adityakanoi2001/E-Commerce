package com.example.UserModule.exceptions;

public class AuthenticationFailException extends Exception {
  public AuthenticationFailException(String msg) {
    super(msg);
  }
}
