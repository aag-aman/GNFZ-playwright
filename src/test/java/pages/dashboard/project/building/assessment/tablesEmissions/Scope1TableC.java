package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;
import com.microsoft.playwright.Locator;

import utils.AutoStep;
/**
 * Scope1TableC - Table C for Scope 1 Emissions (Mobile Combustion)
 *
 * Columns: fuel, emission_factor_(kgco2e), consumption, units
 */
public class Scope1TableC {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String FUEL_INPUT_PATTERN = "input[ftestcaseref='scope1_mobile_combustion_fuel_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope1_mobile_combustion_emission_factor_(kgco2e)_%d']";
    private static final String CONSUMPTION_INPUT_PATTERN = "input[ftestcaseref='scope1_mobile_combustion_consumption_%d']";
    private static final String UNITS_SELECT_PATTERN = "select[ftestcaseref='scope1_mobile_combustion_units_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope1_mobile_combustion_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope1_MobileCombustion_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope1_MobileCombustion_table_tr_row_attach_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope1_MobileCombustion_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='scope1_mobile_combustion_fuel_']";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope1TableC(Page page) {
        this.page = page;
        // Initialize only table-level locators (table total is shared across all rows)
        this.tableTotal = page.locator("input[ftestcaseref='scope1_mobile_combustion_total']");
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

    @SuppressWarnings("unused")
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

    private Locator getAllRows() {
        return page.locator(ALL_ROWS_PATTERN);
    }

    /**
     * Enter methods for specific columns
     */
    @AutoStep
    public void enterFuel(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getFuelInput(rowIndex), value);
    }

    @AutoStep
    public void enterEmissionFactor(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getEmissionFactorInput(rowIndex), value);
    }

    @AutoStep
    public void enterConsumption(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getConsumptionInput(rowIndex), value);
    }


    @AutoStep
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
    @AutoStep
    public String getFuel(int rowIndex) {
        return getFuelInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getEmissionFactor(int rowIndex) {
        return getEmissionFactorInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getConsumption(int rowIndex) {
        return getConsumptionInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getUnits(int rowIndex) {
        return getUnitsSelect(rowIndex).inputValue();
    }

    @AutoStep
    public String getRowTotal(int rowIndex) {
        return page.locator(String.format(ROW_TOTAL_PATTERN, rowIndex)).inputValue();
    }

    @AutoStep
    public String getTableTotal() {
        return this.tableTotal.inputValue();
    }

    /**
     * Row operations
     */
    @AutoStep
    public void addRow(int currentRowIndex) {
        page.waitForLoadState();
        int initialCount = getAllRows().count();
        Locator addButton = getAddRowButton(currentRowIndex);
        addButton.waitFor();
        addButton.click();
        // WaitHelper.waitForNewRow(page, getAllRows(), initialCount, 30000); // Wait for new row to be added
    }

    @AutoStep
    public void removeRow(int rowIndex) {
        page.waitForLoadState();
        Locator removeButton = getRemoveRowButton(rowIndex);
        removeButton.waitFor();
        removeButton.click();
    }

    @AutoStep
    public void attach(int rowIndex) {
        page.waitForLoadState();
        Locator attachButton = getAttachButton(rowIndex);
        attachButton.waitFor();
        attachButton.click();
    }

    /**
     * Fill entire row at once
     */
    @AutoStep
    public void fillRow(int rowIndex, String fuel, String emissionFactor, String consumption, String units) {
        enterFuel(rowIndex, fuel);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterConsumption(rowIndex, consumption);
        selectUnits(rowIndex, units);
    }
}
