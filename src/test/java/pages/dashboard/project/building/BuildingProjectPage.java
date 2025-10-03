package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingProjectPage - Main page for Building project
 * Handles navigation between tabs
 *
 * Tabs:
 * - Overview
 * - Basic Info
 * - Assessment (with sub-tabs)
 * - Net Zero Plan
 * - Carbon Offset
 * - Net Zero Milestone
 * - Summary of Emissions
 * - Project Files
 */
public class BuildingProjectPage {
    private final Page page;

    // Page header
    private final Locator pageHeader;

    // Tab navigation locators
    private final Locator overviewTab;
    private final Locator basicInfoTab;
    private final Locator assessmentTab;
    private final Locator netZeroPlanTab;
    private final Locator carbonOffsetTab;
    private final Locator netZeroMilestoneTab;
    private final Locator summaryOfEmissionsTab;
    private final Locator projectFilesTab;

    public BuildingProjectPage(Page page) {
        this.page = page;

        // Initialize page locators
        this.pageHeader = page.locator("BOILERPLATE_PAGE_HEADER");

        // Initialize tab locators - TODO: Update with actual selectors
        this.overviewTab = page.locator("BOILERPLATE_OVERVIEW_TAB");
        this.basicInfoTab = page.locator("BOILERPLATE_BASIC_INFO_TAB");
        this.assessmentTab = page.locator("BOILERPLATE_ASSESSMENT_TAB");
        this.netZeroPlanTab = page.locator("BOILERPLATE_NET_ZERO_PLAN_TAB");
        this.carbonOffsetTab = page.locator("BOILERPLATE_CARBON_OFFSET_TAB");
        this.netZeroMilestoneTab = page.locator("BOILERPLATE_NET_ZERO_MILESTONE_TAB");
        this.summaryOfEmissionsTab = page.locator("BOILERPLATE_SUMMARY_OF_EMISSIONS_TAB");
        this.projectFilesTab = page.locator("BOILERPLATE_PROJECT_FILES_TAB");
    }

    /**
     * Page visibility
     */
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        return pageHeader.isVisible();
    }

    public String getPageHeaderText() {
        return pageHeader.textContent();
    }

    /**
     * Tab Navigation - Navigate to specific tabs
     */
    public void goToOverviewTab() {
        page.waitForLoadState();
        overviewTab.click();
    }

    public void goToBasicInfoTab() {
        page.waitForLoadState();
        basicInfoTab.click();
    }

    public void goToAssessmentTab() {
        page.waitForLoadState();
        assessmentTab.click();
    }

    public void goToNetZeroPlanTab() {
        page.waitForLoadState();
        netZeroPlanTab.click();
    }

    public void goToCarbonOffsetTab() {
        page.waitForLoadState();
        carbonOffsetTab.click();
    }

    public void goToNetZeroMilestoneTab() {
        page.waitForLoadState();
        netZeroMilestoneTab.click();
    }

    public void goToSummaryOfEmissionsTab() {
        page.waitForLoadState();
        summaryOfEmissionsTab.click();
    }

    public void goToProjectFilesTab() {
        page.waitForLoadState();
        projectFilesTab.click();
    }

    /**
     * Tab Visibility - Check if tabs are visible
     */
    public boolean isOverviewTabVisible() {
        return overviewTab.isVisible();
    }

    public boolean isBasicInfoTabVisible() {
        return basicInfoTab.isVisible();
    }

    public boolean isAssessmentTabVisible() {
        return assessmentTab.isVisible();
    }

    public boolean isNetZeroPlanTabVisible() {
        return netZeroPlanTab.isVisible();
    }

    public boolean isCarbonOffsetTabVisible() {
        return carbonOffsetTab.isVisible();
    }

    public boolean isNetZeroMilestoneTabVisible() {
        return netZeroMilestoneTab.isVisible();
    }

    public boolean isSummaryOfEmissionsTabVisible() {
        return summaryOfEmissionsTab.isVisible();
    }

    public boolean isProjectFilesTabVisible() {
        return projectFilesTab.isVisible();
    }

    /**
     * Active Tab State - Check which tab is currently active
     */
    public boolean isOverviewTabActive() {
        // TODO: Update with actual active state check (e.g., class contains "active")
        return overviewTab.getAttribute("class").contains("active");
    }

    public boolean isBasicInfoTabActive() {
        return basicInfoTab.getAttribute("class").contains("active");
    }

    public boolean isAssessmentTabActive() {
        return assessmentTab.getAttribute("class").contains("active");
    }
}
