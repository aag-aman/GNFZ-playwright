package pages.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * NavbarPage - Common navigation elements across all pages
 */
public class NavbarPage {
    private final Page page;

    // Locators - initialized once and reused
    private final Locator userMenuButton;
    private final Locator logoutButton;
    private final Locator homeButton;
    private final Locator projectsButton;
    private final Locator settingsButton;
    private final Locator userProfileLink;

    public NavbarPage(Page page) {
        this.page = page;

        // Initialize locators once in constructor
        this.userMenuButton = page.locator("[data-testid='user-menu'], .user-menu");
        this.logoutButton = page.locator("text=Logout, a:has-text('Logout')");
        this.homeButton = page.locator("text=Home, a:has-text('Home')");
        this.projectsButton = page.locator("text=Projects, a:has-text('Projects')");
        this.settingsButton = page.locator("text=Settings, a:has-text('Settings')");
        this.userProfileLink = page.locator("[data-testid='user-profile'], .user-profile");
    }

    public void clickUserMenu() {
        userMenuButton.click();
    }

    public void logout() {
        clickUserMenu();
        logoutButton.click();
    }

    public void navigateToHome() {
        homeButton.click();
    }

    public void navigateToProjects() {
        projectsButton.click();
    }

    public void navigateToSettings() {
        settingsButton.click();
    }

    public void clickUserProfile() {
        userProfileLink.click();
    }

    public boolean isUserMenuVisible() {
        return userMenuButton.isVisible();
    }

    public boolean isLoggedIn() {
        page.waitForLoadState();
        return userMenuButton.isVisible() || userProfileLink.isVisible();
    }

    // Getter methods for locators (optional - for advanced usage)
    public Locator getUserMenuButton() {
        return userMenuButton;
    }

    public Locator getLogoutButton() {
        return logoutButton;
    }

    public Locator getHomeButton() {
        return homeButton;
    }

    public Locator getProjectsButton() {
        return projectsButton;
    }
}