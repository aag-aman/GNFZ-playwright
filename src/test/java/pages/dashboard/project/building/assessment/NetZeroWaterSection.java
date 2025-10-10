package pages.dashboard.project.building.assessment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.dashboard.project.building.assessment.tablesWater.ConsumptionPotableTable;
import pages.dashboard.project.building.assessment.tablesWater.ConsumptionNonPotableTable;
import pages.dashboard.project.building.assessment.tablesWater.SupplyRecycledOnsiteTable;
import pages.dashboard.project.building.assessment.tablesWater.SupplyRecycledOffsiteTable;
import pages.dashboard.project.building.assessment.tablesWater.RainwaterTreatmentTable;
import pages.dashboard.project.building.assessment.tablesWater.RainwaterRechargeTable;
import pages.dashboard.project.building.assessment.tablesWater.RainwaterOutsideBoundaryTable;
import pages.dashboard.project.building.assessment.tablesWater.FreshwaterProvisionTable;

/**
 * NetZeroWaterSection - Net Zero Water section in Assessment tab
 * Handles water-related data entry
 *
 * Structure:
 * - a. Consumption: Tables A (Potable), B (Non Potable)
 * - b. Supply: Tables C (Recycled on-site), D (Recycled off-site)
 * - c. Rainwater: Tables E (Run-off - for treatment), F (Run-off - for recharge), G (Run-off:outside project boundary)
 * - d. Freshwater Provision: Table H (Freshwater provision)
 * - e. Annual Freshwater Requirement (Read-only calculation: a-b-c)
 *
 * Usage:
 * water.tableA().enterType(0, "Cooking");
 * water.tableA().enterQuantity(0, "100");
 */
public class NetZeroWaterSection {
    private final Page page;

    // Header and common locators
    private final Locator sectionHeader;
    private final Locator reportingPeriodFrom;
    private final Locator reportingPeriodTo;
    private final Locator baselineCheck;
    private final Locator addNewPeriodButton;

    // Section accordions
    private final Locator consumptionSection;
    private final Locator supplySection;
    private final Locator rainwaterSection;
    private final Locator freshwaterProvisionSection;

    // Section totals (read-only, auto-calculated)
    private final Locator consumptionTotal;
    private final Locator supplyTotal;
    private final Locator rainwaterTotal;
    private final Locator freshwaterProvisionTotal;
    private final Locator annualFreshwaterRequirement;

    // View toggles for each section (summary/detailed)
    private final Locator consumptionSummaryView;
    private final Locator consumptionDetailedView;
    private final Locator supplySummaryView;
    private final Locator supplyDetailedView;
    private final Locator rainwaterSummaryView;
    private final Locator rainwaterDetailedView;
    private final Locator freshwaterProvisionSummaryView;
    private final Locator freshwaterProvisionDetailedView;

    // Save button
    private final Locator saveButton;

    // Table objects - initialized once and reused
    private final ConsumptionPotableTable tableA;
    private final ConsumptionNonPotableTable tableB;
    private final SupplyRecycledOnsiteTable tableC;
    private final SupplyRecycledOffsiteTable tableD;
    private final RainwaterTreatmentTable tableE;
    private final RainwaterRechargeTable tableF;
    private final RainwaterOutsideBoundaryTable tableG;
    private final FreshwaterProvisionTable tableH;

    public NetZeroWaterSection(Page page) {
        this.page = page;

        // Initialize common locators
        this.sectionHeader = page.locator("#net-zero-water");
        this.reportingPeriodFrom = page.locator("#reporting_period_from");
        this.reportingPeriodTo = page.locator("#reporting_period_to");
        this.baselineCheck = page.locator("#reporting_period_baseline");
        this.addNewPeriodButton = page.locator("button.add-period-btn");

        // Section accordions
        this.consumptionSection = page.locator("#flush-heading__water_consumption .accordion-button");
        this.supplySection = page.locator("#flush-heading__water_supply .accordion-button");
        this.rainwaterSection = page.locator("#flush-heading__rainwater .accordion-button");
        this.freshwaterProvisionSection = page.locator("#flush-heading__freshwater_provision .accordion-button");

        // Section totals (auto-calculated, read-only)
        this.consumptionTotal = page.locator("#collapse_total_water_consumption");
        this.supplyTotal = page.locator("#collapse_total_water_supply");
        this.rainwaterTotal = page.locator("#collapse_total_rainwater");
        this.freshwaterProvisionTotal = page.locator("#collapse_total_freshwater_provision");
        this.annualFreshwaterRequirement = page.locator("#collapse_total_scope6");

        // View toggles - Consumption section
        this.consumptionSummaryView = page.locator("#flush-collapse__water_consumption a:has-text('Summary view')");
        this.consumptionDetailedView = page.locator("#flush-collapse__water_consumption a:has-text('Detailed view')");

        // View toggles - Supply section
        this.supplySummaryView = page.locator("#flush-collapse__water_supply a:has-text('Summary view')");
        this.supplyDetailedView = page.locator("#flush-collapse__water_supply a:has-text('Detailed view')");

        // View toggles - Rainwater section
        this.rainwaterSummaryView = page.locator("#flush-collapse__rainwater a:has-text('Summary view')");
        this.rainwaterDetailedView = page.locator("#flush-collapse__rainwater a:has-text('Detailed view')");

        // View toggles - Freshwater Provision section
        this.freshwaterProvisionSummaryView = page.locator("#flush-collapse__freshwater_provision a:has-text('Summary view')");
        this.freshwaterProvisionDetailedView = page.locator("#flush-collapse__freshwater_provision a:has-text('Detailed view')");

        // Save button
        this.saveButton = page.locator("#gnfz-save");

        // Initialize table objects
        this.tableA = new ConsumptionPotableTable(page);
        this.tableB = new ConsumptionNonPotableTable(page);
        this.tableC = new SupplyRecycledOnsiteTable(page);
        this.tableD = new SupplyRecycledOffsiteTable(page);
        this.tableE = new RainwaterTreatmentTable(page);
        this.tableF = new RainwaterRechargeTable(page);
        this.tableG = new RainwaterOutsideBoundaryTable(page);
        this.tableH = new FreshwaterProvisionTable(page);
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
    public void expandConsumption() {
        page.waitForLoadState();
        consumptionSection.click();
        page.waitForLoadState();
    }

    public void expandSupply() {
        page.waitForLoadState();
        supplySection.click();
        page.waitForLoadState();
    }

    public void expandRainwater() {
        page.waitForLoadState();
        rainwaterSection.click();
        page.waitForLoadState();
    }

    public void expandFreshwaterProvision() {
        page.waitForLoadState();
        freshwaterProvisionSection.click();
        page.waitForLoadState();
    }

    /**
     * ========================================
     * VIEW TOGGLES (Summary/Detailed)
     * ========================================
     */
    public void switchToConsumptionSummaryView() {
        consumptionSummaryView.click();
    }

    public void switchToConsumptionDetailedView() {
        consumptionDetailedView.click();
    }

    public void switchToSupplySummaryView() {
        supplySummaryView.click();
    }

    public void switchToSupplyDetailedView() {
        supplyDetailedView.click();
    }

    public void switchToRainwaterSummaryView() {
        rainwaterSummaryView.click();
    }

    public void switchToRainwaterDetailedView() {
        rainwaterDetailedView.click();
    }

    public void switchToFreshwaterProvisionSummaryView() {
        freshwaterProvisionSummaryView.click();
    }

    public void switchToFreshwaterProvisionDetailedView() {
        freshwaterProvisionDetailedView.click();
    }

    /**
     * ========================================
     * SECTION TOTALS (Read-only, auto-calculated)
     * ========================================
     */
    public String getConsumptionTotal() {
        page.waitForLoadState();
        return consumptionTotal.textContent().trim();
    }

    public String getSupplyTotal() {
        page.waitForLoadState();
        return supplyTotal.textContent().trim();
    }

    public String getRainwaterTotal() {
        page.waitForLoadState();
        return rainwaterTotal.textContent().trim();
    }

    public String getFreshwaterProvisionTotal() {
        page.waitForLoadState();
        return freshwaterProvisionTotal.textContent().trim();
    }

    public String getAnnualFreshwaterRequirement() {
        page.waitForLoadState();
        return annualFreshwaterRequirement.textContent().trim();
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

    // a. Consumption Tables
    public ConsumptionPotableTable tableA() {
        return tableA;
    }

    public ConsumptionNonPotableTable tableB() {
        return tableB;
    }

    // b. Supply Tables
    public SupplyRecycledOnsiteTable tableC() {
        return tableC;
    }

    public SupplyRecycledOffsiteTable tableD() {
        return tableD;
    }

    // c. Rainwater Tables
    public RainwaterTreatmentTable tableE() {
        return tableE;
    }

    public RainwaterRechargeTable tableF() {
        return tableF;
    }

    public RainwaterOutsideBoundaryTable tableG() {
        return tableG;
    }

    // d. Freshwater Provision Table
    public FreshwaterProvisionTable tableH() {
        return tableH;
    }
}
