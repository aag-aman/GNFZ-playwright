package pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.AutoStep;
import utils.InputHelper;

import java.util.ArrayList;
import java.util.List;

public class ProjectListPage {
    private final Page page;

    // ==================== EXISTING LOCATORS ====================
    private final Locator projectListHeader;
    private final Locator createNewProjectButton;

    // ==================== SEARCH & ACTIONS ====================
    private final Locator searchInput;
    private final Locator activityLogButton;

    // ==================== FILTER ICONS (Column Headers) ====================
    private final Locator projectIdFilterIcon;
    private final Locator projectNameFilterIcon;
    private final Locator categoryFilterIcon;
    private final Locator statusFilterIcon;
    private final Locator dateFilterIcon;

    // ==================== FILTER INPUTS ====================
    private final Locator projectIdFilterInput;
    private final Locator projectNameFilterInput;
    private final Locator dateFromInput;
    private final Locator dateToInput;

    // ==================== TABLE ELEMENTS ====================
    private final Locator projectTable;
    private final Locator projectTableRows;
    private final Locator viewMoreButton;

    public ProjectListPage(Page page) {
        this.page = page;

        // ==================== INITIALIZE EXISTING LOCATORS ====================
        this.projectListHeader = page.locator("b:has-text('List of projects')");
        this.createNewProjectButton = page.locator("#gnfz-create-project");

        // ==================== INITIALIZE SEARCH & ACTIONS ====================
        this.searchInput = page.locator("#project-search");
        this.activityLogButton = page.locator("button:has-text('Activity log')");

        // ==================== INITIALIZE FILTER ICONS ====================
        this.projectIdFilterIcon = page.locator("#project-filter-project-id");
        this.projectNameFilterIcon = page.locator("#project-filter-building-title");
        this.categoryFilterIcon = page.locator("#project-filter-category");
        this.statusFilterIcon = page.locator("#project-filter-request");
        this.dateFilterIcon = page.locator("#project-filter-date");

        // ==================== INITIALIZE FILTER INPUTS ====================
        this.projectIdFilterInput = page.locator("#filter-project-id");
        this.projectNameFilterInput = page.locator("#filter-building-title");
        this.dateFromInput = page.locator("#project-filter-date-from");
        this.dateToInput = page.locator("#project-filter-date-to");

        // ==================== INITIALIZE TABLE ELEMENTS ====================
        this.projectTable = page.locator("table.table-flush");
        // Target data rows specifically (rows with td elements, excluding header/empty rows)
        this.projectTableRows = page.locator("table.table-flush tbody tr:has(td)");
        this.viewMoreButton = page.locator("button:has-text('View more')");
    }

    // ==================== EXISTING METHODS ====================

    @AutoStep
    public boolean isProjectListVisible() {
        page.waitForLoadState();
        projectListHeader.waitFor();
        return projectListHeader.isVisible();
    }

    @AutoStep
    public boolean isLoginSuccessful() {
        return isProjectListVisible();
    }

    public Locator getProjectListHeader() {
        return projectListHeader;
    }

    @AutoStep
    public void clickCreateNewProject() {
        page.waitForLoadState();
        createNewProjectButton.click();
    }

    @AutoStep
    public boolean isCreateNewProjectButtonVisible() {
        page.waitForLoadState();
        createNewProjectButton.waitFor();
        return createNewProjectButton.isVisible();
    }

    @AutoStep
    public boolean isCreateNewProjectButtonEnabled() {
        page.waitForLoadState();
        createNewProjectButton.waitFor();
        return createNewProjectButton.isEnabled();
    }

    // ==================== SEARCH METHODS ====================

    @AutoStep
    public void searchProjects(String searchText) {
        page.waitForLoadState();
        searchInput.clear();
        InputHelper.humanizedInput(page, searchInput, searchText);
        page.waitForTimeout(1000); // Wait for search results to filter
    }

    @AutoStep
    public void clearSearch() {
        page.waitForLoadState();
        searchInput.clear();
        searchInput.press("Enter");
        page.waitForTimeout(1000);
    }

    @AutoStep
    public String getSearchText() {
        page.waitForLoadState();
        return searchInput.inputValue();
    }

    @AutoStep
    public boolean isSearchFieldVisible() {
        page.waitForLoadState();
        return searchInput.isVisible();
    }

    // ==================== ACTION METHODS ====================

    @AutoStep
    public void clickActivityLog() {
        page.waitForLoadState();
        activityLogButton.click();
    }

    @AutoStep
    public boolean isActivityLogButtonVisible() {
        page.waitForLoadState();
        return activityLogButton.isVisible();
    }

    // ==================== FILTER METHODS - TEXT FILTERS ====================

    @AutoStep
    public void openProjectIdFilter() {
        page.waitForLoadState();
        projectIdFilterIcon.click();
        waitForFilterDropdownToOpen(projectIdFilterIcon);
    }

    @AutoStep
    public void filterByProjectId(String projectId) {
        openProjectIdFilter();
        page.waitForLoadState();
        projectIdFilterInput.clear();
        InputHelper.humanizedInput(page, projectIdFilterInput, projectId);
        page.waitForTimeout(1000); // Wait for filter to apply
    }

    @AutoStep
    public void clearProjectIdFilter() {
        openProjectIdFilter();
        page.waitForLoadState();
        projectIdFilterInput.clear();
        projectIdFilterInput.press("Enter");
        page.waitForTimeout(1000);
    }

    @AutoStep
    public void openProjectNameFilter() {
        page.waitForLoadState();
        projectNameFilterIcon.click();
        waitForFilterDropdownToOpen(projectNameFilterIcon);
    }

    @AutoStep
    public void filterByProjectName(String projectName) {
        openProjectNameFilter();
        page.waitForLoadState();
        projectNameFilterInput.clear();
        InputHelper.humanizedInput(page, projectNameFilterInput, projectName);
        page.waitForTimeout(1000); // Wait for filter to apply
    }

    @AutoStep
    public void clearProjectNameFilter() {
        openProjectNameFilter();
        page.waitForLoadState();
        projectNameFilterInput.clear();
        projectNameFilterInput.press("Enter");
        page.waitForTimeout(1000);
    }

    // ==================== FILTER METHODS - CATEGORY ====================

    @AutoStep
    public void openCategoryFilter() {
        page.waitForLoadState();
        categoryFilterIcon.click();
        waitForFilterDropdownToOpen(categoryFilterIcon);
    }

    @AutoStep
    public void filterByCategory(String category) {
        openCategoryFilter();
        page.waitForLoadState();
        Locator categoryCheckbox = getCategoryCheckboxLocator(category);
        categoryCheckbox.click();
        page.waitForTimeout(1000); // Wait for filter to apply
    }

    @AutoStep
    public void selectAllCategories() {
        openCategoryFilter();
        page.waitForLoadState();
        Locator selectAllCheckbox = page.locator("#project-filter-catgory-select-option-all");
        selectAllCheckbox.click();
        page.waitForTimeout(1000);
    }

    @AutoStep
    public boolean isCategorySelected(String category) {
        openCategoryFilter();
        page.waitForLoadState();
        Locator categoryCheckbox = getCategoryCheckboxLocator(category);
        return isCheckboxSelected(categoryCheckbox);
    }

    // ==================== FILTER METHODS - STATUS ====================

    @AutoStep
    public void openStatusFilter() {
        page.waitForLoadState();
        statusFilterIcon.click();
        waitForFilterDropdownToOpen(statusFilterIcon);
    }

    @AutoStep
    public void filterByStatus(String status) {
        openStatusFilter();
        page.waitForLoadState();
        Locator statusCheckbox = getStatusCheckboxLocator(status);
        statusCheckbox.click();
        page.waitForTimeout(1000); // Wait for filter to apply
    }

    @AutoStep
    public void selectAllStatuses() {
        openStatusFilter();
        page.waitForLoadState();
        Locator selectAllCheckbox = page.locator("#project-filter-request-select-option-all");
        selectAllCheckbox.click();
        page.waitForTimeout(1000);
    }

    @AutoStep
    public boolean isStatusSelected(String status) {
        openStatusFilter();
        page.waitForLoadState();
        Locator statusCheckbox = getStatusCheckboxLocator(status);
        return isCheckboxSelected(statusCheckbox);
    }

    // ==================== FILTER METHODS - DATE RANGE ====================

    @AutoStep
    public void openDateFilter() {
        page.waitForLoadState();
        dateFilterIcon.click();
        waitForFilterDropdownToOpen(dateFilterIcon);
    }

    @AutoStep
    public void filterByDateFrom(String date) {
        openDateFilter();
        page.waitForLoadState();
        InputHelper.selectDateFromDatepicker(page, dateFromInput, date);
        page.waitForTimeout(1000); // Wait for filter to apply
    }

    @AutoStep
    public void filterByDateTo(String date) {
        openDateFilter();
        page.waitForLoadState();
        InputHelper.selectDateFromDatepicker(page, dateToInput, date);
        page.waitForTimeout(1000); // Wait for filter to apply
    }

    @AutoStep
    public void filterByDateRange(String fromDate, String toDate) {
        openDateFilter();
        page.waitForLoadState();
        InputHelper.selectDateFromDatepicker(page, dateFromInput, fromDate);
        page.waitForTimeout(500);
        InputHelper.selectDateFromDatepicker(page, dateToInput, toDate);
        page.waitForTimeout(1000); // Wait for filter to apply
    }

    // ==================== TABLE INTERACTION METHODS ====================

    @AutoStep
    public void clickProjectByName(String projectName) {
        page.waitForLoadState();
        Locator projectRow = projectTableRows.locator(":has-text('" + projectName + "')").first();
        projectRow.click();
    }

    @AutoStep
    public void clickProjectById(String projectId) {
        page.waitForLoadState();
        Locator projectRow = getProjectRow(projectId);
        projectRow.click();
    }

    @AutoStep
    public Locator getProjectRow(String projectId) {
        page.waitForLoadState();
        waitForProjectsToLoad();
        Locator row = projectTableRows.locator(":has-text('" + projectId + "')").first();
        // Wait for the row to be attached to DOM with a reasonable timeout
        try {
            row.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        } catch (Exception e) {
            throw new RuntimeException("Project row with ID '" + projectId + "' not found in table", e);
        }
        return row;
    }

    @AutoStep
    public boolean isProjectDisplayed(String projectName) {
        page.waitForLoadState();
        waitForProjectsToLoad();
        return projectTableRows.locator(":has-text('" + projectName + "')").count() > 0;
    }

    @AutoStep
    public boolean isProjectDisplayedById(String projectId) {
        page.waitForLoadState();
        waitForProjectsToLoad();
        return projectTableRows.locator(":has-text('" + projectId + "')").count() > 0;
    }

    @AutoStep
    public String getProjectName(String projectId) {
        page.waitForLoadState();
        waitForProjectsToLoad();

        // Iterate through all rows to find the matching project ID
        int rowCount = projectTableRows.count();
        for (int i = 0; i < rowCount; i++) {
            Locator row = projectTableRows.nth(i);
            String rowProjectId = row.locator("td").first().textContent().trim();
            if (rowProjectId.equals(projectId)) {
                return row.locator("td").nth(1).textContent().trim();
            }
        }

        throw new RuntimeException("Project with ID '" + projectId + "' not found in table");
    }

    @AutoStep
    public String getProjectCategory(String projectId) {
        page.waitForLoadState();
        waitForProjectsToLoad();

        int rowCount = projectTableRows.count();
        for (int i = 0; i < rowCount; i++) {
            Locator row = projectTableRows.nth(i);
            String rowProjectId = row.locator("td").first().textContent().trim();
            if (rowProjectId.equals(projectId)) {
                return row.locator("td").nth(2).textContent().trim();
            }
        }

        throw new RuntimeException("Project with ID '" + projectId + "' not found in table");
    }

    @AutoStep
    public String getProjectStatus(String projectId) {
        page.waitForLoadState();
        waitForProjectsToLoad();

        int rowCount = projectTableRows.count();
        for (int i = 0; i < rowCount; i++) {
            Locator row = projectTableRows.nth(i);
            String rowProjectId = row.locator("td").first().textContent().trim();
            if (rowProjectId.equals(projectId)) {
                return row.locator("td").nth(3).textContent().trim();
            }
        }

        throw new RuntimeException("Project with ID '" + projectId + "' not found in table");
    }

    @AutoStep
    public String getProjectDate(String projectId) {
        page.waitForLoadState();
        waitForProjectsToLoad();

        int rowCount = projectTableRows.count();
        for (int i = 0; i < rowCount; i++) {
            Locator row = projectTableRows.nth(i);
            String rowProjectId = row.locator("td").first().textContent().trim();
            if (rowProjectId.equals(projectId)) {
                return row.locator("td").nth(4).textContent().trim();
            }
        }

        throw new RuntimeException("Project with ID '" + projectId + "' not found in table");
    }

    @AutoStep
    public List<String> getAllVisibleProjectIds() {
        page.waitForLoadState();
        waitForProjectsToLoad();
        List<String> projectIds = new ArrayList<>();
        int rowCount = projectTableRows.count();
        for (int i = 0; i < rowCount; i++) {
            Locator row = projectTableRows.nth(i);
            // Check if row has td elements (to exclude header rows or empty rows)
            if (row.locator("td").count() > 0) {
                String projectId = row.locator("td").first().textContent().trim();
                // Only add non-empty project IDs
                if (!projectId.isEmpty()) {
                    projectIds.add(projectId);
                }
            }
        }
        return projectIds;
    }

    // ==================== PAGINATION METHODS ====================

    @AutoStep
    public void clickViewMore() {
        page.waitForLoadState();
        viewMoreButton.scrollIntoViewIfNeeded();
        viewMoreButton.click();
        page.waitForTimeout(2000); // Wait for more projects to load
    }

    @AutoStep
    public boolean isViewMoreButtonVisible() {
        page.waitForLoadState();
        return viewMoreButton.isVisible();
    }

    @AutoStep
    public String getProjectCountText() {
        page.waitForLoadState();
        return viewMoreButton.textContent().trim();
    }

    // ==================== VERIFICATION METHODS ====================

    @AutoStep
    public int getTableRowCount() {
        page.waitForLoadState();
        waitForProjectsToLoad();
        return projectTableRows.count();
    }

    @AutoStep
    public boolean isTableEmpty() {
        page.waitForLoadState();
        return projectTableRows.count() == 0;
    }

    @AutoStep
    public void waitForProjectsToLoad() {
        page.waitForLoadState();
        // Wait for table to be visible
        projectTable.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        // Additional wait for dynamic content to settle
        page.waitForTimeout(1000);
        // Wait for at least one row or confirm table is empty
        page.waitForTimeout(500);
    }

    @AutoStep
    public boolean areFiltersVisible() {
        page.waitForLoadState();
        return projectIdFilterIcon.isVisible() &&
               projectNameFilterIcon.isVisible() &&
               categoryFilterIcon.isVisible() &&
               statusFilterIcon.isVisible() &&
               dateFilterIcon.isVisible();
    }

    // ==================== PRIVATE HELPER METHODS ====================

    /**
     * Get category checkbox locator using parameterized pattern
     * @param category - Category name (e.g., "Building", "Portfolio", "Business")
     */
    private Locator getCategoryCheckboxLocator(String category) {
        // Normalize category name: replace spaces with hyphens for ID pattern
        String normalizedCategory = category.replace(" ", "-");
        return page.locator("#project-filter-catgory-select-option-" + normalizedCategory);
    }

    /**
     * Get status checkbox locator using parameterized pattern
     * @param status - Status name (e.g., "In progress", "Completed", "On Hold")
     */
    private Locator getStatusCheckboxLocator(String status) {
        // Normalize status name: replace spaces with hyphens for ID pattern
        String normalizedStatus = status.replace(" ", "-");
        return page.locator("#project-filter-request-select-option-" + normalizedStatus);
    }

    /**
     * Check if a custom checkbox is selected
     * @param checkboxDiv - The checkbox div container
     */
    private boolean isCheckboxSelected(Locator checkboxDiv) {
        // Check if checkbox span has a checkmark or selected state
        Locator checkboxSpan = checkboxDiv.locator("span.checkbox-span");
        // The selected state may be indicated by a child element or class change
        // This implementation checks for the presence of a checkmark icon
        return checkboxSpan.locator("*").count() > 0 || checkboxDiv.getAttribute("class").contains("selected");
    }

    /**
     * Wait for filter dropdown to fully open
     * @param filterIcon - The filter icon that was clicked
     */
    private void waitForFilterDropdownToOpen(Locator filterIcon) {
        page.waitForTimeout(500); // Wait for dropdown animation
        page.waitForLoadState();
    }
}