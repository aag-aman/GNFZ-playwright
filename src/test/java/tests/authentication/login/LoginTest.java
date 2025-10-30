package tests.authentication.login;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;
import utils.TestDataManager;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.util.Map;

/**
 * Login Test - demonstrates @AutoStep annotation usage
 * All Page Object methods with @AutoStep automatically create Allure steps
 * with parameter values visible in the report.
 */


@Epic("User Authentication")
@Feature("Login Functionality")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Login Smoke Test - Valid Credentials Only")
    @Description("Verify successful login with valid credentials - smoke test for critical path validation")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    void testSuccessfulLogin() throws IOException {
        Map<String, String> validUser = TestDataManager.getSmokeUser();
        String email = validUser.get("username");
        String password = validUser.get("password");
        String expectedSuccess = validUser.get("expectedSuccess");

        // Navigate to login page - @AutoStep handles step creation
        pageManager.getLoginPage().navigateToLogin();

        // Enter credentials - @AutoStep handles step creation with parameters visible
        pageManager.getLoginPage().enterEmail(email);
        pageManager.getLoginPage().enterPassword(password);

        // Click sign in - @AutoStep handles step creation
        pageManager.getLoginPage().clickSignInButton();

        // Verify login success
        if (expectedSuccess.equals("List of projects")) {
            // Wait for navigation to complete after login
            page.waitForURL("**/project/list", new Page.WaitForURLOptions().setTimeout(10000));

            // Attach screenshot to report
            takeScreenshot("Login Success Screenshot");
        }
    }
}