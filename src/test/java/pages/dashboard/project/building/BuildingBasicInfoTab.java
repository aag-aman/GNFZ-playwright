package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingBasicInfoTab - Basic Info tab for Building project
 * Contains basic project information fields
 */
public class BuildingBasicInfoTab {
    private final Page page;

    // Form field locators - TODO: Add actual fields
    private final Locator basicInfoTab;
    private final Locator buildingInfoTab;
    private final Locator projectTitleField;
    private final Locator targetCertificationArea;
    private final Locator grossArea;
    private final Locator startDate;
    private final Locator saveButton;

    public BuildingBasicInfoTab(Page page) {
        this.page = page;

        // Initialize locators - TODO: Update with actual selectors
        this.basicInfoTab = page.locator("#gnfz-basicInfo");
        this.buildingInfoTab = page.locator("#buildingInfo");
        this.projectTitleField = page.locator("#building_spaceTitle");
        this.targetCertificationArea = page.locator("#gnfz-basic-info-form-targetCertArea");
        this.grossArea = page.locator("#grossArea");
        this.startDate = page.locator("#startDate");
        this.saveButton = page.locator("#gnfz-save");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return buildingInfoTab.isVisible();
    }

    public void enterProjectTitle(String title) {
        page.waitForLoadState();
        projectTitleField.pressSequentially(title);
    }

    public String getProjectTitle() {
        page.waitForLoadState();
        return projectTitleField.inputValue();
    }

    public void enterTargetCertificationArea(String area) {
        page.waitForLoadState();
        targetCertificationArea.fill(area);
    }

    public String getTargetCertificationArea() {
        page.waitForLoadState();
        return targetCertificationArea.inputValue();
    }

    public void enterGrossArea(String area) {
        page.waitForLoadState();
        grossArea.fill(area);
    }

    public String getGrossArea() {
        page.waitForLoadState();
        return grossArea.inputValue();
    }

    public void enterStartDate(String date) {
        page.waitForLoadState();
        startDate.fill(date);
    }

    public String getStartDate() {
        page.waitForLoadState();
        return startDate.inputValue();
    }

    public void clickSave() {
        page.waitForLoadState();
        saveButton.click();
    }
}
