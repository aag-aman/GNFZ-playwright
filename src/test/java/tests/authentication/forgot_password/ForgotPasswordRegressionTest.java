package tests.authentication.forgot_password;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tests.base.BaseTest;
import utils.TestDataManager;
import pages.authentication.ForgotPasswordPage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

// Static imports for better readability - now we can use "assertTrue" instead of "org.junit.jupiter.api.Assertions.assertTrue"
import static org.junit.jupiter.api.Assertions.*;

/**
 * ForgotPasswordRegressionTest - Comprehensive testing for forgot password
 * functionality
 *
 * This test class contains detailed tests for forgot password feature
 * including:
 * - Positive scenarios (valid email formats)
 * - Negative scenarios (invalid email formats, empty fields)
 * - Form validation testing
 * - Page navigation testing
 *
 * For beginners: This class extends BaseTest which provides browser setup,
 * page management, and screenshot capabilities automatically.
 */
@Epic("User Authentication")
@Feature("Forgot Password Functionality")
public class ForgotPasswordRegressionTest extends BaseTest {

    /**
     * Test forgot password with valid email formats
     *
     * This test runs multiple times with different valid email addresses
     * to ensure the forgot password function works with various email formats.
     *
     * @param description - Human readable description of what's being tested
     * @param testData    - Map containing email and expected results from JSON file
     */
    @ParameterizedTest(name = "Positive Test: {0}")
    @MethodSource("getPositiveTestData")
    @DisplayName("Forgot Password Positive Scenarios")
    @Description("Test forgot password functionality with valid email formats and scenarios")
    @Story("Valid Forgot Password Requests")
    @Severity(SeverityLevel.NORMAL)
    void testValidForgotPasswordScenarios(String description, Map<String, String> testData) {
        // Step 1: Get the page object once and reuse it throughout the test
        ForgotPasswordPage forgotPasswordPage = pageManager.getForgotPasswordPage();

        // Extract test data
        String email = testData.get("email");

        // Navigate to forgot password page - @AutoStep handles step creation
        forgotPasswordPage.navigateToForgotPassword();

        // Verify page loaded correctly - @AutoStep handles step creation
        assertTrue(forgotPasswordPage.isPageDisplayed(),
                "Forgot password page should be displayed");

        // Clear any existing text first (good practice)
        forgotPasswordPage.clearEmailField();

        // Enter the test email - @AutoStep handles step creation with parameter visible
        forgotPasswordPage.enterEmail(email);

        // Verify email was entered correctly (defensive testing)
        String enteredEmail = forgotPasswordPage.getEmailFieldValue();
        assertEquals(email, enteredEmail,
                "Email should be entered correctly");

        // Submit the form - @AutoStep handles step creation
        forgotPasswordPage.clickSubmitButton();

        // Give a brief moment for any potential error messages to appear
        // Note: Playwright has auto-wait, but some error messages might take a moment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify no error message is shown (this means the email format was accepted)
        assertFalse(forgotPasswordPage.isErrorMessageDisplayed(),
                "No error message should be displayed for valid email format: " + email);

        // Step 8: Take screenshot for reporting (helps with debugging)
        takeScreenshot("Forgot Password Valid Email Test - " + email.replaceAll("[^a-zA-Z0-9]", "_"));
    }

    /**
     * Data provider for positive tests
     *
     * This method reads test data from JSON file and converts it to format
     * that JUnit can use for parameterized tests.
     *
     * @return Stream of test arguments (description and test data map)
     */
    static Stream<org.junit.jupiter.params.provider.Arguments> getPositiveTestData() throws IOException {
        List<Map<String, String>> testCases = TestDataManager.getPositiveForgotPasswordTestCases();
        return testCases.stream()
                .map(testCase -> org.junit.jupiter.params.provider.Arguments.of(
                        testCase.get("description"),
                        testCase));
    }

    /**
     * Test forgot password with invalid email formats
     *
     * This test runs multiple times with different invalid email addresses
     * to ensure proper validation and error handling.
     *
     * @param description - Human readable description of what's being tested
     * @param testData    - Map containing invalid email and expected error from
     *                    JSON file
     */
    @ParameterizedTest(name = "Negative Test: {0}")
    @MethodSource("getNegativeTestData")
    @DisplayName("Forgot Password Negative Scenarios")
    @Description("Test forgot password functionality with invalid email formats and edge cases")
    @Story("Invalid Forgot Password Requests")
    @Severity(SeverityLevel.NORMAL)
    void testInvalidForgotPasswordScenarios(String description, Map<String, String> testData) {
        // Step 1: Get the page object once and reuse it throughout the test
        ForgotPasswordPage forgotPasswordPage = pageManager.getForgotPasswordPage();

        // Extract test data
        String email = testData.get("email");
        String expectedError = testData.get("expectedError");

        // Navigate to forgot password page - @AutoStep handles step creation
        forgotPasswordPage.navigateToForgotPassword();

        // Verify page loaded correctly - @AutoStep handles step creation
        assertTrue(forgotPasswordPage.isPageDisplayed(),
                "Forgot password page should be displayed");

        // Clear field first
        forgotPasswordPage.clearEmailField();

        // Only enter email if it's not empty (some tests check empty field validation)
        if (!email.trim().isEmpty()) {
            forgotPasswordPage.enterEmail(email);
        }

        // Submit the form with invalid data - @AutoStep handles step creation
        forgotPasswordPage.clickSubmitButton();

        // Give a bit more time for error message to appear (some validations might be slower)
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check if error message is displayed (this depends on your app's validation behavior)
        if (forgotPasswordPage.isErrorMessageDisplayed()) {
            String actualError = forgotPasswordPage.getErrorMessage();
            assertNotNull(actualError,
                    "Error message should be displayed for invalid email: " + email);

            // For debugging: attach both expected and actual error messages to report
            Allure.addAttachment("Expected Error", expectedError);
            Allure.addAttachment("Actual Error", actualError);
        } else {
            // If no error message, at least verify we haven't proceeded successfully
            // (this is a fallback check in case the app doesn't show specific error messages)
            assertFalse(forgotPasswordPage.isConfirmationDisplayed(),
                    "Confirmation should not be displayed for invalid email: " + email);
        }

        // Step 8: Take screenshot for reporting
        takeScreenshot("Forgot Password Invalid Email Test - " + description.replaceAll("[^a-zA-Z0-9]", "_"));
    }

    /**
     * Data provider for negative tests
     *
     * This method reads test data from JSON file for invalid scenarios.
     *
     * @return Stream of test arguments (description and test data map)
     */
    static Stream<org.junit.jupiter.params.provider.Arguments> getNegativeTestData() throws IOException {
        List<Map<String, String>> testCases = TestDataManager.getNegativeForgotPasswordTestCases();
        return testCases.stream()
                .map(testCase -> org.junit.jupiter.params.provider.Arguments.of(
                        testCase.get("description"),
                        testCase));
    }

    /**
     * Test form field interactions and validations
     *
     * This test checks that form fields work correctly:
     * - Email field can be filled, cleared, and read
     * - Submit button is functional
     * - Back to login link works (if present)
     */
    @Test
    @DisplayName("Forgot Password Form Validation")
    @Description("Test form field validations and interactions")
    @Story("Form Validation")
    @Severity(SeverityLevel.MINOR)
    void testFormValidation() {
        // Get the page object once and reuse it throughout the test
        ForgotPasswordPage forgotPasswordPage = pageManager.getForgotPasswordPage();

        // Navigate to forgot password page - @AutoStep handles step creation
        forgotPasswordPage.navigateToForgotPassword();

        // Verify field is initially empty (good practice to check initial state)
        String initialValue = forgotPasswordPage.getEmailFieldValue();
        assertTrue(initialValue == null || initialValue.isEmpty(),
                "Email field should be initially empty");

        // Test entering text - @AutoStep handles step creation
        forgotPasswordPage.enterEmail("test@example.com");
        String enteredValue = forgotPasswordPage.getEmailFieldValue();
        assertEquals("test@example.com", enteredValue,
                "Email should be entered correctly");

        // Test clearing the field - @AutoStep handles step creation
        forgotPasswordPage.clearEmailField();
        String clearedValue = forgotPasswordPage.getEmailFieldValue();
        assertTrue(clearedValue == null || clearedValue.isEmpty(),
                "Email field should be cleared");

        // Test submit button functionality
        assertTrue(forgotPasswordPage.isSubmitButtonEnabled(),
                "Submit button should be enabled");

        // Test back to login link if it exists
        if (forgotPasswordPage.isBackToLoginLinkVisible()) {
            String backToLoginMessage = forgotPasswordPage.getBackToLoginMessage();
            assertNotNull(backToLoginMessage,
                    "Back to login message should be present");

            // Test clicking the link (Note: this might navigate away from the page)
            forgotPasswordPage.clickBackToLoginLink();

            // Note: You might want to verify navigation here depending on your app's behavior
            // For example: check if we're redirected to login page
        }

        // Take screenshot for reporting
        takeScreenshot("Forgot Password Form Validation Test");
    }

    /**
     * Test page navigation functionality
     *
     * This test verifies that:
     * - Direct navigation to forgot password page works
     * - Page can be reloaded without issues
     */
    @Test
    @DisplayName("Forgot Password Page Navigation")
    @Description("Test navigation to and from forgot password page")
    @Story("Page Navigation")
    @Severity(SeverityLevel.MINOR)
    void testPageNavigation() {
        // Get the page object once and reuse it throughout the test
        ForgotPasswordPage forgotPasswordPage = pageManager.getForgotPasswordPage();

        // Test direct navigation to the page - @AutoStep handles step creation
        forgotPasswordPage.navigateToForgotPassword();

        assertTrue(forgotPasswordPage.isPageDisplayed(),
                "Should be able to navigate directly to forgot password page");

        // Reload the current page (useful to test if page handles refresh correctly)
        page.reload();

        assertTrue(forgotPasswordPage.isPageDisplayed(),
                "Page should still be displayed after reload");

        // Take screenshot for reporting
        takeScreenshot("Forgot Password Navigation Test");
    }
}