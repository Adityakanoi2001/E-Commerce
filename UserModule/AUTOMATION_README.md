# API Automation Testing with Cucumber/Gherkin

## Overview

This project uses **Gherkin** (Behavior-Driven Development language) with **Cucumber** and **REST Assured** to automate API testing for the UserController microservice.

## What is Gherkin?

**Gherkin** is a plain-language syntax that allows you to write test scenarios in a human-readable format. It uses keywords like:
- `Feature`: Describes what is being tested
- `Scenario`: Describes a specific test case
- `Given`: Sets up the initial context
- `When`: Describes the action being performed
- `Then`: Describes the expected outcome
- `And`: Adds additional context or assertions

**Example:**
```gherkin
Scenario: Successful user sign-up
  Given I have a new user with valid data
  When I send a POST request to sign-up endpoint
  Then the response status code should be 200
  And the user should be created successfully
```

## Project Structure

```
src/test/
├── java/com/example/UserModule/automation/
│   ├── ApiUtils.java                          # REST Assured utilities
│   ├── TestRunner.java                        # Cucumber test runner
│   └── StepDefinitions/
│       └── UserControllerSteps.java           # Step definitions mapping Gherkin to Java
└── resources/features/
    └── user_controller.feature                 # Gherkin feature file
```

## Running the Tests

### Prerequisites
1. Java 21 installed
2. Maven installed
3. Application running on port 8800 (or configure custom port)
4. PostgreSQL and MongoDB databases running

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Tags

```bash
# Run only sign-up tests
mvn test -Dcucumber.filter.tags="@SignUp"

# Run only happy path tests
mvn test -Dcucumber.filter.tags="@HappyPath"

# Run only negative tests
mvn test -Dcucumber.filter.tags="@Negative"

# Run multiple tags
mvn test -Dcucumber.filter.tags="@SignUp or @SignIn"
```

### Run with Custom Base URL

```bash
mvn test -Dapi.base.url=http://your-server:8800
```

## Available Test Tags

- `@SignUp` - User registration tests
- `@SignIn` - User authentication tests
- `@PasswordReset` - Password reset functionality tests
- `@PasswordChange` - Password change functionality tests
- `@AccountDeletion` - Account deletion tests
- `@AccountDeactivation` - Account activation/deactivation tests
- `@HappyPath` - Positive test scenarios
- `@Negative` - Negative test scenarios (error cases)

## Test Reports

After running tests, reports are generated in:
- **HTML Report**: `target/cucumber-reports/Cucumber.html`
- **JSON Report**: `target/cucumber-reports/Cucumber.json`
- **XML Report**: `target/cucumber-reports/Cucumber.xml` (for CI/CD integration)

To view HTML report:
```bash
open target/cucumber-reports/Cucumber.html
```

## CI/CD Integration

### GitHub Actions

The workflow file (`.github/workflows/daily-api-tests.yml`) is configured to:
- Run tests daily at 2:00 AM UTC
- Run on push to main/develop branches
- Start PostgreSQL and MongoDB services
- Start the Spring Boot application
- Execute all API tests
- Generate and upload test reports
- Send notifications on failure

### Jenkins

The `Jenkinsfile` is configured to:
- Run tests daily at 2:00 AM
- Start required database services
- Execute tests and publish results
- Send email notifications

## Configuration

### Application Properties

Ensure your `application.properties` has correct database configurations:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_users
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.data.mongodb.uri=mongodb://localhost:27017/ecommerce
server.port=8800
```

### Test Configuration

Update `ApiUtils.java` if your API runs on a different port or URL.

## Adding New Test Scenarios

1. **Add Scenario to Feature File** (`user_controller.feature`):
```gherkin
@NewFeature
Scenario: Test new functionality
  Given some initial condition
  When I perform an action
  Then I should see expected result
```

2. **Implement Step Definitions** (`UserControllerSteps.java`):
```java
@When("I perform an action")
public void iPerformAnAction() {
    // Implementation using REST Assured
    response = ApiUtils.postRequest("/endpoint", data);
}
```

3. **Run the Test**:
```bash
mvn test -Dcucumber.filter.tags="@NewFeature"
```

## Best Practices

1. **Use Descriptive Scenario Names**: Clearly describe what is being tested
2. **Keep Steps Reusable**: Write generic step definitions that can be reused
3. **Tag Appropriately**: Use tags to organize and filter tests
4. **Verify Both Success and Failure**: Test both happy paths and error cases
5. **Keep Test Data Clean**: Use unique test data to avoid conflicts
6. **Document Complex Scenarios**: Add comments in feature files for clarity

## Troubleshooting

### Tests Fail to Connect to API
- Verify application is running: `curl http://localhost:8800/actuator/health`
- Check port configuration in `ApiUtils.java`
- Verify firewall/network settings

### Database Connection Issues
- Ensure PostgreSQL and MongoDB are running
- Verify database credentials in `application.properties`
- Check database connectivity: `psql -h localhost -U username -d database`

### Step Definitions Not Found
- Verify `@SelectClasspathResource("features")` in `TestRunner.java`
- Check package name matches in `@ConfigurationParameter` for GLUE
- Ensure step definitions are in correct package structure

## Daily Automation Pipeline

The CI/CD pipeline is configured to run tests automatically:
- **GitHub Actions**: Runs daily at 2:00 AM UTC
- **Jenkins**: Runs daily at 2:00 AM (configurable)

### Manual Trigger
- **GitHub Actions**: Go to Actions tab → "Daily API Automation Tests" → "Run workflow"
- **Jenkins**: Click "Build Now" on the pipeline job

## Support

For issues or questions:
1. Check test reports for detailed error messages
2. Review application logs
3. Verify all prerequisites are met
4. Contact the development team

