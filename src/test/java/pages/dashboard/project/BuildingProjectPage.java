package pages.dashboard.project;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingProjectPage - Page object for Building project creation/editing
 */
public class BuildingProjectPage {
    private final Page page;

    // Page elements - TODO: Add locators
    private final Locator pageHeader;

    public BuildingProjectPage(Page page) {
        this.page = page;

        // Initialize locators - TODO: Update with actual selectors
        this.pageHeader = page.locator("BOILERPLATE_PAGE_HEADER");
    }

    /**
     * Page visibility
     */
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        return pageHeader.isVisible();
    }

    // TODO: Add form field methods
    // public void enterProjectName(String name) {}
    // public void enterLocation(String location) {}
    // public void clickSubmitButton() {}
    // public void clickCancelButton() {}
    // public boolean isErrorMessageDisplayed() {}
}
