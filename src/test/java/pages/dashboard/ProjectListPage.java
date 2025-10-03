package pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProjectListPage {
    private final Page page;

    // Locators - initialized once and reused
    private final Locator projectListHeader;
    private final Locator projectListText;
    private final Locator projectCards;
    private final Locator createNewProjectButton;

    public ProjectListPage(Page page) {
        this.page = page;

        // Initialize locators once in constructor
        this.projectListHeader = page.locator("b:has-text('List of projects')");
        this.projectListText = page.locator("b");
        this.projectCards = page.locator("[data-testid='project-card'], .project-item");
        this.createNewProjectButton = page.locator("#gnfz-create-project");
    }

    public boolean isProjectListVisible() {
        page.waitForLoadState();
        return projectListHeader.isVisible();
    }

    public String getProjectListText() {
        page.waitForLoadState();
        return projectListText.first().textContent();
    }

    public boolean isLoginSuccessful() {
        return isProjectListVisible();
    }

    public int getProjectCount() {
        page.waitForLoadState();
        return projectCards.count();
    }

    // Getter methods for locators (optional - for advanced usage)
    public Locator getProjectListHeader() {
        return projectListHeader;
    }

    public Locator getProjectCards() {
        return projectCards;
    }

    // Navigate to project selection
    public void clickCreateNewProject() {
        page.waitForLoadState();
        createNewProjectButton.click();
    }

    public boolean isCreateNewProjectButtonVisible() {
        return createNewProjectButton.isVisible();
    }

    public boolean isCreateNewProjectButtonEnabled() {
        return createNewProjectButton.isEnabled();
    }
}