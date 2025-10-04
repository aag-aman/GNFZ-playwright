package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Scope2TableD - Table D for Scope 2 Emissions (Energy)
 * Columns: activity, emission_factor_(kgco2e), consumption, units
 */
public class Scope2TableD {
    protected final Page page;

    // Locator patterns defined once
    private static final String ACTIVITY_INPUT_PATTERN = "input[ftestcaseref='scope2_energy_activity_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope2_energy_emission_factor_(kgco2e)_%d']";
    private static final String CONSUMPTION_INPUT_PATTERN = "input[ftestcaseref='scope2_energy_consumption_%d']";
    private static final String UNITS_SELECT_PATTERN = "select[ftestcaseref='scope2_energy_units_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope2_energy_total_emissions_(kgco2e)_%d']";

    // Table-level locators
    private final Locator tableTotal;

    public Scope2TableD(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope2_energy_total']");
    }

    // ========================================
    // Helper methods - return locators for dynamic rows
    // ========================================
    private Locator getActivityInput(int rowIndex) {
        return page.locator(String.format(ACTIVITY_INPUT_PATTERN, rowIndex));
    }

    private Locator getEmissionFactorInput(int rowIndex) {
        return page.locator(String.format(EMISSION_FACTOR_INPUT_PATTERN, rowIndex));
    }

    private Locator getConsumptionInput(int rowIndex) {
        return page.locator(String.format(CONSUMPTION_INPUT_PATTERN, rowIndex));
    }

    private Locator getUnitsSelect(int rowIndex) {
        return page.locator(String.format(UNITS_SELECT_PATTERN, rowIndex));
    }

    private Locator getRowTotalLocator(int rowIndex) {
        return page.locator(String.format(ROW_TOTAL_PATTERN, rowIndex));
    }

    // ========================================
    // Public action methods (slower inputs with more wait time)
    // ========================================
    public void enterActivity(int rowIndex, String value) {
        page.waitForLoadState();
        Locator activityInput = getActivityInput(rowIndex);
        activityInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        activityInput.scrollIntoViewIfNeeded();

        // Slower input - type character by character
        activityInput.click();
        activityInput.clear();
        activityInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(50));

        // Defocus and wait longer
        activityInput.blur();
        page.waitForTimeout(1500); // Longer wait for auto-population
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        Locator emissionFactorInput = getEmissionFactorInput(rowIndex);
        emissionFactorInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        emissionFactorInput.scrollIntoViewIfNeeded();

        // Slower input
        emissionFactorInput.click();
        emissionFactorInput.clear();
        emissionFactorInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(50));

        // Defocus and wait longer
        emissionFactorInput.blur();
        page.waitForTimeout(1500);
    }

    public void enterConsumption(int rowIndex, String value) {
        page.waitForLoadState();
        Locator consumptionInput = getConsumptionInput(rowIndex);
        consumptionInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        consumptionInput.scrollIntoViewIfNeeded();

        // Slower input
        consumptionInput.click();
        consumptionInput.clear();
        consumptionInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(50));

        // Defocus and wait longer for calculation
        consumptionInput.blur();
        page.waitForTimeout(1500);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        Locator unitsSelect = getUnitsSelect(rowIndex);
        unitsSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        unitsSelect.scrollIntoViewIfNeeded();
        unitsSelect.selectOption(value);
        page.waitForTimeout(500);
    }

    // ========================================
    // Public getter methods
    // ========================================
    public String getActivity(int rowIndex) {
        return getActivityInput(rowIndex).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return getEmissionFactorInput(rowIndex).inputValue();
    }

    public String getConsumption(int rowIndex) {
        return getConsumptionInput(rowIndex).inputValue();
    }

    public String getUnits(int rowIndex) {
        return getUnitsSelect(rowIndex).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return getRowTotalLocator(rowIndex).inputValue();
    }

    public String getTableTotal() {
        return tableTotal.inputValue();
    }

    // ========================================
    // Row operations
    // ========================================
    public void addRow() {
        page.locator("[ftestcaseref='scope2_energy_add']").click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("[ftestcaseref='scope2_energy_remove_%d']", rowIndex)).click();
    }

    public void attach() {
        page.locator("[ftestcaseref='scope2_energy_attach']").click();
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
}
