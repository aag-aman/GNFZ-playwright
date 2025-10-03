package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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
        startDate.waitFor();

        // Focus and click to trigger datepicker
        page.waitForTimeout(2000);
        startDate.focus();
        page.waitForTimeout(2000);
        startDate.click();
        page.waitForTimeout(1100);

        // Wait for datepicker to appear
        Locator datepicker = page.locator("#ui-datepicker-div");
        datepicker.waitFor();

        // Parse date (format: MM/DD/YYYY)
        String[] dateParts = date.split("/");
        String monthValue = String.valueOf(Integer.parseInt(dateParts[0]) - 1); // Month is 0-indexed (0=Jan, 11=Dec)
        String year = dateParts[2];
        String day = String.valueOf(Integer.parseInt(dateParts[1])); // Remove leading zero

        // Select month from dropdown
        Locator monthDropdown = page.locator(".ui-datepicker-month");
        monthDropdown.selectOption(monthValue);
        page.waitForTimeout(200);

        // Select year from dropdown
        Locator yearDropdown = page.locator(".ui-datepicker-year");
        yearDropdown.selectOption(year);
        page.waitForTimeout(200);

        // Click on the day in the calendar
        Locator dayButton = page.locator(".ui-datepicker-calendar td a");
        dayButton.filter(new Locator.FilterOptions().setHasText(day)).first().click();

        page.waitForTimeout(300);
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
        projectIdField.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        return projectIdField.isVisible();
    }

    public String getProjectId() {
        page.waitForLoadState();
        return projectIdField.inputValue();
    }
}