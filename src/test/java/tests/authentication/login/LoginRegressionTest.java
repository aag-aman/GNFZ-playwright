package tests.authentication.login;

import io.qameta.allure.*;
import pages.authentication.LoginPage;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.base.BaseTest;
import utils.TestDataManager;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Authentication")
@Feature("Login Functionality")
public class LoginRegressionTest extends BaseTest {

    static Stream<Map<String, String>> loginTestData() throws IOException {
        return TestDataManager.loadRegressionUsers().stream();
    }

    @ParameterizedTest(name = "Login Test: {0}")
    @MethodSource("loginTestData")
    @DisplayName("Login Regression Tests - All Scenarios")
    @Description("Comprehensive login testing with valid and invalid scenarios")
    @Story("Login Validation")
    @Severity(SeverityLevel.NORMAL)
    void testLoginScenarios(Map<String, String> testData) {
        String email = testData.get("username");
        String password = testData.get("password");
        String expectedSuccess = testData.get("expectedSuccess");
        String description = testData.get("description");
        LoginPage loginPage = pageManager.getLoginPage();
        System.out.println("Running test: " + description);

        // Navigate to login page - @AutoStep handles step creation
        loginPage.navigateToLogin();

        // Enter login credentials - @AutoStep handles step creation with parameters visible
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);

        // Click sign in button - @AutoStep handles step creation
        loginPage.clickSignInButton();

        if ("List of projects".equals(expectedSuccess)) {
            // Positive test case - expect successful login
            assertTrue(loginPage.isLoginSuccess(), "Login should be successful");

            // Take screenshot on success
            takeScreenshot("Success Screenshot - " + description);

            System.out.println("✅ Success: " + description);
        } else {
            // Negative test case - expect error
            // Wait for error message to appear
            page.waitForTimeout(2000);

            // Check for various error indicators
            boolean hasError = page.locator(".error, .alert-danger, [role='alert']").count() > 0 ||
                    page.content().contains("error") ||
                    page.content().contains("invalid") ||
                    page.content().contains("required");

            org.junit.jupiter.api.Assertions.assertTrue(hasError,
                    "Expected error message for: " + description);

            // Take screenshot on error validation
            takeScreenshot("Error Screenshot - " + description);

            System.out.println("✅ Error handled correctly: " + description);
        }
    }

    @Test
    @DisplayName("Valid Login - Happy Path")
    @Description("Test successful login with valid credentials from regression test suite")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    void testValidLoginHappyPath() throws IOException {
        Map<String, String> validUser = TestDataManager.getValidRegressionUser();
        String email = validUser.get("username");
        String password = validUser.get("password");

        // Navigate to login page - @AutoStep handles step creation
        pageManager.getLoginPage().navigateToLogin();

        // Enter valid credentials - @AutoStep handles step creation with parameters visible
        pageManager.getLoginPage().enterEmail(email);
        pageManager.getLoginPage().enterPassword(password);

        // Click sign in button - @AutoStep handles step creation
        pageManager.getLoginPage().clickSignInButton();

        // Wait for navigation to complete after successful login
        page.waitForURL("**/project/list", new Page.WaitForURLOptions().setTimeout(10000));

        // Take success screenshot
        takeScreenshot("Happy Path Success Screenshot");
    }
}