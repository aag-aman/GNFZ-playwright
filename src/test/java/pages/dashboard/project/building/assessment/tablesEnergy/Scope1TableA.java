package pages.dashboard.project.building.assessment.tablesEnergy;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

/**
 * Scope1TableA - Table A for Scope 1 Energy (Fuels)
 *
 * Columns: fuel, emission_factor_(kgco2e), consumption, units
 */
public class Scope1TableA {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String FUEL_INPUT_PATTERN = "input[ftestcaseref='scope1_fuels_fuel_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope1_fuels_emission_factor_(kgco2e)_%d']";
    private static final String CONSUMPTION_INPUT_PATTERN = "input[ftestcaseref='scope1_fuels_consumption_%d']";
    private static final String UNITS_SELECT_PATTERN = "select[ftestcaseref='scope1_fuels_units_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope1_fuels_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope1_Fuels_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope1_Fuels_table_tr_row_attach_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope1_Fuels_table_tr_row_trash_%d";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope1TableA(Page page) {
        this.page = page;
        // Initialize only table-level locators (table total is shared across all rows)
        this.tableTotal = page.locator("input[ftestcaseref='scope1_fuels_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getFuelInput(int rowIndex) {
        return page.locator(String.format(FUEL_INPUT_PATTERN, rowIndex));
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

    private Locator getAddRowButton(int rowIndex) {
        return page.locator(String.format(ADD_ROW_BUTTON_PATTERN, rowIndex));
    }

    private Locator getAttachButton(int rowIndex) {
        return page.locator(String.format(ATTACH_BUTTON_PATTERN, rowIndex));
    }

    private Locator getRemoveRowButton(int rowIndex) {
        return page.locator(String.format(REMOVE_ROW_BUTTON_PATTERN, rowIndex));
    }

    /**
     * Enter methods for specific columns
     */
    public void enterFuel(int rowIndex, String value) {
        page.waitForLoadState();
        Locator fuelInput = getFuelInput(rowIndex);
        // Wait for element to be attached to DOM (less strict than visible)
        fuelInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        // Scroll into view if needed
        fuelInput.scrollIntoViewIfNeeded();
        // Click to focus first
        fuelInput.click();
        page.waitForTimeout(100);
        // Enter value human-like with delays (increased to 150ms for better auto-population trigger)
        fuelInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500);
        // Press Enter to trigger calculation or blur to defocus
        page.keyboard().press("Enter");
        // Wait longer for emission factor auto-population to complete
        page.waitForTimeout(1500);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        Locator emissionFactorInput = getEmissionFactorInput(rowIndex);
        emissionFactorInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        emissionFactorInput.scrollIntoViewIfNeeded();
        emissionFactorInput.fill(value);

        // Defocus the field to trigger any validation/calculation
        emissionFactorInput.blur();
        page.waitForTimeout(500);
    }

    public void enterConsumption(int rowIndex, String value) {
        page.waitForLoadState();
        Locator consumptionInput = getConsumptionInput(rowIndex);
        consumptionInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        consumptionInput.scrollIntoViewIfNeeded();
        consumptionInput.fill(value);
        consumptionInput.blur();
        page.waitForTimeout(1500);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        Locator unitsSelect = getUnitsSelect(rowIndex);
        unitsSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        unitsSelect.scrollIntoViewIfNeeded();
        unitsSelect.selectOption(value);
    }

    /**
     * Get values
     */
    public String getFuel(int rowIndex) {
        return getFuelInput(rowIndex).inputValue();
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
        return this.tableTotal.inputValue();
    }

    /**
     * Row operations
     */
    public void addRow(int currentRowIndex) {
        page.waitForLoadState();
        Locator addButton = getAddRowButton(currentRowIndex);
        addButton.waitFor();
        addButton.click();
        page.waitForTimeout(500); // Wait for new row to be added
    }

    public void removeRow(int rowIndex) {
        page.waitForLoadState();
        Locator removeButton = getRemoveRowButton(rowIndex);
        removeButton.waitFor();
        removeButton.click();
    }

    public void attach(int rowIndex) {
        page.waitForLoadState();
        Locator attachButton = getAttachButton(rowIndex);
        attachButton.waitFor();
        attachButton.click();
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String fuel, String emissionFactor, String consumption, String units) {
        enterFuel(rowIndex, fuel);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterConsumption(rowIndex, consumption);
        selectUnits(rowIndex, units);
    }
}
