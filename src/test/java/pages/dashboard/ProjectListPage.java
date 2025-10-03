package pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProjectListPage {
    private final Page page;

    // Locators - initialized once and reused
    private final Locator projectListHeader;
    private final Locator createNewProjectButton;

    public ProjectListPage(Page page) {
        this.page = page;

        // Initialize locators once in constructor
        this.projectListHeader = page.locator("b:has-text('List of projects')");
        this.createNewProjectButton = page.locator("#gnfz-create-project");
    }

    public boolean isProjectListVisible() {
        page.waitForLoadState();
        projectListHeader.waitFor();
        return projectListHeader.isVisible();
    }

    public boolean isLoginSuccessful() {
        return isProjectListVisible();
    }

    // Getter methods for locators (optional - for advanced usage)
    public Locator getProjectListHeader() {
        return projectListHeader;
    }

    // Navigate to project selection
    public void clickCreateNewProject() {
        page.waitForLoadState();
        createNewProjectButton.click();
    }

    public boolean isCreateNewProjectButtonVisible() {
        page.waitForLoadState();
        createNewProjectButton.waitFor();
        return createNewProjectButton.isVisible();
    }

    public boolean isCreateNewProjectButtonEnabled() {
        page.waitForLoadState();
        createNewProjectButton.waitFor();
        return createNewProjectButton.isEnabled();
    }
}