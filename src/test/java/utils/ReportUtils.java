package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

/**
 * ReportUtils - Utility class for Allure reporting functions
 */
public class ReportUtils {

    /**
     * Add environment information to Allure report
     */
    public static void addEnvironmentInfo() {
        Allure.addAttachment("Environment", "text/plain",
            "Browser: " + BrowserManager.getBrowserName() + "\n" +
            "Headless: " + BrowserManager.isHeadless() + "\n" +
            "OS: " + System.getProperty("os.name") + "\n" +
            "Java Version: " + System.getProperty("java.version"), ".txt");
    }

    /**
     * Add test data to Allure report
     */
    public static void addTestData(String testData) {
        Allure.addAttachment("Test Data", "application/json", testData, ".json");
    }

    /**
     * Add custom step with status
     */
    public static void addStep(String stepName, Status status, String description) {
        Allure.step(stepName, () -> {
            if (description != null && !description.isEmpty()) {
                Allure.addAttachment("Step Details", "text/plain", description, ".txt");
            }
        });
    }

    /**
     * Add screenshot with custom name
     */
    public static void addScreenshot(byte[] screenshot, String name) {
        if (screenshot != null) {
            Allure.addAttachment(name, "image/png", new String(screenshot), ".png");
        }
    }

    /**
     * Add trace file attachment
     */
    public static void addTrace(byte[] trace, String testClassName) {
        if (trace != null) {
            Allure.addAttachment("Playwright Trace - " + testClassName,
                "application/zip", new String(trace), ".zip");
        }
    }

    /**
     * Add error information to report
     */
    public static void addErrorInfo(Throwable error) {
        if (error != null) {
            Allure.addAttachment("Error Details", "text/plain",
                "Error: " + error.getMessage() + "\n" +
                "Stack Trace: " + java.util.Arrays.toString(error.getStackTrace()), ".txt");
        }
    }

    /**
     * Add browser console logs
     */
    public static void addConsoleLogs(String logs) {
        if (logs != null && !logs.isEmpty()) {
            Allure.addAttachment("Console Logs", "text/plain", logs, ".txt");
        }
    }

    /**
     * Add network activity logs
     */
    public static void addNetworkLogs(String networkLogs) {
        if (networkLogs != null && !networkLogs.isEmpty()) {
            Allure.addAttachment("Network Activity", "application/json", networkLogs, ".json");
        }
    }
}