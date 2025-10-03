package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope2TableD - Table D for Scope 2 Emissions (Energy)
 * Columns: activity, emission_factor_(kgco2e), consumption, units
 */
public class Scope2TableD {
    protected final Page page;
    protected final String tablePrefix;

    public Scope2TableD(Page page) {
        this.page = page;
        this.tablePrefix = "scope2_energy";
    }

    /**
     * Generic method to enter data in any field
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
     * Convenience methods for specific columns
     */
    public void enterActivity(int rowIndex, String value) {
        enterField("activity", rowIndex, value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        enterField("emission_factor_(kgco2e)", rowIndex, value);
    }

    public void enterConsumption(int rowIndex, String value) {
        enterField("consumption", rowIndex, value);
    }

    public void selectUnits(int rowIndex, String value) {
        selectField("units", rowIndex, value);
    }

    /**
     * Get values
     */
    public String getActivity(int rowIndex) {
        return getField("activity", rowIndex);
    }

    public String getEmissionFactor(int rowIndex) {
        return getField("emission_factor_(kgco2e)", rowIndex);
    }

    public String getConsumption(int rowIndex) {
        return getField("consumption", rowIndex);
    }

    public String getRowTotal(int rowIndex) {
        return page.locator(buildFieldSelector("total_emissions_(kgco2e)", rowIndex)).textContent();
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
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String activity, String emissionFactor, String consumption, String units) {
        enterActivity(rowIndex, activity);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterConsumption(rowIndex, consumption);
        selectUnits(rowIndex, units);
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
