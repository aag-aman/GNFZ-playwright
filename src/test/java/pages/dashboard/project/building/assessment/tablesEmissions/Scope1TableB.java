package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;
import com.microsoft.playwright.Locator;

import utils.AutoStep;
/**
 * Scope1TableB - Table B for Scope 1 Emissions (Refrigerants)
 *
 * Columns: type, emission_factor_(kgco2e), consumption, unit
 */
public class Scope1TableB {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='scope1_refrigerants_type_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope1_refrigerants_emission_factor_(kgco2e)_%d']";
    private static final String CONSUMPTION_INPUT_PATTERN = "input[ftestcaseref='scope1_refrigerants_consumption_%d']";
    private static final String UNIT_SELECT_PATTERN = "select[ftestcaseref='scope1_refrigerants_unit_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope1_refrigerants_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope1_Refrigerants_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope1_Refrigerants_table_tr_row_attach_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope1_Refrigerants_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='scope1_refrigerants_type_']";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope1TableB(Page page) {
        this.page = page;
        // Initialize only table-level locators (table total is shared across all rows)
        this.tableTotal = page.locator("input[ftestcaseref='scope1_refrigerants_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getTypeInput(int rowIndex) {
        return page.locator(String.format(TYPE_INPUT_PATTERN, rowIndex));
    }

    private Locator getEmissionFactorInput(int rowIndex) {
        return page.locator(String.format(EMISSION_FACTOR_INPUT_PATTERN, rowIndex));
    }

    private Locator getConsumptionInput(int rowIndex) {
        return page.locator(String.format(CONSUMPTION_INPUT_PATTERN, rowIndex));
    }

    private Locator getUnitSelect(int rowIndex) {
        return page.locator(String.format(UNIT_SELECT_PATTERN, rowIndex));
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
    public void enterType(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTypeInput(rowIndex), value);
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
    public void selectUnit(int rowIndex, String value) {
        page.waitForLoadState();
        Locator unitSelect = getUnitSelect(rowIndex);
        unitSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        unitSelect.scrollIntoViewIfNeeded();
        unitSelect.selectOption(value);
    }

    /**
     * Get values
     */
    @AutoStep
    public String getType(int rowIndex) {
        return getTypeInput(rowIndex).inputValue();
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
    public String getUnit(int rowIndex) {
        return getUnitSelect(rowIndex).inputValue();
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
    public void fillRow(int rowIndex, String type, String emissionFactor, String consumption, String unit) {
        enterType(rowIndex, type);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterConsumption(rowIndex, consumption);
        selectUnit(rowIndex, unit);
    }
}
