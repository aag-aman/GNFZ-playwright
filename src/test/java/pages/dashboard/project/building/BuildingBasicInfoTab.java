package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.InputHelper;

/**
 * BuildingBasicInfoTab - Basic Info tab for Building project
 * Contains basic project information fields
 */
public class BuildingBasicInfoTab {
    private final Page page;

    // Form field locators
    private final Locator projectIdField;
    private final Locator projectTitleField;
    private final Locator targetCertificationArea;
    private final Locator grossArea;
    private final Locator startDate;
    private final Locator saveButton;

    public BuildingBasicInfoTab(Page page) {
        this.page = page;

        // Initialize form field locators
        this.projectIdField = page.locator("#building_spaceId");
        this.projectTitleField = page.locator("#building_spaceTitle");
        this.targetCertificationArea = page.locator("#gnfz-basic-info-form-targetCertArea");
        this.grossArea = page.locator("#gnfz-basic-info-form-grossArea");
        this.startDate = page.locator("#startDate");
        this.saveButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));
    }

    /**
     * Form field visibility
     */
    public boolean isFormDisplayed() {
        page.waitForLoadState();
        projectTitleField.waitFor();
        return projectTitleField.isVisible();
    }

    public void enterProjectTitle(String title) {
        page.waitForLoadState();
        InputHelper.humanizedInputNoEnter(page, projectTitleField, title);
    }

    public String getProjectTitle() {
        page.waitForLoadState();
        return projectTitleField.inputValue();
    }

    public void enterTargetCertificationArea(String area) {
        InputHelper.humanizedInputNoEnter(page, targetCertificationArea, area);
    }

    public String getTargetCertificationArea() {
        page.waitForLoadState();
        return targetCertificationArea.inputValue();
    }

    public void enterGrossArea(String area) {
        InputHelper.humanizedInputNoEnter(page, grossArea, area);
    }

    public String getGrossArea() {
        page.waitForLoadState();
        return grossArea.inputValue();
    }

    public void enterStartDate(String date) {
        InputHelper.selectDateFromDatepicker(page, startDate, date);
    }

    public String getStartDate() {
        page.waitForLoadState();
        return startDate.inputValue();
    }

    public void clickSave() {
        page.waitForLoadState();
        saveButton.click();
    }

    /**
     * Wait for Project ID field to be visible (indicates project has been created)
     */
    public boolean isProjectIdFieldVisible() {
        page.waitForLoadState();
        projectIdField.waitFor(new Locator.WaitForOptions().setTimeout(30000));
        return projectIdField.isVisible();
    }

    public String getProjectId() {
        page.waitForLoadState();
        return projectIdField.inputValue();
    }
}