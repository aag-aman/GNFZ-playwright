package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Scope3Table - Base class for Scope 3 Emissions tables (E through R)
 * Can be used directly or extended for table-specific columns
 *
 * Usage:
 * - Scope3Table tableE = new Scope3Table(page, "tablee");
 * - tableE.enterField("fuel", 0, "value");
 */
public class Scope3Table {
    protected final Page page;
    protected final String tablePrefix;

    /**
     * @param page Playwright Page object
     * @param tableName Table name (e.g., "tablee", "tablef", "tableg")
     */
    public Scope3Table(Page page, String tableName) {
        this.page = page;
        this.tablePrefix = "scope3_" + tableName.toLowerCase();
    }

    /**
     * Generic method to enter data in any field
     * @param field Field name (e.g., "fuel", "emissionfactor", "consumption")
     * @param rowIndex Row index (0, 1, 2, ...)
     * @param value Value to enter
     */
    public void enterField(String field, int rowIndex, String value) {
        page.locator(buildFieldSelector(field, rowIndex)).fill(value);
    }

    /**
     * Generic method to select from dropdown
     */
    public void selectField(String field, int rowIndex, String value) {
        page.locator(buildFieldSelector(field, rowIndex)).selectOption(value);
    }

    /**
     * Get field value
     */
    public String getField(String field, int rowIndex) {
        return page.locator(buildFieldSelector(field, rowIndex)).inputValue();
    }

    /**
     * Get values
     */
    public String getRowTotal(int rowIndex) {
        return page.locator(buildFieldSelector("rowtotal", rowIndex)).textContent();
    }

    public String getTableTotal() {
        return page.locator(buildActionSelector("total")).textContent();
    }

    /**
     * Row operations
     */
    public void addRow() {
        page.locator(buildActionSelector("add")).click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("[ftestcaseref='%s_remove_%d']", tablePrefix, rowIndex)).click();
    }

    public void attach() {
        page.locator(buildActionSelector("attach")).click();
    }

    /**
     * Get row count - counts elements with table prefix ending in _0 (first row indicators)
     */
    public int getRowCount() {
        return page.locator(String.format("[ftestcaseref^='%s_'][ftestcaseref$='_0']", tablePrefix)).count();
    }

    /**
     * Humanlike input methods - with delays to trigger auto-calculations
     */
    protected void enterFieldHumanlike(String field, int rowIndex, String value) {
        page.locator(buildFieldSelector(field, rowIndex)).click();
        page.locator(buildFieldSelector(field, rowIndex)).pressSequentially(value, new com.microsoft.playwright.Locator.PressSequentiallyOptions().setDelay(100));
        page.waitForTimeout(300);
    }

    protected void selectFieldHumanlike(String field, int rowIndex, String value) {
        page.locator(buildFieldSelector(field, rowIndex)).click();
        page.waitForTimeout(200);
        page.locator(buildFieldSelector(field, rowIndex)).selectOption(value);
        page.waitForTimeout(300);
    }

    protected void enterAutocomplete(String field, int rowIndex, String value) {
        page.locator(buildFieldSelector(field, rowIndex)).click();
        page.locator(buildFieldSelector(field, rowIndex)).pressSequentially(value, new com.microsoft.playwright.Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500); // Wait for autocomplete suggestions
        page.keyboard().press("Enter");
        page.waitForTimeout(300);
    }

    /**
     * Helper methods to build selectors
     */
    protected String buildFieldSelector(String field, int rowIndex) {
        return String.format("[ftestcaseref='%s_%s_%d']", tablePrefix, field, rowIndex);
    }

    protected String buildActionSelector(String action) {
        return String.format("[ftestcaseref='%s_%s']", tablePrefix, action);
    }
}
