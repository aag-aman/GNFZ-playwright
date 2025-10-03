package tests.dashboard.project.building;

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
 * BuildingProjectRegressionTest - Comprehensive tests for Building project type
 *
 * Detailed tests including:
 * - Building project form validation
 * - Required field testing
 * - Building-specific features
 * - Error handling
 */
@Epic("Project Management")
@Feature("Building Project")
public class BuildingProjectRegressionTest extends BaseTest {

    /**
     * Test Building project form elements
     */
    @Test
    @DisplayName("Building Project Form Elements")
    @Description("Verify all form elements are present on Building project form")
    @Story("Building Project Form")
    @Severity(SeverityLevel.NORMAL)
    void testBuildingProjectFormElements() {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to Building project form
        Allure.step("Login and navigate to Building project form", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail("aman.gupta@promantusinc.com");
            loginPage.enterPassword("Vrishabh@@2025");
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
            projectSelectionPage.selectBuilding();
        });

        // TODO: Add form element verification
        // Allure.step("Verify Building project form elements", () -> {
        // BuildingProjectPage buildingPage = pageManager.getBuildingProjectPage();
        // assertTrue(buildingPage.isPageDisplayed(), "Building form should be
        // displayed");
        // assertTrue(buildingPage.isProjectNameFieldVisible(), "Project name field
        // should be visible");
        // assertTrue(buildingPage.isLocationFieldVisible(), "Location field should be
        // visible");
        // // Add more field checks
        // });

        takeScreenshot("Building Form Elements");
    }

    /**
     * Test creating valid Building project
     */
    @Test
    @DisplayName("Create Valid Building Project")
    @Description("Test creating a Building project with valid data")
    @Story("Building Project Creation")
    @Severity(SeverityLevel.CRITICAL)
    void testCreateValidBuildingProject() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to Building project form
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to Building project form", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("email"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
            projectSelectionPage.selectBuilding();
        });

        // TODO: Fill in Building project form
        // Allure.step("Fill in Building project form", () -> {
        // BuildingProjectPage buildingPage = pageManager.getBuildingProjectPage();
        // buildingPage.enterProjectName("Test Building Project");
        // buildingPage.enterLocation("123 Main St");
        // // Fill in more fields
        // });

        // TODO: Submit and verify
        // Allure.step("Submit Building project", () -> {
        // BuildingProjectPage buildingPage = pageManager.getBuildingProjectPage();
        // buildingPage.clickSubmitButton();
        // });

        takeScreenshot("Valid Building Project Created");
    }

    /**
     * Test Building project form validation
     */
    @Test
    @DisplayName("Building Form Validation")
    @Description("Test form validation for required fields")
    @Story("Building Project Form Validation")
    @Severity(SeverityLevel.NORMAL)
    void testBuildingFormValidation() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to Building project form
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to Building project form", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("email"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
            projectSelectionPage.selectBuilding();
        });

        // TODO: Test validation errors
        // Allure.step("Submit empty form and verify validation", () -> {
        // BuildingProjectPage buildingPage = pageManager.getBuildingProjectPage();
        // buildingPage.clickSubmitButton();
        // assertTrue(buildingPage.isErrorMessageDisplayed(),
        // "Error message should be displayed for empty form");
        // });

        takeScreenshot("Building Form Validation");
    }

    /**
     * Test canceling Building project creation
     */
    @Test
    @DisplayName("Cancel Building Project Creation")
    @Description("Test canceling Building project creation returns to project list")
    @Story("Building Project Creation")
    @Severity(SeverityLevel.MINOR)
    void testCancelBuildingProjectCreation() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to Building project form
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to Building project form", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("email"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
            projectSelectionPage.selectBuilding();
        });

        // TODO: Cancel and verify return to project list
        // Allure.step("Cancel project creation", () -> {
        // BuildingProjectPage buildingPage = pageManager.getBuildingProjectPage();
        // buildingPage.clickCancelButton();
        // assertTrue(projectListPage.isProjectListVisible(),
        // "Should return to project list after cancel");
        // });

        takeScreenshot("Cancel Building Project");
    }
}
