package pages.dashboard.project.building.assessment.tablesWater;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;

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

    /**
     * Enter methods for specific columns
     */
    public void enterDescription(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getDescriptionInput(rowIndex), value);
    }

    public void enterUnit(int rowIndex, String value) {
        // InputHelper.humanizedInput(page, getUnitInput(rowIndex), value);
    }

    public void enterQuantity(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQuantityInput(rowIndex), value);
    }
    

    /**
     * Get values
     */
    public String getDescription(int rowIndex) {
        return getDescriptionInput(rowIndex).inputValue();
    }

    public String getUnit(int rowIndex) {
        return getUnitInput(rowIndex).inputValue();
    }

    public String getQuantity(int rowIndex) {
        return getQuantityInput(rowIndex).inputValue();
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
        page.waitForTimeout(500);
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
    public void fillRow(int rowIndex, String description, String unit, String quantity) {
        enterDescription(rowIndex, description);
        enterUnit(rowIndex, unit);
        enterQuantity(rowIndex, quantity);
    }
}
