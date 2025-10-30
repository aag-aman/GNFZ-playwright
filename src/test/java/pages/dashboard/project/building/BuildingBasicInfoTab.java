package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.InputHelper;

import utils.AutoStep;
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
        this.projectIdField = page.locator("#building_spaceId"); // Read-only field
        this.projectTitleField = page.locator("#building_spaceTitle");
        this.targetCertificationArea = page.locator("#gnfz-basic-info-form-targetCertArea");
        this.grossArea = page.locator("#gnfz-basic-info-form-grossArea");
        this.startDate = page.locator("#startDate");
        this.saveButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));
    }

    /**
     * Form field visibility
     */
    @AutoStep
    public boolean isFormDisplayed() {
        page.waitForLoadState();
        projectTitleField.waitFor();
        return projectTitleField.isVisible();
    }

    @AutoStep
    public void enterProjectTitle(String title) {
        page.waitForLoadState();
        InputHelper.humanizedInputNoEnter(page, projectTitleField, title);
    }

    @AutoStep
    public String getProjectTitle() {
        page.waitForLoadState();
        return projectTitleField.inputValue();
    }

    @AutoStep
    public void enterTargetCertificationArea(String area) {
        InputHelper.humanizedInputNoEnter(page, targetCertificationArea, area);
    }

    @AutoStep
    public String getTargetCertificationArea() {
        page.waitForLoadState();
        return targetCertificationArea.inputValue();
    }

    @AutoStep
    public void enterGrossArea(String area) {
        InputHelper.humanizedInputNoEnter(page, grossArea, area);
    }

    @AutoStep
    public String getGrossArea() {
        page.waitForLoadState();
        return grossArea.inputValue();
    }

    @AutoStep
    public void enterStartDate(String date) {
        InputHelper.selectDateFromDatepicker(page, startDate, date);
    }

    @AutoStep
    public String getStartDate() {
        page.waitForLoadState();
        return startDate.inputValue();
    }

    @AutoStep
    public void clickSave() {
        page.waitForLoadState();
        saveButton.click();
    }

    /**
     * Wait for Project ID field to be visible (indicates project has been created)
     */
    @AutoStep
    public boolean isProjectIdFieldVisible() {
        page.waitForLoadState();
        projectIdField.waitFor(new Locator.WaitForOptions().setTimeout(30000));
        return projectIdField.isVisible();
    }

    @AutoStep
    public String getProjectId() {
        page.waitForLoadState();
        return projectIdField.inputValue();
    }
}