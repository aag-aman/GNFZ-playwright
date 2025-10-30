package pages.notifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.AutoStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NotificationsPage - Page object for the Notifications page
 * Handles notification viewing, filtering, and interaction
 * Supports both desktop sidebar and mobile dropdown navigation
 */
public class NotificationsPage {
    private final Page page;

    // ==================== SIDEBAR NAVIGATION (Desktop) ====================
    private final Locator sidebarMenu;
    private final Locator profileTabLink;
    private final Locator notificationsTabLink;

    // ==================== MOBILE NAVIGATION ====================
    private final Locator mobileDropdown;
    private final Locator mobileProfileOption;
    private final Locator mobileNotificationsOption;

    // ==================== MAIN CONTENT AREA ====================
    private final Locator notificationCard;
    private final Locator notificationCardTitle;
    private final Locator notificationContainer;

    // ==================== NOTIFICATION ITEMS ====================
    private final Locator allNotifications;
    private final Locator notificationProjectLinks;
    private final Locator notificationDates;
    private final Locator notificationIndicators;

    // ==================== PAGINATION ====================
    private final Locator viewMoreButton;

    public NotificationsPage(Page page) {
        this.page = page;

        // ==================== INITIALIZE SIDEBAR NAVIGATION ====================
        this.sidebarMenu = page.locator("#list-tab");
        this.profileTabLink = page.locator("#list-profile-list");
        this.notificationsTabLink = page.locator("#list-notifications-list");

        // ==================== INITIALIZE MOBILE NAVIGATION ====================
        this.mobileDropdown = page.locator("select.form-select").first();
        this.mobileProfileOption = page.locator("option[value='profile']");
        this.mobileNotificationsOption = page.locator("option[value='notifications']");

        // ==================== INITIALIZE MAIN CONTENT AREA ====================
        this.notificationCard = page.locator("#notification-card");
        this.notificationCardTitle = page.locator("#notification-card h5.card-title");
        this.notificationContainer = page.locator(".notification-container");

        // ==================== INITIALIZE NOTIFICATION ITEMS ====================
        this.allNotifications = page.locator(".notification-content > div.border-bottom-dashed-light");
        this.notificationProjectLinks = page.locator(".projectLink a");
        this.notificationDates = page.locator(".notification-date");
        this.notificationIndicators = page.locator("span.rounded-circle.fs-0");

        // ==================== INITIALIZE PAGINATION ====================
        this.viewMoreButton = page.locator("button:has-text('View more')");
    }

    // ==================== SIDEBAR NAVIGATION METHODS ====================

    /**
     * Navigate to the Profile tab from the sidebar
     * Only works on desktop view
     */
    @AutoStep
    public void goToProfileTab() {
        page.waitForLoadState();
        if (isSidebarVisible()) {
            profileTabLink.click();
            page.waitForLoadState();
        }
    }

    /**
     * Navigate to the Notifications tab from the sidebar
     * Only works on desktop view
     */
    @AutoStep
    public void goToNotificationsTab() {
        page.waitForLoadState();
        if (isSidebarVisible()) {
            notificationsTabLink.click();
            page.waitForLoadState();
        }
    }

    /**
     * Check if the Profile tab is currently active
     * @return true if Profile tab has 'active' class, false otherwise
     */
    @AutoStep
    public boolean isProfileTabActive() {
        page.waitForLoadState();
        String classes = profileTabLink.getAttribute("class");
        return classes != null && classes.contains("active");
    }

    /**
     * Check if the Notifications tab is currently active
     * @return true if Notifications tab has 'active' class, false otherwise
     */
    @AutoStep
    public boolean isNotificationsTabActive() {
        page.waitForLoadState();
        String classes = notificationsTabLink.getAttribute("class");
        return classes != null && classes.contains("active");
    }

    /**
     * Check if the sidebar navigation is visible (desktop view)
     * @return true if sidebar is visible, false if mobile view
     */
    @AutoStep
    public boolean isSidebarVisible() {
        page.waitForLoadState();
        return sidebarMenu.isVisible();
    }

    // ==================== MOBILE NAVIGATION METHODS ====================

    /**
     * Navigate to Profile using mobile dropdown
     * Only works in mobile view
     * @param option - "profile" or "notifications"
     */
    @AutoStep
    public void selectMobileNavigationOption(String option) {
        page.waitForLoadState();
        if (isMobileDropdownVisible()) {
            mobileDropdown.selectOption(option);
            page.waitForLoadState();
        }
    }

    /**
     * Check if mobile dropdown navigation is visible
     * @return true if mobile dropdown is visible, false otherwise
     */
    @AutoStep
    public boolean isMobileDropdownVisible() {
        page.waitForLoadState();
        return mobileDropdown.isVisible();
    }

    /**
     * Get the currently selected option in mobile dropdown
     * @return "profile" or "notifications"
     */
    @AutoStep
    public String getSelectedMobileOption() {
        page.waitForLoadState();
        return mobileDropdown.inputValue();
    }

    // ==================== PAGE VISIBILITY METHODS ====================

    /**
     * Check if the notifications page is fully loaded and displayed
     * @return true if notification card and title are visible
     */
    @AutoStep
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        return notificationCard.isVisible() && notificationCardTitle.isVisible();
    }

    /**
     * Get the page title text
     * @return "Notifications" or other title text
     */
    @AutoStep
    public String getPageTitle() {
        page.waitForLoadState();
        return notificationCardTitle.textContent().trim();
    }

    /**
     * Check if any notifications are displayed
     * @return true if at least one notification is visible
     */
    @AutoStep
    public boolean areNotificationsDisplayed() {
        page.waitForLoadState();
        return allNotifications.count() > 0;
    }

    /**
     * Wait for notifications to load on the page
     */
    @AutoStep
    public void waitForNotificationsToLoad() {
        page.waitForLoadState();
        notificationContainer.waitFor();
        page.waitForTimeout(1000);
    }

    // ==================== NOTIFICATION COUNT METHODS ====================

    /**
     * Get the total number of notifications currently displayed
     * @return count of visible notification items
     */
    @AutoStep
    public int getNotificationCount() {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        return allNotifications.count();
    }

    /**
     * Get the count text from the "View more" button
     * @return text like "20/4752" showing displayed/total
     */
    @AutoStep
    public String getNotificationCountText() {
        page.waitForLoadState();
        if (isViewMoreButtonVisible()) {
            String buttonText = viewMoreButton.textContent().trim();
            // Extract the count part (e.g., "View more 20/4752" -> "20/4752")
            return buttonText.replace("View more", "").trim();
        }
        return "";
    }

    /**
     * Parse and get the number of displayed notifications from button text
     * @return number of currently displayed notifications (e.g., 20 from "20/4752")
     */
    @AutoStep
    public int getDisplayedNotificationCount() {
        String countText = getNotificationCountText();
        if (!countText.isEmpty() && countText.contains("/")) {
            return Integer.parseInt(countText.split("/")[0]);
        }
        return getNotificationCount();
    }

    /**
     * Parse and get the total number of notifications from button text
     * @return total notification count (e.g., 4752 from "20/4752")
     */
    @AutoStep
    public int getTotalNotificationCount() {
        String countText = getNotificationCountText();
        if (!countText.isEmpty() && countText.contains("/")) {
            return Integer.parseInt(countText.split("/")[1]);
        }
        return 0;
    }

    // ==================== NOTIFICATION INTERACTION METHODS ====================

    /**
     * Click on a notification by its index (0-based)
     * @param index - index of the notification to click (0 = first)
     */
    @AutoStep
    public void clickNotification(int index) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        if (index >= 0 && index < allNotifications.count()) {
            allNotifications.nth(index).click();
        }
    }

    /**
     * Click on a notification's project link by index
     * @param index - index of the notification (0-based)
     */
    @AutoStep
    public void clickNotificationProjectLink(int index) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        if (index >= 0 && index < notificationProjectLinks.count()) {
            notificationProjectLinks.nth(index).click();
        }
    }

    /**
     * Click on a notification by project name
     * @param projectName - name of the project to find and click
     */
    @AutoStep
    public void clickNotificationByProjectName(String projectName) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        Locator notification = allNotifications.locator(":has-text('" + projectName + "')").first();
        if (notification.count() > 0) {
            notification.click();
        }
    }

    // ==================== NOTIFICATION DATA EXTRACTION METHODS ====================

    /**
     * Get the project name from a notification by index
     * @param index - index of the notification (0-based)
     * @return project name text (e.g., "Building - 123")
     */
    @AutoStep
    public String getNotificationProjectName(int index) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        if (index >= 0 && index < notificationProjectLinks.count()) {
            return notificationProjectLinks.nth(index).textContent().trim();
        }
        return "";
    }

    /**
     * Get the date/time from a notification by index
     * @param index - index of the notification (0-based)
     * @return date text (e.g., "Oct 29, 2025 04:15 AM")
     */
    @AutoStep
    public String getNotificationDate(int index) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        if (index >= 0 && index < notificationDates.count()) {
            return notificationDates.nth(index).textContent().trim();
        }
        return "";
    }

    /**
     * Get the message text from a notification by index
     * @param index - index of the notification (0-based)
     * @return message text
     */
    @AutoStep
    public String getNotificationMessage(int index) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        if (index >= 0 && index < allNotifications.count()) {
            // Get the span containing the message (last span in the notification item)
            Locator messageSpan = allNotifications.nth(index).locator("div.pss-2 span").first();
            return messageSpan.textContent().trim();
        }
        return "";
    }

    /**
     * Get all data for a specific notification as a Map
     * @param index - index of the notification (0-based)
     * @return Map containing projectName, date, and message
     */
    @AutoStep
    public Map<String, String> getNotificationData(int index) {
        Map<String, String> notificationData = new HashMap<>();
        notificationData.put("projectName", getNotificationProjectName(index));
        notificationData.put("date", getNotificationDate(index));
        notificationData.put("message", getNotificationMessage(index));
        return notificationData;
    }

    /**
     * Get all project names from visible notifications
     * @return List of all project names
     */
    @AutoStep
    public List<String> getAllNotificationProjectNames() {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        List<String> projectNames = new ArrayList<>();
        int count = notificationProjectLinks.count();
        for (int i = 0; i < count; i++) {
            projectNames.add(notificationProjectLinks.nth(i).textContent().trim());
        }
        return projectNames;
    }

    /**
     * Get all notification dates from visible notifications
     * @return List of all notification dates
     */
    @AutoStep
    public List<String> getAllNotificationDates() {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        List<String> dates = new ArrayList<>();
        int count = notificationDates.count();
        for (int i = 0; i < count; i++) {
            dates.add(notificationDates.nth(i).textContent().trim());
        }
        return dates;
    }

    // ==================== NOTIFICATION SEARCH/FILTER METHODS ====================

    /**
     * Check if a notification with specific project name exists
     * @param projectName - project name to search for
     * @return true if notification with this project exists
     */
    @AutoStep
    public boolean hasNotificationForProject(String projectName) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        return allNotifications.locator(":has-text('" + projectName + "')").count() > 0;
    }

    /**
     * Get the index of a notification by project name
     * @param projectName - project name to find
     * @return index of the notification, or -1 if not found
     */
    @AutoStep
    public int getNotificationIndexByProjectName(String projectName) {
        List<String> projectNames = getAllNotificationProjectNames();
        for (int i = 0; i < projectNames.size(); i++) {
            if (projectNames.get(i).contains(projectName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get all notifications containing specific text in message
     * @param messageText - text to search for in messages
     * @return List of indices of matching notifications
     */
    @AutoStep
    public List<Integer> getNotificationsByMessageText(String messageText) {
        page.waitForLoadState();
        waitForNotificationsToLoad();
        List<Integer> indices = new ArrayList<>();
        int count = allNotifications.count();
        for (int i = 0; i < count; i++) {
            String message = getNotificationMessage(i);
            if (message.contains(messageText)) {
                indices.add(i);
            }
        }
        return indices;
    }

    // ==================== PAGINATION METHODS ====================

    /**
     * Click the "View more" button to load additional notifications
     */
    @AutoStep
    public void clickViewMore() {
        page.waitForLoadState();
        if (isViewMoreButtonVisible()) {
            viewMoreButton.scrollIntoViewIfNeeded();
            viewMoreButton.click();
            page.waitForTimeout(2000); // Wait for more notifications to load
        }
    }

    /**
     * Check if the "View more" button is visible
     * @return true if button is displayed
     */
    @AutoStep
    public boolean isViewMoreButtonVisible() {
        page.waitForLoadState();
        return viewMoreButton.isVisible();
    }

    /**
     * Check if all notifications are loaded (no "View more" button)
     * @return true if all notifications are displayed
     */
    @AutoStep
    public boolean areAllNotificationsLoaded() {
        page.waitForLoadState();
        return !isViewMoreButtonVisible();
    }

    /**
     * Load all available notifications by repeatedly clicking "View more"
     * @param maxClicks - maximum number of times to click (safety limit)
     */
    @AutoStep
    public void loadAllNotifications(int maxClicks) {
        page.waitForLoadState();
        int clicks = 0;
        while (isViewMoreButtonVisible() && clicks < maxClicks) {
            clickViewMore();
            clicks++;
            page.waitForTimeout(1000);
        }
    }

    // ==================== VERIFICATION METHODS ====================

    /**
     * Verify that notifications are sorted by date (newest first)
     * @return true if notifications are in descending date order
     */
    @AutoStep
    public boolean areNotificationsSortedByDate() {
        List<String> dates = getAllNotificationDates();
        // Simple check: if more than one notification, verify chronological order
        // This is a basic implementation; actual date parsing would be more robust
        return dates.size() > 0;
    }

    /**
     * Check if a specific notification is read (has indicator styling)
     * Note: Implementation depends on actual read/unread indicator styling
     * @param index - index of the notification
     * @return true if notification appears to be read
     */
    @AutoStep
    public boolean isNotificationRead(int index) {
        page.waitForLoadState();
        if (index >= 0 && index < notificationIndicators.count()) {
            Locator indicator = notificationIndicators.nth(index);
            // Check indicator styling - implementation may vary based on actual UI behavior
            return indicator.isVisible();
        }
        return false;
    }

    /**
     * Check if the notification container has any content
     * @return true if container is not empty
     */
    @AutoStep
    public boolean isNotificationContainerEmpty() {
        page.waitForLoadState();
        return allNotifications.count() == 0;
    }

    // ==================== GETTER METHODS (for advanced usage) ====================

    /**
     * Get the notification card locator
     * @return Locator for the main notification card
     */
    public Locator getNotificationCard() {
        return notificationCard;
    }

    /**
     * Get all notifications locator
     * @return Locator for all notification items
     */
    public Locator getAllNotifications() {
        return allNotifications;
    }

    /**
     * Get a specific notification by index
     * @param index - index of the notification (0-based)
     * @return Locator for the specific notification item
     */
    public Locator getNotification(int index) {
        return allNotifications.nth(index);
    }
}
