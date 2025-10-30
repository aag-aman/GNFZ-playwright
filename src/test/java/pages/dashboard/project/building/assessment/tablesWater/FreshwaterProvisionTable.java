package pages.dashboard.project.building.assessment.tablesWater;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;
import utils.AutoStep;
/**
 * FreshwaterProvisionTable - Table H for Freshwater Provision
 *
 * Columns: Description, Unit, Quantity
 */
public class FreshwaterProvisionTable {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='scope1_freshwater_provision_type_%d']";
    private static final String UNIT_INPUT_PATTERN = "input[ftestcaseref='scope1_freshwater_provision_unit_%d']";
    private static final String QUANTITY_INPUT_PATTERN = "input[ftestcaseref='scope1_freshwater_provision_quantity_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope1_Freshwater provision_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope1_Freshwater provision_table_tr_row_upload_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope1_Freshwater provision_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='scope1_freshwater_provision_type_']";

    // Table-level locators
    private final Locator tableTotal;

    // Constructor
    public FreshwaterProvisionTable(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope1_freshwater_provision_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getDescriptionInput(int rowIndex) {
        return page.locator(String.format(TYPE_INPUT_PATTERN, rowIndex));
    }

    private Locator getUnitInput(int rowIndex) {
        return page.locator(String.format(UNIT_INPUT_PATTERN, rowIndex));
    }

    private Locator getQuantityInput(int rowIndex) {
        return page.locator(String.format(QUANTITY_INPUT_PATTERN, rowIndex));
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
    public void enterDescription(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getDescriptionInput(rowIndex), value);
    }

    @AutoStep
    public void enterUnit(int rowIndex, String value) {
        // InputHelper.humanizedInput(page, getUnitInput(rowIndex), value);
    }

    @AutoStep
    public void enterQuantity(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQuantityInput(rowIndex), value);
    }
    

    /**
     * Get values
     */
    @AutoStep
    public String getDescription(int rowIndex) {
        return getDescriptionInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getUnit(int rowIndex) {
        return getUnitInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getQuantity(int rowIndex) {
        return getQuantityInput(rowIndex).inputValue();
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
        // WaitHelper.waitForNewRow(page, getAllRows(), initialCount, 30000);
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
    public void fillRow(int rowIndex, String description, String unit, String quantity) {
        enterDescription(rowIndex, description);
        enterUnit(rowIndex, unit);
        enterQuantity(rowIndex, quantity);
    }
}
