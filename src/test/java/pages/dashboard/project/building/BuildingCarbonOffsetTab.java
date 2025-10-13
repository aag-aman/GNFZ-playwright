package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import utils.InputHelper;

/**
 * BuildingCarbonOffsetTab - Carbon Offset tab for Building project
 * This tab tracks planned and actual carbon offset emissions to offset the building's emissions
 */
public class BuildingCarbonOffsetTab {
    private final Page page;

    // Main content
    private final Locator tabContent;
    private final Locator instructionText;
    private final Locator carbonOffsetTable;

    // Table header tooltips
    private final Locator plannedTooltipIcon;
    private final Locator plannedTooltipText;
    private final Locator cumulativeTooltipIcon;
    private final Locator cumulativeTooltipText;

    // Table total row
    private final Locator totalLabel;
    private final Locator totalCumulativeInput;

    // Actions
    private final Locator saveButton;

    // Row patterns (dynamic)
    private static final String ROW_PATTERN = "#records_%d";
    private static final String DATE_INPUT_PATTERN = "input.carbon-offset-datepicker[data-index='%d']";
    private static final String PLANNED_INPUT_PATTERN = "#carbon_offset_planned_%d";
    private static final String ACTUAL_INPUT_PATTERN = "#carbon_offset_actual_%d";
    private static final String CUMULATIVE_INPUT_PATTERN = "#plan_and_targets_carbon_offset #records_%d td:nth-child(4) input[readonly]";
    private static final String ATTACH_ICON_PATTERN = "#records_%d a i.bi-paperclip";
    private static final String STATUS_SELECT_PATTERN = "#gnfz-carbon-offset-status-%d";
    private static final String CHAT_ICON_PATTERN = "#records_%d img[alt='iconPreview']";
    private static final String REMOVE_ROW_PATTERN = "#records_%d i.bi-trash";
    private static final String ADD_ROW_PATTERN = "#records_%d i.bi-plus-square";

    public BuildingCarbonOffsetTab(Page page) {
        this.page = page;

        // Initialize locators
        this.tabContent = page.locator(".tab5.page-section.tab-container");
        this.instructionText = page.locator("div.text-dark.label-text.font-weight-600");
        this.carbonOffsetTable = page.locator("#plan_and_targets_carbon_offset");

        // Table header tooltips
        this.plannedTooltipIcon = page.locator("th:has-text('Planned') span.tooltipp i.bi-info-circle");
        this.plannedTooltipText = page.locator("span.tooltiptext.planned-tooltip");
        this.cumulativeTooltipIcon = page.locator("td:has-text('Cumulative Total') + td span.tooltipp i.bi-info-circle");
        this.cumulativeTooltipText = page.locator("span.tooltiptext.cummulative-tooltip");

        // Table total row
        this.totalLabel = page.locator("#plan_and_targets_carbon_offset tr.border-bottom-white td:has-text('Total')");
        this.totalCumulativeInput = page.locator("#plan_and_targets_carbon_offset tr.border-bottom-white td:nth-child(2) input[readonly]");

        // Actions
        this.saveButton = page.locator("#plan_and_targets_carbon_offset button#gnfz-save");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return tabContent.isVisible();
    }

    public boolean isCarbonOffsetTableVisible() {
        page.waitForLoadState();
        return carbonOffsetTable.isVisible();
    }

    /**
     * Get instruction text
     */
    public String getInstructionText() {
        page.waitForLoadState();
        return instructionText.textContent().trim();
    }

    /**
     * Tooltip interactions
     */
    public void hoverOverPlannedTooltip() {
        page.waitForLoadState();
        plannedTooltipIcon.hover();
        page.waitForTimeout(300);
    }

    public String getPlannedTooltipText() {
        page.waitForLoadState();
        hoverOverPlannedTooltip();
        return plannedTooltipText.textContent().trim();
    }

    public void hoverOverCumulativeTooltip() {
        page.waitForLoadState();
        cumulativeTooltipIcon.hover();
        page.waitForTimeout(300);
    }

    public String getCumulativeTooltipText() {
        page.waitForLoadState();
        hoverOverCumulativeTooltip();
        return cumulativeTooltipText.textContent().trim();
    }

    /**
     * Row interactions - Date field (regular input, not datepicker)
     */
    private Locator getDateInput(int rowIndex) {
        return page.locator(String.format(DATE_INPUT_PATTERN, rowIndex));
    }

    public void enterDate(int rowIndex, String date) {
        page.waitForLoadState();
        Locator dateInput = getDateInput(rowIndex);
        dateInput.scrollIntoViewIfNeeded();
        dateInput.click();
        page.waitForTimeout(100);
        InputHelper.selectDateFromDatepicker(page, dateInput, date);
        page.waitForTimeout(300);
        // Trigger blur to enable other fields
        dateInput.evaluate("el => el.blur()");
        page.waitForTimeout(1000);
    }

    public String getDate(int rowIndex) {
        page.waitForLoadState();
        return getDateInput(rowIndex).inputValue();
    }

    /**
     * Row interactions - Planned Emissions field
     */
    private Locator getPlannedInput(int rowIndex) {
        return page.locator(String.format(PLANNED_INPUT_PATTERN, rowIndex));
    }

    public void enterPlannedEmissions(int rowIndex, String value) {
        Locator plannedInput = getPlannedInput(rowIndex);
        InputHelper.humanizedInput(page, plannedInput, value);
        plannedInput.evaluate("el => el.blur()");
        page.waitForTimeout(500);
    }

    public String getPlannedEmissions(int rowIndex) {
        page.waitForLoadState();
        return getPlannedInput(rowIndex).inputValue();
    }

    public boolean isPlannedInputEnabled(int rowIndex) {
        page.waitForLoadState();
        return getPlannedInput(rowIndex).isEnabled();
    }

    /**
     * Row interactions - Actual Emissions field
     */
    private Locator getActualInput(int rowIndex) {
        return page.locator(String.format(ACTUAL_INPUT_PATTERN, rowIndex));
    }

    public void enterActualEmissions(int rowIndex, String value) {
        Locator actualInput = getActualInput(rowIndex);
        InputHelper.humanizedInput(page, actualInput, value);
        actualInput.evaluate("el => el.blur()");
        page.waitForTimeout(500);
    }

    public String getActualEmissions(int rowIndex) {
        page.waitForLoadState();
        return getActualInput(rowIndex).inputValue();
    }

    public boolean isActualInputEnabled(int rowIndex) {
        page.waitForLoadState();
        return getActualInput(rowIndex).isEnabled();
    }

    /**
     * Row interactions - Cumulative Total field (read-only)
     */
    private Locator getCumulativeInput(int rowIndex) {
        return page.locator(String.format(CUMULATIVE_INPUT_PATTERN, rowIndex));
    }

    public String getCumulativeTotal(int rowIndex) {
        page.waitForLoadState();
        return getCumulativeInput(rowIndex).inputValue();
    }

    /**
     * Row interactions - Attach icon
     */
    private Locator getAttachIcon(int rowIndex) {
        return page.locator(String.format(ATTACH_ICON_PATTERN, rowIndex));
    }

    public void clickAttachIcon(int rowIndex) {
        page.waitForLoadState();
        Locator attachIcon = getAttachIcon(rowIndex);
        attachIcon.scrollIntoViewIfNeeded();
        attachIcon.click();
        page.waitForTimeout(500);
    }

    /**
     * Row interactions - Certificate Status dropdown
     */
    private Locator getStatusSelect(int rowIndex) {
        return page.locator(String.format(STATUS_SELECT_PATTERN, rowIndex));
    }

    public void selectStatus(int rowIndex, String status) {
        page.waitForLoadState();
        Locator statusSelect = getStatusSelect(rowIndex);
        statusSelect.scrollIntoViewIfNeeded();
        statusSelect.selectOption(new SelectOption().setLabel(status));
        page.waitForTimeout(500);
    }

    public String getSelectedStatus(int rowIndex) {
        page.waitForLoadState();
        return getStatusSelect(rowIndex).inputValue();
    }

    public boolean isStatusSelectEnabled(int rowIndex) {
        page.waitForLoadState();
        return getStatusSelect(rowIndex).isEnabled();
    }

    /**
     * Row interactions - Chat icon
     */
    private Locator getChatIcon(int rowIndex) {
        return page.locator(String.format(CHAT_ICON_PATTERN, rowIndex));
    }

    public void clickChatIcon(int rowIndex) {
        page.waitForLoadState();
        Locator chatIcon = getChatIcon(rowIndex);
        chatIcon.scrollIntoViewIfNeeded();
        chatIcon.click();
        page.waitForTimeout(500);
    }

    /**
     * Row interactions - Add/Remove rows
     */
    private Locator getRemoveRowIcon(int rowIndex) {
        return page.locator(String.format(REMOVE_ROW_PATTERN, rowIndex));
    }

    public void clickRemoveRow(int rowIndex) {
        page.waitForLoadState();
        Locator removeIcon = getRemoveRowIcon(rowIndex);
        removeIcon.scrollIntoViewIfNeeded();
        removeIcon.click();
        page.waitForTimeout(1000);
    }

    private Locator getAddRowIcon(int rowIndex) {
        return page.locator(String.format(ADD_ROW_PATTERN, rowIndex));
    }

    public void clickAddRow(int rowIndex) {
        page.waitForLoadState();
        Locator addIcon = getAddRowIcon(rowIndex);
        addIcon.scrollIntoViewIfNeeded();
        addIcon.click();
        page.waitForTimeout(1000);
    }

    /**
     * Convenience method to fill a complete row
     */
    public void fillRow(int rowIndex, String date, String plannedEmissions, String actualEmissions, String status) {
        enterDate(rowIndex, date);
        System.out.println("Entered date: " + date);

        if (plannedEmissions != null && !plannedEmissions.isEmpty()) {
            enterPlannedEmissions(rowIndex, plannedEmissions);
            System.out.println("Entered planned emissions: " + plannedEmissions);
        }
        if (actualEmissions != null && !actualEmissions.isEmpty()) {
            enterActualEmissions(rowIndex, actualEmissions);
            System.out.println("Entered actual emissions: " + actualEmissions);
        }
        if (status != null && !status.isEmpty()) {
            selectStatus(rowIndex, status);
            System.out.println("Selected status: " + status);
        }
    }

    /**
     * Table totals
     */
    public String getTotalCumulative() {
        page.waitForLoadState();
        return totalCumulativeInput.inputValue();
    }

    public boolean isTotalLabelVisible() {
        page.waitForLoadState();
        return totalLabel.isVisible();
    }

    /**
     * Check if row exists
     */
    public boolean isRowVisible(int rowIndex) {
        page.waitForLoadState();
        return page.locator(String.format(ROW_PATTERN, rowIndex)).isVisible();
    }

    /**
     * Count visible rows
     */
    public int getRowCount() {
        page.waitForLoadState();
        return page.locator("tr[id^='records_']").count();
    }

    /**
     * Save button
     */
    public boolean isSaveButtonVisible() {
        page.waitForLoadState();
        return saveButton.isVisible();
    }

    public boolean isSaveButtonEnabled() {
        page.waitForLoadState();
        return saveButton.isEnabled();
    }

    public void clickSave() {
        page.waitForLoadState();
        saveButton.scrollIntoViewIfNeeded();
        saveButton.click();
        page.waitForTimeout(1000);
    }

    public String getSaveButtonText() {
        page.waitForLoadState();
        return saveButton.textContent().trim();
    }
}
