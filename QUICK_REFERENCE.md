# @AutoStep Quick Reference Card

## 🚀 Running Tests

### Basic Commands
```bash
# Run all tests
mvn clean test

# Run single test
mvn test -Dtest=LoginTest

# Run single test method
mvn test -Dtest=LoginTest#testSuccessfulLogin

# Run with visible browser (for debugging)
mvn test -Dtest=LoginTest -Dheadless=false

# Run with slow motion
mvn test -Dtest=LoginTest -Dheadless=false -Dslowmo=true
```

### View Reports
```bash
# View Allure report (auto-opens browser)
mvn allure:serve

# Generate static report
mvn allure:report
```

---

## ✍️ Writing Tests

### Simple Test (Steps Automatic!)
```java
@Test
@DisplayName("Test Login")
void testLogin() {
    // Just call methods - steps are automatic!
    pageManager.getLoginPage().navigateToLogin();
    pageManager.getLoginPage().enterEmail("test@example.com");
    pageManager.getLoginPage().enterPassword("password");
    pageManager.getLoginPage().clickSignInButton();

    // Verify
    AssertLogger.assertTrue(
        pageManager.getProjectListPage().isProjectListVisible(),
        "Project list should be visible"
    );
}
```

**Allure Report Shows:**
```
✓ Navigate To Login
✓ Enter Email (email=test@example.com)
✓ Enter Password (password=***)
✓ Click Sign In Button
✓ Project list should be visible
```

---

## 🎯 Adding @AutoStep to New Page Objects

### Step 1: Add Import
```java
import utils.AutoStep;
```

### Step 2: Add Annotations
```java
// Auto-generated name
@AutoStep
public void clickButton() {
    button.click();
}

// Custom name
@AutoStep("Click the submit button")
public void clickSubmit() {
    submitButton.click();
}

// Method with parameters (shows in report)
@AutoStep
public void enterEmail(String email) {
    emailField.fill(email);
}
```

### Step 3: Rebuild
```bash
mvn clean test-compile
```

---

## 📋 What Gets Annotated

### ✅ Annotate These:
- Action methods: `clickButton()`, `enterText()`, `selectOption()`
- Navigation: `goToTab()`, `navigateToPage()`
- Validation: `isVisible()`, `isEnabled()`, `getText()`
- Complex flows: `fillForm()`, `login()`, `submitAndWait()`

### ❌ Don't Annotate These:
- Constructors
- Simple getters that return Locators: `public Locator getButton()`
- Private helper methods

---

## 🐛 Troubleshooting

### Steps Not Appearing in Allure?
```bash
# Rebuild project
mvn clean test-compile

# Check weaving worked
mvn test-compile | grep "Join point"
# Should see: "advised by around advice from 'utils.AutoStepAspect'"
```

### Test Won't Run?
**Problem:** Using VS Code test runner
**Solution:** Use Maven in terminal:
```bash
mvn test -Dtest=YourTest
```

### Compilation Errors?
```bash
# Update dependencies
mvn clean install -U

# Check Java version
java -version  # Must be Java 21
```

---

## 📊 Step Name Examples

| Method | Generated Step Name |
|--------|---------------------|
| `navigateToLogin()` | Navigate To Login |
| `enterEmail("test@example.com")` | Enter Email (email=test@example.com) |
| `enterPassword("pass")` | Enter Password (password=***) |
| `clickSignInButton()` | Click Sign In Button |
| `fillRow(0, "Wood", "100")` | Fill Row (rowIndex=0, type=Wood, quantity=100) |
| `isProjectListVisible()` | Is Project List Visible |

---

## 🎨 Password Masking

Automatic for parameters containing "password":

```java
@AutoStep
public void enterPassword(String password) {
    passwordField.fill(password);
}
```

**Allure shows:** `Enter Password (password=***)`

---

## 🔢 Comparing Numbers in Tests

**ALWAYS use NumberParser for number comparisons:**

```java
// ✅ CORRECT - Handles both "2,539.25" and "2539.25"
double value = NumberParser.parseDouble(emissionFactor);
AssertLogger.assertNumberEquals(expectedTotal, actualTotal, "message");
```

```java
// ❌ WRONG - Fails when app returns comma-formatted numbers
double value = Double.parseDouble(str.replace(",", ""));
AssertLogger.assertEquals(expected, actual, "message");
```

---

## 📁 Documentation Files

- **QUICK_REFERENCE.md** (this file) - Quick reference
- **SESSION_SUMMARY.md** - Everything accomplished
- **NUMBER_COMPARISON_FIX.md** - Number comparison fix details
- **AUTOSTEP_USAGE.md** - Detailed usage guide
- **PROJECT_WIDE_IMPLEMENTATION.md** - Complete rollout summary
- **IMPLEMENTATION_SUMMARY.md** - Initial implementation
- **ALLURE_IMPROVEMENT_PROPOSAL.md** - Full analysis

---

## ⚡ Pro Tips

1. **Use Maven Terminal in VS Code**
   - Open terminal: `Ctrl+\`` or `Cmd+\``
   - Run: `mvn test -Dtest=YourTest -Dheadless=false`

2. **View Specific Trace**
   ```bash
   ./debug-failures.sh
   # Or:
   npx playwright show-trace test-results/traces/run_*/TestName.zip
   ```

3. **Run Multiple Tests**
   ```bash
   # All login tests
   mvn test -Dtest="Login*"

   # Smoke tests
   mvn clean test -Psmoke
   ```

4. **Custom Step Names**
   - Use for unclear method names
   - Keep business-focused
   - Example: `@AutoStep("Verify user is redirected to dashboard")`

---

## 📞 Need Help?

1. Check **AUTOSTEP_USAGE.md** for examples
2. Check **PROJECT_WIDE_IMPLEMENTATION.md** for troubleshooting
3. Review AspectJ weaving logs: `mvn test-compile | grep "Join point"`

---

## ✅ Current Status

- ✅ 66 page objects annotated
- ✅ 8 test files cleaned up
- ✅ No nested steps
- ✅ Parameter visibility working
- ✅ Password masking working
- ✅ Production ready!

🎉 **Happy Testing!** 🎉
