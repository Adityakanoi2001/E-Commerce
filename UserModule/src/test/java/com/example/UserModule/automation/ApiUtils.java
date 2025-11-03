package com.example.UserModule.automation;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * Utility class for API testing with REST Assured
 * Provides common methods for making HTTP requests and validating responses
 */
public class ApiUtils {

    private static final String BASE_URI = "http://localhost";
    private static final int DEFAULT_PORT = 8800;
    private static String baseUrl = BASE_URI + ":" + DEFAULT_PORT;
    private static String authToken = null;

    static {
        // Configure REST Assured
        RestAssured.config = RestAssured.config()
            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        
        // Set base URI
        String customBaseUrl = System.getProperty("api.base.url");
        if (customBaseUrl != null && !customBaseUrl.isEmpty()) {
            baseUrl = customBaseUrl;
        }
        
        RestAssured.baseURI = baseUrl;
    }

    /**
     * Set the base URL for API testing
     * @param url The base URL (e.g., "http://localhost:8800")
     */
    public static void setBaseUrl(String url) {
        baseUrl = url;
        RestAssured.baseURI = baseUrl;
    }

    /**
     * Get the base URL
     */
    public static String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Store authentication token for subsequent requests
     */
    public static void setAuthToken(String token) {
        authToken = token;
    }

    /**
     * Get the stored authentication token
     */
    public static String getAuthToken() {
        return authToken;
    }

    /**
     * Create a base request specification with common headers
     */
    public static RequestSpecification getRequestSpec() {
        RequestSpecification request = RestAssured.given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON);

        // Add authentication token if available
        if (authToken != null && !authToken.isEmpty()) {
            request = request.header("Authorization", "Bearer " + authToken);
        }

        return request;
    }

    /**
     * Make a POST request
     */
    public static Response postRequest(String endpoint, Object body) {
        return getRequestSpec()
            .body(body)
            .when()
            .post(endpoint)
            .then()
            .extract()
            .response();
    }

    /**
     * Make a POST request with query parameters
     */
    public static Response postRequestWithParams(String endpoint, Map<String, String> params) {
        RequestSpecification request = getRequestSpec();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                request = request.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return request
            .when()
            .post(endpoint)
            .then()
            .extract()
            .response();
    }

    /**
     * Make a PUT request
     */
    public static Response putRequest(String endpoint, Object body) {
        return getRequestSpec()
            .body(body)
            .when()
            .put(endpoint)
            .then()
            .extract()
            .response();
    }

    /**
     * Make a PUT request with query parameters
     */
    public static Response putRequestWithParams(String endpoint, Map<String, String> params) {
        RequestSpecification request = getRequestSpec();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                request = request.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return request
            .when()
            .put(endpoint)
            .then()
            .extract()
            .response();
    }

    /**
     * Make a DELETE request with query parameters
     */
    public static Response deleteRequestWithParams(String endpoint, Map<String, String> params) {
        RequestSpecification request = getRequestSpec();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                request = request.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return request
            .when()
            .delete(endpoint)
            .then()
            .extract()
            .response();
    }

    /**
     * Make a GET request
     */
    public static Response getRequest(String endpoint) {
        return getRequestSpec()
            .when()
            .get(endpoint)
            .then()
            .extract()
            .response();
    }

    /**
     * Validate response status code
     */
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    /**
     * Check if response contains a specific string
     */
    public static boolean responseContains(Response response, String text) {
        String responseBody = response.getBody().asString();
        return responseBody.contains(text);
    }

    /**
     * Get response body as string
     */
    public static String getResponseBody(Response response) {
        return response.getBody().asString();
    }

    /**
     * Extract value from JSON response using JSON path
     */
    public static String extractJsonValue(Response response, String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }

    /**
     * Clear stored authentication token
     */
    public static void clearAuthToken() {
        authToken = null;
    }
}

