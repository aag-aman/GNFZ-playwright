# Playwright Testing Guide for Beginners

## Table of Contents
1. [Introduction](#introduction)
2. [Project Structure](#project-structure)
3. [Basic Concepts](#basic-concepts)
4. [Locator Strategies](#locator-strategies)
5. [Common Actions](#common-actions)
6. [Form Interactions](#form-interactions)
7. [Assertions](#assertions)
8. [Advanced Patterns](#advanced-patterns)
9. [Creating New Tests](#creating-new-tests)
10. [Best Practices](#best-practices)
11. [Troubleshooting](#troubleshooting)

---

## Introduction

Playwright is a powerful automation library for testing web applications. This guide will teach you how to write effective tests using our project structure, with practical examples you can copy and modify.

### What You'll Learn
- How to locate elements on web pages
- Common user interactions (clicking, typing, selecting)
- How to verify expected behavior
- Writing maintainable test code
- Debugging techniques

---

## Project Structure

Our project follows industry best practices with organized folders:

```
src/test/java/
├── pages/                     # Page Object Models
│   ├── authentication/       # Login, registration pages
│   ├── dashboard/            # Dashboard-related pages
│   ├── common/               # Shared components (navbar, etc.)
│   └── PageManager.java      # Manages all page objects
├── tests/
│   ├── base/
│   │   └── BaseTest.java     # Common test setup
│   ├── smoke/                # Critical path tests
│   └── regression/           # Comprehensive tests
├── utils/                    # Helper utilities
└── data/                     # Test data files
```

### Key Files You'll Work With
- **BaseTest.java**: Extend this for all your tests
- **PageManager**: Access all page objects through this
- **Test Data**: JSON files with test inputs

---

## Basic Concepts

### Test Structure
Every test follows this pattern:

```java
@Test
@DisplayName("Descriptive test name")
@Description("What this test validates")
@Story("User story being tested")
@Severity(SeverityLevel.CRITICAL)  // or HIGH, MEDIUM, LOW
void testMethodName() {
    // 1. Setup (handled by BaseTest)
    // 2. Navigate to page
    // 3. Perform actions
    // 4. Verify results
    // 5. Cleanup (handled by BaseTest)
}
```

### Page Object Model
Instead of writing locators directly in tests, we use Page Objects:

```java
// ❌ Don't do this in tests
page.locator("#email").fill("user@example.com");

// ✅ Do this instead
pageManager.getLoginPage().enterEmail("user@example.com");
```

---

## Locator Strategies

Locators are how Playwright finds elements on the page. Choose the right strategy for reliability and maintainability.

### 1. By Test ID (Most Reliable)
```java
// HTML: <button data-testid="submit-button">Submit</button>
Locator submitButton = page.locator("[data-testid='submit-button']");
```

### 2. By ID (Very Reliable)
```java
// HTML: <input id="username" />
Locator usernameField = page.locator("#username");
```

### 3. By Role (Semantic and Accessible)
```java
// For buttons
Locator loginButton = page.getByRole("button", new Page.GetByRoleOptions().setName("Login"));

// For links
Locator homeLink = page.getByRole("link", new Page.GetByRoleOptions().setName("Home"));

// For textboxes
Locator emailField = page.getByRole("textbox", new Page.GetByRoleOptions().setName("Email"));

// For dropdowns/comboboxes
Locator countrySelect = page.getByRole("combobox", new Page.GetByRoleOptions().setName("Country"));
```

### 4. By Text Content
```java
// Exact text match
Locator saveButton = page.getByText("Save Changes");

// Partial text match
Locator welcomeMessage = page.getByText("Welcome back");

// Case insensitive
Locator signUpLink = page.getByText("sign up", new Page.GetByTextOptions().setExact(false));
```

### 5. By Label (For Form Fields)
```java
// HTML: <label for="email">Email Address</label><input id="email" />
Locator emailField = page.getByLabel("Email Address");
```

### 6. By Placeholder
```java
// HTML: <input placeholder="Enter your email" />
Locator emailField = page.getByPlaceholder("Enter your email");
```

### 7. CSS Selectors
```java
// Class names
Locator errorMessage = page.locator(".error-message");

// Attributes
Locator submitButton = page.locator("button[type='submit']");

// Combinators
Locator firstTableRow = page.locator("table tr:first-child");

// Child selectors
Locator navItems = page.locator("nav > ul > li");
```

### 8. XPath (Use Sparingly)
```java
// Simple xpath
Locator submitButton = page.locator("//button[@type='submit']");

// Complex xpath with text
Locator specificButton = page.locator("//button[contains(text(), 'Save')]");

// Following siblings
Locator nextField = page.locator("//label[text()='Username']//following-sibling::input");
```

### Locator Best Practices
```java
// ✅ Prefer stable selectors
page.locator("[data-testid='user-menu']")     // Best
page.locator("#user-menu")                    // Good
page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Menu"))  // Good

// ⚠️ Use with caution
page.locator(".btn-primary")                  // Can break with styling changes
page.locator("div:nth-child(3)")             // Breaks if DOM structure changes

// ❌ Avoid
page.locator("body > div > div > button")     // Too fragile
```

---

## Common Actions

### Clicking Elements
```java
// Simple click
page.locator("#save-button").click();

// Click with options
page.locator("#menu-button").click(new Locator.ClickOptions()
    .setButton(MouseButton.RIGHT)     // Right click
    .setClickCount(2));               // Double click

// Click and wait for navigation
page.locator("#submit").click();
page.waitForURL("**/dashboard");

// Force click (bypass actionability checks)
page.locator("#hidden-button").click(new Locator.ClickOptions().setForce(true));
```

### Typing Text
```java
// Clear and type
page.locator("#search").fill("playwright testing");

// Type without clearing (append)
page.locator("#comments").type("Additional text");

// Type with delay (simulate human typing)
page.locator("#message").type("slow typing", new Locator.TypeOptions().setDelay(100));

// Press special keys
page.locator("#search").press("Enter");
page.locator("#text-area").press("Control+A");  // Select all
```

### Handling Dropdowns
```java
// Select by visible text
page.locator("#country").selectOption("United States");

// Select by value
page.locator("#country").selectOption(new SelectOption().setValue("US"));

// Select by index
page.locator("#country").selectOption(new SelectOption().setIndex(0));

// Select multiple options
page.locator("#languages").selectOption(new String[]{"English", "Spanish"});

// Custom dropdown (not <select> element)
page.locator("#custom-dropdown").click();
page.locator("#option-value-1").click();
```

### Checkboxes and Radio Buttons
```java
// Check a checkbox
page.locator("#agree-terms").check();

// Uncheck a checkbox
page.locator("#newsletter").uncheck();

// Check only if not already checked
if (!page.locator("#remember-me").isChecked()) {
    page.locator("#remember-me").check();
}

// Radio buttons
page.locator("input[value='male']").check();
```

### File Uploads
```java
// Single file upload
page.locator("#file-upload").setInputFiles(Paths.get("test-data/sample.pdf"));

// Multiple files
page.locator("#file-upload").setInputFiles(new Path[]{
    Paths.get("file1.jpg"),
    Paths.get("file2.png")
});

// Remove files
page.locator("#file-upload").setInputFiles(new Path[0]);
```

### Hovering
```java
// Hover over element
page.locator("#tooltip-trigger").hover();

// Hover and then click on revealed element
page.locator("#menu-button").hover();
page.locator("#submenu-item").click();
```

---

## Form Interactions

### Complete Form Example
```java
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Test
void testUserRegistration() {
    // Navigate to registration page
    pageManager.getRegistrationPage().navigateToRegistration();

    // Fill form fields
    pageManager.getRegistrationPage().enterFirstName("John");
    pageManager.getRegistrationPage().enterLastName("Doe");
    pageManager.getRegistrationPage().enterEmail("john.doe@example.com");
    pageManager.getRegistrationPage().enterPassword("SecurePass123!");
    pageManager.getRegistrationPage().confirmPassword("SecurePass123!");

    // Select from dropdown
    pageManager.getRegistrationPage().selectCountry("United States");

    // Check agreements
    pageManager.getRegistrationPage().acceptTerms();
    pageManager.getRegistrationPage().subscribeNewsletter(false);

    // Submit form
    pageManager.getRegistrationPage().submitForm();

    // Verify success
    assertThat(page.locator(".success-message")).containsText("Registration successful");
}
```

### Form Validation Testing
```java
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Test
void testFormValidation() {
    pageManager.getRegistrationPage().navigateToRegistration();

    // Submit empty form
    pageManager.getRegistrationPage().submitForm();

    // Verify error messages
    assertThat(page.locator("#email-error")).containsText("Email is required");
    assertThat(page.locator("#password-error")).containsText("Password is required");

    // Test invalid email format
    pageManager.getRegistrationPage().enterEmail("invalid-email");
    pageManager.getRegistrationPage().submitForm();
    assertThat(page.locator("#email-error")).containsText("Please enter a valid email");
}
```

---

## Assertions

Assertions verify that your application behaves as expected.

### Element State Assertions
```java
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
// Visibility
assertThat(page.locator("#success-message")).isVisible();
assertThat(page.locator("#loading-spinner")).isHidden();

// Text content
assertThat(page.locator("#welcome-message")).hasText("Welcome, John!");
assertThat(page.locator("#error")).containsText("Invalid credentials");

// Attributes
assertThat(page.locator("#submit-button")).hasAttribute("disabled", "");
assertThat(page.locator("#link")).hasAttribute("href", "https://example.com");

// Form fields
assertThat(page.locator("#email")).hasValue("user@example.com");
assertThat(page.locator("#remember-me")).isChecked();

// CSS properties
assertThat(page.locator("#error")).hasCSS("color", "rgb(255, 0, 0)");

// Count
assertThat(page.locator(".todo-item")).hasCount(5);
```

### Page Assertions
```java
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.regex.Pattern;

// URL
assertThat(page).hasURL("https://example.com/dashboard");
assertThat(page).hasURL(Pattern.compile(".*/dashboard.*"));

// Title
assertThat(page).hasTitle("My Dashboard");
assertThat(page).hasTitle(Pattern.compile(".*Dashboard.*"));
```

### Custom Assertions
```java
import static org.junit.jupiter.api.Assertions.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
// Using JUnit assertions with Playwright queries
String actualText = page.locator("#result").textContent();
assertEquals("Expected Result", actualText);

boolean isElementVisible = page.locator("#element").isVisible();
assertTrue(isElementVisible, "Element should be visible");

// With custom error messages
assertThat(page.locator("#status"))
    .hasText("Active", "User status should be Active after login");
```

---

## Advanced Patterns

### Waiting Strategies
```java
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import java.util.regex.Pattern;
// Wait for element to be visible
page.locator("#loading").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

// Wait for element to be hidden
page.locator("#loading").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

// Wait for page load
page.waitForLoadState();
page.waitForLoadState(LoadState.NETWORKIDLE);  // Wait until no network activity

// Wait for URL
page.waitForURL("**/dashboard");
page.waitForURL(java.util.regex.Pattern.compile(".*/profile/\\d+"));

// Wait for specific condition using custom wait
page.waitForFunction("() => document.querySelectorAll('.item').length > 5");

// Wait with timeout
page.locator("#slow-element").waitFor(new Locator.WaitForOptions()
    .setState(WaitForSelectorState.VISIBLE)
    .setTimeout(10000));  // 10 seconds
```

### Handling Multiple Elements
```java
// Get all matching elements
Locator allItems = page.locator(".list-item");

// Count elements
int itemCount = allItems.count();

// Iterate through elements
for (int i = 0; i < allItems.count(); i++) {
    String itemText = allItems.nth(i).textContent();
    System.out.println("Item " + i + ": " + itemText);
}

// Find specific element in list
Locator specificItem = page.locator(".list-item").filter(new Locator.FilterOptions()
    .setHasText("Specific Item"));

// First and last elements
Locator firstItem = allItems.first();
Locator lastItem = allItems.last();
```

### Handling Popups and Dialogs
```java
// Handle JavaScript alerts
page.onDialog(dialog -> {
    System.out.println("Dialog message: " + dialog.message());
    dialog.accept();  // or dialog.dismiss()
});

// Handle new pages/windows
Page popup = page.waitForPopup(() -> {
    page.locator("#open-popup").click();
});
popup.locator("#popup-content").click();
popup.close();
```

### Working with Tables
```java
// Find table rows
Locator tableRows = page.locator("table tbody tr");

// Find specific row by content
Locator userRow = page.locator("tr").filter(new Locator.FilterOptions()
    .setHasText("john.doe@example.com"));

// Get cell value
String cellValue = page.locator("tr:nth-child(2) td:nth-child(3)").textContent();

// Click button in specific row
page.locator("tr").filter(new Locator.FilterOptions()
    .setHasText("john.doe@example.com"))
    .locator("button[data-action='edit']")
    .click();
```

---

## Creating New Tests

### Step 1: Identify What to Test
Before writing code, answer:
- What user flow are you testing?
- What should happen when the flow succeeds?
- What could go wrong?
- What data do you need?

### Step 2: Create Test Data
Add test data to appropriate JSON file:

```json
// data/user-management-users.json
[
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@test.com",
    "role": "admin",
    "expectedResult": "success"
  },
  {
    "firstName": "",
    "lastName": "Smith",
    "email": "invalid-email",
    "role": "user",
    "expectedResult": "error",
    "expectedError": "First name is required"
  }
]
```

### Step 3: Create Page Object (if needed)
```java
// pages/administration/UserManagementPage.java
package pages.administration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UserManagementPage {
    private final Page page;

    // Locators
    private final Locator addUserButton;
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator emailField;
    private final Locator roleDropdown;
    private final Locator saveButton;
    private final Locator successMessage;
    private final Locator errorMessage;

    public UserManagementPage(Page page) {
        this.page = page;

        // Initialize locators
        this.addUserButton = page.getByRole("button",
            new Page.GetByRoleOptions().setName("Add User"));
        this.firstNameField = page.getByLabel("First Name");
        this.lastNameField = page.getByLabel("Last Name");
        this.emailField = page.getByLabel("Email");
        this.roleDropdown = page.getByLabel("Role");
        this.saveButton = page.getByRole("button",
            new Page.GetByRoleOptions().setName("Save"));
        this.successMessage = page.locator(".alert-success");
        this.errorMessage = page.locator(".alert-error");
    }

    public void navigateToUserManagement() {
        page.navigate("/admin/users");
    }

    public void clickAddUser() {
        addUserButton.click();
    }

    public void enterFirstName(String firstName) {
        firstNameField.fill(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.fill(lastName);
    }

    public void enterEmail(String email) {
        emailField.fill(email);
    }

    public void selectRole(String role) {
        roleDropdown.selectOption(role);
    }

    public void saveUser() {
        saveButton.click();
    }

    public String getSuccessMessage() {
        return successMessage.textContent();
    }

    public String getErrorMessage() {
        return errorMessage.textContent();
    }

    public boolean isSuccessMessageVisible() {
        return successMessage.isVisible();
    }

    public boolean isErrorMessageVisible() {
        return errorMessage.isVisible();
    }
}
```

### Step 4: Add to PageManager
```java
// Add to PageManager.java
private UserManagementPage userManagementPage;

public UserManagementPage getUserManagementPage() {
    if (userManagementPage == null) {
        userManagementPage = new UserManagementPage(page);
    }
    return userManagementPage;
}
```

### Step 5: Create Test Class
```java
// tests/regression/UserManagementTest.java
package tests.regression;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.base.BaseTest;
import utils.TestDataManager;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

@Epic("User Management")
@Feature("User Administration")
public class UserManagementTest extends BaseTest {

    static Stream<Map<String, String>> userData() throws IOException {
        return TestDataManager.loadTestData("user-management-users.json").stream();
    }

    @ParameterizedTest(name = "Create User: {0}")
    @MethodSource("userData")
    @DisplayName("User Creation - All Scenarios")
    @Description("Test user creation with valid and invalid data")
    @Story("Create User")
    @Severity(SeverityLevel.HIGH)
    void testUserCreation(Map<String, String> testData) {
        String firstName = testData.get("firstName");
        String lastName = testData.get("lastName");
        String email = testData.get("email");
        String role = testData.get("role");
        String expectedResult = testData.get("expectedResult");
        String expectedError = testData.get("expectedError");

        addStep("Navigate to user management", () -> {
            pageManager.getUserManagementPage().navigateToUserManagement();
        });

        addStep("Click Add User button", () -> {
            pageManager.getUserManagementPage().clickAddUser();
        });

        addStep("Fill user form", () -> {
            pageManager.getUserManagementPage().enterFirstName(firstName);
            pageManager.getUserManagementPage().enterLastName(lastName);
            pageManager.getUserManagementPage().enterEmail(email);
            pageManager.getUserManagementPage().selectRole(role);
        });

        addStep("Save user", () -> {
            pageManager.getUserManagementPage().saveUser();
        });

        if ("success".equals(expectedResult)) {
            addStep("Verify successful user creation", () -> {
                assertThat(page.locator(".alert-success"))
                    .containsText("User created successfully");
                takeScreenshot("User Creation Success");
            });
        } else {
            addStep("Verify error handling", () -> {
                assertThat(page.locator(".alert-error"))
                    .containsText(expectedError);
                takeScreenshot("User Creation Error");
            });
        }
    }

    @Test
    @DisplayName("User Management Page Load")
    @Description("Verify user management page loads correctly")
    @Story("Page Navigation")
    @Severity(SeverityLevel.MEDIUM)
    void testPageLoad() {
        addStep("Navigate to user management", () -> {
            pageManager.getUserManagementPage().navigateToUserManagement();
        });

        addStep("Verify page elements", () -> {
            assertThat(page).hasTitle("User Management");
            assertThat(page.getByRole("button",
                new Page.GetByRoleOptions().setName("Add User"))).isVisible();
            takeScreenshot("User Management Page");
        });
    }
}
```

---

## Best Practices

### 1. Locator Strategy Priority
```java
// 1. data-testid (most stable)
page.locator("[data-testid='submit-button']")

// 2. Semantic role-based
page.getByRole("button", new Page.GetByRoleOptions().setName("Submit"))

// 3. ID
page.locator("#submit-button")

// 4. Label association
page.getByLabel("Email Address")

// 5. Text content (for static text)
page.getByText("Submit")
```

### 2. Wait Strategies
```java
// ✅ Explicit waits
page.locator("#element").waitFor();
page.waitForURL("**/dashboard");

// ✅ Automatic waiting (Playwright does this automatically)
page.locator("#button").click();  // Waits for element to be actionable

// ❌ Hard waits (avoid unless absolutely necessary)
page.waitForTimeout(5000);  // Don't do this
```

### 3. Error Handling
```java
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Test
void testWithErrorHandling() {
    try {
        pageManager.getLoginPage().login("user@test.com", "password");

        // Verify success
        assertThat(page).hasURL("**/dashboard");

    } catch (Exception e) {
        // Take screenshot for debugging
        takeScreenshot("Test Failure - " + e.getMessage());
        throw e;  // Re-throw to fail the test
    }
}
```

### 4. Data-Driven Testing
```java
// Use external data files
static Stream<Map<String, String>> loginData() throws IOException {
    return TestDataManager.loadTestData("login-scenarios.json").stream();
}

@ParameterizedTest
@MethodSource("loginData")
void testLoginScenarios(Map<String, String> data) {
    // Test logic using data.get("field")
}
```

### 5. Page Object Design
```java
// ✅ Good: Action-oriented methods
public void login(String email, String password) {
    enterEmail(email);
    enterPassword(password);
    clickSignInButton();
}

// ✅ Good: Verification methods
public boolean isLoginSuccessful() {
    return page.locator("#dashboard").isVisible();
}

// ❌ Avoid: Exposing internal details
public Locator getEmailField() {
    return emailField;  // Don't expose locators
}
```

---

## Troubleshooting

### Common Issues and Solutions

#### 1. Element Not Found
```java
// Problem: Locator can't find element
// page.locator("#my-button").click();  // Fails

// Debug: Check if element exists
if (page.locator("#my-button").count() == 0) {
    System.out.println("Element not found!");

    // Take screenshot to see current state
    takeScreenshot("Element Not Found Debug");

    // Try alternative locators
    page.locator("button:has-text('Submit')").click();
}

// Solution: Use more robust locator
page.getByRole("button", new Page.GetByRoleOptions().setName("Submit")).click();
```

#### 2. Element Not Actionable
```java
// Problem: Element is not clickable (hidden, disabled, etc.)
// Solution: Wait for element to be actionable
page.locator("#button").waitFor(new Locator.WaitForOptions()
    .setState(WaitForSelectorState.VISIBLE));

// Or force the action (use carefully)
page.locator("#button").click(new Locator.ClickOptions().setForce(true));
```

#### 3. Timing Issues
```java
// Problem: Test runs faster than app can respond
// Solution: Use proper waits
page.locator("#submit").click();
page.waitForURL("**/success");  // Wait for navigation

// Wait for specific element to appear
page.locator("#result").waitFor();

// Wait for network to be idle
page.waitForLoadState(LoadState.NETWORKIDLE);
```

#### 4. Dynamic Content
```java
// Problem: Content loads dynamically
// Solution: Wait for specific condition
page.waitForCondition(() ->
    page.locator(".item").count() > 0
);

// Or wait for specific element with content
page.locator("#status").waitFor(new Locator.WaitForOptions()
    .setState(WaitForSelectorState.VISIBLE));
assertThat(page.locator("#status")).hasText("Loaded");
```

### Debugging Tips

#### 1. Screenshots
```java
// Take screenshot at any point
takeScreenshot("Debug - Current State");

// Automatic screenshots on failure (handled by BaseTest)
```

#### 2. Console Output
```java
// Print element text for debugging
String elementText = page.locator("#element").textContent();
System.out.println("Element text: " + elementText);

// Print page URL
System.out.println("Current URL: " + page.url());

// Print element count
System.out.println("Element count: " + page.locator(".item").count());
```

#### 3. Pause Execution
```java
// Pause test for manual inspection (remove before committing)
page.pause();  // Opens browser developer tools
```

#### 4. Trace Files
Our framework automatically generates trace files. View them with:
```bash
npx playwright show-trace trace_TestClassName_timestamp.zip
```

---

## Quick Reference

### Essential Locators
```java
// By test ID
page.locator("[data-testid='element']")

// By role
page.getByRole("button", new Page.GetByRoleOptions().setName("Text"))

// By text
page.getByText("Exact Text")

// By label
page.getByLabel("Label Text")

// CSS selector
page.locator(".class-name")
page.locator("#id")
page.locator("button[type='submit']")
```

### Essential Actions
```java
// Click
element.click()

// Type
element.fill("text")

// Select dropdown
element.selectOption("value")

// Check/uncheck
element.check()
element.uncheck()

// Upload file
element.setInputFiles(path)
```

### Essential Assertions
```java
// Visibility
assertThat(element).isVisible()

// Text
assertThat(element).hasText("text")

// URL
assertThat(page).hasURL("url")

// Attribute
assertThat(element).hasAttribute("attr", "value")
```

---

This guide provides the foundation for writing effective Playwright tests. Start with simple tests and gradually incorporate more advanced patterns as you become comfortable with the basics. Remember to follow our project structure and extend BaseTest for all new test classes.

For more specific examples, refer to existing tests in the `tests/` directory and page objects in the `pages/` directory.