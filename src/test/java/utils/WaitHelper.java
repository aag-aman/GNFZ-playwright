package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * WaitHelper - Intelligent Wait Utility
 *
 * Provides reusable wait methods that poll for conditions instead of using hardcoded timeouts.
 * All methods use incremental polling (250ms) up to a maximum timeout (default 30 seconds).
 *
 * Benefits:
 * - Tests wait only as long as needed (faster execution)
 * - More reliable (30-second max prevents false failures)
 * - Better error messages (knows what it was waiting for)
 * - Integrated with Allure reporting via TestLogger
 *
 * Usage Examples:
 * - Wait for calculation: WaitHelper.waitForCalculationComplete(page, totalLocator, 30000)
 * - Wait for new row: WaitHelper.waitForNewRow(page, tableRows, oldCount, 30000)
 * - Wait for text change: WaitHelper.waitForTextChange(page, locator, oldValue, 30000)
 */
public class WaitHelper {

    // Default timeout: 30 seconds
    private static final int DEFAULT_TIMEOUT_MS = 30000;

    // Polling interval: 250ms (balanced between speed and resource usage)
    private static final int POLL_INTERVAL_MS = 250;

    // Stability period for waitForElementStable
    private static final int DEFAULT_STABILITY_PERIOD_MS = 300;

    // ==================== CORE WAIT METHODS ====================

    /**
     * Wait for element text content to change from initial value
     *
     * @param page Playwright page
     * @param locator Element to monitor
     * @param initialValue The initial text value
     * @param timeoutMs Maximum time to wait in milliseconds
     * @return The new text value
     * @throws RuntimeException if timeout is exceeded
     */
    public static String waitForTextChange(Page page, Locator locator, String initialValue, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for text to change from '%s' (timeout: %dms)", initialValue, timeoutMs));

        long startTime = System.currentTimeMillis();
        String currentValue = initialValue;

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                currentValue = locator.textContent().trim();
                if (!currentValue.equals(initialValue)) {
                    TestLogger.debug(String.format("Text changed to '%s' after %dms", currentValue, System.currentTimeMillis() - startTime));
                    return currentValue;
                }
            } catch (Exception e) {
                // Element may not be available yet, continue polling
            }
            page.waitForTimeout(POLL_INTERVAL_MS);
        }

        throw new RuntimeException(String.format(
            "Timeout waiting for text to change. Expected: different from '%s', Actual: '%s', Timeout: %dms",
            initialValue, currentValue, timeoutMs
        ));
    }

    /**
     * Wait for element text content to change from initial value (default 30s timeout)
     */
    public static String waitForTextChange(Page page, Locator locator, String initialValue) {
        return waitForTextChange(page, locator, initialValue, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for element to contain specific text
     *
     * @param page Playwright page
     * @param locator Element to monitor
     * @param expectedText Text that should be contained
     * @param timeoutMs Maximum time to wait in milliseconds
     */
    public static void waitForTextToContain(Page page, Locator locator, String expectedText, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for text to contain '%s' (timeout: %dms)", expectedText, timeoutMs));

        long startTime = System.currentTimeMillis();
        String currentValue = "";

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                currentValue = locator.textContent().trim();
                if (currentValue.contains(expectedText)) {
                    TestLogger.debug(String.format("Text contains '%s' after %dms", expectedText, System.currentTimeMillis() - startTime));
                    return;
                }
            } catch (Exception e) {
                // Element may not be available yet, continue polling
            }
            page.waitForTimeout(POLL_INTERVAL_MS);
        }

        throw new RuntimeException(String.format(
            "Timeout waiting for text to contain '%s'. Actual text: '%s', Timeout: %dms",
            expectedText, currentValue, timeoutMs
        ));
    }

    /**
     * Wait for element to contain specific text (default 30s timeout)
     */
    public static void waitForTextToContain(Page page, Locator locator, String expectedText) {
        waitForTextToContain(page, locator, expectedText, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for element attribute to change from initial value
     *
     * @param page Playwright page
     * @param locator Element to monitor
     * @param attribute Attribute name (e.g., "class", "disabled", "aria-expanded")
     * @param initialValue The initial attribute value
     * @param timeoutMs Maximum time to wait in milliseconds
     * @return The new attribute value
     */
    public static String waitForAttributeChange(Page page, Locator locator, String attribute, String initialValue, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for attribute '%s' to change from '%s' (timeout: %dms)", attribute, initialValue, timeoutMs));

        long startTime = System.currentTimeMillis();
        String currentValue = initialValue;

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                currentValue = locator.getAttribute(attribute);
                if (currentValue != null && !currentValue.equals(initialValue)) {
                    TestLogger.debug(String.format("Attribute '%s' changed to '%s' after %dms", attribute, currentValue, System.currentTimeMillis() - startTime));
                    return currentValue;
                }
            } catch (Exception e) {
                // Element may not be available yet, continue polling
            }
            page.waitForTimeout(POLL_INTERVAL_MS);
        }

        throw new RuntimeException(String.format(
            "Timeout waiting for attribute '%s' to change. Expected: different from '%s', Actual: '%s', Timeout: %dms",
            attribute, initialValue, currentValue, timeoutMs
        ));
    }

    /**
     * Wait for element attribute to change (default 30s timeout)
     */
    public static String waitForAttributeChange(Page page, Locator locator, String attribute, String initialValue) {
        return waitForAttributeChange(page, locator, attribute, initialValue, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for element count to change from initial count
     * Useful for pagination, add/remove row operations
     *
     * @param page Playwright page
     * @param locator Locator that matches multiple elements
     * @param initialCount The initial count
     * @param timeoutMs Maximum time to wait in milliseconds
     * @return The new count
     */
    public static int waitForCountChange(Page page, Locator locator, int initialCount, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for element count to change from %d (timeout: %dms)", initialCount, timeoutMs));

        long startTime = System.currentTimeMillis();
        int currentCount = initialCount;

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                currentCount = locator.count();
                if (currentCount != initialCount) {
                    TestLogger.debug(String.format("Element count changed to %d after %dms", currentCount, System.currentTimeMillis() - startTime));
                    return currentCount;
                }
            } catch (Exception e) {
                // Continue polling
            }
            page.waitForTimeout(POLL_INTERVAL_MS);
        }

        throw new RuntimeException(String.format(
            "Timeout waiting for element count to change. Expected: different from %d, Actual: %d, Timeout: %dms",
            initialCount, currentCount, timeoutMs
        ));
    }

    /**
     * Wait for element count to change (default 30s timeout)
     */
    public static int waitForCountChange(Page page, Locator locator, int initialCount) {
        return waitForCountChange(page, locator, initialCount, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for element to be stable (text content stops changing)
     * Useful for auto-calculated fields that update multiple times
     *
     * @param page Playwright page
     * @param locator Element to monitor
     * @param stabilityPeriodMs How long the value must remain unchanged (milliseconds)
     * @param timeoutMs Maximum time to wait in milliseconds
     */
    public static void waitForElementStable(Page page, Locator locator, int stabilityPeriodMs, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for element to be stable for %dms (timeout: %dms)", stabilityPeriodMs, timeoutMs));

        long startTime = System.currentTimeMillis();
        String lastValue = null;
        long lastChangeTime = startTime;

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                String currentValue = locator.textContent().trim();

                if (!currentValue.equals(lastValue)) {
                    // Value changed, reset stability timer
                    lastValue = currentValue;
                    lastChangeTime = System.currentTimeMillis();
                } else {
                    // Value unchanged, check if stable period elapsed
                    if (System.currentTimeMillis() - lastChangeTime >= stabilityPeriodMs) {
                        TestLogger.debug(String.format("Element stable at '%s' after %dms", currentValue, System.currentTimeMillis() - startTime));
                        return;
                    }
                }
            } catch (Exception e) {
                // Element may not be available yet, reset timer
                lastChangeTime = System.currentTimeMillis();
            }
            page.waitForTimeout(POLL_INTERVAL_MS);
        }

        throw new RuntimeException(String.format(
            "Timeout waiting for element to stabilize. Last value: '%s', Timeout: %dms",
            lastValue, timeoutMs
        ));
    }

    /**
     * Wait for element to be stable (default stability period and 30s timeout)
     */
    public static void waitForElementStable(Page page, Locator locator) {
        waitForElementStable(page, locator, DEFAULT_STABILITY_PERIOD_MS, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Generic wait for any condition with custom polling
     *
     * @param <T> Type of value returned by condition
     * @param condition Supplier that provides the value to check
     * @param check Predicate that tests if condition is met
     * @param timeoutMs Maximum time to wait in milliseconds
     * @param errorMessage Error message if timeout occurs
     * @return The value when condition is met
     */
    public static <T> T waitForCondition(Supplier<T> condition, Predicate<T> check, int timeoutMs, String errorMessage) {
        TestLogger.debug(String.format("Waiting for custom condition (timeout: %dms)", timeoutMs));

        long startTime = System.currentTimeMillis();
        T currentValue = null;

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                currentValue = condition.get();
                if (check.test(currentValue)) {
                    TestLogger.debug(String.format("Condition met after %dms", System.currentTimeMillis() - startTime));
                    return currentValue;
                }
            } catch (Exception e) {
                // Continue polling
            }
            try {
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Wait interrupted", e);
            }
        }

        throw new RuntimeException(String.format(
            "%s. Last value: %s, Timeout: %dms",
            errorMessage, currentValue, timeoutMs
        ));
    }

    /**
     * Generic wait for condition (default 30s timeout)
     */
    public static <T> T waitForCondition(Supplier<T> condition, Predicate<T> check, String errorMessage) {
        return waitForCondition(condition, check, DEFAULT_TIMEOUT_MS, errorMessage);
    }

    // ==================== SPECIALIZED WAIT METHODS ====================

    /**
     * Wait for calculation to complete (value stops changing)
     * Commonly used for auto-calculated totals, sums, etc.
     *
     * @param page Playwright page
     * @param locator Element containing the calculated value
     * @param timeoutMs Maximum time to wait in milliseconds
     * @return The final calculated value
     */
    public static String waitForCalculationComplete(Page page, Locator locator, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for calculation to complete (timeout: %dms)", timeoutMs));

        // Wait for element to be stable for 300ms (calculations should settle)
        waitForElementStable(page, locator, DEFAULT_STABILITY_PERIOD_MS, timeoutMs);

        String finalValue = locator.textContent().trim();
        TestLogger.debug(String.format("Calculation complete: '%s'", finalValue));
        return finalValue;
    }

    /**
     * Wait for calculation to complete (default 30s timeout)
     */
    public static String waitForCalculationComplete(Page page, Locator locator) {
        return waitForCalculationComplete(page, locator, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for loading indicator to disappear
     *
     * @param page Playwright page
     * @param loadingSelector Selector for loading indicator (spinner, "Loading..." text, etc.)
     * @param timeoutMs Maximum time to wait in milliseconds
     */
    public static void waitForLoadingComplete(Page page, String loadingSelector, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for loading indicator '%s' to disappear (timeout: %dms)", loadingSelector, timeoutMs));

        try {
            Locator loadingIndicator = page.locator(loadingSelector);
            loadingIndicator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeoutMs));
            TestLogger.debug("Loading complete");
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Timeout waiting for loading indicator '%s' to disappear. Timeout: %dms",
                loadingSelector, timeoutMs
            ), e);
        }
    }

    /**
     * Wait for loading indicator to disappear (default 30s timeout)
     */
    public static void waitForLoadingComplete(Page page, String loadingSelector) {
        waitForLoadingComplete(page, loadingSelector, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for save operation to complete successfully
     * Looks for success indicator (message, icon, etc.)
     *
     * @param page Playwright page
     * @param successIndicator Locator for success indicator
     * @param timeoutMs Maximum time to wait in milliseconds
     */
    public static void waitForSaveComplete(Page page, Locator successIndicator, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for save operation to complete (timeout: %dms)", timeoutMs));

        try {
            successIndicator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMs));
            TestLogger.debug("Save operation completed successfully");
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Timeout waiting for save operation to complete. Timeout: %dms",
                timeoutMs
            ), e);
        }
    }

    /**
     * Wait for save operation to complete (default 30s timeout)
     */
    public static void waitForSaveComplete(Page page, Locator successIndicator) {
        waitForSaveComplete(page, successIndicator, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for new table row to appear after add operation
     *
     * @param page Playwright page
     * @param tableRows Locator matching all table rows
     * @param initialCount The count before adding row
     * @param timeoutMs Maximum time to wait in milliseconds
     * @return The new row count
     */
    public static int waitForNewRow(Page page, Locator tableRows, int initialCount, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for new table row (current: %d, timeout: %dms)", initialCount, timeoutMs));

        int newCount = waitForCountChange(page, tableRows, initialCount, timeoutMs);

        if (newCount <= initialCount) {
            throw new RuntimeException(String.format(
                "Expected row count to increase from %d, but got %d",
                initialCount, newCount
            ));
        }

        TestLogger.debug(String.format("New row added (count: %d → %d)", initialCount, newCount));
        return newCount;
    }

    /**
     * Wait for new table row to appear (default 30s timeout)
     */
    public static int waitForNewRow(Page page, Locator tableRows, int initialCount) {
        return waitForNewRow(page, tableRows, initialCount, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for row to be removed from table
     *
     * @param page Playwright page
     * @param tableRows Locator matching all table rows
     * @param initialCount The count before removing row
     * @param timeoutMs Maximum time to wait in milliseconds
     * @return The new row count
     */
    public static int waitForRowRemoval(Page page, Locator tableRows, int initialCount, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for row removal (current: %d, timeout: %dms)", initialCount, timeoutMs));

        int newCount = waitForCountChange(page, tableRows, initialCount, timeoutMs);

        if (newCount >= initialCount) {
            throw new RuntimeException(String.format(
                "Expected row count to decrease from %d, but got %d",
                initialCount, newCount
            ));
        }

        TestLogger.debug(String.format("Row removed (count: %d → %d)", initialCount, newCount));
        return newCount;
    }

    /**
     * Wait for row to be removed (default 30s timeout)
     */
    public static int waitForRowRemoval(Page page, Locator tableRows, int initialCount) {
        return waitForRowRemoval(page, tableRows, initialCount, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Wait for element to become visible and stable
     * Useful for elements that appear with animation
     *
     * @param page Playwright page
     * @param locator Element to wait for
     * @param timeoutMs Maximum time to wait in milliseconds
     */
    public static void waitForVisibleAndStable(Page page, Locator locator, int timeoutMs) {
        TestLogger.debug(String.format("Waiting for element to be visible and stable (timeout: %dms)", timeoutMs));

        // First wait for visibility
        try {
            locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMs / 2));
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Timeout waiting for element to become visible. Timeout: %dms",
                timeoutMs
            ), e);
        }

        // Then wait for it to stabilize
        waitForElementStable(page, locator, DEFAULT_STABILITY_PERIOD_MS, timeoutMs / 2);
        TestLogger.debug("Element visible and stable");
    }

    /**
     * Wait for element to become visible and stable (default 30s timeout)
     */
    public static void waitForVisibleAndStable(Page page, Locator locator) {
        waitForVisibleAndStable(page, locator, DEFAULT_TIMEOUT_MS);
    }
}
