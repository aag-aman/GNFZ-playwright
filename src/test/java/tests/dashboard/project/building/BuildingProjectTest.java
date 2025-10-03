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
 * BuildingProjectTest - Smoke tests for Building project type
 *
 * Tests the flow: Login -> Create Project -> Select Building -> [Building Form]
 */
@Epic("Project Management")
@Feature("Building Project")
public class BuildingProjectTest extends BaseTest {

    /**
     * Test that Building option is visible and selectable
     */
    @Test
    @DisplayName("Building Option Visible")
    @Description("Verify Building project option is visible on project selection page")
    @Story("Building Project Selection")
    @Severity(SeverityLevel.CRITICAL)
    void testBuildingOptionVisible() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to project selection", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("email"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
        });

        // Verify Building option is visible
        Allure.step("Verify Building option is visible", () -> {
            assertTrue(projectSelectionPage.isBuildingOptionVisible(),
                "Building option should be visible");
        });

        takeScreenshot("Building Option Visible");
    }

    /**
     * Test selecting Building project type
     */
    @Test
    @DisplayName("Select Building Project")
    @Description("Test selecting Building project type from options")
    @Story("Building Project Selection")
    @Severity(SeverityLevel.CRITICAL)
    void testSelectBuildingProject() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to project selection", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("email"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
        });

        // Select Building option
        Allure.step("Select Building project type", () -> {
            projectSelectionPage.selectBuilding();
        });

        // TODO: Add verification for Building project form/page
        // Allure.step("Verify Building project form is displayed", () -> {
        //     assertTrue(buildingProjectPage.isPageDisplayed(),
        //         "Building project form should be displayed");
        // });

        takeScreenshot("Building Project Selected");
    }

    /**
     * Test Building option text
     */
    @Test
    @DisplayName("Building Option Text")
    @Description("Verify Building option displays correct text")
    @Story("Building Project Selection")
    @Severity(SeverityLevel.MINOR)
    void testBuildingOptionText() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to project selection", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("email"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
        });

        // Verify Building option text
        Allure.step("Verify Building option text", () -> {
            String optionText = projectSelectionPage.getBuildingOptionText();
            assertNotNull(optionText, "Building option text should not be null");
            assertTrue(optionText.toLowerCase().contains("building"),
                "Building option should contain 'building' text");
        });

        takeScreenshot("Building Option Text");
    }
}
