package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingProjectPage - Main page for Building project
 *
 * Page Structure:
 * 1. Breadcrumbs: Projects → Building → [Current Project Name]
 * 2. Page Header: Project title
 * 3. Tabs Navigation: 8 tabs for different sections
 * 4. Tab Content: Content sections for each tab
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

    // Breadcrumbs section
    private final Locator breadcrumbsNav;
    private final Locator breadcrumbsList;
    private final Locator projectsBreadcrumb;
    private final Locator buildingTypeBreadcrumb;
    private final Locator currentProjectBreadcrumb;

    // Page header
    private final Locator pageHeader;

    // Tab navigation - Radio inputs
    private final Locator tab1Input;
    private final Locator tab2Input;
    private final Locator tab3Input;
    private final Locator tab4Input;
    private final Locator tab5Input;
    private final Locator tab6Input;
    private final Locator tab7Input;
    private final Locator tab8Input;

    // Tab navigation - Labels (clickable)
    private final Locator overviewTab;
    private final Locator basicInfoTab;
    private final Locator assessmentTab;
    private final Locator netZeroPlanTab;
    private final Locator carbonOffsetTab;
    private final Locator netZeroMilestoneTab;
    private final Locator summaryOfEmissionsTab;
    private final Locator projectFilesTab;

    // Tab content sections
    private final Locator overviewTabContent;
    private final Locator basicInfoTabContent;
    private final Locator assessmentTabContent;
    private final Locator netZeroPlanTabContent;
    private final Locator carbonOffsetTabContent;
    private final Locator netZeroMilestoneTabContent;
    private final Locator summaryOfEmissionsTabContent;
    private final Locator projectFilesTabContent;

    public BuildingProjectPage(Page page) {
        this.page = page;

        // Initialize breadcrumbs locators
        this.breadcrumbsNav = page.locator("nav[aria-label='breadcrumb']");
        this.breadcrumbsList = page.locator("ol.breadcrumb");
        this.projectsBreadcrumb = page.locator("li.breadcrumb-item:has-text('Projects') a");
        this.buildingTypeBreadcrumb = page.locator("li.breadcrumb-item:has-text('Building') a");
        this.currentProjectBreadcrumb = page.locator("li.breadcrumb-item.active");

        // Initialize page header
        this.pageHeader = page.locator("h3:has-text('Net Zero certification for')");

        // Initialize tab radio inputs
        this.tab1Input = page.locator("#tab1");
        this.tab2Input = page.locator("#tab2");
        this.tab3Input = page.locator("#tab3");
        this.tab4Input = page.locator("#tab4");
        this.tab5Input = page.locator("#tab5");
        this.tab6Input = page.locator("#tab6");
        this.tab7Input = page.locator("#tab7");
        this.tab8Input = page.locator("#tab8");

        // Initialize tab label locators (clickable)
        this.overviewTab = page.locator("#gnfz-overview > label");
        this.basicInfoTab = page.locator("#gnfz-basicInfo > label");
        this.assessmentTab = page.locator("#gnfz-assessment > label");
        this.netZeroPlanTab = page.locator("#gnfz-nzp > label");
        this.carbonOffsetTab = page.locator("#gnfz-carbon-offset > label");
        this.netZeroMilestoneTab = page.locator("#gnfz-nzm > label");
        this.summaryOfEmissionsTab = page.locator("#gnfz-summary-of-emission > label");
        this.projectFilesTab = page.locator("#gnfz-project-files > label");

        // Initialize tab content sections
        this.overviewTabContent = page.locator(".tab1.page-section.tab-container");
        this.basicInfoTabContent = page.locator(".tab2.page-section.tab-container");
        this.assessmentTabContent = page.locator(".tab3.page-section.tab-container");
        this.netZeroPlanTabContent = page.locator(".tab4.page-section.tab-container");
        this.carbonOffsetTabContent = page.locator(".tab5.page-section.tab-container");
        this.netZeroMilestoneTabContent = page.locator(".tab6.page-section.tab-container");
        this.summaryOfEmissionsTabContent = page.locator(".tab7.page-section.tab-container");
        this.projectFilesTabContent = page.locator(".tab8.page-section.tab-container");
    }

    /**
     * Page visibility
     */
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        return pageHeader.isVisible();
    }

    /**
     * Breadcrumbs Navigation
     */
    public boolean isBreadcrumbsVisible() {
        page.waitForLoadState();
        return breadcrumbsNav.isVisible();
    }

    public void clickProjectsBreadcrumb() {
        page.waitForLoadState();
        projectsBreadcrumb.click();
        page.waitForTimeout(500);
    }

    public void clickBuildingTypeBreadcrumb() {
        page.waitForLoadState();
        buildingTypeBreadcrumb.click();
        page.waitForTimeout(500);
    }

    public String getCurrentProjectName() {
        page.waitForLoadState();
        return currentProjectBreadcrumb.textContent().trim();
    }

    public String getProjectsBreadcrumbText() {
        page.waitForLoadState();
        return projectsBreadcrumb.textContent().trim();
    }

    public String getBuildingTypeBreadcrumbText() {
        page.waitForLoadState();
        return buildingTypeBreadcrumb.textContent().trim();
    }

    public int getBreadcrumbCount() {
        page.waitForLoadState();
        return breadcrumbsList.locator("li").count();
    }

    /**
     * Page Header
     */
    public String getPageHeaderText() {
        page.waitForLoadState();
        return pageHeader.textContent().trim();
    }

    public String getProjectTitle() {
        page.waitForLoadState();
        // Extract project name from "Net Zero certification for [Project Name]"
        String fullTitle = pageHeader.textContent().trim();
        return fullTitle.replace("Net Zero certification for", "").trim();
    }

    public boolean isPageHeaderVisible() {
        page.waitForLoadState();
        return pageHeader.isVisible();
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
     * Active tabs have their radio input checked
     */
    public boolean isOverviewTabActive() {
        page.waitForLoadState();
        return tab1Input.isChecked();
    }

    public boolean isBasicInfoTabActive() {
        page.waitForLoadState();
        return tab2Input.isChecked();
    }

    public boolean isAssessmentTabActive() {
        page.waitForLoadState();
        return tab3Input.isChecked();
    }

    public boolean isNetZeroPlanTabActive() {
        page.waitForLoadState();
        return tab4Input.isChecked();
    }

    public boolean isCarbonOffsetTabActive() {
        page.waitForLoadState();
        return tab5Input.isChecked();
    }

    public boolean isNetZeroMilestoneTabActive() {
        page.waitForLoadState();
        return tab6Input.isChecked();
    }

    public boolean isSummaryOfEmissionsTabActive() {
        page.waitForLoadState();
        return tab7Input.isChecked();
    }

    public boolean isProjectFilesTabActive() {
        page.waitForLoadState();
        return tab8Input.isChecked();
    }

    /**
     * Tab Content Visibility - Check if tab content is visible
     */
    public boolean isOverviewTabContentVisible() {
        page.waitForLoadState();
        return overviewTabContent.isVisible();
    }

    public boolean isBasicInfoTabContentVisible() {
        page.waitForLoadState();
        return basicInfoTabContent.isVisible();
    }

    public boolean isAssessmentTabContentVisible() {
        page.waitForLoadState();
        return assessmentTabContent.isVisible();
    }

    public boolean isNetZeroPlanTabContentVisible() {
        page.waitForLoadState();
        return netZeroPlanTabContent.isVisible();
    }

    public boolean isCarbonOffsetTabContentVisible() {
        page.waitForLoadState();
        return carbonOffsetTabContent.isVisible();
    }

    public boolean isNetZeroMilestoneTabContentVisible() {
        page.waitForLoadState();
        return netZeroMilestoneTabContent.isVisible();
    }

    public boolean isSummaryOfEmissionsTabContentVisible() {
        page.waitForLoadState();
        return summaryOfEmissionsTabContent.isVisible();
    }

    public boolean isProjectFilesTabContentVisible() {
        page.waitForLoadState();
        return projectFilesTabContent.isVisible();
    }

    /**
     * Get currently active tab name
     */
    public String getActiveTabName() {
        page.waitForLoadState();
        if (isOverviewTabActive()) return "Overview";
        if (isBasicInfoTabActive()) return "Basic Info";
        if (isAssessmentTabActive()) return "Assessment";
        if (isNetZeroPlanTabActive()) return "Net Zero Plan";
        if (isCarbonOffsetTabActive()) return "Carbon Offset";
        if (isNetZeroMilestoneTabActive()) return "Net Zero Milestone";
        if (isSummaryOfEmissionsTabActive()) return "Summary of Emissions";
        if (isProjectFilesTabActive()) return "Project Files";
        return "None";
    }
}
