package pages.dashboard.project.building.assessment.tablesWater;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;
import utils.AutoStep;
/**
 * ConsumptionPotableTable - Table A for Water Consumption (Potable)
 *
 * Columns: Type, Source, Quality, Quantity(kLd), Avg/Peak, No. of Days, KL/ annum
 */
public class ConsumptionPotableTable {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='scope1_potable_type_%d']";
    private static final String SOURCE_INPUT_PATTERN = "input[ftestcaseref='scope1_potable_source_%d']";
    private static final String QUALITY_INPUT_PATTERN = "input[ftestcaseref='scope1_potable_quality_%d']";
    private static final String QUANTITY_INPUT_PATTERN = "input[ftestcaseref='scope1_potable_quantity(kld)_%d']";
    private static final String AVG_PEAK_INPUT_PATTERN = "input[ftestcaseref='scope1_potable_avg/peak_%d']";
    private static final String NO_OF_DAYS_INPUT_PATTERN = "input[ftestcaseref='scope1_potable_no._of_days_%d']";
    private static final String KL_ANNUM_INPUT_PATTERN = "input[ftestcaseref='scope1_potable_kl/_annum_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope1_Potable_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope1_Potable_table_tr_row_upload_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope1_Potable_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='scope1_potable_type_']";

    // Table-level locators
    private final Locator tableTotal;

    // Constructor
    public ConsumptionPotableTable(Page page) {
        this.page = page;
        // Initialize only table-level locators (table total is shared across all rows)
        this.tableTotal = page.locator("input[ftestcaseref='scope1_potable_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getTypeInput(int rowIndex) {
        return page.locator(String.format(TYPE_INPUT_PATTERN, rowIndex));
    }

    private Locator getSourceInput(int rowIndex) {
        return page.locator(String.format(SOURCE_INPUT_PATTERN, rowIndex));
    }

    private Locator getQualityInput(int rowIndex) {
        return page.locator(String.format(QUALITY_INPUT_PATTERN, rowIndex));
    }

    private Locator getQuantityInput(int rowIndex) {
        return page.locator(String.format(QUANTITY_INPUT_PATTERN, rowIndex));
    }

    private Locator getAvgPeakInput(int rowIndex) {
        return page.locator(String.format(AVG_PEAK_INPUT_PATTERN, rowIndex));
    }

    private Locator getNoOfDaysInput(int rowIndex) {
        return page.locator(String.format(NO_OF_DAYS_INPUT_PATTERN, rowIndex));
    }

    private Locator getKlAnnumInput(int rowIndex) {
        return page.locator(String.format(KL_ANNUM_INPUT_PATTERN, rowIndex));
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
    public void enterSource(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getSourceInput(rowIndex), value);
    }

    @AutoStep
    public void enterQuality(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQualityInput(rowIndex), value);
    }

    @AutoStep
    public void enterQuantity(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQuantityInput(rowIndex), value);
    }

    @AutoStep
    public void enterAvgPeak(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getAvgPeakInput(rowIndex), value);
    }


    @AutoStep
    public void enterNoOfDays(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getNoOfDaysInput(rowIndex), value);
    }


    /**
     * Get values
     */
    @AutoStep
    public String getType(int rowIndex) {
        return getTypeInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getSource(int rowIndex) {
        return getSourceInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getQuality(int rowIndex) {
        return getQualityInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getQuantity(int rowIndex) {
        return getQuantityInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getAvgPeak(int rowIndex) {
        return getAvgPeakInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getNoOfDays(int rowIndex) {
        return getNoOfDaysInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getKlAnnum(int rowIndex) {
        return getKlAnnumInput(rowIndex).inputValue();
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
    public void fillRow(int rowIndex, String type, String source, String quality, String quantity, String avgPeak, String noOfDays) {
        enterType(rowIndex, type);
        enterSource(rowIndex, source);
        enterQuality(rowIndex, quality);
        enterQuantity(rowIndex, quantity);
        enterAvgPeak(rowIndex, avgPeak);
        enterNoOfDays(rowIndex, noOfDays);
    }
}
