package pages.authentication;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.AutoStep;
import utils.InputHelper;

/**
 * ForgotPasswordPage - Page Object Model for the Forgot Password page
 *
 * This class represents the forgot password page and contains all the locators
 * and methods needed to interact with the page elements.
 *
 * For beginners: This follows the Page Object Model (POM) pattern which:
 * - Separates test logic from page element details
 * - Makes tests easier to maintain when UI changes
 * - Provides reusable methods for interacting with the page
 * - Keeps locators in one place for easy updates
 */
public class ForgotPasswordPage {
    private Page page;

    // Locators - These find elements on the page and are initialized once in constructor
    // For beginners: Locators are like "addresses" that tell Playwright where to find elements
    private final Locator pageHeader;          // Main heading of the page
    private final Locator pageSubHeader;       // Descriptive text below the heading
    private final Locator emailField;          // Input field where user enters email
    private final Locator submitButton;        // Button to submit the forgot password request
    private final Locator confirmationHeader;  // Header shown after successful submission
    private final Locator confirmationMessage; // Message shown after successful submission
    private final Locator backToLoginMessage;  // Text for back to login option
    private final Locator backToLoginLink;     // Clickable link to go back to login
    private final Locator errorMessage;        // Error message for validation failures

    /**
     * Constructor - Initializes the page object with locators
     *
     * @param page - The Playwright Page object to interact with
     *
     * For beginners: The constructor runs when you create a new ForgotPasswordPage object.
     * It sets up all the locators (element finders) once, so they can be reused efficiently.
     */
    public ForgotPasswordPage(Page page) {
        this.page = page;

        // Initialize locators once in constructor for better performance
        // For beginners: These are different ways to find elements:
        // - #id finds elements by ID (most reliable)
        // - XPath finds elements by their position in HTML (less reliable but sometimes necessary)
        this.pageHeader = page.locator("#gnfz-forgot-header");
        this.pageSubHeader = page.locator("#gnfz-forgot-description");
        this.emailField = page.locator("#gnfz-forget-email");
        this.submitButton = page.locator("#reset-password");

        // Note: These XPath locators should be replaced with more robust selectors when possible
        // XPath is brittle and can break easily when the page structure changes
        // Using xpath= prefix to properly indicate XPath selectors
        this.confirmationHeader = page.locator("xpath=/html/body/app-root/gnfz-forget-password-modal/div/div/div/div[1]/section/div/div/div[2]/p[1]/b");
        this.confirmationMessage = page.locator("xpath=/html/body/app-root/gnfz-forget-password-modal/div/div/div/div[1]/section/div/div/div[2]/p[2]");
        this.backToLoginMessage = page.locator("xpath=//*[@id=\"gnfz-remember-password\"]");
        this.backToLoginLink = page.locator("xpath=//*[@id=\"gnfz-remember-password\"]/a");
        this.errorMessage = page.locator("xpath=/html/body/app-root/gnfz-forget-password-modal/div/div/div/div[1]/section/div/div/div[2]/form/div/div[1]/div/small");
    }

    // =============================================================================
    // NAVIGATION METHODS
    // =============================================================================

    /**
     * Navigate to the forgot password page
     *
     * For beginners: This method handles navigation to the forgot password page.
     * It checks if we're already on the right page to avoid unnecessary navigation.
     */
    @AutoStep
    public void navigateToForgotPassword() {
        String baseUrl = System.getProperty("baseUrl", "https://dev-platform.globalnetworkforzero.com");
        String currentUrl = page.url();
        String expectedUrl = baseUrl + "/forgot-password";

        // Only navigate if we're not already on the forgot password page
        if (!currentUrl.equals(expectedUrl)) {
            page.navigate(baseUrl + "/forgot-password");
        }
    }

    // =============================================================================
    // FORM INTERACTION METHODS
    // =============================================================================

    /**
     * Enter email address in the email field
     *
     * @param email - The email address to enter
     *
     * For beginners: This method types the email into the email input field.
     * Playwright automatically waits for the element to be ready before typing.
     */
    @AutoStep
    public void enterEmail(String email) {
        InputHelper.humanizedInputNoEnter(page, emailField, email);
    }

    /**
     * Click the submit/reset password button
     *
     * For beginners: This method clicks the button to submit the forgot password request.
     * Playwright automatically waits for the button to be clickable.
     */
    @AutoStep
    public void clickSubmitButton() {
        submitButton.click();
    }

    /**
     * Complete forgot password process in one step
     *
     * @param email - The email address to use for password reset
     *
     * For beginners: This is a convenience method that combines entering email
     * and clicking submit. It's useful when you want to do both actions together.
     */
    @AutoStep
    public void requestPasswordReset(String email) {
        enterEmail(email);
        clickSubmitButton();
    }

    // =============================================================================
    // TEXT RETRIEVAL METHODS
    // =============================================================================

    /**
     * Get the confirmation header text after successful submission
     *
     * @return String containing the confirmation header text
     *
     * For beginners: This method gets the text from the confirmation header element.
     * Use this to verify that the correct success message is displayed.
     */
    @AutoStep
    public String getConfirmationHeader() {
        return confirmationHeader.textContent();
    }

    /**
     * Get the confirmation message text after successful submission
     *
     * @return String containing the confirmation message text
     */
    @AutoStep
    public String getConfirmationMessage() {
        return confirmationMessage.textContent();
    }

    /**
     * Get the error message text when validation fails
     *
     * @return String containing the error message text
     *
     * For beginners: This method gets error text shown when something goes wrong
     * (like invalid email format). Use this to verify proper error handling.
     */
    @AutoStep
    public String getErrorMessage() {
        return errorMessage.textContent();
    }

    /**
     * Get the main page header text
     *
     * @return String containing the page header text
     */
    @AutoStep
    public String getPageHeader() {
        return pageHeader.textContent();
    }

    /**
     * Get the page sub-header/description text
     *
     * @return String containing the page sub-header text
     */
    @AutoStep
    public String getPageSubHeader() {
        return pageSubHeader.textContent();
    }

    /**
     * Get the back to login message text
     *
     * @return String containing the back to login message
     */
    @AutoStep
    public String getBackToLoginMessage() {
        return backToLoginMessage.textContent();
    }

    // =============================================================================
    // ELEMENT STATE CHECKING METHODS
    // =============================================================================

    /**
     * Check if the forgot password page is displayed
     *
     * @return true if page is displayed, false otherwise
     *
     * For beginners: This method checks if we're on the right page by looking
     * for the page header. It's the main way to verify page navigation worked.
     */
    @AutoStep
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        emailField.waitFor();
        return emailField.isVisible();
    }

    /**
     * Check if the email field is visible
     *
     * @return true if email field is visible, false otherwise
     */
    @AutoStep
    public boolean isEmailFieldVisible() {
        return emailField.isVisible();
    }

    /**
     * Check if the submit button is visible
     *
     * @return true if submit button is visible, false otherwise
     */
    @AutoStep
    public boolean isSubmitButtonVisible() {
        return submitButton.isVisible();
    }

    /**
     * Check if the submit button is enabled (clickable)
     *
     * @return true if submit button is enabled, false otherwise
     *
     * For beginners: Some buttons might be visible but disabled (grayed out).
     * This method checks if the button can actually be clicked.
     */
    @AutoStep
    public boolean isSubmitButtonEnabled() {
        return submitButton.isEnabled();
    }

    /**
     * Check if confirmation message is displayed after successful submission
     *
     * @return true if confirmation is displayed, false otherwise
     */
    @AutoStep
    public boolean isConfirmationDisplayed() {
        return confirmationHeader.isVisible();
    }

    /**
     * Check if error message is displayed
     *
     * @return true if error message is displayed, false otherwise
     *
     * For beginners: This is useful for negative testing - when you expect
     * something to fail and want to verify an error message appears.
     */
    @AutoStep
    public boolean isErrorMessageDisplayed() {
        return errorMessage.isVisible();
    }

    /**
     * Check if back to login link is visible
     *
     * @return true if back to login link is visible, false otherwise
     */
    @AutoStep
    public boolean isBackToLoginLinkVisible() {
        return backToLoginLink.isVisible();
    }

    // =============================================================================
    // FIELD MANIPULATION METHODS
    // =============================================================================

    /**
     * Clear the email field
     *
     * For beginners: This method empties the email field. It's good practice
     * to clear fields before entering new text to avoid mixing old and new text.
     */
    @AutoStep
    public void clearEmailField() {
        emailField.clear();
    }

    /**
     * Get the current value in the email field
     *
     * @return String containing the current email field value
     *
     * For beginners: This method reads what's currently typed in the email field.
     * Use this to verify that text was entered correctly in your tests.
     */
    @AutoStep
    public String getEmailFieldValue() {
        return emailField.inputValue();
    }

    // =============================================================================
    // ADDITIONAL INTERACTION METHODS
    // =============================================================================

    /**
     * Click the back to login link
     *
     * For beginners: This method clicks the link to go back to the login page.
     * Note: This will navigate away from the forgot password page.
     */
    @AutoStep
    public void clickBackToLoginLink() {
        backToLoginLink.click();
    }

}
