package pages.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.AutoStep;

/**
 * NavbarPage - Common navigation elements across all pages
 * Supports both desktop and mobile navigation
 */
public class NavbarPage {
    private final Page page;

    // ==================== BRANDING & LOGO ====================
    private final Locator gnfzLogo;
    private final Locator navbarTitle;

    // ==================== MAIN NAVIGATION LINKS ====================
    private final Locator projectLink;
    private final Locator reviewLink;
    private final Locator resourcesLink;
    private final Locator educationLink;
    private final Locator helpLink;

    // ==================== USER PROFILE & DROPDOWN ====================
    private final Locator userProfileDropdown;
    private final Locator userInitialsDisplay;
    private final Locator bellIcon;
    private final Locator userDropdownMenu;

    // ==================== USER DROPDOWN MENU ITEMS ====================
    private final Locator notificationsMenuItem;
    private final Locator profileMenuItem;
    private final Locator administrationMenuItem;
    private final Locator signOutMenuItem;

    // ==================== MOBILE NAVIGATION ====================
    private final Locator mobileMenuButton;
    private final Locator mobileOffcanvasMenu;
    private final Locator mobileCloseButton;

    public NavbarPage(Page page) {
        this.page = page;

        // ==================== INITIALIZE BRANDING & LOGO ====================
        this.gnfzLogo = page.locator("a[href='https://www.globalnetworkforzero.com/'] img");
        this.navbarTitle = page.locator(".navbar-net-zero-title");

        // ==================== INITIALIZE MAIN NAVIGATION LINKS ====================
        this.projectLink = page.locator("a[href='/project/list']");
        this.reviewLink = page.locator("a[href='/project/review']");
        this.resourcesLink = page.locator("a[href='/resources']");
        this.educationLink = page.locator("a[href='/education']");
        this.helpLink = page.locator("a:has-text('Help')").first();

        // ==================== INITIALIZE USER PROFILE & DROPDOWN ====================
        this.userProfileDropdown = page.locator("#navbarDarkDropdownMenuLink").first();
        this.userInitialsDisplay = page.locator("#navbarDarkDropdownMenuLink span").first();
        this.bellIcon = page.locator("#bell-icon");
        this.userDropdownMenu = page.locator("ul.dropdown-menu.nav-user-menu").first();

        // ==================== INITIALIZE USER DROPDOWN MENU ITEMS ====================
        this.notificationsMenuItem = page.locator("a.dropdown-item[href='/notifications'], a.dropdown-item:has-text('Notifications')").first();
        this.profileMenuItem = page.locator("a.dropdown-item[href='/profile'], a.dropdown-item:has-text('Profile')").first();
        this.administrationMenuItem = page.locator("a.dropdown-item:has-text('Administration')").first();
        this.signOutMenuItem = page.locator("a.dropdown-item:has-text('Sign out')").first();

        // ==================== INITIALIZE MOBILE NAVIGATION ====================
        this.mobileMenuButton = page.locator("button.header-burger-btn");
        this.mobileOffcanvasMenu = page.locator("#offcanvasTop");
        this.mobileCloseButton = page.locator("#side-nav-close");
    }

    // ==================== BRANDING & LOGO METHODS ====================

    @AutoStep
    public void clickLogo() {
        page.waitForLoadState();
        gnfzLogo.first().click();
    }

    @AutoStep
    public boolean isLogoVisible() {
        page.waitForLoadState();
        return gnfzLogo.first().isVisible();
    }

    @AutoStep
    public String getNavbarTitle() {
        page.waitForLoadState();
        return navbarTitle.textContent().trim();
    }

    @AutoStep
    public boolean isNavbarTitleVisible() {
        page.waitForLoadState();
        return navbarTitle.isVisible();
    }

    // ==================== MAIN NAVIGATION LINK METHODS ====================

    @AutoStep
    public void navigateToProjects() {
        page.waitForLoadState();
        projectLink.click();
    }

    @AutoStep
    public void navigateToReview() {
        page.waitForLoadState();
        reviewLink.click();
    }

    @AutoStep
    public void navigateToResources() {
        page.waitForLoadState();
        resourcesLink.click();
    }

    @AutoStep
    public void navigateToEducation() {
        page.waitForLoadState();
        educationLink.click();
    }

    @AutoStep
    public void clickHelp() {
        page.waitForLoadState();
        helpLink.click();
    }

    @AutoStep
    public boolean isProjectLinkVisible() {
        page.waitForLoadState();
        return projectLink.isVisible();
    }

    @AutoStep
    public boolean isReviewLinkVisible() {
        page.waitForLoadState();
        return reviewLink.isVisible();
    }

    @AutoStep
    public boolean isResourcesLinkVisible() {
        page.waitForLoadState();
        return resourcesLink.isVisible();
    }

    @AutoStep
    public boolean isEducationLinkVisible() {
        page.waitForLoadState();
        return educationLink.isVisible();
    }

    @AutoStep
    public boolean isHelpLinkVisible() {
        page.waitForLoadState();
        return helpLink.isVisible();
    }

    @AutoStep
    public boolean isProjectLinkActive() {
        page.waitForLoadState();
        String classes = projectLink.getAttribute("class");
        return classes != null && classes.contains("text-underline");
    }

    // ==================== USER PROFILE & DROPDOWN METHODS ====================

    @AutoStep
    public void openUserDropdown() {
        page.waitForLoadState();
        userProfileDropdown.click();
        page.waitForTimeout(500); // Wait for dropdown animation
    }

    @AutoStep
    public void closeUserDropdown() {
        page.waitForLoadState();
        // Click outside the dropdown to close it
        page.locator("body").click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public boolean isUserDropdownVisible() {
        page.waitForLoadState();
        return userProfileDropdown.isVisible();
    }

    @AutoStep
    public String getUserInitials() {
        page.waitForLoadState();
        return userInitialsDisplay.textContent().trim();
    }

    @AutoStep
    public boolean isBellIconVisible() {
        page.waitForLoadState();
        // Check if bell icon is visible (not hidden)
        return bellIcon.isVisible() && !bellIcon.getAttribute("hidden").equals("");
    }

    @AutoStep
    public boolean isUserDropdownMenuOpen() {
        page.waitForLoadState();
        return userDropdownMenu.isVisible();
    }

    // ==================== USER DROPDOWN MENU ITEM METHODS ====================

    @AutoStep
    public void goToNotifications() {
        openUserDropdown();
        page.waitForLoadState();
        notificationsMenuItem.click();
    }

    @AutoStep
    public void goToProfile() {
        openUserDropdown();
        page.waitForLoadState();
        profileMenuItem.click();
    }

    @AutoStep
    public void goToAdministration() {
        openUserDropdown();
        page.waitForLoadState();
        administrationMenuItem.click();
    }

    @AutoStep
    public void logout() {
        openUserDropdown();
        page.waitForLoadState();
        signOutMenuItem.click();
        page.waitForLoadState();
    }

    @AutoStep
    public boolean isNotificationsMenuItemVisible() {
        openUserDropdown();
        page.waitForLoadState();
        boolean visible = notificationsMenuItem.isVisible();
        closeUserDropdown();
        return visible;
    }

    @AutoStep
    public boolean isProfileMenuItemVisible() {
        openUserDropdown();
        page.waitForLoadState();
        boolean visible = profileMenuItem.isVisible();
        closeUserDropdown();
        return visible;
    }

    @AutoStep
    public boolean isAdministrationMenuItemVisible() {
        openUserDropdown();
        page.waitForLoadState();
        boolean visible = administrationMenuItem.isVisible();
        closeUserDropdown();
        return visible;
    }

    @AutoStep
    public boolean isSignOutMenuItemVisible() {
        openUserDropdown();
        page.waitForLoadState();
        boolean visible = signOutMenuItem.isVisible();
        closeUserDropdown();
        return visible;
    }

    // ==================== MOBILE NAVIGATION METHODS ====================

    @AutoStep
    public void openMobileMenu() {
        page.waitForLoadState();
        if (isMobileMenuButtonVisible()) {
            mobileMenuButton.click();
            page.waitForTimeout(500); // Wait for offcanvas animation
        }
    }

    @AutoStep
    public void closeMobileMenu() {
        page.waitForLoadState();
        if (isMobileMenuOpen()) {
            mobileCloseButton.click();
            page.waitForTimeout(500); // Wait for offcanvas animation
        }
    }

    @AutoStep
    public boolean isMobileMenuButtonVisible() {
        page.waitForLoadState();
        return mobileMenuButton.isVisible();
    }

    @AutoStep
    public boolean isMobileMenuOpen() {
        page.waitForLoadState();
        return mobileOffcanvasMenu.isVisible();
    }

    // ==================== GENERAL VERIFICATION METHODS ====================

    @AutoStep
    public boolean isLoggedIn() {
        page.waitForLoadState();
        return userProfileDropdown.isVisible();
    }

    @AutoStep
    public boolean isNavbarVisible() {
        page.waitForLoadState();
        return page.locator("#gnfz-nav-header").isVisible();
    }

    @AutoStep
    public boolean areNavigationLinksVisible() {
        page.waitForLoadState();
        return projectLink.isVisible() &&
               reviewLink.isVisible() &&
               resourcesLink.isVisible() &&
               educationLink.isVisible();
    }

    @AutoStep
    public void waitForNavbarToLoad() {
        page.waitForLoadState();
        page.locator("#gnfz-nav-header").waitFor();
        page.waitForTimeout(500);
    }

    // ==================== GETTER METHODS (for advanced usage) ====================

    public Locator getUserProfileDropdown() {
        return userProfileDropdown;
    }

    public Locator getProjectLink() {
        return projectLink;
    }

    public Locator getReviewLink() {
        return reviewLink;
    }

    public Locator getResourcesLink() {
        return resourcesLink;
    }

    public Locator getEducationLink() {
        return educationLink;
    }
}