package tests.authentication.signup;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.authentication.SignUpPage;
import tests.base.BaseTest;
import utils.TestDataManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SignUpRegressionTest - Comprehensive testing for sign-up functionality
 *
 * This test class contains detailed tests for sign-up feature including:
 * - Positive scenarios (valid sign-ups with various data)
 * - Negative scenarios (invalid data, validation errors)
 * - Form validation testing
 * - Page navigation testing
 */
@Epic("User Authentication")
@Feature("Sign Up Functionality")
public class SignUpRegressionTest extends BaseTest {

    /**
     * Test sign-up with valid data
     */
    @ParameterizedTest(name = "Positive Test: {0}")
    @MethodSource("getPositiveTestData")
    @DisplayName("Sign Up Positive Scenarios")
    @Description("Test sign-up functionality with valid user data")
    @Story("Valid Sign Up Requests")
    @Severity(SeverityLevel.NORMAL)
    void testValidSignUpScenarios(String description, Map<String, String> testData) {
        // Get the sign-up page object
        SignUpPage signUpPage = pageManager.getSignUpPage();

        // Extract test data
        String firstName = testData.get("firstName");
        String lastName = testData.get("lastName");
        String email = testData.get("email");
        String password = testData.get("password");
        String confirmPassword = testData.get("confirmPassword");

        // Navigate to sign-up page
        Allure.step("Navigate to sign-up page", () -> {
            signUpPage.navigateToSignUp();
        });

        // Verify page loaded
        Allure.step("Verify sign-up page is displayed", () -> {
            assertTrue(signUpPage.isPageDisplayed(),
                "Sign-up page should be displayed");
        });

        // Fill in form
        Allure.step("Fill in sign-up form: " + email, () -> {
            signUpPage.clearAllFields();

            signUpPage.enterFirstName(firstName);
            signUpPage.enterLastName(lastName);
            signUpPage.enterEmail(email);
            signUpPage.enterPassword(password);
            signUpPage.enterConfirmPassword(confirmPassword);

            // Verify data entered correctly
            assertEquals(firstName, signUpPage.getFirstNameFieldValue(),
                "First name should be entered correctly");
            assertEquals(lastName, signUpPage.getLastNameFieldValue(),
                "Last name should be entered correctly");
            assertEquals(email, signUpPage.getEmailFieldValue(),
                "Email should be entered correctly");
        });

        // Submit form
        Allure.step("Submit sign-up form", () -> {
            signUpPage.clickSignUpButton();
        });

        // Verify no error for valid data
        Allure.step("Verify no error message for valid data", () -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            assertFalse(signUpPage.isErrorMessageDisplayed(),
                "No error message should be displayed for valid data: " + email);
        });

        // Take screenshot
        takeScreenshot("Sign Up Valid Test - " + description.replaceAll("[^a-zA-Z0-9]", "_"));
    }

    /**
     * Data provider for positive tests
     */
    static Stream<org.junit.jupiter.params.provider.Arguments> getPositiveTestData() throws IOException {
        List<Map<String, String>> testCases = TestDataManager.getPositiveSignUpTestCases();
        return testCases.stream()
            .map(testCase -> org.junit.jupiter.params.provider.Arguments.of(
                testCase.get("description"),
                testCase));
    }

    /**
     * Test sign-up with invalid data
     */
    @ParameterizedTest(name = "Negative Test: {0}")
    @MethodSource("getNegativeTestData")
    @DisplayName("Sign Up Negative Scenarios")
    @Description("Test sign-up functionality with invalid data and edge cases")
    @Story("Invalid Sign Up Requests")
    @Severity(SeverityLevel.NORMAL)
    void testInvalidSignUpScenarios(String description, Map<String, String> testData) {
        // Get the sign-up page object
        SignUpPage signUpPage = pageManager.getSignUpPage();

        // Extract test data
        String firstName = testData.get("firstName");
        String lastName = testData.get("lastName");
        String email = testData.get("email");
        String password = testData.get("password");
        String confirmPassword = testData.get("confirmPassword");
        String expectedError = testData.get("expectedError");

        // Navigate to sign-up page
        Allure.step("Navigate to sign-up page", () -> {
            signUpPage.navigateToSignUp();
        });

        // Verify page loaded
        Allure.step("Verify sign-up page is displayed", () -> {
            assertTrue(signUpPage.isPageDisplayed(),
                "Sign-up page should be displayed");
        });

        // Fill in form with invalid data
        Allure.step("Fill in sign-up form with invalid data", () -> {
            signUpPage.clearAllFields();

            if (!firstName.isEmpty()) signUpPage.enterFirstName(firstName);
            if (!lastName.isEmpty()) signUpPage.enterLastName(lastName);
            if (!email.isEmpty()) signUpPage.enterEmail(email);
            if (!password.isEmpty()) signUpPage.enterPassword(password);
            if (!confirmPassword.isEmpty()) signUpPage.enterConfirmPassword(confirmPassword);
        });

        // Submit form
        Allure.step("Submit sign-up form with invalid data", () -> {
            signUpPage.clickSignUpButton();
        });

        // Verify error handling
        Allure.step("Verify error handling for invalid data", () -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Check if error message is displayed
            if (signUpPage.isErrorMessageDisplayed()) {
                String actualError = signUpPage.getErrorMessage();
                assertNotNull(actualError,
                    "Error message should be displayed for invalid data");

                // Attach errors to report
                Allure.addAttachment("Expected Error", expectedError);
                Allure.addAttachment("Actual Error", actualError);
            } else {
                // If no error message, verify no success
                assertFalse(signUpPage.isSuccessMessageDisplayed(),
                    "Success message should not be displayed for invalid data");
            }
        });

        // Take screenshot
        takeScreenshot("Sign Up Invalid Test - " + description.replaceAll("[^a-zA-Z0-9]", "_"));
    }

    /**
     * Data provider for negative tests
     */
    static Stream<org.junit.jupiter.params.provider.Arguments> getNegativeTestData() throws IOException {
        List<Map<String, String>> testCases = TestDataManager.getNegativeSignUpTestCases();
        return testCases.stream()
            .map(testCase -> org.junit.jupiter.params.provider.Arguments.of(
                testCase.get("description"),
                testCase));
    }

    /**
     * Test form field validations
     */
    @Test
    @DisplayName("Sign Up Form Validation")
    @Description("Test form field validations and interactions")
    @Story("Form Validation")
    @Severity(SeverityLevel.MINOR)
    void testFormValidation() {
        // Get the sign-up page object
        SignUpPage signUpPage = pageManager.getSignUpPage();

        // Navigate to sign-up page
        Allure.step("Navigate to sign-up page", () -> {
            signUpPage.navigateToSignUp();
        });

        // Test field interactions
        Allure.step("Test form field interactions", () -> {
            // Test entering and clearing data
            signUpPage.enterFirstName("Test");
            assertEquals("Test", signUpPage.getFirstNameFieldValue(),
                "First name should be entered");

            signUpPage.clearFirstNameField();
            String cleared = signUpPage.getFirstNameFieldValue();
            assertTrue(cleared == null || cleared.isEmpty(),
                "First name should be cleared");

            // Test password fields
            signUpPage.enterPassword("Password123!");
            signUpPage.enterConfirmPassword("Password123!");

            // Verify button state
            assertTrue(signUpPage.isSignUpButtonEnabled(),
                "Sign-up button should be enabled");
        });

        // Take screenshot
        takeScreenshot("Sign Up Form Validation");
    }

    /**
     * Test page navigation
     */
    @Test
    @DisplayName("Sign Up Page Navigation")
    @Description("Test navigation to and from sign-up page")
    @Story("Page Navigation")
    @Severity(SeverityLevel.MINOR)
    void testPageNavigation() {
        // Get the sign-up page object
        SignUpPage signUpPage = pageManager.getSignUpPage();

        // Test direct navigation
        Allure.step("Test direct navigation to sign-up page", () -> {
            signUpPage.navigateToSignUp();
            assertTrue(signUpPage.isPageDisplayed(),
                "Should be able to navigate to sign-up page");
        });

        // Test page reload
        Allure.step("Test page reload", () -> {
            page.reload();
            assertTrue(signUpPage.isPageDisplayed(),
                "Page should still be displayed after reload");
        });

        // Take screenshot
        takeScreenshot("Sign Up Navigation Test");
    }
}
