package pages.dashboard.project.building.assessment.tablesWaste;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;
import utils.AutoStep;
/**
 * WasteRecycledTable - Table E for Waste Recycled
 *
 * Columns: Type of Waste, Quantity of Waste recycled (Tonnes)
 */
public class WasteRecycledTable {
    protected final Page page;

    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='recycled_type_of_waste_%d']";
    private static final String QUANTITY_INPUT_PATTERN = "input[ftestcaseref='recycled_quantity_of_waste_recycled_(tonnes)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#Recycled_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#Recycled_table_tr_row_upload_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#Recycled_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='recycled_type_of_waste_']";

    private final Locator tableTotal;

    public WasteRecycledTable(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='recycled_total']");
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

    private Locator getAllRows() {
        return page.locator(ALL_ROWS_PATTERN);
    }

    @AutoStep
    public void enterType(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTypeInput(rowIndex), value);
    }

    @AutoStep
    public void enterQuantity(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQuantityInput(rowIndex), value);
    }

    @AutoStep
    public String getType(int rowIndex) {
        return getTypeInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getQuantity(int rowIndex) {
        return getQuantityInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getTableTotal() {
        return this.tableTotal.inputValue();
    }

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

    @AutoStep
    public void fillRow(int rowIndex, String type, String quantity) {
        enterType(rowIndex, type);
        enterQuantity(rowIndex, quantity);
    }
}
