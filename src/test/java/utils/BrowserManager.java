package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

/**
 * BrowserManager - Utility class for browser configuration and management
 */
public class BrowserManager {

    /**
     * Get browser launch options based on environment
     */
    public static BrowserType.LaunchOptions getBrowserLaunchOptions() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        boolean slowMo = Boolean.parseBoolean(System.getProperty("slowmo", "false"));

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
            .setHeadless(headless);

        if (slowMo) {
            options.setSlowMo(1000); // 1 second delay between actions
        }

        return options;
    }

    /**
     * Launch Chrome browser with default options
     */
    public static Browser launchChrome(Playwright playwright) {
        return playwright.chromium().launch(getBrowserLaunchOptions());
    }

    /**
     * Launch Firefox browser with default options
     */
    public static Browser launchFirefox(Playwright playwright) {
        return playwright.firefox().launch(getBrowserLaunchOptions());
    }

    /**
     * Launch WebKit browser with default options
     */
    public static Browser launchWebKit(Playwright playwright) {
        return playwright.webkit().launch(getBrowserLaunchOptions());
    }

    /**
     * Get browser based on system property or default to Chrome
     */
    public static Browser launchBrowser(Playwright playwright) {
        String browserName = System.getProperty("browser", "chrome").toLowerCase();

        return switch (browserName) {
            case "firefox" -> launchFirefox(playwright);
            case "webkit", "safari" -> launchWebKit(playwright);
            default -> launchChrome(playwright);
        };
    }

    /**
     * Get browser name for reporting purposes
     */
    public static String getBrowserName() {
        return System.getProperty("browser", "chrome");
    }

    /**
     * Check if running in headless mode
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}