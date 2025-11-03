# Quick Start Guide - API Automation Testing

## 🚀 Getting Started in 5 Minutes

### Step 1: Start Your Application

Make sure your Spring Boot application is running:
```bash
mvn spring-boot:run
```

The application should start on `http://localhost:8800`

### Step 2: Run Your First Test

Run all tests:
```bash
mvn clean test
```

Or run a specific test tag:
```bash
# Test only sign-up functionality
mvn test -Dcucumber.filter.tags="@SignUp"

# Test only happy path scenarios
mvn test -Dcucumber.filter.tags="@HappyPath"
```

### Step 3: View Test Reports

Open the HTML report:
```bash
open target/cucumber-reports/Cucumber.html
```

Or navigate to: `target/cucumber-reports/Cucumber.html` in your browser.

## 📝 Understanding the Test Structure

### Feature File (Gherkin)
Location: `src/test/resources/features/user_controller.feature`

This file contains test scenarios written in plain English:
```gherkin
Scenario: Successful user sign-up
  Given I have a new user with valid data
  When I send a POST request to sign-up endpoint
  Then the response status code should be 200
```

### Step Definitions (Java)
Location: `src/test/java/.../StepDefinitions/UserControllerSteps.java`

This file maps Gherkin steps to actual test code:
```java
@When("I send a POST request to {string}")
public void iSendAPOSTRequest(String endpoint) {
    response = ApiUtils.postRequest(endpoint, data);
}
```

## 🎯 Common Commands

```bash
# Run all tests
mvn test

# Run with specific tag
mvn test -Dcucumber.filter.tags="@SignIn"

# Run multiple tags
mvn test -Dcucumber.filter.tags="@SignUp or @SignIn"

# Run with custom API URL
mvn test -Dapi.base.url=http://your-server:8800

# Skip tests during build
mvn clean install -DskipTests
```

## 🔧 Configuration

### Change API Base URL
Edit `src/test/java/.../ApiUtils.java` or set environment variable:
```bash
export API_BASE_URL=http://your-server:8800
```

### Change Test Data
Edit scenarios in `user_controller.feature` file.

## 📊 Test Tags Reference

| Tag | Description |
|-----|-------------|
| `@SignUp` | User registration tests |
| `@SignIn` | User authentication tests |
| `@PasswordReset` | Password reset tests |
| `@PasswordChange` | Password change tests |
| `@AccountDeletion` | Account deletion tests |
| `@AccountDeactivation` | Account activation/deactivation tests |
| `@HappyPath` | Positive test scenarios |
| `@Negative` | Negative/error test scenarios |

## ❓ Troubleshooting

**Tests can't connect to API:**
- Check if application is running: `curl http://localhost:8800/actuator/health`
- Verify port 8800 is correct

**Tests fail with database errors:**
- Ensure PostgreSQL and MongoDB are running
- Check `application.properties` for correct credentials

**Step definitions not found:**
- Verify package structure matches `TestRunner.java` configuration
- Run `mvn clean compile test-compile` to rebuild

## 🔄 Setting Up Daily Automation

### Option 1: GitHub Actions (Already configured)
The workflow file `.github/workflows/daily-api-tests.yml` is ready to use.

1. Push your code to GitHub
2. Go to Actions tab
3. The workflow will run automatically daily at 2:00 AM UTC
4. You can also manually trigger it

### Option 2: Jenkins (Already configured)
1. Create a new Pipeline job in Jenkins
2. Point it to the `Jenkinsfile` in your repository
3. Configure database credentials in Jenkins
4. Schedule will run daily at 2:00 AM

### Option 3: Local Cron Job
Add to your crontab:
```bash
0 2 * * * cd /path/to/UserModule && mvn test >> /var/log/api-tests.log 2>&1
```

## 📚 Next Steps

1. Review `AUTOMATION_README.md` for detailed documentation
2. Add more test scenarios in `user_controller.feature`
3. Customize step definitions as needed
4. Set up CI/CD pipeline for your team

## 💡 Pro Tips

1. **Use tags effectively**: Organize tests with meaningful tags
2. **Keep tests independent**: Each scenario should work standalone
3. **Use descriptive names**: Make test scenarios self-documenting
4. **Clean test data**: Use unique emails/data for each test run
5. **Review reports**: Check HTML reports regularly for insights

---

Need help? Check `AUTOMATION_README.md` for comprehensive documentation!

