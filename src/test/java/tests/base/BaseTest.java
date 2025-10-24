package tests.base;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import pages.PageManager;
import steps.AuthenticationSteps;
import steps.BuildingSteps;
import utils.BrowserManager;
import utils.ReportUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BaseTest class providing unified setup for all test classes
 * Handles browser management, tracing, and Allure reporting
 *
 * Trace Organization:
 * - Root folder: test-results/traces/
 * - Each test run creates: test-results/traces/run_YYYYMMDD_HHMMSS/
 * - Individual traces: run_YYYYMMDD_HHMMSS/ClassName_testDisplayName.zip
 * - Includes parameterized test parameters in filename for easy debugging
 */
public abstract class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected PageManager pageManager;

    // Reusable workflow steps
    protected AuthenticationSteps authSteps;
    protected BuildingSteps buildingSteps;

    // Trace file management
    private static String runFolder;
    private static final String TRACES_ROOT = "test-results/traces";

    @BeforeAll
    static void setupPlaywright() {
        playwright = Playwright.create();
        browser = BrowserManager.launchBrowser(playwright);

        // Create organized trace folder structure for this test run
        setupTraceFolder();

        // Add environment info to Allure report
        ReportUtils.addEnvironmentInfo();
    }

    /**
     * Create organized trace folder structure
     * Creates: test-results/traces/run_YYYYMMDD_HHMMSS/
     */
    private static void setupTraceFolder() {
        try {
            // Create run-specific folder with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            runFolder = String.format("run_%s", timestamp);

            Path tracesPath = Paths.get(TRACES_ROOT, runFolder);
            Files.createDirectories(tracesPath);

            System.out.println("📁 Trace folder created: " + tracesPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("⚠️ Could not create trace folder: " + e.getMessage());
            // Fallback to root directory
            runFolder = "";
        }
    }

    @BeforeEach
    void setupTest() {
        // Create browser context with tracing
        context = browser.newContext();

        // Start tracing
        context.tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));

        // Create page and initialize PageManager
        page = context.newPage();
        pageManager = new PageManager(page);

        // Initialize reusable workflow steps
        authSteps = new AuthenticationSteps(pageManager);
        buildingSteps = new BuildingSteps(pageManager);
    }

    @AfterEach
    void teardownTest(TestInfo testInfo) {
        try {
            // Generate trace file name with test display name (includes parameters)
            String testClassName = this.getClass().getSimpleName();
            String displayName = sanitizeFileName(testInfo.getDisplayName());
            String traceFileBaseName = String.format("%s_%s.zip", testClassName, displayName);

            // Create full path within the run folder
            String traceFilePath;
            if (runFolder != null && !runFolder.isEmpty()) {
                traceFilePath = Paths.get(TRACES_ROOT, runFolder, traceFileBaseName).toString();
            } else {
                traceFilePath = traceFileBaseName;
            }

            Path tracePath = Paths.get(traceFilePath);

            // Stop tracing and save
            context.tracing().stop(new Tracing.StopOptions()
                .setPath(tracePath));

            System.out.println("💾 Trace saved: " + tracePath.toAbsolutePath());

            // Attach trace to Allure report
            if (Files.exists(tracePath)) {
                byte[] traceBytes = Files.readAllBytes(tracePath);
                ReportUtils.addTrace(traceBytes, testInfo.getDisplayName());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Could not save trace: " + e.getMessage());
        } finally {
            // Always close context
            if (context != null) {
                context.close();
            }
        }
    }

    @AfterAll
    static void teardownPlaywright() {
        // Print run summary
        if (runFolder != null && !runFolder.isEmpty()) {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("📋 Test run completed");
            System.out.println("📂 Traces saved in: test-results/traces/" + runFolder);
            System.out.println("📊 View Allure report: allure serve target/allure-results");
            System.out.println("🔍 Debug failures: ./debug-failures.sh");
            System.out.println("=".repeat(80) + "\n");
        }

        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    /**
     * Sanitize filename by removing invalid characters
     */
    private String sanitizeFileName(String displayName) {
        return displayName
            .replaceAll("[^a-zA-Z0-9._\\-]", "_")
            .replaceAll("_{2,}", "_")
            .substring(0, Math.min(displayName.length(), 200)); // Limit length
    }

    /**
     * Take screenshot and attach to Allure report
     */
    protected void takeScreenshot(String name) {
        try {
            byte[] screenshot = page.screenshot();
            ReportUtils.addScreenshot(screenshot, name);
        } catch (Exception e) {
            System.err.println("Could not take screenshot: " + e.getMessage());
        }
    }

    /**
     * Add step to Allure report
     */
    protected void addStep(String stepName, Runnable action) {
        Allure.step(stepName, () -> {
            action.run();
            return null;
        });
    }

    /**
     * Get the current run folder name
     */
    protected static String getRunFolder() {
        return runFolder;
    }

    /**
     * Get the full path to the traces directory for this run
     */
    protected static String getTracesDirectory() {
        if (runFolder != null && !runFolder.isEmpty()) {
            return Paths.get(TRACES_ROOT, runFolder).toString();
        }
        return TRACES_ROOT;
    }

}