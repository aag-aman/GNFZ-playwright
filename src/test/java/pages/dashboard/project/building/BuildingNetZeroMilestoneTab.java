package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

/**
 * BuildingNetZeroMilestoneTab - Net Zero Milestone tab for Building project
 * This tab tracks planned and actual emission reduction milestones over time
 */
public class BuildingNetZeroMilestoneTab {
    private final Page page;

    // Main content
    private final Locator tabContent;
    private final Locator instructionText;
    private final Locator milestoneTable;

    // Table header elements
    private final Locator plannedTooltipIcon;
    private final Locator plannedTooltipText;
    private final Locator attachTooltipIcon;
    private final Locator attachTooltipText;
    private final Locator unitsHeaderSelect;

    // Table total row
    private final Locator totalLabel;
    private final Locator totalCumulativeInput;
    private final Locator totalUnitsText;

    // Actions
    private final Locator saveButton;

    // Row patterns (dynamic)
    private static final String ROW_PATTERN = "#records_%d";
    private static final String YEAR_INPUT_PATTERN = "#NZM_yearpicker_%d";
    private static final String PLANNED_INPUT_PATTERN = "#net_zero_milestone_planned_%d";
    private static final String ACTUAL_INPUT_PATTERN = "#net_zero_milestone_actual_%d";
    private static final String CUMULATIVE_INPUT_PATTERN = "#records_%d td:nth-child(4) input[readonly]";
    private static final String UNITS_TEXT_PATTERN = "#records_%d td:nth-child(5)";
    private static final String ATTACH_ICON_PATTERN = "#records_%d a i.bi-paperclip";
    private static final String STATUS_SELECT_PATTERN = "#gnfz-net-zero-milestone-status-%d";
    private static final String CHAT_ICON_PATTERN = "#records_%d img.chat-icon";
    private static final String REMOVE_ROW_PATTERN = "#records_%d i.bi-trash";
    private static final String ADD_ROW_PATTERN = "#records_%d i.bi-plus-square";

    public BuildingNetZeroMilestoneTab(Page page) {
        this.page = page;

        // Initialize locators
        this.tabContent = page.locator(".tab6.page-section.tab-container");
        this.instructionText = page.locator("div.text-dark.label-text.font-weight-600");
        this.milestoneTable = page.locator("#netZeroTargets");

        // Table header elements
        this.plannedTooltipIcon = page.locator("th:has-text('Planned') span.tooltipp i.bi-info-circle");
        this.plannedTooltipText = page.locator("span.tooltiptext.planned-tooltip").first();
        this.attachTooltipIcon = page.locator("tr.bg-readonly th[width='30'] span.tooltipp i.bi-info-circle");
        this.attachTooltipText = page.locator("span.tooltiptext.planned-tooltip.left-100");
        this.unitsHeaderSelect = page.locator("tr.bg-readonly select.source-type");

        // Table total row
        this.totalLabel = page.locator("tr.border-bottom-white td:has-text('Total')");
        this.totalCumulativeInput = page.locator("tr.border-bottom-white td:nth-child(4) input[readonly]");
        this.totalUnitsText = page.locator("tr.border-bottom-white td:nth-child(5)");

        // Actions
        this.saveButton = page.locator("button#gnfz-save");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return tabContent.isVisible();
    }

    public boolean isMilestoneTableVisible() {
        page.waitForLoadState();
        return milestoneTable.isVisible();
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

    public void hoverOverAttachTooltip() {
        page.waitForLoadState();
        attachTooltipIcon.hover();
        page.waitForTimeout(300);
    }

    public String getAttachTooltipText() {
        page.waitForLoadState();
        hoverOverAttachTooltip();
        return attachTooltipText.textContent().trim();
    }

    /**
     * Units header dropdown - controls units for all rows
     */
    public void selectUnitsHeader(String units) {
        page.waitForLoadState();
        unitsHeaderSelect.scrollIntoViewIfNeeded();
        unitsHeaderSelect.selectOption(new SelectOption().setLabel(units));
        page.waitForTimeout(500);
    }

    public String getSelectedUnitsHeader() {
        page.waitForLoadState();
        return unitsHeaderSelect.inputValue();
    }

    public boolean isUnitsHeaderSelectVisible() {
        page.waitForLoadState();
        return unitsHeaderSelect.isVisible();
    }

    /**
     * Row interactions - Year field
     */
    private Locator getYearInput(int rowIndex) {
        return page.locator(String.format(YEAR_INPUT_PATTERN, rowIndex));
    }

    public void enterYear(int rowIndex, String year) {
        page.waitForLoadState();
        Locator yearInput = getYearInput(rowIndex);
        yearInput.scrollIntoViewIfNeeded();
        yearInput.click();
        page.waitForTimeout(500);

        // Wait for yearpicker dropdown to appear
        page.locator(".yearpicker-container").waitFor(new Locator.WaitForOptions()
            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));

        // Click on the year in the yearpicker
        page.locator(String.format(".yearpicker-items:has-text('%s')", year)).click();
        page.waitForTimeout(500);
    }

    public String getYear(int rowIndex) {
        page.waitForLoadState();
        return getYearInput(rowIndex).inputValue();
    }

    /**
     * Row interactions - Planned Reduction field
     */
    private Locator getPlannedInput(int rowIndex) {
        return page.locator(String.format(PLANNED_INPUT_PATTERN, rowIndex));
    }

    public void enterPlannedReduction(int rowIndex, String value) {
        page.waitForLoadState();
        Locator plannedInput = getPlannedInput(rowIndex);
        plannedInput.scrollIntoViewIfNeeded();
        plannedInput.click();
        plannedInput.fill(value);
        page.waitForTimeout(500);
    }

    public String getPlannedReduction(int rowIndex) {
        page.waitForLoadState();
        return getPlannedInput(rowIndex).inputValue();
    }

    public boolean isPlannedInputEnabled(int rowIndex) {
        page.waitForLoadState();
        return getPlannedInput(rowIndex).isEnabled();
    }

    /**
     * Row interactions - Actual Reduction field
     */
    private Locator getActualInput(int rowIndex) {
        return page.locator(String.format(ACTUAL_INPUT_PATTERN, rowIndex));
    }

    public void enterActualReduction(int rowIndex, String value) {
        page.waitForLoadState();
        Locator actualInput = getActualInput(rowIndex);
        actualInput.scrollIntoViewIfNeeded();
        actualInput.click();
        actualInput.fill(value);
        page.waitForTimeout(500);
    }

    public String getActualReduction(int rowIndex) {
        page.waitForLoadState();
        return getActualInput(rowIndex).inputValue();
    }

    public boolean isActualInputEnabled(int rowIndex) {
        page.waitForLoadState();
        return getActualInput(rowIndex).isEnabled();
    }

    /**
     * Row interactions - Cumulative Reduction field (read-only)
     */
    private Locator getCumulativeInput(int rowIndex) {
        return page.locator(String.format(CUMULATIVE_INPUT_PATTERN, rowIndex));
    }

    public String getCumulativeReduction(int rowIndex) {
        page.waitForLoadState();
        return getCumulativeInput(rowIndex).inputValue();
    }

    /**
     * Row interactions - Units text (displays unit for the row)
     */
    private Locator getUnitsText(int rowIndex) {
        return page.locator(String.format(UNITS_TEXT_PATTERN, rowIndex));
    }

    public String getUnits(int rowIndex) {
        page.waitForLoadState();
        return getUnitsText(rowIndex).textContent().trim();
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
    public void fillRow(int rowIndex, String year, String plannedReduction, String actualReduction, String status) {
        enterYear(rowIndex, year);
        if (plannedReduction != null && !plannedReduction.isEmpty()) {
            enterPlannedReduction(rowIndex, plannedReduction);
        }
        if (actualReduction != null && !actualReduction.isEmpty()) {
            enterActualReduction(rowIndex, actualReduction);
        }
        if (status != null && !status.isEmpty()) {
            selectStatus(rowIndex, status);
        }
    }

    /**
     * Table totals
     */
    public String getTotalCumulative() {
        page.waitForLoadState();
        return totalCumulativeInput.inputValue();
    }

    public String getTotalUnits() {
        page.waitForLoadState();
        return totalUnitsText.textContent().trim();
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
