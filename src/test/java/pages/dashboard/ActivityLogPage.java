package pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.AutoStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ActivityLogPage - Page object for the Activity Log page
 * Displays chronological list of user activities and project events
 * Supports pagination and activity filtering
 */
public class ActivityLogPage {
    private final Page page;

    // ==================== BREADCRUMB NAVIGATION ====================
    private final Locator breadcrumbNav;
    private final Locator dashboardBreadcrumb;
    private final Locator activityBreadcrumb;

    // ==================== MAIN CONTENT AREA ====================
    private final Locator activityPage;
    private final Locator activityLabel;
    private final Locator activityCard;

    // ==================== ACTIVITY ENTRIES ====================
    private final Locator allActivityEntries;
    private final Locator activityUserNames;
    private final Locator activityDates;
    private final Locator activityIcons;
    private final Locator activityProjectLinks;

    // ==================== PAGINATION ====================
    private final Locator viewMoreButton;

    public ActivityLogPage(Page page) {
        this.page = page;

        // ==================== INITIALIZE BREADCRUMB NAVIGATION ====================
        this.breadcrumbNav = page.locator("nav[aria-label='breadcrumb']");
        this.dashboardBreadcrumb = page.locator("li.breadcrumb-item a:has-text('Dashboard')");
        this.activityBreadcrumb = page.locator("li.breadcrumb-item.active:has-text('Activity')");

        // ==================== INITIALIZE MAIN CONTENT AREA ====================
        this.activityPage = page.locator("#activityPage");
        this.activityLabel = page.locator("h3.activity-label, h3:has-text('Activity log')");
        this.activityCard = page.locator(".activity-card");

        // ==================== INITIALIZE ACTIVITY ENTRIES ====================
        this.allActivityEntries = page.locator(".activity-card > .border-bottom-dashed-light");
        this.activityUserNames = page.locator("a.text-dark-primary");
        this.activityDates = page.locator("small.activity-date");
        this.activityIcons = page.locator("i.bi-chat-dots");
        this.activityProjectLinks = page.locator(".projectLink a");

        // ==================== INITIALIZE PAGINATION ====================
        this.viewMoreButton = page.locator("button:has-text('View more')");
    }

    // ==================== BREADCRUMB NAVIGATION METHODS ====================

    /**
     * Click the Dashboard breadcrumb to navigate back to dashboard
     */
    @AutoStep
    public void clickDashboardBreadcrumb() {
        page.waitForLoadState();
        dashboardBreadcrumb.click();
        page.waitForLoadState();
    }

    /**
     * Check if the breadcrumb navigation is visible
     * @return true if breadcrumb navigation is displayed
     */
    @AutoStep
    public boolean isBreadcrumbVisible() {
        page.waitForLoadState();
        return breadcrumbNav.isVisible();
    }

    /**
     * Get the text of the active breadcrumb (should be "Activity")
     * @return breadcrumb text
     */
    @AutoStep
    public String getActiveBreadcrumbText() {
        page.waitForLoadState();
        return activityBreadcrumb.textContent().trim();
    }

    /**
     * Verify user is on Activity Log page via breadcrumb
     * @return true if Activity breadcrumb is active
     */
    @AutoStep
    public boolean isOnActivityLogPage() {
        page.waitForLoadState();
        return activityBreadcrumb.isVisible() && getActiveBreadcrumbText().contains("Activity");
    }

    // ==================== PAGE VISIBILITY METHODS ====================

    /**
     * Check if the Activity Log page is fully loaded and displayed
     * @return true if page container and header are visible
     */
    @AutoStep
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        return activityPage.isVisible() && activityLabel.isVisible();
    }

    /**
     * Get the page title text
     * @return "Activity log" or other title text
     */
    @AutoStep
    public String getPageTitle() {
        page.waitForLoadState();
        return activityLabel.textContent().trim();
    }

    /**
     * Check if the activity card container is visible
     * @return true if activity card is displayed
     */
    @AutoStep
    public boolean isActivityCardVisible() {
        page.waitForLoadState();
        return activityCard.isVisible();
    }

    /**
     * Wait for activity log page to load completely
     */
    @AutoStep
    public void waitForActivityLogToLoad() {
        page.waitForLoadState();
        activityPage.waitFor();
        activityCard.waitFor();
        page.waitForTimeout(1000);
    }

    // ==================== ACTIVITY COUNT METHODS ====================

    /**
     * Get the total number of activity entries currently displayed
     * @return count of visible activity entries
     */
    @AutoStep
    public int getActivityCount() {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        return allActivityEntries.count();
    }

    /**
     * Check if any activities are displayed
     * @return true if at least one activity entry is visible
     */
    @AutoStep
    public boolean areActivitiesDisplayed() {
        page.waitForLoadState();
        return allActivityEntries.count() > 0;
    }

    /**
     * Get the count text from the "View more" button
     * @return text like "20/5047" showing displayed/total
     */
    @AutoStep
    public String getActivityCountText() {
        page.waitForLoadState();
        if (isViewMoreButtonVisible()) {
            String buttonText = viewMoreButton.textContent().trim();
            // Extract the count part (e.g., "View more 20/5047" -> "20/5047")
            return buttonText.replace("View more", "").trim();
        }
        return "";
    }

    /**
     * Parse and get the number of displayed activities from button text
     * @return number of currently displayed activities (e.g., 20 from "20/5047")
     */
    @AutoStep
    public int getDisplayedActivityCount() {
        String countText = getActivityCountText();
        if (!countText.isEmpty() && countText.contains("/")) {
            return Integer.parseInt(countText.split("/")[0]);
        }
        return getActivityCount();
    }

    /**
     * Parse and get the total number of activities from button text
     * @return total activity count (e.g., 5047 from "20/5047")
     */
    @AutoStep
    public int getTotalActivityCount() {
        String countText = getActivityCountText();
        if (!countText.isEmpty() && countText.contains("/")) {
            return Integer.parseInt(countText.split("/")[1]);
        }
        return 0;
    }

    // ==================== ACTIVITY INTERACTION METHODS ====================

    /**
     * Click on an activity entry by its index (0-based)
     * @param index - index of the activity to click (0 = first)
     */
    @AutoStep
    public void clickActivity(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < allActivityEntries.count()) {
            allActivityEntries.nth(index).click();
        }
    }

    /**
     * Click on an activity's project link by index
     * @param index - index of the activity (0-based)
     */
    @AutoStep
    public void clickActivityProjectLink(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < activityProjectLinks.count()) {
            activityProjectLinks.nth(index).click();
        }
    }

    /**
     * Click on an activity's user name by index
     * @param index - index of the activity (0-based)
     */
    @AutoStep
    public void clickActivityUserName(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < activityUserNames.count()) {
            activityUserNames.nth(index).click();
        }
    }

    /**
     * Click on an activity by project name
     * @param projectName - name of the project to find and click
     */
    @AutoStep
    public void clickActivityByProjectName(String projectName) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        Locator activity = allActivityEntries.locator(":has-text('" + projectName + "')").first();
        if (activity.count() > 0) {
            activity.click();
        }
    }

    // ==================== ACTIVITY DATA EXTRACTION METHODS ====================

    /**
     * Get the user name from an activity by index
     * @param index - index of the activity (0-based)
     * @return user name text (e.g., "Vigneshkumar G")
     */
    @AutoStep
    public String getActivityUserName(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < activityUserNames.count()) {
            return activityUserNames.nth(index).textContent().trim();
        }
        return "";
    }

    /**
     * Get the date/time from an activity by index
     * @param index - index of the activity (0-based)
     * @return date text (e.g., "Oct 29, 2025 03:23 AM")
     */
    @AutoStep
    public String getActivityDate(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < activityDates.count()) {
            return activityDates.nth(index).textContent().trim();
        }
        return "";
    }

    /**
     * Get the project name from an activity by index
     * @param index - index of the activity (0-based)
     * @return project name text (e.g., "Building test")
     */
    @AutoStep
    public String getActivityProjectName(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < activityProjectLinks.count()) {
            return activityProjectLinks.nth(index).textContent().trim();
        }
        return "";
    }

    /**
     * Get the action type from an activity by index
     * @param index - index of the activity (0-based)
     * @return action text (e.g., "Created project", "Inventory of Emissions")
     */
    @AutoStep
    public String getActivityActionType(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < allActivityEntries.count()) {
            // Get the first div containing user name and action
            Locator headerDiv = allActivityEntries.nth(index).locator("div").first();
            String fullText = headerDiv.textContent().trim();

            // Extract action type (between user name and date)
            // Format: "Username Action Date"
            String userName = getActivityUserName(index);
            String date = getActivityDate(index);

            return fullText.replace(userName, "")
                          .replace(date, "")
                          .trim();
        }
        return "";
    }

    /**
     * Get the description/status from an activity by index
     * @param index - index of the activity (0-based)
     * @return description text (e.g., "Building project created", "Assigned for review")
     */
    @AutoStep
    public String getActivityDescription(int index) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (index >= 0 && index < allActivityEntries.count()) {
            // Get the div with class ps-md-2.pe-2 which contains the description span
            // Description is in the last span within this div
            Locator descSpan = allActivityEntries.nth(index).locator("div.ps-md-2.pe-2 span").last();
            if (descSpan.count() > 0) {
                return descSpan.textContent().trim();
            }
        }
        return "";
    }

    /**
     * Get all data for a specific activity as a Map
     * @param index - index of the activity (0-based)
     * @return Map containing userName, actionType, date, projectName, and description
     */
    @AutoStep
    public Map<String, String> getActivityData(int index) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("userName", getActivityUserName(index));
        activityData.put("actionType", getActivityActionType(index));
        activityData.put("date", getActivityDate(index));
        activityData.put("projectName", getActivityProjectName(index));
        activityData.put("description", getActivityDescription(index));
        return activityData;
    }

    /**
     * Get all user names from visible activities
     * @return List of all user names
     */
    @AutoStep
    public List<String> getAllActivityUserNames() {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<String> userNames = new ArrayList<>();
        int count = activityUserNames.count();
        for (int i = 0; i < count; i++) {
            userNames.add(activityUserNames.nth(i).textContent().trim());
        }
        return userNames;
    }

    /**
     * Get all project names from visible activities
     * @return List of all project names
     */
    @AutoStep
    public List<String> getAllActivityProjectNames() {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<String> projectNames = new ArrayList<>();
        int count = activityProjectLinks.count();
        for (int i = 0; i < count; i++) {
            projectNames.add(activityProjectLinks.nth(i).textContent().trim());
        }
        return projectNames;
    }

    /**
     * Get all activity dates from visible activities
     * @return List of all activity dates
     */
    @AutoStep
    public List<String> getAllActivityDates() {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<String> dates = new ArrayList<>();
        int count = activityDates.count();
        for (int i = 0; i < count; i++) {
            dates.add(activityDates.nth(i).textContent().trim());
        }
        return dates;
    }

    /**
     * Get all activities as a List of Maps
     * @return List of activity data Maps, each containing all activity fields
     */
    @AutoStep
    public List<Map<String, String>> getAllActivities() {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<Map<String, String>> activities = new ArrayList<>();
        int count = allActivityEntries.count();
        for (int i = 0; i < count; i++) {
            activities.add(getActivityData(i));
        }
        return activities;
    }

    // ==================== ACTIVITY SEARCH/FILTER METHODS ====================

    /**
     * Check if an activity for a specific project exists
     * @param projectName - project name to search for
     * @return true if activity with this project exists
     */
    @AutoStep
    public boolean hasActivityForProject(String projectName) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        return allActivityEntries.locator(":has-text('" + projectName + "')").count() > 0;
    }

    /**
     * Get the index of an activity by project name
     * @param projectName - project name to find
     * @return index of the activity, or -1 if not found
     */
    @AutoStep
    public int getActivityIndexByProjectName(String projectName) {
        List<String> projectNames = getAllActivityProjectNames();
        for (int i = 0; i < projectNames.size(); i++) {
            if (projectNames.get(i).contains(projectName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get all activities by a specific user
     * @param userName - user name to filter by
     * @return List of indices of activities by this user
     */
    @AutoStep
    public List<Integer> getActivitiesByUser(String userName) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<Integer> indices = new ArrayList<>();
        int count = allActivityEntries.count();
        for (int i = 0; i < count; i++) {
            String activityUser = getActivityUserName(i);
            if (activityUser.contains(userName)) {
                indices.add(i);
            }
        }
        return indices;
    }

    /**
     * Get all activities with a specific action type
     * @param actionType - action type to filter by (e.g., "Created project")
     * @return List of indices of activities with this action type
     */
    @AutoStep
    public List<Integer> getActivitiesByActionType(String actionType) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<Integer> indices = new ArrayList<>();
        int count = allActivityEntries.count();
        for (int i = 0; i < count; i++) {
            String action = getActivityActionType(i);
            if (action.contains(actionType)) {
                indices.add(i);
            }
        }
        return indices;
    }

    /**
     * Get all activities containing specific text in description
     * @param descriptionText - text to search for in descriptions
     * @return List of indices of matching activities
     */
    @AutoStep
    public List<Integer> getActivitiesByDescription(String descriptionText) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<Integer> indices = new ArrayList<>();
        int count = allActivityEntries.count();
        for (int i = 0; i < count; i++) {
            String description = getActivityDescription(i);
            if (description.contains(descriptionText)) {
                indices.add(i);
            }
        }
        return indices;
    }

    /**
     * Get activities from a specific date
     * @param dateText - date text to search for (e.g., "Oct 29, 2025")
     * @return List of indices of activities from this date
     */
    @AutoStep
    public List<Integer> getActivitiesByDate(String dateText) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        List<Integer> indices = new ArrayList<>();
        int count = allActivityEntries.count();
        for (int i = 0; i < count; i++) {
            String date = getActivityDate(i);
            if (date.contains(dateText)) {
                indices.add(i);
            }
        }
        return indices;
    }

    // ==================== PAGINATION METHODS ====================

    /**
     * Click the "View more" button to load additional activities
     */
    @AutoStep
    public void clickViewMore() {
        page.waitForLoadState();
        if (isViewMoreButtonVisible()) {
            viewMoreButton.scrollIntoViewIfNeeded();
            viewMoreButton.click();
            page.waitForTimeout(2000); // Wait for more activities to load
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
     * Check if all activities are loaded (no "View more" button)
     * @return true if all activities are displayed
     */
    @AutoStep
    public boolean areAllActivitiesLoaded() {
        page.waitForLoadState();
        return !isViewMoreButtonVisible();
    }

    /**
     * Load all available activities by repeatedly clicking "View more"
     * @param maxClicks - maximum number of times to click (safety limit)
     */
    @AutoStep
    public void loadAllActivities(int maxClicks) {
        page.waitForLoadState();
        int clicks = 0;
        while (isViewMoreButtonVisible() && clicks < maxClicks) {
            clickViewMore();
            clicks++;
            page.waitForTimeout(1000);
        }
    }

    /**
     * Load activities until a specific count is reached
     * @param targetCount - target number of activities to load
     * @param maxClicks - maximum number of times to click (safety limit)
     * @return true if target count was reached, false if max clicks exceeded
     */
    @AutoStep
    public boolean loadActivitiesUntilCount(int targetCount, int maxClicks) {
        page.waitForLoadState();
        int clicks = 0;
        while (getActivityCount() < targetCount && isViewMoreButtonVisible() && clicks < maxClicks) {
            clickViewMore();
            clicks++;
        }
        return getActivityCount() >= targetCount;
    }

    // ==================== VERIFICATION METHODS ====================

    /**
     * Verify that activities are sorted by date (newest first)
     * Note: This is a basic check, actual date parsing would be more robust
     * @return true if activities appear to be in chronological order
     */
    @AutoStep
    public boolean areActivitiesSortedByDate() {
        List<String> dates = getAllActivityDates();
        return dates.size() > 0;
    }

    /**
     * Check if the activity list is empty
     * @return true if no activities are displayed
     */
    @AutoStep
    public boolean isActivityListEmpty() {
        page.waitForLoadState();
        return allActivityEntries.count() == 0;
    }

    /**
     * Verify that a specific activity exists with all matching criteria
     * @param userName - expected user name
     * @param projectName - expected project name
     * @param actionType - expected action type
     * @return true if matching activity is found
     */
    @AutoStep
    public boolean hasActivityMatching(String userName, String projectName, String actionType) {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        int count = allActivityEntries.count();
        for (int i = 0; i < count; i++) {
            if (getActivityUserName(i).contains(userName) &&
                getActivityProjectName(i).contains(projectName) &&
                getActivityActionType(i).contains(actionType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the most recent activity (first in the list)
     * @return Map containing the most recent activity data
     */
    @AutoStep
    public Map<String, String> getMostRecentActivity() {
        page.waitForLoadState();
        waitForActivityLogToLoad();
        if (getActivityCount() > 0) {
            return getActivityData(0);
        }
        return new HashMap<>();
    }

    /**
     * Check if an activity icon is visible for a specific entry
     * @param index - index of the activity (0-based)
     * @return true if icon is visible
     */
    @AutoStep
    public boolean isActivityIconVisible(int index) {
        page.waitForLoadState();
        if (index >= 0 && index < activityIcons.count()) {
            return activityIcons.nth(index).isVisible();
        }
        return false;
    }

    // ==================== GETTER METHODS (for advanced usage) ====================

    /**
     * Get the activity page container locator
     * @return Locator for the main activity page
     */
    public Locator getActivityPage() {
        return activityPage;
    }

    /**
     * Get the activity card locator
     * @return Locator for the activity card container
     */
    public Locator getActivityCard() {
        return activityCard;
    }

    /**
     * Get all activity entries locator
     * @return Locator for all activity entries
     */
    public Locator getAllActivityEntries() {
        return allActivityEntries;
    }

    /**
     * Get a specific activity entry by index
     * @param index - index of the activity (0-based)
     * @return Locator for the specific activity entry
     */
    public Locator getActivityEntry(int index) {
        return allActivityEntries.nth(index);
    }

    /**
     * Get the view more button locator
     * @return Locator for the view more button
     */
    public Locator getViewMoreButton() {
        return viewMoreButton;
    }
}
