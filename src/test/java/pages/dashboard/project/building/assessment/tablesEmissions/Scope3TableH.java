package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Scope3TableH - Table H for Scope 3 Emissions (Waste Recycled)
 *
 * Columns: type_of_waste, emission_factor_(kgco2e), quantity_of_waste_recycled, unit
 */
public class Scope3TableH {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String TYPE_OF_WASTE_INPUT_PATTERN = "input[ftestcaseref='scope3_waste_recycled_type_of_waste_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope3_waste_recycled_emission_factor_(kgco2e)_%d']";
    private static final String QUANTITY_RECYCLED_INPUT_PATTERN = "input[ftestcaseref='scope3_waste_recycled_quantity_of_waste_recycled_%d']";
    private static final String UNIT_SELECT_PATTERN = "select[ftestcaseref='scope3_waste_recycled_unit_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope3_waste_recycled_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope3_WasteRecycled_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope3_WasteRecycled_table_tr_row_attach_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope3_WasteRecycled_table_tr_row_trash_%d";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope3TableH(Page page) {
        this.page = page;
        // Initialize only table-level locators (table total is shared across all rows)
        this.tableTotal = page.locator("input[ftestcaseref='scope3_waste_recycled_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getTypeOfWasteInput(int rowIndex) {
        return page.locator(String.format(TYPE_OF_WASTE_INPUT_PATTERN, rowIndex));
    }

    private Locator getEmissionFactorInput(int rowIndex) {
        return page.locator(String.format(EMISSION_FACTOR_INPUT_PATTERN, rowIndex));
    }

    private Locator getQuantityRecycledInput(int rowIndex) {
        return page.locator(String.format(QUANTITY_RECYCLED_INPUT_PATTERN, rowIndex));
    }

    private Locator getUnitSelect(int rowIndex) {
        return page.locator(String.format(UNIT_SELECT_PATTERN, rowIndex));
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
    public void enterTypeOfWaste(int rowIndex, String value) {
        page.waitForLoadState();
        Locator typeOfWasteInput = getTypeOfWasteInput(rowIndex);
        typeOfWasteInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        typeOfWasteInput.scrollIntoViewIfNeeded();

        // Slower input - type character by character
        typeOfWasteInput.click();
        typeOfWasteInput.clear();
        typeOfWasteInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(50));

        // Defocus and wait longer
        typeOfWasteInput.blur();
        page.waitForTimeout(1500);
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

    public void enterQuantityOfWasteRecycled(int rowIndex, String value) {
        page.waitForLoadState();
        Locator quantityRecycledInput = getQuantityRecycledInput(rowIndex);
        quantityRecycledInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        quantityRecycledInput.scrollIntoViewIfNeeded();

        // Slower input
        quantityRecycledInput.click();
        quantityRecycledInput.clear();
        quantityRecycledInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(50));

        // Defocus and wait longer for calculation
        quantityRecycledInput.blur();
        page.waitForTimeout(1500);
    }

    public void selectUnit(int rowIndex, String value) {
        page.waitForLoadState();
        Locator unitSelect = getUnitSelect(rowIndex);
        unitSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        unitSelect.scrollIntoViewIfNeeded();
        unitSelect.selectOption(value);
        page.waitForTimeout(500);
    }

    /**
     * Get values
     */
    public String getTypeOfWaste(int rowIndex) {
        return getTypeOfWasteInput(rowIndex).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return getEmissionFactorInput(rowIndex).inputValue();
    }

    public String getQuantityOfWasteRecycled(int rowIndex) {
        return getQuantityRecycledInput(rowIndex).inputValue();
    }

    public String getUnit(int rowIndex) {
        return getUnitSelect(rowIndex).inputValue();
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
    public void fillRow(int rowIndex, String typeOfWaste, String emissionFactor, String quantityRecycled, String unit) {
        enterTypeOfWaste(rowIndex, typeOfWaste);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterQuantityOfWasteRecycled(rowIndex, quantityRecycled);
        selectUnit(rowIndex, unit);
    }
}
