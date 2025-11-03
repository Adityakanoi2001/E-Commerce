Feature: User Controller API Automation
  As a developer
  I want to validate all UserController REST APIs
  So that I can ensure the microservice is working correctly

  Background:
    Given the User API base URL is configured
    And the API server is running on port 8800

  @SignUp @HappyPath
  Scenario: Successful user sign-up with valid data
    Given I have a new user with:
      | firstName | lastName | usn      | email                | password    | phoneNumber | city      | state   |
      | John      | Doe      | USN001   | john.doe@test.com    | Test@123456 | 9876543210  | Bangalore | Karnataka |
    When I send a POST request to "/bliCommerce/UserController/Sign-Up" with the user data
    Then the response status code should be 200
    And the response should contain "Success" or "EXISTING_USER"
    And the response body should be valid JSON

  @SignUp @Negative
  Scenario: Sign-up with existing email should return appropriate message
    Given I have a user with email "existing.user@test.com" already registered
    When I send a POST request to "/bliCommerce/UserController/Sign-Up" with:
      | firstName | lastName | usn      | email                  | password    | phoneNumber | city      | state   |
      | Jane      | Smith    | USN002   | existing.user@test.com | Test@123456 | 9876543211  | Mumbai    | Maharashtra |
    Then the response status code should be 200
    And the response message should indicate existing user

  @SignIn @HappyPath
  Scenario: Successful user sign-in with valid credentials
    Given I have a registered user with:
      | email              | password    |
      | john.doe@test.com  | Test@123456 |
    When I send a POST request to "/bliCommerce/UserController/Sign-In" with credentials and optional IP "192.168.1.1"
    Then the response status code should be 200
    And the response should contain authentication token
    And the response message should be "Success"

  @SignIn @Negative
  Scenario: Sign-in with invalid credentials should fail
    When I send a POST request to "/bliCommerce/UserController/Sign-In" with:
      | email              | password      |
      | invalid@test.com   | WrongPass123  |
    Then the response status code should be 200
    And the response should indicate authentication failure

  @PasswordReset @HappyPath
  Scenario: Request password reset link for registered email
    Given I have a registered user with email "john.doe@test.com"
    When I send a POST request to "/bliCommerce/UserController/Reset-Password-Link" with email "john.doe@test.com"
    Then the response status code should be 200
    And the response should indicate password reset link sent

  @PasswordReset @Negative
  Scenario: Request password reset link for non-existent email
    When I send a POST request to "/bliCommerce/UserController/Reset-Password-Link" with email "nonexistent@test.com"
    Then the response status code should be 200
    And the response should handle non-existent user gracefully

  @PasswordChange @HappyPath
  Scenario: Change password with valid token and new password
    Given I have a valid authentication token
    When I send a PUT request to "/bliCommerce/UserController/Update-Password" with:
      | token               | oldPassword | newPassword |
      | valid_token_here    | Test@123456 | NewPass@123 |
    Then the response status code should be 200
    And the response should indicate password changed successfully

  @AccountDeletion @HappyPath
  Scenario: Delete user account with valid authentication token
    Given I have a valid authentication token for deletion
    When I send a DELETE request to "/bliCommerce/UserController/Delete-User-Account" with token parameter
    Then the response status code should be 200
    And the response should indicate account deleted successfully

  @AccountDeletion @Negative
  Scenario: Delete account with invalid token should fail
    When I send a DELETE request to "/bliCommerce/UserController/Delete-User-Account" with token "invalid_token"
    Then the response status code should be 200
    And the response should indicate invalid token or authentication failure

  @AccountDeactivation @HappyPath
  Scenario: Deactivate user account with valid token
    Given I have a valid authentication token for deactivation
    When I send a PUT request to "/bliCommerce/UserController/Deactivate-User-Account" with token and action "deactivate"
    Then the response status code should be 200
    And the response should indicate account deactivated successfully

  @AccountDeactivation @HappyPath
  Scenario: Activate user account with valid token
    Given I have a valid authentication token for activation
    When I send a PUT request to "/bliCommerce/UserController/Deactivate-User-Account" with token and action "activate"
    Then the response status code should be 200
    And the response should indicate account activated successfully

  @AccountDeactivation @Negative
  Scenario: Deactivate account with invalid token should fail
    When I send a PUT request to "/bliCommerce/UserController/Deactivate-User-Account" with token "invalid_token" and action "deactivate"
    Then the response status code should be 200
    And the response should indicate invalid token or authentication failure
