package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;

import utils.AutoStep;
/**
 * Scope3TableR - Table R for Scope 3 Emissions (Reused Materials)
 *
 * Columns: type_of_material, emission_factor_(kgco2e), quantity, units
 */
public class Scope3TableR {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String TYPE_OF_MATERIAL_INPUT_PATTERN = "input[ftestcaseref='scope3_reused_materials_type_of_material_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope3_reused_materials_emission_factor_(kgco2e)_%d']";
    private static final String QUANTITY_INPUT_PATTERN = "input[ftestcaseref='scope3_reused_materials_quantity_%d']";
    private static final String UNITS_SELECT_PATTERN = "select[ftestcaseref='scope3_reused_materials_units_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope3_reused_materials_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope3_Reused_Materials_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope3_Reused_Materials_table_tr_row_attach_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope3_Reused_Materials_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='scope3_reused_materials_type_of_material_']";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope3TableR(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope3_reused_materials_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getTypeOfMaterialInput(int rowIndex) {
        return page.locator(String.format(TYPE_OF_MATERIAL_INPUT_PATTERN, rowIndex));
    }

    private Locator getEmissionFactorInput(int rowIndex) {
        return page.locator(String.format(EMISSION_FACTOR_INPUT_PATTERN, rowIndex));
    }

    private Locator getQuantityInput(int rowIndex) {
        return page.locator(String.format(QUANTITY_INPUT_PATTERN, rowIndex));
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

    private Locator getAllRows() {
        return page.locator(ALL_ROWS_PATTERN);
    }

    /**
     * Enter methods for specific columns
     */
    @AutoStep
    public void enterTypeOfMaterial(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTypeOfMaterialInput(rowIndex), value);
    }

    @AutoStep
    public void enterEmissionFactor(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getEmissionFactorInput(rowIndex), value);
    }
    

    @AutoStep
    public void enterQuantity(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQuantityInput(rowIndex), value);
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
    public String getTypeOfMaterial(int rowIndex) {
        return getTypeOfMaterialInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getEmissionFactor(int rowIndex) {
        return getEmissionFactorInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getQuantity(int rowIndex) {
        return getQuantityInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getRowTotal(int rowIndex) {
        return getRowTotalLocator(rowIndex).inputValue();
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
        Locator addButton = getAddRowButton(currentRowIndex);
        addButton.waitFor();
        addButton.click();
        page.waitForTimeout(1500);
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
    public void fillRow(int rowIndex, String typeOfMaterial, String emissionFactor, String quantity, String units) {
        enterTypeOfMaterial(rowIndex, typeOfMaterial);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterQuantity(rowIndex, quantity);
        selectUnits(rowIndex, units);
    }
}
