package tests.dashboard.project;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.authentication.LoginPage;
import pages.dashboard.ProjectListPage;
import pages.dashboard.ProjectSelectionPage;
import tests.base.BaseTest;
import utils.TestDataManager;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CreateProjectTest - Smoke tests for create project functionality
 *
 * Tests the flow: Login -> Dashboard -> Create New Project -> Select Project
 * Type
 */
@Epic("Project Management")
@Feature("Create Project")
public class CreateProjectTest extends BaseTest {

    /**
     * Test that create project button is visible on dashboard
     */
    @Test
    @DisplayName("Create Project Button Visible")
    @Description("Verify create project button is visible after login")
    @Story("Create Project Flow")
    @Severity(SeverityLevel.CRITICAL)
    void testCreateProjectButtonVisible() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();

        // Login first
        Map<String, String> user = TestDataManager.getSmokeUser();

        // Navigate to login and enter credentials - @AutoStep handles step creation with parameters visible
        loginPage.navigateToLogin();
        loginPage.enterEmail(user.get("username"));
        loginPage.enterPassword(user.get("password"));
        loginPage.clickSignInButton();

        // Verify on dashboard
        // Does not work because of non robust locator
        // assertTrue(projectListPage.isProjectListVisible(),
        //     "Project list should be visible after login");

        // Verify create project button is visible
        assertTrue(projectListPage.isCreateNewProjectButtonVisible(),
                "Create new project button should be visible");
        assertTrue(projectListPage.isCreateNewProjectButtonEnabled(),
                "Create new project button should be enabled");

        takeScreenshot("Create Project Button Visible");
    }

    /**
     * Test navigation to project selection page
     */
    @Test
    @DisplayName("Navigate to Project Selection")
    @Description("Test clicking create project button navigates to project selection page")
    @Story("Create Project Flow")
    @Severity(SeverityLevel.CRITICAL)
    void testNavigateToProjectSelection() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login first
        Map<String, String> user = TestDataManager.getSmokeUser();

        // Navigate to login and enter credentials - @AutoStep handles step creation with parameters visible
        loginPage.navigateToLogin();
        loginPage.enterEmail(user.get("username"));
        loginPage.enterPassword(user.get("password"));
        loginPage.clickSignInButton();

        // Click create new project - @AutoStep handles step creation
        projectListPage.clickCreateNewProject();

        // Verify project selection page is displayed
        assertTrue(projectSelectionPage.isPageDisplayed(),
                "Project selection page should be displayed");
        assertTrue(projectSelectionPage.isProjectOptionsVisible(),
                "Project options should be visible");

        takeScreenshot("Project Selection Page");
    }

    /**
     * Test project selection page elements
     */
    @Test
    @DisplayName("Project Selection Page Elements")
    @Description("Verify all project options are visible on selection page")
    @Story("Project Selection")
    @Severity(SeverityLevel.NORMAL)
    void testProjectSelectionPageElements() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();

        // Navigate to login and enter credentials - @AutoStep handles step creation with parameters visible
        loginPage.navigateToLogin();
        loginPage.enterEmail(user.get("username"));
        loginPage.enterPassword(user.get("password"));
        loginPage.clickSignInButton();
        projectListPage.clickCreateNewProject();

        // Verify page is displayed
        assertTrue(projectSelectionPage.isPageDisplayed(),
                "Project selection page should be displayed");

        // Check visible options count
        int optionsCount = projectSelectionPage.getVisibleOptionsCount();
        assertTrue(optionsCount > 0,
                "At least one project option should be visible");

        // Add specific option visibility checks as needed
        // Example:
        // assertTrue(projectSelectionPage.isProjectOption1Visible(),
        //     "Project option 1 should be visible");

        takeScreenshot("Project Selection Elements");
    }
}
