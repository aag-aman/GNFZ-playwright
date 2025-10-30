package tests.authentication.forgot_password;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;
import utils.TestDataManager;
import pages.authentication.ForgotPasswordPage;

import java.io.IOException;
import java.util.Map;

// Static imports for better readability - no more "org.junit.jupiter.api.Assertions.assertTrue"
import static org.junit.jupiter.api.Assertions.*;

/**
 * ForgotPasswordTest - Smoke tests for forgot password functionality
 *
 * This test class contains smoke tests (quick, essential tests) for the
 * forgot password feature. These tests verify the basic functionality works.
 *
 * For beginners: Smoke tests are run first to ensure core functionality
 * is working before running more detailed regression tests.
 */
@Epic("User Authentication")
@Feature("Forgot Password Functionality")
public class ForgotPasswordTest extends BaseTest {

    /**
     * Main smoke test for forgot password functionality
     *
     * This test verifies that the forgot password feature works with a valid email.
     * It's the most important test for this feature - if this fails, the feature is broken.
     *
     * @throws IOException if test data file cannot be read
     */
    @Test
    @DisplayName("Forgot Password Smoke Test - Valid Email")
    @Description("Verify forgot password functionality with a valid registered email - smoke test for critical path validation")
    @Story("Valid Forgot Password Request")
    @Severity(SeverityLevel.CRITICAL)
    void testForgotPasswordSmoke() throws IOException {
        // Step 1: Get the page object once and reuse it throughout the test
        ForgotPasswordPage forgotPasswordPage = pageManager.getForgotPasswordPage();

        // Get test data from JSON file
        Map<String, String> testData = TestDataManager.getForgotPasswordSmokeData();
        String email = testData.get("email");

        // Navigate to forgot password page - @AutoStep handles step creation
        forgotPasswordPage.navigateToForgotPassword();

        // Verify page loaded correctly and all elements are visible - @AutoStep handles step creation
        assertTrue(forgotPasswordPage.isPageDisplayed(),
            "Forgot password page should be displayed");

        assertTrue(forgotPasswordPage.isEmailFieldVisible(),
            "Email field should be visible");

        assertTrue(forgotPasswordPage.isSubmitButtonVisible(),
            "Submit button should be visible");

        // Enter email and submit the request - @AutoStep handles step creation with parameters visible
        forgotPasswordPage.enterEmail(email);
        forgotPasswordPage.clickSubmitButton();

        // Check if confirmation is displayed (this depends on your app's behavior)
        if (forgotPasswordPage.isConfirmationDisplayed()) {
            String confirmationHeader = forgotPasswordPage.getConfirmationHeader();
            assertNotNull(confirmationHeader,
                "Confirmation header should be displayed");

            String confirmationMessage = forgotPasswordPage.getConfirmationMessage();
            assertNotNull(confirmationMessage,
                "Confirmation message should be displayed");
        } else {
            // Alternative: Check if we're redirected or if there's another success indicator
            // This would depend on your application's specific behavior
            assertFalse(forgotPasswordPage.isErrorMessageDisplayed(),
                "No error message should be displayed for valid email");
        }

        // Take screenshot for reporting (helps with debugging)
        takeScreenshot("Forgot Password Success Screenshot");
    }

    /**
     * Test that all page elements are present and functional
     *
     * This test verifies that the forgot password page loads correctly with all
     * expected elements (header, form fields, buttons, links).
     * This is important for UI/UX validation.
     */
    @Test
    @DisplayName("Forgot Password Page Elements Test")
    @Description("Verify all elements are present and functional on forgot password page")
    @Story("Page Elements Validation")
    @Severity(SeverityLevel.NORMAL)
    void testForgotPasswordPageElements() {
        // Get the page object once and reuse it throughout the test
        ForgotPasswordPage forgotPasswordPage = pageManager.getForgotPasswordPage();

        // Navigate to forgot password page - @AutoStep handles step creation
        forgotPasswordPage.navigateToForgotPassword();

        // Verify page headers and descriptive text
        assertTrue(forgotPasswordPage.isPageDisplayed(),
            "Page header should be visible");

        String pageHeader = forgotPasswordPage.getPageHeader();
        assertNotNull(pageHeader,
            "Page header text should be present");

        String pageSubHeader = forgotPasswordPage.getPageSubHeader();
        assertNotNull(pageSubHeader,
            "Page sub-header text should be present");

        // Verify form elements are present and functional
        assertTrue(forgotPasswordPage.isEmailFieldVisible(),
            "Email field should be visible");

        assertTrue(forgotPasswordPage.isSubmitButtonVisible(),
            "Submit button should be visible");

        assertTrue(forgotPasswordPage.isSubmitButtonEnabled(),
            "Submit button should be enabled");

        // Verify navigation elements (if present on your page)
        // Some forgot password pages have a "Back to Login" link
        if (forgotPasswordPage.isBackToLoginLinkVisible()) {
            String backToLoginMessage = forgotPasswordPage.getBackToLoginMessage();
            assertNotNull(backToLoginMessage,
                "Back to login message should be present");
        }

        // Take screenshot for reporting
        takeScreenshot("Forgot Password Page Elements Screenshot");
    }
}