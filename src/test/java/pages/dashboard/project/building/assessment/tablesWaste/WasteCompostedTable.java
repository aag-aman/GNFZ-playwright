package pages.dashboard.project.building.assessment.tablesWaste;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;

/**
 * WasteCompostedTable - Table D for Waste Composted
 *
 * Columns: Type of Waste, Quantity of Waste Composted(Tonnes)
 */
public class WasteCompostedTable {
    protected final Page page;

    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='composted_type_of_waste_%d']";
    private static final String QUANTITY_INPUT_PATTERN = "input[ftestcaseref='composted_quantity_of_waste_composted(tonnes)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#Composted_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#Composted_table_tr_row_upload_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#Composted_table_tr_row_trash_%d";

    private final Locator tableTotal;

    public WasteCompostedTable(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='composted_total']");
    }

    private Locator getTypeInput(int rowIndex) {
        return page.locator(String.format(TYPE_INPUT_PATTERN, rowIndex));
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

    public void enterType(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTypeInput(rowIndex), value);
    }

    public void enterQuantity(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQuantityInput(rowIndex), value);
    }

    public String getType(int rowIndex) {
        return getTypeInput(rowIndex).inputValue();
    }

    public String getQuantity(int rowIndex) {
        return getQuantityInput(rowIndex).inputValue();
    }

    public String getTableTotal() {
        return this.tableTotal.inputValue();
    }

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

    public void fillRow(int rowIndex, String type, String quantity) {
        enterType(rowIndex, type);
        enterQuantity(rowIndex, quantity);
    }
}
