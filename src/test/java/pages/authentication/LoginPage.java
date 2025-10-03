package pages.authentication;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;
    private final String baseUrl;

    // Locators - initialized once and reused
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator signInButton;

    public LoginPage(Page page) {
        this.page = page;
        this.baseUrl = System.getProperty("baseUrl", "https://dev-platform.globalnetworkforzero.com");

        // Initialize locators once in constructor
        this.emailField = page.locator("#gnfz-login-email");
        this.passwordField = page.locator("#gnfz-login-password");
        this.signInButton = page.locator("button[type='submit']");
    }

    public void navigateToLogin() {
        String currentUrl = page.url();
        String expectedUrl = baseUrl + "/login";

        if (!currentUrl.equals(expectedUrl)) {
            page.navigate(baseUrl);
        }
    }

    public void enterEmail(String email) {
        emailField.fill(email);
    }

    public void enterPassword(String password) {
        passwordField.fill(password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
    }

    // Getter methods for locators (optional - for advanced usage)
    public Locator getEmailField() {
        return emailField;
    }

    public Locator getPasswordField() {
        return passwordField;
    }

    public Locator getSignInButton() {
        return signInButton;
    }
}