package com.example.UserModule.helper;


public enum UserActivity {
  FRESH("fresh"),
  ACTIVE("active"),
  DEACTIVATED_ACCOUNT("deactivated_account"),
  DORMANT("dormant");

  String name;

  UserActivity(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
