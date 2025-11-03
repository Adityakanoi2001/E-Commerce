package com.example.UserModule.automation;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * Cucumber Test Runner
 * This class is used to execute all Gherkin feature files
 * 
 * To run specific tags, use Maven command:
 * mvn test -Dcucumber.filter.tags="@SignUp"
 * 
 * Available tags:
 * - @SignUp - sign-up scenarios
 * - @SignIn - sign-in scenarios  
 * - @PasswordReset - password reset scenarios
 * - @PasswordChange - password change scenarios
 * - @AccountDeletion - account deletion scenarios
 * - @AccountDeactivation - account deactivation scenarios
 * - @HappyPath - positive test scenarios
 * - @Negative - negative test scenarios
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, 
    value = "pretty, html:target/cucumber-reports/Cucumber.html, " +
            "json:target/cucumber-reports/Cucumber.json, " +
            "junit:target/cucumber-reports/Cucumber.xml")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, 
    value = "com.example.UserModule.automation.StepDefinitions")
public class TestRunner {
    // Test runner configuration - no code needed here
    // Configuration is done via annotations above
}

