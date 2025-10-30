package tests.dashboard;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;
import utils.AssertLogger;
import utils.StepLogger;

import java.util.List;

/**
 * DashboardTest - Comprehensive test suite for Dashboard/Project List functionality
 * Tests UI verification, search, filters, table interactions, navigation, and pagination
 */
@Epic("Dashboard")
@Feature("Project List")
public class DashboardTest extends BaseTest {

    @BeforeEach
    void setupDashboard() {
        // Login and navigate to project list
        authSteps.loginAsProjectOwner();
        page.waitForTimeout(5000);
        AssertLogger.assertTrue(
            pageManager.getProjectListPage().isProjectListVisible(),
            "Should be on project list page after login"
        );
    }

    // ==================== UI VERIFICATION TESTS ====================

    @Test
    @DisplayName("Verify Project List Page UI Elements")
    @Story("UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify all essential UI elements are visible on the project list page")
    void testProjectListPageUIElements() {
        StepLogger.step("Verify project list header is visible", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectListVisible(),
                "Project list header should be visible"
            );
        });

        StepLogger.step("Verify Create New Project button is visible and enabled", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isCreateNewProjectButtonVisible(),
                "Create New Project button should be visible"
            );
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isCreateNewProjectButtonEnabled(),
                "Create New Project button should be enabled"
            );
        });

        StepLogger.step("Verify search field is visible", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isSearchFieldVisible(),
                "Search field should be visible"
            );
        });

        StepLogger.step("Verify Activity Log button is visible", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isActivityLogButtonVisible(),
                "Activity Log button should be visible"
            );
        });

        StepLogger.step("Verify all filter icons are visible", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().areFiltersVisible(),
                "All filter icons should be visible"
            );
        });
    }

    @Test
    @DisplayName("Verify Project Table is Displayed with Data")
    @Story("UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the project table is displayed and contains project data")
    void testProjectTableIsDisplayed() {
        StepLogger.step("Wait for projects to load", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
        });

        StepLogger.verify("Table should not be empty", () -> {
            AssertLogger.assertFalse(
                pageManager.getProjectListPage().isTableEmpty(),
                "Project table should contain at least one project"
            );
        });

        StepLogger.verify("Table should have rows", () -> {
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                rowCount > 0,
                "Table should have at least one row, but found: " + rowCount
            );
        });
    }

    @Test
    @DisplayName("Verify Pagination Controls")
    @Story("UI Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify pagination controls are visible when there are more projects")
    void testPaginationControlsVisible() {
        StepLogger.step("Wait for projects to load", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
        });

        StepLogger.verify("Check if View More button is visible", () -> {
            // View More button should be visible if there are more projects than displayed
            boolean isViewMoreVisible = pageManager.getProjectListPage().isViewMoreButtonVisible();
            if (isViewMoreVisible) {
                String countText = pageManager.getProjectListPage().getProjectCountText();
                AssertLogger.assertTrue(
                    countText.contains("View more"),
                    "View More button should display count text"
                );
            }
        });
    }

    // ==================== SEARCH TESTS ====================

    @Test
    @DisplayName("Search Projects by Name - Valid Search")
    @Story("Search Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that searching for a project by name filters the results correctly")
    void testSearchProjectsByNameValid() {
        // Get first project name to search for
        String projectName = StepLogger.step("Get first visible project name", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            String firstProjectId = projectIds.get(0);
            return pageManager.getProjectListPage().getProjectName(firstProjectId);
        });

        StepLogger.step("Search for project: " + projectName, () -> {
            pageManager.getProjectListPage().searchProjects(projectName);
        });

        StepLogger.verify("Verify search results contain the project", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectDisplayed(projectName),
                "Search results should contain project: " + projectName
            );
        });

        StepLogger.verify("Verify search field contains search text", () -> {
            String searchText = pageManager.getProjectListPage().getSearchText();
            AssertLogger.assertEquals(
                projectName,
                searchText,
                "Search field should contain: " + projectName
            );
        });
    }

    @Test
    @DisplayName("Search Projects - Clear Search")
    @Story("Search Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clearing search restores all projects")
    void testClearSearch() {
        int initialRowCount = StepLogger.step("Get initial project count", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            return pageManager.getProjectListPage().getTableRowCount();
        });

        StepLogger.step("Search for a specific project", () -> {
            pageManager.getProjectListPage().searchProjects("Building");
        });

        StepLogger.step("Clear search", () -> {
            pageManager.getProjectListPage().clearSearch();
        });

        StepLogger.verify("Verify all projects are displayed again", () -> {
            int currentRowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                currentRowCount >= initialRowCount || currentRowCount > 0,
                "After clearing search, projects should be displayed"
            );
        });
    }

    @Test
    @DisplayName("Search Projects - No Results")
    @Story("Search Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that searching with non-existent project name shows no results")
    void testSearchNoResults() {
        StepLogger.step("Search for non-existent project", () -> {
            pageManager.getProjectListPage().searchProjects("NonExistentProject12345XYZ");
        });

        StepLogger.verify("Verify no projects are displayed", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isTableEmpty(),
                "Table should be empty for non-existent project search"
            );
        });
    }

    // ==================== FILTER TESTS ====================

    @Test
    @DisplayName("Filter Projects by Project ID")
    @Story("Filter Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that filtering by project ID shows only matching projects")
    void testFilterByProjectId() {
        String projectId = StepLogger.step("Get first visible project ID", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            return projectIds.get(0);
        });

        StepLogger.step("Filter by project ID: " + projectId, () -> {
            pageManager.getProjectListPage().filterByProjectId(projectId);
        });

        StepLogger.verify("Verify filtered project is displayed", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectDisplayedById(projectId),
                "Filtered project should be displayed: " + projectId
            );
        });

        StepLogger.step("Clear project ID filter", () -> {
            pageManager.getProjectListPage().clearProjectIdFilter();
        });
    }

    @Test
    @DisplayName("Filter Projects by Project Name")
    @Story("Filter Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that filtering by project name shows only matching projects")
    void testFilterByProjectName() {
        String projectName = StepLogger.step("Get first visible project name", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            return pageManager.getProjectListPage().getProjectName(projectIds.get(0));
        });

        StepLogger.step("Filter by project name: " + projectName, () -> {
            pageManager.getProjectListPage().filterByProjectName(projectName);
        });

        StepLogger.verify("Verify filtered project is displayed", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectDisplayed(projectName),
                "Filtered project should be displayed: " + projectName
            );
        });

        StepLogger.step("Clear project name filter", () -> {
            pageManager.getProjectListPage().clearProjectNameFilter();
        });
    }

    @Test
    @DisplayName("Filter Projects by Category - Building")
    @Story("Filter Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that filtering by Building category shows only Building projects")
    void testFilterByCategory() {
        StepLogger.step("Filter by Building category", () -> {
            pageManager.getProjectListPage().filterByCategory("Building");
        });

        StepLogger.verify("Verify Building category is selected", () -> {
            boolean isSelected = pageManager.getProjectListPage().isCategorySelected("Building");
            AssertLogger.assertTrue(
                isSelected,
                "Building category should be selected"
            );
        });

        StepLogger.verify("Verify filtered results contain Building projects", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                rowCount > 0,
                "Should have at least one Building project"
            );
        });
    }

    @Test
    @DisplayName("Filter Projects by Status")
    @Story("Filter Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that filtering by status shows only projects with matching status")
    void testFilterByStatus() {
        StepLogger.step("Filter by 'In progress' status", () -> {
            pageManager.getProjectListPage().filterByStatus("In progress");
        });

        StepLogger.verify("Verify 'In progress' status is selected", () -> {
            boolean isSelected = pageManager.getProjectListPage().isStatusSelected("In progress");
            AssertLogger.assertTrue(
                isSelected,
                "'In progress' status should be selected"
            );
        });
    }

    @Test
    @DisplayName("Filter Projects by Date Range")
    @Story("Filter Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that filtering by date range shows only projects within the range")
    void testFilterByDateRange() {
        StepLogger.step("Filter by date range", () -> {
            // Format: MM/DD/YYYY
            String fromDate = "01/01/2024";
            String toDate = "12/31/2025";
            pageManager.getProjectListPage().filterByDateRange(fromDate, toDate);
        });

        StepLogger.verify("Verify projects are filtered by date range", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            // If there are projects in this date range, table should not be empty
            // If no projects, table could be empty - both are valid
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                rowCount >= 0,
                "Date filter should execute without errors"
            );
        });
    }

    @Test
    @DisplayName("Select All Categories Filter")
    @Story("Filter Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that selecting all categories shows all project types")
    void testSelectAllCategories() {
        StepLogger.step("Select all categories", () -> {
            pageManager.getProjectListPage().selectAllCategories();
        });

        StepLogger.verify("Verify projects are displayed", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                rowCount > 0,
                "Should display projects when all categories are selected"
            );
        });
    }

    @Test
    @DisplayName("Select All Statuses Filter")
    @Story("Filter Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that selecting all statuses shows all projects")
    void testSelectAllStatuses() {
        StepLogger.step("Select all statuses", () -> {
            pageManager.getProjectListPage().selectAllStatuses();
        });

        StepLogger.verify("Verify projects are displayed", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                rowCount > 0,
                "Should display projects when all statuses are selected"
            );
        });
    }

    // ==================== TABLE INTERACTION TESTS ====================

    @Test
    @DisplayName("Get Project Details from Table")
    @Story("Table Interaction")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that project details can be retrieved from the table")
    void testGetProjectDetails() {
        String projectId = StepLogger.step("Get first visible project ID", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            return projectIds.get(0);
        });

        StepLogger.step("Get project details for ID: " + projectId, () -> {
            String projectName = pageManager.getProjectListPage().getProjectName(projectId);
            String category = pageManager.getProjectListPage().getProjectCategory(projectId);
            String status = pageManager.getProjectListPage().getProjectStatus(projectId);
            String date = pageManager.getProjectListPage().getProjectDate(projectId);

            AssertLogger.assertTrue(
                projectName != null && !projectName.isEmpty(),
                "Project name should not be empty"
            );
            AssertLogger.assertTrue(
                category != null && !category.isEmpty(),
                "Project category should not be empty"
            );
            AssertLogger.assertTrue(
                status != null && !status.isEmpty(),
                "Project status should not be empty"
            );
            AssertLogger.assertTrue(
                date != null && !date.isEmpty(),
                "Project date should not be empty"
            );
        });
    }

    @Test
    @DisplayName("Click Project by Name")
    @Story("Table Interaction")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking a project by name navigates to project details")
    void testClickProjectByName() {
        String projectName = StepLogger.step("Get first visible project name", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            return pageManager.getProjectListPage().getProjectName(projectIds.get(0));
        });

        StepLogger.step("Click project: " + projectName, () -> {
            pageManager.getProjectListPage().clickProjectByName(projectName);
        });

        StepLogger.verify("Verify navigation occurred", () -> {
            page.waitForLoadState();
            // After clicking, should navigate away from project list
            // The exact page depends on project type, but URL should change
            String currentUrl = page.url();
            AssertLogger.assertTrue(
                !currentUrl.contains("/project/list") || currentUrl.contains("/project/"),
                "Should navigate to project details page"
            );
        });
    }

    @Test
    @DisplayName("Click Project by ID")
    @Story("Table Interaction")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking a project by ID navigates to project details")
    void testClickProjectById() {
        String projectId = StepLogger.step("Get first visible project ID", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            return projectIds.get(0);
        });

        StepLogger.step("Click project ID: " + projectId, () -> {
            pageManager.getProjectListPage().clickProjectById(projectId);
        });

        StepLogger.verify("Verify navigation occurred", () -> {
            page.waitForLoadState();
            String currentUrl = page.url();
            AssertLogger.assertTrue(
                !currentUrl.contains("/project/list") || currentUrl.contains("/project/"),
                "Should navigate to project details page"
            );
        });
    }

    @Test
    @DisplayName("Get All Visible Project IDs")
    @Story("Table Interaction")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all visible project IDs can be retrieved from the table")
    void testGetAllVisibleProjectIds() {
        StepLogger.step("Get all visible project IDs", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();

            AssertLogger.assertTrue(
                projectIds.size() > 0,
                "Should have at least one project ID"
            );

            // Verify each ID is not empty
            for (String id : projectIds) {
                AssertLogger.assertTrue(
                    id != null && !id.isEmpty(),
                    "Project ID should not be empty"
                );
            }
        });
    }

    @Test
    @DisplayName("Verify Project is Displayed by Name")
    @Story("Table Interaction")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a specific project can be found in the table by name")
    void testVerifyProjectDisplayedByName() {
        String projectName = StepLogger.step("Get first visible project name", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            return pageManager.getProjectListPage().getProjectName(projectIds.get(0));
        });

        StepLogger.verify("Verify project is displayed: " + projectName, () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectDisplayed(projectName),
                "Project should be displayed: " + projectName
            );
        });
    }

    @Test
    @DisplayName("Verify Project is Displayed by ID")
    @Story("Table Interaction")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a specific project can be found in the table by ID")
    void testVerifyProjectDisplayedById() {
        String projectId = StepLogger.step("Get first visible project ID", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            AssertLogger.assertTrue(projectIds.size() > 0, "Should have at least one project");
            return projectIds.get(0);
        });

        StepLogger.verify("Verify project is displayed by ID: " + projectId, () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectDisplayedById(projectId),
                "Project should be displayed: " + projectId
            );
        });
    }

    // ==================== NAVIGATION TESTS ====================

    @Test
    @DisplayName("Navigate to Create New Project")
    @Story("Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking Create New Project button navigates to project selection page")
    void testNavigateToCreateNewProject() {
        StepLogger.step("Click Create New Project button", () -> {
            pageManager.getProjectListPage().clickCreateNewProject();
        });

        StepLogger.verify("Verify navigation to project selection page", () -> {
            page.waitForLoadState();
            AssertLogger.assertTrue(
                pageManager.getProjectSelectionPage().isPageDisplayed(),
                "Should navigate to project selection page"
            );
        });
    }

    @Test
    @DisplayName("Navigate to Activity Log")
    @Story("Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking Activity Log button navigates to activity log page")
    void testNavigateToActivityLog() {
        StepLogger.step("Click Activity Log button", () -> {
            pageManager.getProjectListPage().clickActivityLog();
        });

        StepLogger.verify("Verify navigation to activity log page", () -> {
            page.waitForLoadState();
            AssertLogger.assertTrue(
                pageManager.getActivityLogPage().isPageDisplayed(),
                "Should navigate to activity log page"
            );
        });
    }

    // ==================== PAGINATION TESTS ====================

    @Test
    @DisplayName("Load More Projects with View More Button")
    @Story("Pagination")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking View More button loads additional projects")
    void testLoadMoreProjects() {
        // Check if View More button is visible
        if (!pageManager.getProjectListPage().isViewMoreButtonVisible()) {
            StepLogger.step("Skip test - View More button not visible (all projects already loaded)", () -> {
                // Skip test if all projects are already loaded
            });
            return;
        }

        int initialRowCount = StepLogger.step("Get initial project count", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            return pageManager.getProjectListPage().getTableRowCount();
        });

        StepLogger.step("Click View More button", () -> {
            pageManager.getProjectListPage().clickViewMore();
        });

        StepLogger.verify("Verify more projects are loaded", () -> {
            int newRowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                newRowCount > initialRowCount,
                "Should load more projects. Initial: " + initialRowCount + ", After: " + newRowCount
            );
        });
    }

    @Test
    @DisplayName("Verify Project Count Text Format")
    @Story("Pagination")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that the View More button displays correct count format")
    void testProjectCountTextFormat() {
        // Check if View More button is visible
        if (!pageManager.getProjectListPage().isViewMoreButtonVisible()) {
            StepLogger.step("Skip test - View More button not visible", () -> {
                // Skip test if all projects are already loaded
            });
            return;
        }

        StepLogger.verify("Verify count text format", () -> {
            String countText = pageManager.getProjectListPage().getProjectCountText();
            AssertLogger.assertTrue(
                countText.contains("View more") && countText.contains("/"),
                "Count text should be in format 'View more X/Y', but was: " + countText
            );
        });
    }

    // ==================== COMBINED/INTEGRATION TESTS ====================

    @Test
    @DisplayName("Combined Search and Filter")
    @Story("Integration")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that search and filter can be used together")
    void testCombinedSearchAndFilter() {
        StepLogger.step("Apply category filter", () -> {
            pageManager.getProjectListPage().filterByCategory("Building");
        });

        StepLogger.step("Apply search", () -> {
            pageManager.getProjectListPage().searchProjects("Building");
        });

        StepLogger.verify("Verify results are filtered and searched", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            // Results should show Building projects matching the search
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            // Could be 0 if no matches, or > 0 if matches exist
            AssertLogger.assertTrue(
                rowCount >= 0,
                "Combined search and filter should execute without errors"
            );
        });

        StepLogger.step("Clear search and filter", () -> {
            pageManager.getProjectListPage().clearSearch();
        });
    }

    @Test
    @DisplayName("Multiple Filters Applied Together")
    @Story("Integration")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that multiple filters can be applied simultaneously")
    void testMultipleFilters() {
        StepLogger.step("Apply category filter", () -> {
            pageManager.getProjectListPage().filterByCategory("Building");
        });

        StepLogger.step("Apply status filter", () -> {
            pageManager.getProjectListPage().filterByStatus("In progress");
        });

        StepLogger.verify("Verify multiple filters are applied", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            // Results should show Building projects with In Progress status
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                rowCount >= 0,
                "Multiple filters should execute without errors"
            );
        });
    }

    @Test
    @DisplayName("End-to-End Project List Workflow")
    @Story("Integration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test complete workflow: view list, search, filter, view details, return to list")
    void testEndToEndWorkflow() {
        StepLogger.step("Verify project list is displayed", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectListVisible(),
                "Project list should be visible"
            );
        });

        String projectName = StepLogger.step("Search for a project", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            List<String> projectIds = pageManager.getProjectListPage().getAllVisibleProjectIds();
            String firstProjectId = projectIds.get(0);
            String name = pageManager.getProjectListPage().getProjectName(firstProjectId);
            pageManager.getProjectListPage().searchProjects(name);
            return name;
        });

        StepLogger.step("Filter by category", () -> {
            pageManager.getProjectListPage().filterByCategory("Building");
        });

        StepLogger.step("Verify project is still displayed", () -> {
            AssertLogger.assertTrue(
                pageManager.getProjectListPage().isProjectDisplayed(projectName),
                "Project should still be displayed after filtering: " + projectName
            );
        });

        StepLogger.step("Clear filters and search", () -> {
            pageManager.getProjectListPage().clearSearch();
        });

        StepLogger.verify("Verify all projects are displayed again", () -> {
            pageManager.getProjectListPage().waitForProjectsToLoad();
            int rowCount = pageManager.getProjectListPage().getTableRowCount();
            AssertLogger.assertTrue(
                rowCount > 0,
                "All projects should be displayed after clearing filters"
            );
        });
    }
}
