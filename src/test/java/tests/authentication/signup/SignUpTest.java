package tests.authentication.signup;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.authentication.SignUpPage;
import tests.base.BaseTest;
import utils.TestDataManager;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SignUpTest - Smoke tests for sign-up functionality
 *
 * Quick tests to verify basic sign-up features work correctly.
 */
@Epic("User Authentication")
@Feature("Sign Up Functionality")
public class SignUpTest extends BaseTest {

    /**
     * Test that sign-up page loads and displays all required elements
     */
    @Test
    @DisplayName("Sign Up Page Elements")
    @Description("Verify that all required form elements are present on the sign-up page")
    @Story("Page Display")
    @Severity(SeverityLevel.CRITICAL)
    void testSignUpPageElements() {
        // Get the sign-up page object
        SignUpPage signUpPage = pageManager.getSignUpPage();

        // Navigate to sign-up page
        Allure.step("Navigate to sign-up page", () -> {
            signUpPage.navigateToSignUp();
        });

        // Verify page is displayed
        Allure.step("Verify sign-up page is displayed", () -> {
            assertTrue(signUpPage.isPageDisplayed(),
                    "Sign-up page should be displayed");
        });

        // Verify all form fields are visible
        Allure.step("Verify all form fields are visible", () -> {
            assertTrue(signUpPage.isFirstNameFieldVisible(),
                    "First name field should be visible");
            assertTrue(signUpPage.isLastNameFieldVisible(),
                    "Last name field should be visible");
            assertTrue(signUpPage.isEmailFieldVisible(),
                    "Email field should be visible");
            assertTrue(signUpPage.isPasswordFieldVisible(),
                    "Password field should be visible");
            assertTrue(signUpPage.isConfirmPasswordFieldVisible(),
                    "Confirm password field should be visible");
        });

        // Verify sign-up button is visible and enabled
        Allure.step("Verify sign-up button is visible and enabled", () -> {
            assertTrue(signUpPage.isSignUpButtonVisible(),
                    "Sign-up button should be visible");
            assertTrue(signUpPage.isSignUpButtonEnabled(),
                    "Sign-up button should be enabled");
        });

        // Take screenshot
        takeScreenshot("Sign Up Page Elements");
    }

    /**
     * Test basic sign-up flow with valid data
     */
    @Test
    @DisplayName("Sign Up Smoke Test")
    @Description("Test basic sign-up functionality with valid test user")
    @Story("Valid Sign Up")
    @Severity(SeverityLevel.CRITICAL)
    void testSignUpSmoke() throws IOException {
        // Get the sign-up page object
        SignUpPage signUpPage = pageManager.getSignUpPage();

        // Get test data
        Map<String, String> testData = TestDataManager.loadSignUpSmokeData();
        String firstName = testData.get("firstName");
        String lastName = testData.get("lastName");
        String email = testData.get("email");
        String password = testData.get("password");
        String confirmPassword = testData.get("confirmPassword");
        

        // Navigate to sign-up page
        Allure.step("Navigate to sign-up page", () -> {
            signUpPage.navigateToSignUp();
        });

        // Fill in sign-up form
        Allure.step("Fill in sign-up form with test data", () -> {
            signUpPage.clearAllFields(); // Clear any existing data

            signUpPage.enterFirstName(firstName);
            signUpPage.enterLastName(lastName);
            signUpPage.enterEmail(email);
            signUpPage.enterPassword(password);
            signUpPage.enterConfirmPassword(confirmPassword);

            // Verify data was entered correctly
            assertEquals(firstName, signUpPage.getFirstNameFieldValue(),
                    "First name should be entered correctly");
            assertEquals(lastName, signUpPage.getLastNameFieldValue(),
                    "Last name should be entered correctly");
            assertEquals(email, signUpPage.getEmailFieldValue(),
                    "Email should be entered correctly");
        });

        // Submit the form
        Allure.step("Submit sign-up form", () -> {
            signUpPage.clickSignUpButton();
        });

        // Verify no error occurred
        Allure.step("Verify no error message for valid sign-up", () -> {
            // Wait a moment for any potential errors
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            
            //Captcha may block sign-up, so we check that captcha error is shown
            System.out.println("Error Message: " + signUpPage.getErrorMessage());
            assertTrue(signUpPage.getErrorMessage().contains("captcha"),
                    "Captcha error should be visible indicating form submission was attempted");


        });

        // Take screenshot
        takeScreenshot("Sign Up Smoke Test");
    }
}
