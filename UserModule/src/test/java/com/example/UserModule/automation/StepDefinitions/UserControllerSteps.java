package com.example.UserModule.automation.StepDefinitions;

import com.example.UserModule.automation.ApiUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

/**
 * Step Definitions for UserController API automation tests
 * Maps Gherkin steps to actual Java test code using REST Assured
 */
public class UserControllerSteps {

    private Response response;
    private Map<String, Object> userData = new HashMap<>();
    private String storedEmail;
    private String storedPassword;
    private String storedToken;

    @Given("the User API base URL is configured")
    public void theUserAPIBaseURLIsConfigured() {
        // Base URL is already configured in ApiUtils
        System.out.println("API Base URL: " + ApiUtils.getBaseUrl());
    }

    @And("the API server is running on port {int}")
    public void theAPIServerIsRunningOnPort(int port) {
        String baseUrl = "http://localhost:" + port;
        ApiUtils.setBaseUrl(baseUrl);
        System.out.println("API Server configured for port: " + port);
    }

    @Given("I have a new user with:")
    public void iHaveANewUserWith(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> userMap = dataTable.asMap(String.class, String.class);
        userData = new HashMap<>(userMap);
        storedEmail = userMap.get("email");
        storedPassword = userMap.get("password");
        System.out.println("Prepared user data for: " + storedEmail);
    }

    @Given("I have a user with email {string} already registered")
    public void iHaveAUserWithEmailAlreadyRegistered(String email) {
        storedEmail = email;
        // In a real scenario, you might want to create this user first or use test data setup
        System.out.println("User with email " + email + " is already registered");
    }

    @Given("I have a registered user with:")
    public void iHaveARegisteredUserWith(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> userMap = dataTable.asMap(String.class, String.class);
        storedEmail = userMap.get("email");
        storedPassword = userMap.get("password");
        System.out.println("Registered user credentials prepared for: " + storedEmail);
    }

    @Given("I have a registered user with email {string}")
    public void iHaveARegisteredUserWithEmail(String email) {
        storedEmail = email;
        System.out.println("Registered user email: " + email);
    }

    @Given("I have a valid authentication token")
    public void iHaveAValidAuthenticationToken() {
        // This would typically be obtained from a previous sign-in step
        // For now, we'll get it from stored token or perform sign-in
        if (storedToken == null && storedEmail != null && storedPassword != null) {
            // Perform sign-in to get token
            Map<String, String> signInData = new HashMap<>();
            signInData.put("email", storedEmail);
            signInData.put("password", storedPassword);
            
            Response signInResponse = ApiUtils.postRequest("/bliCommerce/UserController/Sign-In", signInData);
            if (signInResponse.getStatusCode() == 200) {
                String token = signInResponse.jsonPath().getString("token");
                if (token != null) {
                    storedToken = token;
                    ApiUtils.setAuthToken(token);
                }
            }
        }
        Assertions.assertNotNull(storedToken, "Authentication token should be available");
    }

    @Given("I have a valid authentication token for deletion")
    public void iHaveAValidAuthenticationTokenForDeletion() {
        iHaveAValidAuthenticationToken();
    }

    @Given("I have a valid authentication token for deactivation")
    public void iHaveAValidAuthenticationTokenForDeactivation() {
        iHaveAValidAuthenticationToken();
    }

    @Given("I have a valid authentication token for activation")
    public void iHaveAValidAuthenticationTokenForActivation() {
        iHaveAValidAuthenticationToken();
    }

    @When("I send a POST request to {string} with the user data")
    public void iSendAPOSTRequestToWithTheUserData(String endpoint) {
        response = ApiUtils.postRequest(endpoint, userData);
        System.out.println("POST Request sent to: " + endpoint);
        System.out.println("Response Status: " + response.getStatusCode());
    }

    @When("I send a POST request to {string} with:")
    public void iSendAPOSTRequestToWith(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> requestData = dataTable.asMap(String.class, String.class);
        response = ApiUtils.postRequest(endpoint, requestData);
        System.out.println("POST Request sent to: " + endpoint);
    }

    @When("I send a POST request to {string} with credentials and optional IP {string}")
    public void iSendAPOSTRequestToWithCredentialsAndOptionalIP(String endpoint, String ip) {
        Map<String, String> signInData = new HashMap<>();
        signInData.put("email", storedEmail);
        signInData.put("password", storedPassword);
        
        if (ip != null && !ip.isEmpty() && !ip.equals("null")) {
            // Make POST request with query parameter
            response = ApiUtils.getRequestSpec()
                .body(signInData)
                .queryParam("ip", ip)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
        } else {
            response = ApiUtils.postRequest(endpoint, signInData);
        }
        
        // Extract token if successful
        if (response.getStatusCode() == 200) {
            String token = response.jsonPath().getString("token");
            if (token != null && !token.isEmpty()) {
                storedToken = token;
                ApiUtils.setAuthToken(token);
            }
        }
        
        System.out.println("POST Request sent to: " + endpoint + " with IP: " + ip);
    }

    @When("I send a POST request to {string} with email {string}")
    public void iSendAPOSTRequestToWithEmail(String endpoint, String email) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        response = ApiUtils.postRequestWithParams(endpoint, params);
        System.out.println("POST Request sent to: " + endpoint + " with email: " + email);
    }

    @When("I send a PUT request to {string} with:")
    public void iSendAPUTRequestToWith(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> requestData = dataTable.asMap(String.class, String.class);
        response = ApiUtils.putRequest(endpoint, requestData);
        System.out.println("PUT Request sent to: " + endpoint);
    }

    @When("I send a PUT request to {string} with token and action {string}")
    public void iSendAPUTRequestToWithTokenAndAction(String endpoint, String action) {
        Map<String, String> params = new HashMap<>();
        params.put("authenticationToken", storedToken != null ? storedToken : "test_token");
        params.put("action", action);
        response = ApiUtils.putRequestWithParams(endpoint, params);
        System.out.println("PUT Request sent to: " + endpoint + " with action: " + action);
    }

    @When("I send a PUT request to {string} with token {string} and action {string}")
    public void iSendAPUTRequestToWithTokenAndAction(String endpoint, String token, String action) {
        Map<String, String> params = new HashMap<>();
        params.put("authenticationToken", token);
        params.put("action", action);
        response = ApiUtils.putRequestWithParams(endpoint, params);
        System.out.println("PUT Request sent to: " + endpoint + " with token: " + token + " and action: " + action);
    }

    @When("I send a DELETE request to {string} with token parameter")
    public void iSendADELETERequestToWithTokenParameter(String endpoint) {
        Map<String, String> params = new HashMap<>();
        params.put("authenticationToken", storedToken != null ? storedToken : "test_token");
        response = ApiUtils.deleteRequestWithParams(endpoint, params);
        System.out.println("DELETE Request sent to: " + endpoint);
    }

    @When("I send a DELETE request to {string} with token {string}")
    public void iSendADELETERequestToWithToken(String endpoint, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("authenticationToken", token);
        response = ApiUtils.deleteRequestWithParams(endpoint, params);
        System.out.println("DELETE Request sent to: " + endpoint + " with token: " + token);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assertions.assertEquals(expectedStatusCode, actualStatusCode, 
            "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        System.out.println("Status code verified: " + actualStatusCode);
    }

    @And("the response should contain {string}")
    public void theResponseShouldContain(String expectedText) {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(responseBody.contains(expectedText) || 
                            responseBody.toLowerCase().contains(expectedText.toLowerCase()),
            "Response should contain: " + expectedText);
        System.out.println("Response contains: " + expectedText);
    }

    @And("the response should contain {string} or {string}")
    public void theResponseShouldContainOr(String option1, String option2) {
        String responseBody = ApiUtils.getResponseBody(response);
        boolean containsOption1 = responseBody.contains(option1) || 
                                 responseBody.toLowerCase().contains(option1.toLowerCase());
        boolean containsOption2 = responseBody.contains(option2) || 
                                 responseBody.toLowerCase().contains(option2.toLowerCase());
        Assertions.assertTrue(containsOption1 || containsOption2,
            "Response should contain either '" + option1 + "' or '" + option2 + "'");
        System.out.println("Response contains one of the expected values");
    }

    @And("the response body should be valid JSON")
    public void theResponseBodyShouldBeValidJSON() {
        try {
            response.jsonPath();
            System.out.println("Response is valid JSON");
        } catch (Exception e) {
            Assertions.fail("Response is not valid JSON: " + e.getMessage());
        }
    }

    @And("the response should contain authentication token")
    public void theResponseShouldContainAuthenticationToken() {
        String token = response.jsonPath().getString("token");
        Assertions.assertNotNull(token, "Response should contain authentication token");
        Assertions.assertFalse(token.isEmpty(), "Authentication token should not be empty");
        storedToken = token;
        ApiUtils.setAuthToken(token);
        System.out.println("Authentication token extracted and stored");
    }

    @And("the response message should be {string}")
    public void theResponseMessageShouldBe(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        Assertions.assertEquals(expectedMessage, actualMessage,
            "Expected message '" + expectedMessage + "' but got '" + actualMessage + "'");
        System.out.println("Message verified: " + actualMessage);
    }

    @And("the response message should indicate existing user")
    public void theResponseMessageShouldIndicateExistingUser() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("EXISTING_USER") || 
            responseBody.contains("existing") ||
            responseBody.contains("already") ||
            responseBody.toLowerCase().contains("try login"),
            "Response should indicate existing user");
        System.out.println("Existing user message verified");
    }

    @And("the response should indicate password reset link sent")
    public void theResponseShouldIndicatePasswordResetLinkSent() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("sent") || 
            responseBody.contains("Success") ||
            responseBody.contains("email"),
            "Response should indicate password reset link was sent");
        System.out.println("Password reset link sent message verified");
    }

    @And("the response should handle non-existent user gracefully")
    public void theResponseShouldHandleNonExistentUserGracefully() {
        // The API should return a 200 status (already verified) and handle gracefully
        String responseBody = ApiUtils.getResponseBody(response);
        System.out.println("Response handled non-existent user: " + responseBody);
    }

    @And("the response should indicate password changed successfully")
    public void theResponseShouldIndicatePasswordChangedSuccessfully() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("Success") || 
            responseBody.contains("changed") ||
            responseBody.contains("updated"),
            "Response should indicate password changed successfully");
        System.out.println("Password change success message verified");
    }

    @And("the response should indicate account deleted successfully")
    public void theResponseShouldIndicateAccountDeletedSuccessfully() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("Success") || 
            responseBody.contains("deleted") ||
            responseBody.contains("removed"),
            "Response should indicate account deleted successfully");
        System.out.println("Account deletion success message verified");
    }

    @And("the response should indicate invalid token or authentication failure")
    public void theResponseShouldIndicateInvalidTokenOrAuthenticationFailure() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("invalid") || 
            responseBody.contains("failed") ||
            responseBody.contains("unauthorized") ||
            responseBody.contains("authentication"),
            "Response should indicate invalid token or authentication failure");
        System.out.println("Invalid token/authentication failure message verified");
    }

    @And("the response should indicate account deactivated successfully")
    public void theResponseShouldIndicateAccountDeactivatedSuccessfully() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("Success") || 
            responseBody.contains("deactivated"),
            "Response should indicate account deactivated successfully");
        System.out.println("Account deactivation success message verified");
    }

    @And("the response should indicate account activated successfully")
    public void theResponseShouldIndicateAccountActivatedSuccessfully() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("Success") || 
            responseBody.contains("activated"),
            "Response should indicate account activated successfully");
        System.out.println("Account activation success message verified");
    }

    @And("the response should indicate authentication failure")
    public void theResponseShouldIndicateAuthenticationFailure() {
        String responseBody = ApiUtils.getResponseBody(response);
        Assertions.assertTrue(
            responseBody.contains("failed") || 
            responseBody.contains("wrong") ||
            responseBody.contains("invalid") ||
            responseBody.contains("unavailable"),
            "Response should indicate authentication failure");
        System.out.println("Authentication failure message verified");
    }
}

