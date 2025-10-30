package pages.dashboard.project.building.assessment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.dashboard.project.building.assessment.tablesWaste.WasteGeneratedTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteLandfillTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteIncineratedTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteCompostedTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteRecycledTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteReusedTable;
import utils.InputHelper;

import utils.AutoStep;
/**
 * NetZeroWasteSection - Net Zero Waste section in Assessment tab
 * Handles waste-related data entry
 *
 * Structure:
 * - a. Generated (Table A)
 * - b. Sent to Landfill (Table B)
 * - c. Incinerated (Table C)
 * - d. Composted (Table D)
 * - e. Recycled (Table E)
 * - f. Reused (Table F)
 * - g. Waste to be Reduced (Read-only calculation: a-(d+e+f))
 *
 * Usage:
 * waste.tableA().enterType(0, "Wood");
 * waste.tableA().enterQuantity(0, "100");
 */
public class NetZeroWasteSection {
    private final Page page;

    // Header and common locators
    private final Locator sectionHeader;
    private final Locator reportingPeriodFrom;
    private final Locator reportingPeriodTo;
    private final Locator baselineCheck;
    private final Locator addNewPeriodButton;

    // Section accordions
    private final Locator generatedSection;
    private final Locator landfillSection;
    private final Locator incineratedSection;
    private final Locator compostedSection;
    private final Locator recycledSection;
    private final Locator reusedSection;

    // Section totals (read-only, auto-calculated)
    private final Locator generatedTotal;
    private final Locator landfillTotal;
    private final Locator incineratedTotal;
    private final Locator compostedTotal;
    private final Locator recycledTotal;
    private final Locator reusedTotal;
    private final Locator wasteToBeReduced;

    // Save button
    private final Locator saveButton;

    // Table objects - initialized once and reused
    private final WasteGeneratedTable tableA;
    private final WasteLandfillTable tableB;
    private final WasteIncineratedTable tableC;
    private final WasteCompostedTable tableD;
    private final WasteRecycledTable tableE;
    private final WasteReusedTable tableF;

    public NetZeroWasteSection(Page page) {
        this.page = page;

        // Initialize common locators
        this.sectionHeader = page.locator("#net-zero-waste");
        this.reportingPeriodFrom = page.locator("#reporting_period_from");
        this.reportingPeriodTo = page.locator("#reporting_period_to");
        this.baselineCheck = page.locator("#reporting_period_baseline");
        this.addNewPeriodButton = page.locator("button.add-period-btn");

        // Section accordions - using ftestcaseref from headers
        this.generatedSection = page.locator("#header_0[ftestcaseref='waste_generated']");
        this.landfillSection = page.locator("#header_1[ftestcaseref='waste_to_landfill']");
        this.incineratedSection = page.locator("#header_2[ftestcaseref='waste_incinerated']");
        this.compostedSection = page.locator("#header_3[ftestcaseref='waste_composted']");
        this.recycledSection = page.locator("#header_4[ftestcaseref='waste_recycled']");
        this.reusedSection = page.locator("#header_5[ftestcaseref='waste_reused']");

        // Section totals (auto-calculated, read-only)
        this.generatedTotal = page.locator("#collapse_total_0");
        this.landfillTotal = page.locator("#collapse_total_1");
        this.incineratedTotal = page.locator("#collapse_total_2");
        this.compostedTotal = page.locator("#collapse_total_3");
        this.recycledTotal = page.locator("#collapse_total_4");
        this.reusedTotal = page.locator("#collapse_total_5");
        this.wasteToBeReduced = page.locator("#collapse_total_scope7");

        // Save button
        this.saveButton = page.locator("#gnfz-save");

        // Initialize table objects
        this.tableA = new WasteGeneratedTable(page);
        this.tableB = new WasteLandfillTable(page);
        this.tableC = new WasteIncineratedTable(page);
        this.tableD = new WasteCompostedTable(page);
        this.tableE = new WasteRecycledTable(page);
        this.tableF = new WasteReusedTable(page);
    }

    /**
     * ========================================
     * SECTION VISIBILITY
     * ========================================
     */
    @AutoStep
    public boolean isSectionDisplayed() {
        page.waitForLoadState();
        return sectionHeader.isVisible();
    }

    /**
     * ========================================
     * REPORTING PERIOD
     * ========================================
     */
    @AutoStep
    public void enterReportingPeriodFrom(String fromDate) {
        InputHelper.selectDateFromDatepicker(page, reportingPeriodFrom, fromDate);
    }

    @AutoStep
    public void enterReportingPeriodTo(String toDate) {
        InputHelper.selectDateFromDatepicker(page, reportingPeriodTo, toDate);
    }

    @AutoStep
    public void checkBaseline() {
        page.waitForLoadState();
        baselineCheck.check();
    }

    @AutoStep
    public void uncheckBaseline() {
        page.waitForLoadState();
        baselineCheck.uncheck();
    }

    @AutoStep
    public void clickAddNewPeriod() {
        page.waitForLoadState();
        addNewPeriodButton.click();
    }

    /**
     * ========================================
     * SECTION VISIBILITY (Expand/Collapse)
     * ========================================
     */
    @AutoStep
    public void expandGenerated() {
        page.waitForLoadState();
        generatedSection.click();
        page.waitForLoadState();
    }

    @AutoStep
    public void expandLandfill() {
        page.waitForLoadState();
        landfillSection.click();
        page.waitForLoadState();
    }

    @AutoStep
    public void expandIncinerated() {
        page.waitForLoadState();
        incineratedSection.click();
        page.waitForLoadState();
    }

    @AutoStep
    public void expandComposted() {
        page.waitForLoadState();
        compostedSection.click();
        page.waitForLoadState();
    }

    @AutoStep
    public void expandRecycled() {
        page.waitForLoadState();
        recycledSection.click();
        page.waitForLoadState();
    }

    @AutoStep
    public void expandReused() {
        page.waitForLoadState();
        reusedSection.click();
        page.waitForLoadState();
    }

    /**
     * ========================================
     * SECTION TOTALS (Read-only, auto-calculated)
     * ========================================
     */
    @AutoStep
    public String getGeneratedTotal() {
        page.waitForLoadState();
        return generatedTotal.textContent().trim();
    }

    @AutoStep
    public String getLandfillTotal() {
        page.waitForLoadState();
        return landfillTotal.textContent().trim();
    }

    @AutoStep
    public String getIncineratedTotal() {
        page.waitForLoadState();
        return incineratedTotal.textContent().trim();
    }

    @AutoStep
    public String getCompostedTotal() {
        page.waitForLoadState();
        return compostedTotal.textContent().trim();
    }

    @AutoStep
    public String getRecycledTotal() {
        page.waitForLoadState();
        return recycledTotal.textContent().trim();
    }

    @AutoStep
    public String getReusedTotal() {
        page.waitForLoadState();
        return reusedTotal.textContent().trim();
    }

    @AutoStep
    public String getWasteToBeReduced() {
        page.waitForLoadState();
        return wasteToBeReduced.textContent().trim();
    }

    /**
     * ========================================
     * SAVE BUTTON
     * ========================================
     */
    @AutoStep
    public void clickSave() {
        page.waitForLoadState();
        saveButton.scrollIntoViewIfNeeded();
        saveButton.click();
        page.waitForTimeout(500);
    }

    /**
     * ========================================
     * TABLE ACCESSORS - Get table objects
     * ========================================
     */

    // a. Generated
    @AutoStep
    public WasteGeneratedTable tableA() {
        return tableA;
    }

    // b. Sent to Landfill
    @AutoStep
    public WasteLandfillTable tableB() {
        return tableB;
    }

    // c. Incinerated
    @AutoStep
    public WasteIncineratedTable tableC() {
        return tableC;
    }

    // d. Composted
    @AutoStep
    public WasteCompostedTable tableD() {
        return tableD;
    }

    // e. Recycled
    @AutoStep
    public WasteRecycledTable tableE() {
        return tableE;
    }

    // f. Reused
    @AutoStep
    public WasteReusedTable tableF() {
        return tableF;
    }
}
