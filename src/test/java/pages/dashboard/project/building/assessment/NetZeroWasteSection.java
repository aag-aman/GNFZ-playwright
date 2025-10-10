package pages.dashboard.project.building.assessment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.dashboard.project.building.assessment.tablesWaste.WasteGeneratedTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteLandfillTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteIncineratedTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteCompostedTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteRecycledTable;
import pages.dashboard.project.building.assessment.tablesWaste.WasteReusedTable;

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
    public boolean isSectionDisplayed() {
        page.waitForLoadState();
        return sectionHeader.isVisible();
    }

    /**
     * ========================================
     * REPORTING PERIOD
     * ========================================
     */
    public void enterReportingPeriodFrom(String fromDate) {
        page.waitForLoadState();
        reportingPeriodFrom.fill(fromDate);
    }

    public void enterReportingPeriodTo(String toDate) {
        page.waitForLoadState();
        reportingPeriodTo.waitFor();

        // Focus and click to trigger datepicker
        page.waitForTimeout(2000);
        reportingPeriodTo.focus();
        page.waitForTimeout(2000);
        reportingPeriodTo.click();
        page.waitForTimeout(1100);

        // Wait for datepicker to appear
        Locator datepicker = page.locator("#ui-datepicker-div");
        datepicker.waitFor();

        // Parse date (format: MM/DD/YYYY)
        String[] dateParts = toDate.split("/");
        String monthValue = String.valueOf(Integer.parseInt(dateParts[0]) - 1); // Month is 0-indexed
        String year = dateParts[2];
        String day = String.valueOf(Integer.parseInt(dateParts[1])); // Remove leading zero

        // Select month from dropdown
        Locator monthDropdown = page.locator(".ui-datepicker-month");
        monthDropdown.selectOption(monthValue);
        page.waitForTimeout(200);

        // Select year from dropdown
        Locator yearDropdown = page.locator(".ui-datepicker-year");
        yearDropdown.selectOption(year);
        page.waitForTimeout(200);

        // Click on the day in the calendar
        Locator dayButton = page.locator(".ui-datepicker-calendar td a");
        dayButton.filter(new Locator.FilterOptions().setHasText(day)).first().click();

        page.waitForTimeout(300);

        reportingPeriodTo.fill(toDate);
    }

    public void checkBaseline() {
        page.waitForLoadState();
        baselineCheck.check();
    }

    public void uncheckBaseline() {
        page.waitForLoadState();
        baselineCheck.uncheck();
    }

    public void clickAddNewPeriod() {
        page.waitForLoadState();
        addNewPeriodButton.click();
    }

    /**
     * ========================================
     * SECTION VISIBILITY (Expand/Collapse)
     * ========================================
     */
    public void expandGenerated() {
        page.waitForLoadState();
        generatedSection.click();
        page.waitForLoadState();
    }

    public void expandLandfill() {
        page.waitForLoadState();
        landfillSection.click();
        page.waitForLoadState();
    }

    public void expandIncinerated() {
        page.waitForLoadState();
        incineratedSection.click();
        page.waitForLoadState();
    }

    public void expandComposted() {
        page.waitForLoadState();
        compostedSection.click();
        page.waitForLoadState();
    }

    public void expandRecycled() {
        page.waitForLoadState();
        recycledSection.click();
        page.waitForLoadState();
    }

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
    public String getGeneratedTotal() {
        page.waitForLoadState();
        return generatedTotal.textContent().trim();
    }

    public String getLandfillTotal() {
        page.waitForLoadState();
        return landfillTotal.textContent().trim();
    }

    public String getIncineratedTotal() {
        page.waitForLoadState();
        return incineratedTotal.textContent().trim();
    }

    public String getCompostedTotal() {
        page.waitForLoadState();
        return compostedTotal.textContent().trim();
    }

    public String getRecycledTotal() {
        page.waitForLoadState();
        return recycledTotal.textContent().trim();
    }

    public String getReusedTotal() {
        page.waitForLoadState();
        return reusedTotal.textContent().trim();
    }

    public String getWasteToBeReduced() {
        page.waitForLoadState();
        return wasteToBeReduced.textContent().trim();
    }

    /**
     * ========================================
     * SAVE BUTTON
     * ========================================
     */
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
    public WasteGeneratedTable tableA() {
        return tableA;
    }

    // b. Sent to Landfill
    public WasteLandfillTable tableB() {
        return tableB;
    }

    // c. Incinerated
    public WasteIncineratedTable tableC() {
        return tableC;
    }

    // d. Composted
    public WasteCompostedTable tableD() {
        return tableD;
    }

    // e. Recycled
    public WasteRecycledTable tableE() {
        return tableE;
    }

    // f. Reused
    public WasteReusedTable tableF() {
        return tableF;
    }
}
