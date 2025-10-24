package utils;

import io.qameta.allure.Allure;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * TestLogger - Utility for logging that appears in both console and Allure reports
 *
 * Usage:
 *   TestLogger.info("User logged in successfully");
 *   TestLogger.debug("Response status code: " + statusCode);
 *   TestLogger.warn("Retrying failed operation");
 *   TestLogger.error("Failed to connect to database");
 *
 * All logs are:
 * - Printed to console (System.out)
 * - Attached to Allure report as text attachments
 * - Timestamped for easy debugging
 */
public class TestLogger {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final List<String> logBuffer = new ArrayList<>();
    private static final int BUFFER_SIZE = 50; // Attach logs in batches to avoid too many attachments

    /**
     * Log an info message (most common log level)
     */
    public static void info(String message) {
        log("INFO", message);
    }

    /**
     * Log a debug message (detailed information)
     */
    public static void debug(String message) {
        log("DEBUG", message);
    }

    /**
     * Log a warning message (potential issues)
     */
    public static void warn(String message) {
        log("WARN", message);
    }

    /**
     * Log an error message (failures, exceptions)
     */
    public static void error(String message) {
        log("ERROR", message);
    }

    /**
     * Log an error with exception details
     */
    public static void error(String message, Throwable throwable) {
        log("ERROR", message + "\n" + getStackTrace(throwable));
    }

    /**
     * Log key-value pair (useful for showing test data)
     */
    public static void keyValue(String key, Object value) {
        String message = String.format("%-20s : %s", key, value);
        log("INFO", message);
        // Also add as Allure parameter for better visibility
        Allure.parameter(key, value);
    }

    /**
     * Log multiple key-value pairs (with Allure parameters)
     */
    public static void logData(String title, Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Key-value pairs must be even number of arguments");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i].toString();
            Object value = keyValuePairs[i + 1];
            sb.append(String.format("%-20s : %s\n", key, value));
            Allure.parameter(key, value);
        }

        log("INFO", sb.toString());
    }

    /**
     * Log multiple key-value pairs WITHOUT adding Allure parameters (for internal use)
     * Use this for assertion details to avoid cluttering the parameters section
     */
    public static void logDataNoParams(String title, Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Key-value pairs must be even number of arguments");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i].toString();
            Object value = keyValuePairs[i + 1];
            sb.append(String.format("%-30s : %s\n", key, value));
        }

        log("INFO", sb.toString());
    }

    /**
     * Flush current log buffer to Allure (forces immediate attachment)
     */
    public static void flush() {
        if (!logBuffer.isEmpty()) {
            attachLogs();
        }
    }

    /**
     * Core logging method
     */
    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(timeFormatter);
        String formattedLog = String.format("[%s] [%s] %s", timestamp, level, message);

        // Print to console
        System.out.println(formattedLog);

        // Add to buffer
        logBuffer.add(formattedLog);

        // Attach to Allure if buffer is full or if it's an ERROR
        if (logBuffer.size() >= BUFFER_SIZE || level.equals("ERROR")) {
            attachLogs();
        }
    }

    /**
     * Attach buffered logs to Allure report
     */
    private static void attachLogs() {
        if (logBuffer.isEmpty()) {
            return;
        }

        String logs = String.join("\n", logBuffer);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Allure.addAttachment(
            "Test Logs (" + timestamp + ")",
            "text/plain",
            logs,
            ".txt"
        );

        logBuffer.clear();
    }

    /**
     * Get stack trace as string
     */
    private static String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.getClass().getName()).append(": ").append(throwable.getMessage()).append("\n");

        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("    at ").append(element.toString()).append("\n");
        }

        if (throwable.getCause() != null) {
            sb.append("Caused by: ").append(getStackTrace(throwable.getCause()));
        }

        return sb.toString();
    }

    /**
     * Create a separator line in logs (useful for grouping)
     */
    public static void separator() {
        separator("=");
    }

    /**
     * Create a separator line with custom character
     */
    public static void separator(String character) {
        String line = character.repeat(80);
        log("INFO", line);
    }

    /**
     * Log section header
     */
    public static void section(String title) {
        separator("=");
        log("INFO", "    " + title);
        separator("=");
    }
}
