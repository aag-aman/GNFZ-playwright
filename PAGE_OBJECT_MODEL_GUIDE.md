# Page Object Model (POM) Guide

## Overview
This project follows the standard Playwright Page Object Model pattern with singleton instantiation for efficient object reuse.

## Architecture

### 1. Standard POM Structure
```
src/test/java/pages/
├── PageManager.java          # Singleton manager for page objects
├── LoginPage.java           # Login page objects and actions
├── ProjectListPage.java     # Project list page objects and actions
└── [Future pages...]
```

### 2. Key Components

#### Locators (Standard Pattern)
```java
// ✅ Good - Locators initialized once in constructor
private final Locator emailField;
private final Locator passwordField;
private final Locator signInButton;

public LoginPage(Page page) {
    this.page = page;
    // Initialize locators once - reused throughout test
    this.emailField = page.locator("#gnfz-login-email");
    this.passwordField = page.locator("#gnfz-login-password");
    this.signInButton = page.locator("button[type='submit']");
}

// ❌ Bad - Creating locators repeatedly
public void enterEmail(String email) {
    page.locator("#gnfz-login-email").pressSequentially(email); // Don't do this - create locator once
}
```

#### PageManager (Singleton Pattern)
```java
// Single instance per Page - reuses page objects
PageManager pageManager = new PageManager(page);

// Page objects created once and reused
LoginPage loginPage = pageManager.getLoginPage();        // Creates once
LoginPage samePage = pageManager.getLoginPage();        // Returns same instance
```

## Usage Examples

### Test Implementation
```java
@BeforeEach
void setUp() {
    page = context.newPage();
    pageManager = new PageManager(page);  // One manager per page
}

@Test
void testLogin() {
    // Page objects are created once and reused
    pageManager.getLoginPage().navigateToLogin();
    pageManager.getLoginPage().enterEmail("test@example.com");
    pageManager.getLoginPage().enterPassword("password");
    pageManager.getLoginPage().clickSignInButton();

    // Verify on different page using same manager
    pageManager.getProjectListPage().isProjectListVisible();
}
```

### Fluent Interface Pattern (Optional Enhancement)
```java
// Current usage
pageManager.getLoginPage().enterEmail(email);
pageManager.getLoginPage().enterPassword(password);
pageManager.getLoginPage().clickSignInButton();

// Fluent pattern (future enhancement)
pageManager.getLoginPage()
    .enterEmail(email)
    .enterPassword(password)
    .clickSignInButton();
```

## Benefits

### 1. **Performance**
- ✅ Locators initialized once in constructor
- ✅ Page objects created once per test context
- ✅ No repeated object creation overhead

### 2. **Maintainability**
- ✅ Centralized locator management
- ✅ Single source of truth for UI elements
- ✅ Easy to update when UI changes

### 3. **Reusability**
- ✅ Same page objects used across multiple tests
- ✅ Consistent interaction patterns
- ✅ Reduced code duplication

### 4. **Readability**
- ✅ Clear separation of page logic and test logic
- ✅ Semantic method names (enterEmail, clickSignInButton)
- ✅ Business-focused test steps

## Page Object Examples

### LoginPage.java
```java
public class LoginPage {
    private final Page page;

    // Locators - initialized once
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator signInButton;

    public LoginPage(Page page) {
        this.page = page;
        this.emailField = page.locator("#gnfz-login-email");
        this.passwordField = page.locator("#gnfz-login-password");
        this.signInButton = page.locator("button[type='submit']");
    }

    // Actions
    public void enterEmail(String email) {
        InputHelper.humanizedInput(page, emailField, email);
    }

    public void enterPassword(String password) {
        InputHelper.humanizedInput(page, passwordField, password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    // Combined actions
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
    }

    // Getter methods for advanced usage
    public Locator getEmailField() {
        return emailField;
    }
}
```

### ProjectListPage.java
```java
public class ProjectListPage {
    private final Page page;

    // Locators - initialized once
    private final Locator projectListHeader;
    private final Locator projectCards;

    public ProjectListPage(Page page) {
        this.page = page;
        this.projectListHeader = page.locator("b:has-text('List of projects')");
        this.projectCards = page.locator("[data-testid='project-card']");
    }

    // Verification methods
    public boolean isProjectListVisible() {
        page.waitForLoadState();
        return projectListHeader.isVisible();
    }

    public String getProjectListText() {
        page.waitForLoadState();
        return projectListHeader.textContent();
    }

    public int getProjectCount() {
        return projectCards.count();
    }
}
```

## Best Practices

### 1. Locator Strategy
```java
// ✅ Preferred - Stable selectors
page.locator("[data-testid='login-email']")     // Test IDs
page.locator("#gnfz-login-email")               // IDs
page.locator("button[type='submit']")           // Semantic attributes

// ⚠️ Use with caution - Fragile selectors
page.locator(".form-input:nth-child(1)")        // CSS positions
page.locator("xpath=//div[3]/input")            // XPath positions
```

### 2. Method Naming
```java
// ✅ Good - Business focused
enterEmail(String email)
clickSignInButton()
isProjectListVisible()

// ❌ Bad - Implementation focused
fillEmailTextbox(String email)
clickSubmitBtn()
checkIfElementExists()
```

### 3. Wait Strategies
```java
// ✅ Good - Explicit waits in page objects
public boolean isProjectListVisible() {
    page.waitForLoadState();  // Wait for page to be ready
    return projectListHeader.isVisible();
}

// ✅ Good - Wait for specific conditions
public void clickSignInButton() {
    signInButton.waitFor();  // Wait for element to be actionable
    signInButton.click();
}
```

### 4. Error Handling
```java
public void enterEmail(String email) {
    try {
        InputHelper.humanizedInput(page, emailField, email);
    } catch (TimeoutError e) {
        throw new AssertionError("Email field not found or not interactable", e);
    }
}
```

## Migration from Old Pattern

### Before (Old Pattern)
```java
// Multiple locator creations and non-humanized input
page.locator("#gnfz-login-email").pressSequentially(email);
page.locator("#gnfz-login-password").pressSequentially(password);
page.locator("button[type='submit']").click();

// New page objects every time
LoginPage loginPage = new LoginPage(page);
ProjectListPage projectPage = new ProjectListPage(page);
```

### After (New Pattern)
```java
// Single PageManager instance
PageManager pageManager = new PageManager(page);

// Reused page objects with pre-initialized locators
pageManager.getLoginPage().enterEmail(email);
pageManager.getLoginPage().enterPassword(password);
pageManager.getLoginPage().clickSignInButton();
```

## Adding New Pages

### 1. Create Page Object
```java
// pages/DashboardPage.java
public class DashboardPage {
    private final Page page;
    private final Locator welcomeMessage;
    private final Locator navigationMenu;

    public DashboardPage(Page page) {
        this.page = page;
        this.welcomeMessage = page.locator("[data-testid='welcome']");
        this.navigationMenu = page.locator(".nav-menu");
    }

    public boolean isWelcomeMessageVisible() {
        return welcomeMessage.isVisible();
    }
}
```

### 2. Add to PageManager
```java
// In PageManager.java
private DashboardPage dashboardPage;

public DashboardPage getDashboardPage() {
    if (dashboardPage == null) {
        dashboardPage = new DashboardPage(page);
    }
    return dashboardPage;
}
```

### 3. Use in Tests
```java
@Test
void testDashboard() {
    pageManager.getDashboardPage().isWelcomeMessageVisible();
}
```

## Current Implementation Status

✅ **Implemented**:
- Standard Playwright locator pattern
- Singleton PageManager
- LoginPage with pre-initialized locators
- ProjectListPage with pre-initialized locators
- Proper separation of concerns

✅ **Benefits Achieved**:
- Single locator initialization per test
- Reusable page objects across test methods
- Consistent interaction patterns
- Improved maintainability

🎯 **Ready for Extension**:
- Easy to add new pages
- Scalable architecture
- Industry-standard pattern