package tests.authentication.login;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;
import utils.TestDataManager;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.util.Map;

// Static imports for better readability
import static org.junit.jupiter.api.Assertions.*;

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

        // Navigate to login page
        Allure.step("Navigate to login page", () -> {
            pageManager.getLoginPage().navigateToLogin();
        });

        // Perform login using the verified working methods
        Allure.step("Enter login credentials", () -> {
            pageManager.getLoginPage().enterEmail(email);
            pageManager.getLoginPage().enterPassword(password);
        });

        Allure.step("Click sign in button", () -> {
            pageManager.getLoginPage().clickSignInButton();
        });

        if(expectedSuccess.equals("List of projects")) {
            Allure.step("Verify successful login", () -> {
                // Wait for navigation to complete after login
                page.waitForURL("**/project/list", new Page.WaitForURLOptions().setTimeout(10000));

                // Verify the "List of projects" text is present on the page
                String containsListOfProjects = pageManager.getProjectListPage().getProjectListText();
                org.junit.jupiter.api.Assertions.assertEquals("List of projects", containsListOfProjects,
                        "Page should contain 'List of projects' text");

                // Attach screenshot to report
                takeScreenshot("Login Success Screenshot");
            });
        }
    }
}