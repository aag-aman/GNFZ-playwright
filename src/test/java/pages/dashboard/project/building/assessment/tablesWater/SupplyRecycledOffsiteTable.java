package pages.dashboard.project.building.assessment.tablesWater;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;

/**
 * SupplyRecycledOffsiteTable - Table D for Water Supply (Recycled off-site)
 *
 * Columns: Type, Source, Quality, Quantity(kLd), Avg/Peak, No. of Days, KL/ annum
 */
public class SupplyRecycledOffsiteTable {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='scope1_recycled_off-site_type_%d']";
    private static final String SOURCE_INPUT_PATTERN = "input[ftestcaseref='scope1_recycled_off-site_source_%d']";
    private static final String QUALITY_INPUT_PATTERN = "input[ftestcaseref='scope1_recycled_off-site_quality_%d']";
    private static final String QUANTITY_INPUT_PATTERN = "input[ftestcaseref='scope1_recycled_off-site_quantity(kld)_%d']";
    private static final String AVG_PEAK_INPUT_PATTERN = "input[ftestcaseref='scope1_recycled_off-site_avg/peak_%d']";
    private static final String NO_OF_DAYS_INPUT_PATTERN = "input[ftestcaseref='scope1_recycled_off-site_no._of_days_%d']";
    private static final String KL_ANNUM_INPUT_PATTERN = "input[ftestcaseref='scope1_recycled_off-site_kl/_annum_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope1_Recycled off-site_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope1_Recycled off-site_table_tr_row_upload_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope1_Recycled off-site_table_tr_row_trash_%d";

    // Table-level locators
    private final Locator tableTotal;

    // Constructor
    public SupplyRecycledOffsiteTable(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope1_recycled_off-site_total']");
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

    /**
     * Enter methods for specific columns
     */
    public void enterType(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTypeInput(rowIndex), value);
    }

    public void enterSource(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getSourceInput(rowIndex), value);
    }

    public void enterQuality(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQualityInput(rowIndex), value);
    }

    public void enterQuantity(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getQuantityInput(rowIndex), value);
    }

    public void enterAvgPeak(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getAvgPeakInput(rowIndex), value);
    }

    public void enterNoOfDays(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getNoOfDaysInput(rowIndex), value);
    }

    /**
     * Get values
     */
    public String getType(int rowIndex) {
        return getTypeInput(rowIndex).inputValue();
    }

    public String getSource(int rowIndex) {
        return getSourceInput(rowIndex).inputValue();
    }

    public String getQuality(int rowIndex) {
        return getQualityInput(rowIndex).inputValue();
    }

    public String getQuantity(int rowIndex) {
        return getQuantityInput(rowIndex).inputValue();
    }

    public String getAvgPeak(int rowIndex) {
        return getAvgPeakInput(rowIndex).inputValue();
    }

    public String getNoOfDays(int rowIndex) {
        return getNoOfDaysInput(rowIndex).inputValue();
    }

    public String getKlAnnum(int rowIndex) {
        return getKlAnnumInput(rowIndex).inputValue();
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
    public void fillRow(int rowIndex, String type, String source, String quality, String quantity, String avgPeak, String noOfDays) {
        enterType(rowIndex, type);
        enterSource(rowIndex, source);
        enterQuality(rowIndex, quality);
        enterQuantity(rowIndex, quantity);
        enterAvgPeak(rowIndex, avgPeak);
        enterNoOfDays(rowIndex, noOfDays);
    }
}
