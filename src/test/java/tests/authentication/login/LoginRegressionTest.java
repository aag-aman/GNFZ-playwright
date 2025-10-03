package tests.authentication.login;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.base.BaseTest;
import utils.TestDataManager;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

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
        String expectedError = testData.get("expectedError");
        String description = testData.get("description");

        System.out.println("Running test: " + description);

        // Navigate to login page
        Allure.step("Navigate to login page", () -> {
            pageManager.getLoginPage().navigateToLogin();
        });

        // Perform login
        Allure.step("Enter login credentials", () -> {
            pageManager.getLoginPage().enterEmail(email);
            pageManager.getLoginPage().enterPassword(password);
        });

        Allure.step("Click sign in button", () -> {
            pageManager.getLoginPage().clickSignInButton();
        });

        if ("List of projects".equals(expectedSuccess)) {
            // Positive test case - expect successful login
            Allure.step("Verify successful login", () -> {
                page.waitForURL("**/project/list", new Page.WaitForURLOptions().setTimeout(10000));

                // Take screenshot on success
                takeScreenshot("Success Screenshot - " + description);

                System.out.println("✅ Success: " + description);
            });
        } else {
            // Negative test case - expect error
            Allure.step("Verify error handling", () -> {
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
            });
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

        Allure.step("Navigate to login page", () -> {
            pageManager.getLoginPage().navigateToLogin();
        });

        Allure.step("Enter valid credentials", () -> {
            pageManager.getLoginPage().enterEmail(email);
            pageManager.getLoginPage().enterPassword(password);
        });

        Allure.step("Click sign in button", () -> {
            pageManager.getLoginPage().clickSignInButton();
        });

        Allure.step("Verify successful login", () -> {
            page.waitForURL("**/project/list", new Page.WaitForURLOptions().setTimeout(10000));

            // Take success screenshot
            takeScreenshot("Happy Path Success Screenshot");
        });
    }
}