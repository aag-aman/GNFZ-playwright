# Java Playwright Test Automation Framework

A comprehensive test automation framework for web application testing using Java, Playwright, JUnit 5, and Allure reporting.

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Running Tests](#running-tests)
- [Viewing Reports](#viewing-reports)
- [Project Structure](#project-structure)
- [Writing Tests](#writing-tests)
- [Troubleshooting](#troubleshooting)

---

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

### Required Software

1. **Java 21** (JDK)
   - Download from: https://www.oracle.com/java/technologies/downloads/#java21
   - Verify installation:
     ```bash
     java -version
     # Should show: java version "21.x.x"
     ```

2. **Maven 3.8+**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation:
     ```bash
     mvn -version
     # Should show: Apache Maven 3.x.x
     ```

3. **Git**
   - Download from: https://git-scm.com/downloads
   - Verify installation:
     ```bash
     git --version
     ```

### Optional (for Allure reports)

4. **Allure CLI** (for viewing reports locally)
   - Installation:
     ```bash
     # macOS (using Homebrew)
     brew install allure

     # Windows (using Scoop)
     scoop install allure

     # Linux (manual installation)
     # Download from: https://github.com/allure-framework/allure2/releases
     ```

5. **Node.js and npm** (for Playwright trace viewer)
   - Download from: https://nodejs.org/
   - Verify installation:
     ```bash
     node --version
     npm --version
     ```

---

## Project Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd java-playwright
```

### 2. Install Dependencies

Maven will automatically download all required dependencies when you run tests. To manually download dependencies:

```bash
mvn clean install -DskipTests
```

This will:
- Download Playwright (1.45.0)
- Download JUnit 5 (5.10.0)
- Download Jackson (2.15.2) for JSON handling
- Download Allure (2.24.0) for reporting
- Install Playwright browsers automatically

### 3. Install Playwright Browsers

Playwright requires browser binaries. Run:

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

Or simply run your first test - browsers will be installed automatically.

---

## Running Tests

### Basic Test Execution

#### Run All Tests
```bash
mvn test
```

#### Run Specific Test Class
```bash
mvn test -Dtest="LoginTest"
```

#### Run Specific Test Method
```bash
mvn test -Dtest="LoginTest#testSuccessfulLogin"
```

#### Run Tests by Package
```bash
# All authentication tests
mvn test -Dtest="tests.authentication.**.*"

# All login tests
mvn test -Dtest="tests.authentication.login.*"
```

### Test Profiles

#### Smoke Tests Only
```bash
mvn test -P smoke
```

#### Regression Tests Only
```bash
mvn test -P regression
```

### Browser Configuration

#### Run with Different Browsers
```bash
# Chrome (default)
mvn test -Dbrowser=chrome

# Firefox
mvn test -Dbrowser=firefox

# WebKit (Safari engine)
mvn test -Dbrowser=webkit
```

#### Headless Mode
```bash
# Run in headless mode (default)
mvn test -Dheadless=true

# Run with visible browser
mvn test -Dheadless=false
```

#### Slow Motion (for debugging)
```bash
mvn test -Dslowmo=true
```

### Combined Examples

```bash
# Run smoke tests in Firefox with visible browser
mvn test -P smoke -Dbrowser=firefox -Dheadless=false

# Run specific test in slow motion
mvn test -Dtest="LoginTest" -Dslowmo=true -Dheadless=false
```

---

## Viewing Reports

### 1. Allure Reports (Recommended)

#### Generate and Open Report
```bash
mvn allure:serve
```
This will:
- Generate the Allure report
- Start a local web server
- Automatically open the report in your browser

#### Alternative: Generate Static Report
```bash
mvn allure:report
# Report will be in: target/site/allure-maven-plugin/index.html
```

### 2. Surefire HTML Reports

#### Generate Report
```bash
mvn surefire-report:report
```
View at: `target/site/surefire-report.html`

#### Generate Full Site with Reports
```bash
mvn clean test site
```
View at: `target/site/index.html`

### 3. Playwright Traces (for debugging test failures)

#### Using Helper Script (Easiest)
```bash
./debug-failures.sh
```
This script will:
- List all test traces from the latest run
- Let you select which trace to view
- Open Playwright trace viewer automatically

#### Manual Trace Viewing
```bash
# View latest trace
npx playwright show-trace $(find test-results/traces -name "*.zip" | sort | tail -1)

# View specific trace
npx playwright show-trace test-results/traces/run_YYYYMMDD_HHMMSS/TestClass_testName.zip
```

#### Online Trace Viewer
You can also drag and drop trace `.zip` files to: https://trace.playwright.dev/

---

## Project Structure

```
java-playwright/
├── src/test/java/
│   ├── tests/                          # Test classes
│   │   ├── base/BaseTest.java         # Base class for all tests
│   │   ├── authentication/
│   │   │   ├── login/
│   │   │   │   ├── LoginTest.java           # Smoke tests
│   │   │   │   └── LoginRegressionTest.java # Regression tests
│   │   │   ├── forgot_password/
│   │   │   └── signup/
│   │   └── dashboard/
│   │       └── project/
│   │           ├── CreateProjectTest.java
│   │           └── building/
│   │               ├── BuildingProjectTest.java
│   │               └── BuildingProjectRegressionTest.java
│   │
│   ├── pages/                          # Page Object Model
│   │   ├── PageManager.java           # Singleton manager for all pages
│   │   ├── authentication/
│   │   │   ├── LoginPage.java
│   │   │   ├── ForgotPasswordPage.java
│   │   │   └── SignUpPage.java
│   │   ├── dashboard/
│   │   │   ├── ProjectListPage.java
│   │   │   ├── ProjectSelectionPage.java
│   │   │   └── project/
│   │   │       └── building/
│   │   │           ├── BuildingProjectPage.java      # Tab navigation
│   │   │           ├── BuildingBasicInfoTab.java     # Tab content
│   │   │           ├── BuildingAssessmentTab.java    # Tab with sub-sections
│   │   │           └── assessment/
│   │   │               ├── NetZeroEmissionsSection.java
│   │   │               ├── NetZeroWasteSection.java
│   │   │               ├── NetZeroEnergySection.java
│   │   │               └── NetZeroWaterSection.java
│   │   └── common/
│   │       └── NavbarPage.java
│   │
│   ├── utils/                          # Utility classes
│   │   ├── BrowserManager.java        # Browser configuration
│   │   ├── InputHelper.java           # Humanized input for form fields
│   │   ├── WaitHelper.java            # Intelligent waiting utilities
│   │   ├── AssertLogger.java          # Assertions with Allure logging
│   │   ├── ReportUtils.java           # Allure helpers
│   │   └── TestDataManager.java       # JSON test data loader
│   │
│   └── data/                           # Test data (JSON)
│       ├── smoke-users.json
│       ├── regression-users.json
│       ├── forgot-password-smoke.json
│       ├── forgot-password-regression.json
│       ├── signup-smoke.json
│       └── signup-regression.json
│
├── test-results/                       # Test artifacts (gitignored)
│   └── traces/                        # Playwright traces
│       └── run_YYYYMMDD_HHMMSS/       # One folder per run
│
├── target/                            # Maven build output (gitignored)
│   ├── allure-results/               # Allure raw data
│   └── surefire-reports/             # JUnit test results
│
├── pom.xml                            # Maven configuration
├── CLAUDE.md                          # Developer guide for AI assistants
├── WAIT_MIGRATION.md                  # Wait migration guide and patterns
├── debug-failures.sh                  # Helper script for traces
└── README.md                          # This file
```

---

## Writing Tests

### 1. Create a New Test Class

All test classes should:
- Extend `BaseTest`
- Use Allure annotations
- Access page objects via `pageManager`

```java
package tests.authentication.login;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Successful Login")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    void testSuccessfulLogin() {
        // Use PageManager to get page objects
        pageManager.getLoginPage().navigateToLogin();
        pageManager.getLoginPage().enterEmail("test@example.com");
        pageManager.getLoginPage().enterPassword("password");
        pageManager.getLoginPage().clickSignInButton();

        // Assertions
        assertTrue(pageManager.getProjectListPage().isProjectListVisible());

        // Optional: Take screenshot
        takeScreenshot("Successful Login");
    }
}
```

### 2. Create a Page Object

```java
package pages.authentication;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Locators - initialized once in constructor
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator signInButton;

    public LoginPage(Page page) {
        this.page = page;
        this.emailField = page.locator("#email");
        this.passwordField = page.locator("#password");
        this.signInButton = page.locator("#sign-in");
    }

    public void navigateToLogin() {
        page.navigate("https://example.com/login");
    }

    public void enterEmail(String email) {
        InputHelper.humanizedInput(page, emailField, email);
    }

    public void enterPassword(String password) {
        InputHelper.humanizedInput(page, passwordField, password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}
```

### 3. Add Page Object to PageManager

Edit `src/test/java/pages/PageManager.java`:

```java
private LoginPage loginPage;

public LoginPage getLoginPage() {
    if (loginPage == null) {
        loginPage = new LoginPage(page);
    }
    return loginPage;
}
```

### 4. Use WaitHelper for Dynamic Content

Always use `WaitHelper` instead of hardcoded `page.waitForTimeout()` for waiting on dynamic content:

```java
// Wait for auto-calculated totals
String total = WaitHelper.waitForCalculationComplete(page, totalLocator, 30000);

// Wait for new table row after add
int newCount = WaitHelper.waitForNewRow(page, tableRows, initialCount, 30000);

// Wait for custom condition
String value = WaitHelper.waitForCondition(
    () -> getTotal(),
    val -> val != null && !val.equals("0.00"),
    30000,
    "Total calculation timed out"
);

// Short animation waits (≤500ms) are still acceptable
section.expand();
page.waitForTimeout(500); // OK for UI animation
```

**See also:** `WAIT_MIGRATION.md` for comprehensive guide on wait patterns.

### 5. Add Test Data (JSON)

Create file in `src/test/java/data/`:

```json
[
  {
    "email": "test@example.com",
    "password": "password123",
    "expectedSuccess": "List of projects"
  }
]
```

Load in tests using `TestDataManager`:

```java
Map<String, String> user = TestDataManager.getSmokeUser();
String email = user.get("email");
```

---

## Troubleshooting

### Tests Not Running

**Issue**: No tests found
```bash
# Check if test classes are named correctly (*Test.java)
# Check test methods have @Test annotation
```

**Issue**: Browser not found
```bash
# Install Playwright browsers
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### Maven Errors

**Issue**: Java version mismatch
```bash
# Check Java version
java -version
# Should be Java 21

# Update JAVA_HOME if needed
export JAVA_HOME=$(/usr/libexec/java_home -v 21)  # macOS
```

**Issue**: Dependencies not downloading
```bash
# Clean and reinstall
mvn clean install -U -DskipTests
```

### Allure Reports Not Generating

**Issue**: `allure: command not found`
```bash
# Install Allure CLI
brew install allure  # macOS
```

**Issue**: Report empty or missing data
```bash
# Ensure tests ran successfully
mvn clean test
# Then generate report
mvn allure:serve
```

### Trace Viewer Issues

**Issue**: `npx playwright show-trace` not working
```bash
# Install Playwright CLI
npm install -g playwright
# Or use npx (automatically downloads if needed)
npx playwright show-trace <path-to-trace>
```

### Test Failures

1. **Check Allure Report** - Provides detailed error messages and screenshots
2. **View Playwright Trace** - Shows exact UI state when test failed
3. **Run in Visible Mode** - See what's happening:
   ```bash
   mvn test -Dtest="YourTest" -Dheadless=false -Dslowmo=true
   ```

---

## Additional Resources

- **Playwright Java Docs**: https://playwright.dev/java/
- **JUnit 5 Docs**: https://junit.org/junit5/docs/current/user-guide/
- **Allure Docs**: https://docs.qameta.io/allure/
- **Maven Docs**: https://maven.apache.org/guides/

For more detailed information about the framework architecture and patterns, see [CLAUDE.md](CLAUDE.md).

---

## Support

For questions or issues:
1. Check the [Troubleshooting](#troubleshooting) section
2. Review test traces: `./debug-failures.sh`
3. Check Allure reports: `mvn allure:serve`
4. Contact the QA team

---

**Happy Testing! 🚀**
