package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingOverviewTab - Overview tab for Building project
 */
public class BuildingOverviewTab {
    private final Page page;

    // Tab content locators - TODO: Add actual form fields
    private final Locator overviewTab;
    private final Locator overviewHeader;

    private final Locator buildingInfoSelect;
    private final Locator teamInfoSelect;

    // Other locators (placeholders for future use)
    @SuppressWarnings("unused")
    private final Locator assessmentSelect;
    @SuppressWarnings("unused")
    private final Locator netZeroPlanSelect;
    @SuppressWarnings("unused")
    private final Locator carbonOffsetSelect;
    @SuppressWarnings("unused")
    private final Locator netZeroMilestoneSelect;
    @SuppressWarnings("unused")
    private final Locator netZeroCertificationStatus;
    @SuppressWarnings("unused")
    private final Locator demoLink;

    public BuildingOverviewTab(Page page) {
        this.page = page;

        this.overviewTab = page.locator("#gnfz-overview");
        this.overviewHeader = page.locator("//*[@id=\"processStatus\"]/div/div[1]/div/div/b");
        this.buildingInfoSelect = page.locator("#gnfz-building-info");
        this.teamInfoSelect = page.locator("#gnfz-team-info");
        this.assessmentSelect = page.locator("#gnfz-complete-assessment");
        this.netZeroPlanSelect = page.locator("#gnfz-nzp");
        this.carbonOffsetSelect = page
                .locator("//*[@id=\"processStatus\"]/div/div[2]/div/table/tbody/tr[7]/td[3]/div/input");
        this.netZeroMilestoneSelect = page
                .locator("//*[@id=\"processStatus\"]/div/div[2]/div/table/tbody/tr[8]/td[3]/div/input");
        this.netZeroCertificationStatus = page
                .locator("//*[@id=\"processStatus\"]/div/div[2]/div/table/tbody/tr[9]/td[3]");
        this.demoLink = page.locator("//*[@id=\"processStatus\"]/div/div[2]/div/table/tbody/tr[10]/td[2]/a");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return overviewHeader.isVisible();
    }

    // TODO: Add overview-specific methods
    public boolean navigateToOverviewTab() {
        page.waitForLoadState();
        overviewTab.click();
        return overviewHeader.isVisible();
    }

    public void clickBuildingInfo() {
        page.waitForLoadState();
        buildingInfoSelect.click();
    }

    public void clickTeamInfo() {
        page.waitForLoadState();
        teamInfoSelect.click();
    }
}
