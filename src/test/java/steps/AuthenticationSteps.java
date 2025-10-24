package steps;

import java.io.IOException;
import java.util.Map;

import io.qameta.allure.Allure;
import pages.PageManager;
import utils.TestDataManager;

/**
 * AuthenticationSteps - Reusable authentication workflows
 *
 * Provides common login/logout scenarios for different user roles
 */
public class AuthenticationSteps {

    private final PageManager pageManager;

    public AuthenticationSteps(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    /**
     * Login as project owner
     */
    public void loginAsProjectOwner() {
        try {
            Map<String, String> user = TestDataManager.getProjectOwnerUser();
            login(user.get("username"), user.get("password"), "Project Owner");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load project owner user data", e);
        }
    }

    /**
     * Login as team member
     */
    public void loginAsTeamMember() {
        try {
            Map<String, String> user = TestDataManager.getTeamMemberUser();
            login(user.get("username"), user.get("password"), "Team Member");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load team member user data", e);
        }
    }

    /**
     * Logout from application
     */
    public void logout() {
        Allure.step("Logout from application", () -> {
            pageManager.getNavbarPage().logout();
            System.out.println("✓ Logged out successfully");
        });
    }

    /**
     * Private helper method to perform login
     */
    private void login(String username, String password, String userRole) {
        Allure.step("Login as " + userRole + ": " + username, () -> {
            pageManager.getLoginPage().navigateToLogin();
            pageManager.getLoginPage().login(username, password);

            // Validate login was successful using LoginPage's validation method
            if (pageManager.getLoginPage().isLoginSuccess()) {
                System.out.println("✓ Logged in as " + userRole);
            } else {
                throw new RuntimeException("Login failed for " + userRole + ": " + username);
            }
        });
    }
}
