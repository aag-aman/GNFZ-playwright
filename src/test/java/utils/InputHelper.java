package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * InputHelper - Utility class for humanized input operations
 *
 * Provides methods that simulate human-like typing to trigger proper input events,
 * validations, and auto-population behaviors in the application.
 *
 * Why humanized input?
 * - Many form fields have JavaScript listeners that only respond to keyboard events
 * - Using .fill() bypasses these events and won't trigger auto-calculations/validations
 * - pressSequentially() fires proper keyboard events that trigger application logic
 *
 * Example usage:
 * {@code
 * public void enterEmail(String email) {
 *     InputHelper.humanizedInput(page, emailField, email);
 * }
 * }
 */
public class InputHelper {

    /**
     * Standard humanized input with Enter key press
     *
     * Use this for most text input fields, especially those with:
     * - Auto-population based on input
     * - Validation triggers
     * - Calculation dependencies
     *
     * @param page The Playwright Page instance
     * @param locator The input field locator
     * @param value The text to enter
     */
    public static void humanizedInput(Page page, Locator locator, String value) {
        page.waitForLoadState();
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        locator.scrollIntoViewIfNeeded();
        locator.click();
        page.waitForTimeout(100);
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500);
        page.keyboard().press("Enter");
        page.waitForTimeout(1500);
    }

    /**
     * Humanized input without Enter key press
     *
     * Use for fields that don't require Enter key to trigger behavior,
     * or when you want to avoid form submission
     *
     * @param page The Playwright Page instance
     * @param locator The input field locator
     * @param value The text to enter
     */
    public static void humanizedInputNoEnter(Page page, Locator locator, String value) {
        page.waitForLoadState();
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        locator.scrollIntoViewIfNeeded();
        locator.click();
        page.waitForTimeout(100);
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(1500);
    }

    /**
     * Humanized input with custom typing delay
     *
     * Use when you need to adjust the typing speed:
     * - Faster typing: delayMs < 150 (e.g., 50ms)
     * - Slower typing: delayMs > 150 (e.g., 300ms)
     *
     * @param page The Playwright Page instance
     * @param locator The input field locator
     * @param value The text to enter
     * @param delayMs Delay in milliseconds between each keystroke
     */
    public static void humanizedInputCustomDelay(Page page, Locator locator, String value, int delayMs) {
        page.waitForLoadState();
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        locator.scrollIntoViewIfNeeded();
        locator.click();
        page.waitForTimeout(100);
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(delayMs));
        page.waitForTimeout(500);
        page.keyboard().press("Enter");
        page.waitForTimeout(1500);
    }

    /**
     * Humanized input with custom wait times
     *
     * Use when you need fine-grained control over wait times:
     * - initialWait: Wait before typing starts (default 100ms)
     * - afterTypingWait: Wait after typing completes (default 500ms)
     * - finalWait: Wait after Enter key (default 1500ms)
     *
     * @param page The Playwright Page instance
     * @param locator The input field locator
     * @param value The text to enter
     * @param initialWait Wait before typing (ms)
     * @param afterTypingWait Wait after typing (ms)
     * @param finalWait Wait after Enter key (ms)
     */
    public static void humanizedInputCustomWaits(Page page, Locator locator, String value,
                                                   int initialWait, int afterTypingWait, int finalWait) {
        page.waitForLoadState();
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        locator.scrollIntoViewIfNeeded();
        locator.click();
        page.waitForTimeout(initialWait);
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(afterTypingWait);
        page.keyboard().press("Enter");
        page.waitForTimeout(finalWait);
    }

    /**
     * Select date from jQuery UI datepicker
     *
     * Use for date fields that use jQuery UI datepicker widget.
     * Handles the common pattern of clicking the field to open the picker,
     * then selecting month, year, and day.
     *
     * @param page The Playwright Page instance
     * @param dateFieldLocator The date input field locator
     * @param date The date in MM/DD/YYYY format (e.g., "12/25/2024")
     *
     * Example usage:
     * {@code
     * InputHelper.selectDateFromDatepicker(page, startDateField, "01/15/2024");
     * }
     */
    public static void selectDateFromDatepicker(Page page, Locator dateFieldLocator, String date) {
        InputHelper.selectDateFromDatepickerCustomWaits(page, dateFieldLocator, date,
                2000, 2000, 1100);
    }

    /**
     * Select date from jQuery UI datepicker with custom wait times
     *
     * Use when the default wait times in selectDateFromDatepicker() are too short/long
     * for your specific datepicker implementation.
     *
     * @param page The Playwright Page instance
     * @param dateFieldLocator The date input field locator
     * @param date The date in MM/DD/YYYY format (e.g., "12/25/2024")
     * @param initialWait Wait before focusing field (default 2000ms)
     * @param focusToClickWait Wait between focus and click (default 2000ms)
     * @param afterClickWait Wait after clicking field (default 1100ms)
     */
    public static void selectDateFromDatepickerCustomWaits(Page page, Locator dateFieldLocator, String date,
                                                             int initialWait, int focusToClickWait, int afterClickWait) {
        page.waitForLoadState();
        dateFieldLocator.waitFor();

        // Focus and click to trigger datepicker
        page.waitForTimeout(initialWait);
        dateFieldLocator.focus();
        page.waitForTimeout(focusToClickWait);
        dateFieldLocator.click();
        page.waitForTimeout(afterClickWait);

        // Wait for datepicker to appear
        Locator datepicker = page.locator("#ui-datepicker-div");
        datepicker.waitFor();

        // Parse date (format: MM/DD/YYYY)
        String[] dateParts = date.split("/");
        String monthValue = String.valueOf(Integer.parseInt(dateParts[0]) - 1); // Month is 0-indexed (0=Jan, 11=Dec)
        String year = dateParts[2];
        String day = String.valueOf(Integer.parseInt(dateParts[1])); // Remove leading zero

        // Select month from dropdown
        Locator monthDropdown = page.locator(".ui-datepicker-month");
        monthDropdown.selectOption(monthValue);
        page.waitForTimeout(200);

        // Select year from dropdown
        Locator yearDropdown = page.locator(".ui-datepicker-year");
        yearDropdown.selectOption(year);
        page.waitForTimeout(200);

        // Click on the day in the calendar
        Locator dayButton = page.locator(".ui-datepicker-calendar td a");
        dayButton.filter(new Locator.FilterOptions().setHasText(day)).first().click();

        page.waitForTimeout(300);
    }
}
