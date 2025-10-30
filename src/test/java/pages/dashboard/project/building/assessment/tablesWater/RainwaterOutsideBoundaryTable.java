package pages.dashboard.project.building.assessment.tablesWater;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;
import utils.AutoStep;
/**
 * RainwaterOutsideBoundaryTable - Table G for Rainwater (Run-off:outside project boundary)
 *
 * Columns: Type, Source, Quality, Quantity, Unit, Avg/Peak, No. of Days, KL/ annum
 */
public class RainwaterOutsideBoundaryTable {
    protected final Page page;

    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_type_%d']";
    private static final String SOURCE_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_source_%d']";
    private static final String QUALITY_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_quality_%d']";
    private static final String QUANTITY_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_quantity_%d']";
    private static final String UNIT_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_unit_%d']";
    private static final String AVG_PEAK_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_avg/peak_%d']";
    private static final String NO_OF_DAYS_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_no._of_days_%d']";
    private static final String KL_ANNUM_INPUT_PATTERN = "input[ftestcaseref='scope1_run-off:outside_project_boundary_kl/_annum_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope1_Run-off:outside project boundary_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope1_Run-off:outside project boundary_table_tr_row_upload_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope1_Run-off:outside project boundary_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='scope1_run-off:outside_project_boundary_type_']";

    private final Locator tableTotal;

    public RainwaterOutsideBoundaryTable(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope1_run-off:outside_project_boundary_total']");
    }

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

    private Locator getUnitInput(int rowIndex) {
        return page.locator(String.format(UNIT_INPUT_PATTERN, rowIndex));
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
    public void enterUnit(int rowIndex, String value) {
        // InputHelper.humanizedInput(page, getUnitInput(rowIndex), value);
    }


    @AutoStep
    public void enterAvgPeak(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getAvgPeakInput(rowIndex), value);
    }


    @AutoStep
    public void enterNoOfDays(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getNoOfDaysInput(rowIndex), value);
    }


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
    public String getUnit(int rowIndex) {
        return getUnitInput(rowIndex).inputValue();
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
    public void fillRow(int rowIndex, String type, String source, String quality, String quantity, String unit, String avgPeak, String noOfDays) {
        enterType(rowIndex, type);
        enterSource(rowIndex, source);
        enterQuality(rowIndex, quality);
        enterQuantity(rowIndex, quantity);
        enterUnit(rowIndex, unit);
        enterAvgPeak(rowIndex, avgPeak);
        enterNoOfDays(rowIndex, noOfDays);
    }
}
