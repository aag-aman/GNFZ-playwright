package pages.authentication;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * SignUpPage - Page Object Model for the Sign Up page
 *
 * This class represents the sign-up/registration page and contains all locators
 * and methods needed to interact with the sign-up form.
 */
public class SignUpPage {

    private final Page page;

    // Locators - initialized once and reused
    private final Locator pageHeader;
    private final Locator pageSubHeader;
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator confirmPasswordField;
    private final Locator signUpButton;
    private final Locator successMessage;
    private final Locator loginLink;

    // Field errors
    private final Locator firstNameError;
    private final Locator emailError;
    private final Locator passwordError;
    private final Locator confirmPasswordError;

    public SignUpPage(Page page) {
        this.page = page;

        // Initialize locators once in constructor
        this.pageHeader = page.locator("h1, [data-testid='signup-header']");
        this.pageSubHeader = page.locator("p, [data-testid='signup-description']");
        this.firstNameField = page.locator("#gnfz-register-firstName");
        this.lastNameField = page.locator("#gnfz-register-lastName");
        this.emailField = page.locator("#gnfz-register-email");
        this.passwordField = page.locator("#gnfz-register-password");
        this.confirmPasswordField = page.locator("#gnfz-register-confirmPassword");
        this.signUpButton = page.locator("#gnfz-sign-up-button");

        this.firstNameError = page.locator("div")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile("^First name$"))).locator("small");
        this.emailError = page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Email$")))
                .locator("small");
        this.passwordError = page.locator("div")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Password$"))).locator("small");
        this.confirmPasswordError = page.locator("div")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Confirm Password$"))).locator("small");
        // this.errorMessage = page.locator(".error-message, .alert-danger,
        // small.text-danger");
        this.successMessage = page.locator(".success-message, .alert-success");
        this.loginLink = page.locator("a[href*='login'], a:has-text('Login')");
    }

    // =============================================================================
    // NAVIGATION METHODS
    // =============================================================================

    public void navigateToSignUp() {
        String baseUrl = System.getProperty("baseUrl", "https://dev-platform.globalnetworkforzero.com");
        String currentUrl = page.url();
        String expectedUrl = baseUrl + "/register";

        if (!currentUrl.equals(expectedUrl)) {
            page.navigate(baseUrl + "/register");
        }
    }

    // =============================================================================
    // FORM INTERACTION METHODS
    // =============================================================================

    public void enterFirstName(String firstName) {
        firstNameField.fill(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.fill(lastName);
    }

    public void enterEmail(String email) {
        emailField.fill(email);
    }

    public void enterPassword(String password) {
        passwordField.fill(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        confirmPasswordField.fill(confirmPassword);
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    /**
     * Complete sign-up process with all required fields
     */
    public void signUp(String firstName, String lastName, String email, String password, String confirmPassword) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        clickSignUpButton();
    }

    /**
     * Click the login link to navigate to login page
     */
    public void clickLoginLink() {
        loginLink.click();
    }

    // =============================================================================
    // FIELD MANIPULATION METHODS
    // =============================================================================

    public void clearFirstNameField() {
        firstNameField.clear();
    }

    public void clearLastNameField() {
        lastNameField.clear();
    }

    public void clearEmailField() {
        emailField.clear();
    }

    public void clearPasswordField() {
        passwordField.clear();
    }

    public void clearConfirmPasswordField() {
        confirmPasswordField.clear();
    }

    public void clearAllFields() {
        clearFirstNameField();
        clearLastNameField();
        clearEmailField();
        clearPasswordField();
        clearConfirmPasswordField();
    }

    // =============================================================================
    // TEXT RETRIEVAL METHODS
    // =============================================================================

    public String getFirstNameFieldValue() {
        return firstNameField.inputValue();
    }

    public String getLastNameFieldValue() {
        return lastNameField.inputValue();
    }

    public String getEmailFieldValue() {
        return emailField.inputValue();
    }

    public String getPasswordFieldValue() {
        return passwordField.inputValue();
    }

    public String getConfirmPasswordFieldValue() {
        return confirmPasswordField.inputValue();
    }

    public String getFirstNameError() {
        return firstNameError.textContent();
    }

    public String getEmailError() {
        return emailError.textContent();
    }

    public String getPasswordError() {
        return passwordError.textContent();
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError.textContent();
    }

    public boolean getEmailErrorVisible() {
        return emailError.isVisible();
    }

    public boolean getPasswordErrorVisible() {
        return passwordError.isVisible();
    }

    public boolean getConfirmPasswordErrorVisible() {
        return confirmPasswordError.isVisible();
    }

    public String getSuccessMessage() {
        return successMessage.textContent();
    }

    public String getPageHeader() {
        return pageHeader.textContent();
    }

    public String getPageSubHeader() {
        return pageSubHeader.textContent();
    }

    // =============================================================================
    // ELEMENT STATE CHECKING METHODS
    // =============================================================================

    /**
     * Check if the sign-up page is displayed
     */
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        emailField.waitFor();
        return emailField.isVisible();
    }

    public boolean isFirstNameFieldVisible() {
        return firstNameField.isVisible();
    }

    public boolean isLastNameFieldVisible() {
        return lastNameField.isVisible();
    }

    public boolean isEmailFieldVisible() {
        return emailField.isVisible();
    }

    public boolean isPasswordFieldVisible() {
        return passwordField.isVisible();
    }

    public boolean isConfirmPasswordFieldVisible() {
        return confirmPasswordField.isVisible();
    }

    public boolean isSignUpButtonVisible() {
        return signUpButton.isVisible();
    }

    public boolean isSignUpButtonEnabled() {
        return signUpButton.isEnabled();
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isVisible();
    }

    public boolean isLoginLinkVisible() {
        return loginLink.isVisible();
    }

    // =============================================================================
    // GETTER METHODS FOR LOCATORS (for advanced usage)
    // =============================================================================

    public Locator getFirstNameField() {
        return firstNameField;
    }

    public Locator getLastNameField() {
        return lastNameField;
    }

    public Locator getEmailField() {
        return emailField;
    }

    public Locator getPasswordField() {
        return passwordField;
    }

    public Locator getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public Locator getSignUpButton() {
        return signUpButton;
    }
}
