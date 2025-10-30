package pages.profile;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.AutoStep;
import utils.InputHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProfilePage - Page object for the Profile page
 * Handles profile information viewing and editing
 * Supports both desktop sidebar and mobile dropdown navigation
 * Shares sidebar navigation with NotificationsPage
 */
public class ProfilePage {
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
    private final Locator profileCard;
    private final Locator profileCardTitle;

    // ==================== FORM FIELDS ====================
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator emailDisplay;
    private final Locator passwordDisplay;
    private final Locator changePasswordButton;
    private final Locator supportTagsInput;
    private final Locator linkedinInput;
    private final Locator shareTextarea;

    // ==================== ACTION BUTTONS ====================
    private final Locator saveButton;

    // ==================== LABELS (for verification) ====================
    private final Locator firstNameLabel;
    private final Locator lastNameLabel;
    private final Locator emailLabel;
    private final Locator passwordLabel;

    // ==================== TAG ELEMENTS ====================
    private final Locator tagifyContainer;
    private final Locator allTags;
    private final Locator tagRemoveButtons;

    public ProfilePage(Page page) {
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
        this.profileCard = page.locator("#profile-card");
        this.profileCardTitle = page.locator("#profile-card h5.card-title");

        // ==================== INITIALIZE FORM FIELDS ====================
        this.firstNameInput = page.locator("#first-name");
        this.lastNameInput = page.locator("#last-name");
        this.emailDisplay = page.locator("#email-id");
        this.passwordDisplay = page.locator("#password");
        this.changePasswordButton = page.locator(".changeBtn, button:has-text('Change password')").first();
        this.supportTagsInput = page.locator("#supportTags");
        this.linkedinInput = page.locator("#linkedin");
        this.shareTextarea = page.locator("#share-text");

        // ==================== INITIALIZE ACTION BUTTONS ====================
        this.saveButton = page.locator(".savebtn, button.native-button.label-text.mt-4.mb-4.savebtn").first();

        // ==================== INITIALIZE LABELS ====================
        this.firstNameLabel = page.locator("#firstName-label");
        this.lastNameLabel = page.locator("#lastName-label");
        this.emailLabel = page.locator("#email-label");
        this.passwordLabel = page.locator("#password-label");

        // ==================== INITIALIZE TAG ELEMENTS ====================
        this.tagifyContainer = page.locator("tags.tagify");
        this.allTags = page.locator("tag.tagify__tag");
        this.tagRemoveButtons = page.locator("x.tagify__tag__removeBtn");
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
     * Navigate to Profile or Notifications using mobile dropdown
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
     * Check if the profile page is fully loaded and displayed
     * @return true if profile card and title are visible
     */
    @AutoStep
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        return profileCard.isVisible() && profileCardTitle.isVisible();
    }

    /**
     * Get the page title text
     * @return "Profile information" or other title text
     */
    @AutoStep
    public String getPageTitle() {
        page.waitForLoadState();
        return profileCardTitle.textContent().trim();
    }

    /**
     * Wait for profile page to load completely
     */
    @AutoStep
    public void waitForProfileToLoad() {
        page.waitForLoadState();
        profileCard.waitFor();
        page.waitForTimeout(1000);
    }

    // ==================== FORM FIELD INTERACTION METHODS ====================

    /**
     * Enter first name in the first name field
     * @param firstName - first name to enter
     */
    @AutoStep
    public void enterFirstName(String firstName) {
        page.waitForLoadState();
        firstNameInput.clear();
        InputHelper.humanizedInput(page, firstNameInput, firstName);
    }

    /**
     * Enter last name in the last name field
     * @param lastName - last name to enter
     */
    @AutoStep
    public void enterLastName(String lastName) {
        page.waitForLoadState();
        lastNameInput.clear();
        InputHelper.humanizedInput(page, lastNameInput, lastName);
    }

    /**
     * Enter LinkedIn profile URL
     * @param linkedinUrl - LinkedIn profile URL (must start with https://)
     */
    @AutoStep
    public void enterLinkedInProfile(String linkedinUrl) {
        page.waitForLoadState();
        linkedinInput.clear();
        InputHelper.humanizedInput(page, linkedinInput, linkedinUrl);
    }

    /**
     * Enter text in the share textarea
     * @param shareText - text to share about expertise and net zero transition
     */
    @AutoStep
    public void enterShareText(String shareText) {
        page.waitForLoadState();
        shareTextarea.clear();
        InputHelper.humanizedInput(page, shareTextarea, shareText);
    }

    /**
     * Clear the first name field
     */
    @AutoStep
    public void clearFirstName() {
        page.waitForLoadState();
        firstNameInput.clear();
    }

    /**
     * Clear the last name field
     */
    @AutoStep
    public void clearLastName() {
        page.waitForLoadState();
        lastNameInput.clear();
    }

    /**
     * Clear the LinkedIn profile field
     */
    @AutoStep
    public void clearLinkedInProfile() {
        page.waitForLoadState();
        linkedinInput.clear();
    }

    /**
     * Clear the share text field
     */
    @AutoStep
    public void clearShareText() {
        page.waitForLoadState();
        shareTextarea.clear();
    }

    // ==================== DATA EXTRACTION METHODS ====================

    /**
     * Get the current value of the first name field
     * @return first name value
     */
    @AutoStep
    public String getFirstName() {
        page.waitForLoadState();
        return firstNameInput.inputValue();
    }

    /**
     * Get the current value of the last name field
     * @return last name value
     */
    @AutoStep
    public String getLastName() {
        page.waitForLoadState();
        return lastNameInput.inputValue();
    }

    /**
     * Get the email address displayed (read-only)
     * @return email address
     */
    @AutoStep
    public String getEmail() {
        page.waitForLoadState();
        return emailDisplay.textContent().trim();
    }

    /**
     * Get the password display (should be asterisks)
     * @return password display text (e.g., "*********")
     */
    @AutoStep
    public String getPasswordDisplay() {
        page.waitForLoadState();
        return passwordDisplay.textContent().trim();
    }

    /**
     * Get the LinkedIn profile URL
     * @return LinkedIn profile URL
     */
    @AutoStep
    public String getLinkedInProfile() {
        page.waitForLoadState();
        return linkedinInput.inputValue();
    }

    /**
     * Get the share text content
     * @return share text content
     */
    @AutoStep
    public String getShareText() {
        page.waitForLoadState();
        return shareTextarea.inputValue();
    }

    /**
     * Get all profile data as a Map
     * @return Map containing firstName, lastName, email, linkedin, shareText
     */
    @AutoStep
    public Map<String, String> getProfileData() {
        Map<String, String> profileData = new HashMap<>();
        profileData.put("firstName", getFirstName());
        profileData.put("lastName", getLastName());
        profileData.put("email", getEmail());
        profileData.put("linkedin", getLinkedInProfile());
        profileData.put("shareText", getShareText());
        return profileData;
    }

    // ==================== TAG MANAGEMENT METHODS ====================

    /**
     * Get all support network tags currently selected
     * @return List of tag text values
     */
    @AutoStep
    public List<String> getAllSupportTags() {
        page.waitForLoadState();
        List<String> tags = new ArrayList<>();
        int count = allTags.count();
        for (int i = 0; i < count; i++) {
            String tagText = allTags.nth(i).locator(".tagify__tag-text").textContent().trim();
            tags.add(tagText);
        }
        return tags;
    }

    /**
     * Check if a specific tag is present
     * @param tagText - tag text to search for (e.g., "an educator")
     * @return true if tag exists, false otherwise
     */
    @AutoStep
    public boolean hasTag(String tagText) {
        page.waitForLoadState();
        List<String> tags = getAllSupportTags();
        return tags.contains(tagText);
    }

    /**
     * Get the count of selected support tags
     * @return number of tags
     */
    @AutoStep
    public int getTagCount() {
        page.waitForLoadState();
        return allTags.count();
    }

    /**
     * Remove a tag by its text value
     * @param tagText - exact text of the tag to remove
     */
    @AutoStep
    public void removeTagByText(String tagText) {
        page.waitForLoadState();
        int count = allTags.count();
        for (int i = 0; i < count; i++) {
            String currentTagText = allTags.nth(i).locator(".tagify__tag-text").textContent().trim();
            if (currentTagText.equals(tagText)) {
                allTags.nth(i).locator("x.tagify__tag__removeBtn").click();
                page.waitForTimeout(500);
                break;
            }
        }
    }

    /**
     * Remove a tag by its index
     * @param index - index of the tag to remove (0-based)
     */
    @AutoStep
    public void removeTagByIndex(int index) {
        page.waitForLoadState();
        if (index >= 0 && index < allTags.count()) {
            allTags.nth(index).locator("x.tagify__tag__removeBtn").click();
            page.waitForTimeout(500);
        }
    }

    /**
     * Add a new support tag by typing in the tagify input
     * @param tagText - text of the new tag to add
     */
    @AutoStep
    public void addSupportTag(String tagText) {
        page.waitForLoadState();
        Locator tagifyInput = tagifyContainer.locator("span.tagify__input");
        tagifyInput.click();
        tagifyInput.pressSequentially(tagText);
        page.keyboard().press("Enter");
        page.waitForTimeout(500);
    }

    /**
     * Clear all support tags
     */
    @AutoStep
    public void clearAllTags() {
        page.waitForLoadState();
        int count = allTags.count();
        for (int i = count - 1; i >= 0; i--) {
            removeTagByIndex(i);
        }
    }

    // ==================== PASSWORD METHODS ====================

    /**
     * Click the "Change password" button
     */
    @AutoStep
    public void clickChangePassword() {
        page.waitForLoadState();
        changePasswordButton.click();
        page.waitForLoadState();
    }

    /**
     * Check if the "Change password" button is visible
     * @return true if button is visible, false otherwise
     */
    @AutoStep
    public boolean isChangePasswordButtonVisible() {
        page.waitForLoadState();
        return changePasswordButton.isVisible();
    }

    // ==================== SAVE METHODS ====================

    /**
     * Click the Save button to save profile changes
     */
    @AutoStep
    public void clickSave() {
        page.waitForLoadState();
        saveButton.click();
        page.waitForTimeout(2000); // Wait for save operation
    }

    /**
     * Check if the Save button is visible
     * @return true if button is visible, false otherwise
     */
    @AutoStep
    public boolean isSaveButtonVisible() {
        page.waitForLoadState();
        return saveButton.isVisible();
    }

    /**
     * Check if the Save button is enabled
     * @return true if button is enabled, false otherwise
     */
    @AutoStep
    public boolean isSaveButtonEnabled() {
        page.waitForLoadState();
        return saveButton.isEnabled();
    }

    // ==================== COMPLETE PROFILE UPDATE METHODS ====================

    /**
     * Update basic profile information (first name and last name)
     * @param firstName - first name to enter
     * @param lastName - last name to enter
     */
    @AutoStep
    public void updateBasicInfo(String firstName, String lastName) {
        enterFirstName(firstName);
        enterLastName(lastName);
    }

    /**
     * Update complete profile information
     * @param firstName - first name
     * @param lastName - last name
     * @param linkedin - LinkedIn profile URL
     * @param shareText - share text about expertise
     */
    @AutoStep
    public void updateCompleteProfile(String firstName, String lastName, String linkedin, String shareText) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterLinkedInProfile(linkedin);
        enterShareText(shareText);
    }

    /**
     * Update profile with tags
     * @param firstName - first name
     * @param lastName - last name
     * @param linkedin - LinkedIn profile URL
     * @param shareText - share text
     * @param tags - list of support network tags to add
     */
    @AutoStep
    public void updateProfileWithTags(String firstName, String lastName, String linkedin,
                                     String shareText, List<String> tags) {
        updateCompleteProfile(firstName, lastName, linkedin, shareText);
        clearAllTags();
        for (String tag : tags) {
            addSupportTag(tag);
        }
    }

    // ==================== VALIDATION METHODS ====================

    /**
     * Check if the first name field is empty
     * @return true if empty, false otherwise
     */
    @AutoStep
    public boolean isFirstNameEmpty() {
        page.waitForLoadState();
        return getFirstName().isEmpty();
    }

    /**
     * Check if the last name field is empty
     * @return true if empty, false otherwise
     */
    @AutoStep
    public boolean isLastNameEmpty() {
        page.waitForLoadState();
        return getLastName().isEmpty();
    }

    /**
     * Check if the LinkedIn field is empty
     * @return true if empty, false otherwise
     */
    @AutoStep
    public boolean isLinkedInEmpty() {
        page.waitForLoadState();
        return getLinkedInProfile().isEmpty();
    }

    /**
     * Check if the share text field is empty
     * @return true if empty, false otherwise
     */
    @AutoStep
    public boolean isShareTextEmpty() {
        page.waitForLoadState();
        return getShareText().isEmpty();
    }

    /**
     * Check if all required fields have values
     * Note: Only firstName and lastName are typically required
     * @return true if first name and last name are not empty
     */
    @AutoStep
    public boolean areRequiredFieldsFilled() {
        page.waitForLoadState();
        return !isFirstNameEmpty() && !isLastNameEmpty();
    }

    /**
     * Check if the email display is visible (read-only field)
     * @return true if email is visible, false otherwise
     */
    @AutoStep
    public boolean isEmailVisible() {
        page.waitForLoadState();
        return emailDisplay.isVisible();
    }

    /**
     * Verify that all profile labels are displayed
     * @return true if all main labels are visible
     */
    @AutoStep
    public boolean areAllLabelsVisible() {
        page.waitForLoadState();
        return firstNameLabel.isVisible() &&
               lastNameLabel.isVisible() &&
               emailLabel.isVisible() &&
               passwordLabel.isVisible();
    }

    /**
     * Check if the profile form is in edit mode
     * Determined by whether input fields are enabled
     * @return true if form fields are enabled for editing
     */
    @AutoStep
    public boolean isFormEditable() {
        page.waitForLoadState();
        return firstNameInput.isEnabled() && lastNameInput.isEnabled();
    }

    // ==================== GETTER METHODS (for advanced usage) ====================

    /**
     * Get the profile card locator
     * @return Locator for the main profile card
     */
    public Locator getProfileCard() {
        return profileCard;
    }

    /**
     * Get the first name input locator
     * @return Locator for first name input field
     */
    public Locator getFirstNameInput() {
        return firstNameInput;
    }

    /**
     * Get the last name input locator
     * @return Locator for last name input field
     */
    public Locator getLastNameInput() {
        return lastNameInput;
    }

    /**
     * Get the save button locator
     * @return Locator for the save button
     */
    public Locator getSaveButton() {
        return saveButton;
    }

    /**
     * Get the tagify container locator
     * @return Locator for the tags container
     */
    public Locator getTagifyContainer() {
        return tagifyContainer;
    }
}
