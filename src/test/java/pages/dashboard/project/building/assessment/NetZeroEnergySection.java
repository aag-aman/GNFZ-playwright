package pages.dashboard.project.building.assessment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.dashboard.project.building.assessment.tablesEnergy.Scope1TableA;
import pages.dashboard.project.building.assessment.tablesEnergy.Scope1TableB;
import pages.dashboard.project.building.assessment.tablesEnergy.Scope2TableC;

/**
 * NetZeroEnergySection - Net Zero Energy section in Assessment tab
 * Handles energy-related data entry for Scope 1 and 2
 *
 * Structure:
 * - Scope 1: Tables A, B
 * - Scope 2: Table C
 *
 * Usage:
 * energy.tableA().enterFuel(0, "Diesel");
 * energy.tableA().enterEmissionFactor(0, "2.68");
 * energy.tableB().enterFuel(0, "Coal");
 */
public class NetZeroEnergySection {
    private final Page page;

    // Header and common locators
    private final Locator sectionHeader;
    private final Locator reportingPeriodFrom;
    private final Locator reportingPeriodTo;
    private final Locator baselineCheck;

    // Scope sections
    private final Locator scope1Section;
    private final Locator scope2Section;
    private final Locator summaryOfScopes;

    // Scope view toggles
    private final Locator scope1SummaryView;
    private final Locator scope1DetailedView;
    private final Locator scope2SummaryView;
    private final Locator scope2DetailedView;

    // Scope totals (auto-populated)
    private final Locator scope1TotalEnergy;
    private final Locator scope2TotalEnergy;

    // Summary of Scopes table (read-only totals) - using CSS selectors based on
    // table structure
    private final Locator summaryScope1KgCO2e;
    private final Locator summaryScope1MtCO2e;
    private final Locator summaryScope2KgCO2e;
    private final Locator summaryScope2MtCO2e;
    private final Locator summaryTotalKgCO2e;
    private final Locator summaryTotalMtCO2e;

    // Table objects - initialized once and reused
    private final Scope1TableA tableA;
    private final Scope1TableB tableB;
    private final Scope2TableC tableC;

    public NetZeroEnergySection(Page page) {
        this.page = page;

        // Initialize common locators - using ID attributes from actual HTML
        // Energy section uses same structure as emissions but different container
        this.sectionHeader = page.locator("#net-zero-energy");
        this.reportingPeriodFrom = page.locator("#net-zero-energy #reporting_period_from");
        this.reportingPeriodTo = page.locator("#net-zero-energy #reporting_period_to");
        this.baselineCheck = page.locator("#net-zero-energy #reporting_period_baseline");

        // Scope sections - using the accordion structure within energy tab
        this.scope1Section = page.locator("#net-zero-energy #energy-heading__Scope1 .accordion-button");
        this.scope2Section = page.locator("#net-zero-energy #energy-heading__Scope2 .accordion-button");
        this.summaryOfScopes = page.locator("#net-zero-energy #flush-heading__ScopeSummary .accordion-button");

        // View toggles - using the buttons in detailed/summary view
        // Scope 1 toggles
        this.scope1SummaryView = page.locator("#flush-collapse__Scope1 .d-flex.justify-content-end a:has-text('Summary view')");
        this.scope1DetailedView = page.locator("#flush-collapse__Scope1 .d-flex.justify-content-end a:has-text('Detailed view')");

        // Scope 2 toggles
        this.scope2SummaryView = page.locator("#flush-collapse__Scope2 .d-flex.justify-content-end a:has-text('Summary view')");
        this.scope2DetailedView = page.locator("#flush-collapse__Scope2 .d-flex.justify-content-end a:has-text('Detailed view')");

        // Scope totals (auto-populated, read-only) - these use ftestcaseref from the table structure
        // Scope 1 total is in the Mobile Combustion table footer
        this.scope1TotalEnergy = page.locator("#net-zero-energy input[ftestcaseref='scope_1_total']");
        this.scope2TotalEnergy = page.locator("#net-zero-energy input[ftestcaseref='scope2_energy_total']");

        // Summary of Scopes table - scoped within the energy tab's summary section
        // Using the parent div ID to ensure we target the correct table within energy tab
        String summaryTableBase = "#net-zero-energy #flush-collapse__Summary .summary-table tbody";

        // Row 2: Scope 1 (a. Scope 1)
        this.summaryScope1KgCO2e = page.locator(summaryTableBase + " tr:has-text('a. Scope 1') td:nth-child(2)");
        this.summaryScope1MtCO2e = page.locator(summaryTableBase + " tr:has-text('a. Scope 1') td:nth-child(3)");

        // Row 3: Scope 2 (b. Scope 2)
        this.summaryScope2KgCO2e = page.locator(summaryTableBase + " tr:has-text('b. Scope 2') td:nth-child(2)");
        this.summaryScope2MtCO2e = page.locator(summaryTableBase + " tr:has-text('b. Scope 2') td:nth-child(3)");

        // Row 4: Total energy (c. Total energy of the building)
        this.summaryTotalKgCO2e = page
                .locator(summaryTableBase + " tr:has-text('c. Total energy of the building') td:nth-child(2) b");
        this.summaryTotalMtCO2e = page
                .locator(summaryTableBase + " tr:has-text('c. Total energy of the building') td:nth-child(3) b");

        // Initialize table objects - each table knows its own structure
        this.tableA = new Scope1TableA(page);
        this.tableB = new Scope1TableB(page);
        this.tableC = new Scope2TableC(page);
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
        // Humanize using datepicker
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
        String monthValue = String.valueOf(Integer.parseInt(dateParts[0]) - 1); // Month is 0-indexed (0=Jan, 11=Dec)
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

    /**
     * ========================================
     * SCOPE VIEW TOGGLES
     * ========================================
     */
    public void switchToScope1DetailedView() {
        scope1DetailedView.click();
    }

    public void switchToScope1SummaryView() {
        scope1SummaryView.click();
    }

    public void switchToScope2DetailedView() {
        scope2DetailedView.click();
    }

    public void switchToScope2SummaryView() {
        scope2SummaryView.click();
    }

    /**
     * ========================================
     * SCOPE VISIBILITY
     * ========================================
     */
    public void expandScope1() {
        page.waitForLoadState();
        scope1Section.click();
        page.waitForLoadState();
    }

    public void expandScope2() {
        page.waitForLoadState();
        scope2Section.click();
        page.waitForLoadState();
    }

    public void expandSummaryOfScopes() {
        page.waitForLoadState();
        summaryOfScopes.click();
        page.waitForLoadState();
    }

    /**
     * ========================================
     * SCOPE TOTALS (Read-only, auto-populated)
     * ========================================
     */
    public String getScope1Total() {
        return scope1TotalEnergy.inputValue();
    }

    public String getScope2Total() {
        return scope2TotalEnergy.inputValue();
    }

    /**
     * ========================================
     * SUMMARY OF SCOPES TABLE (Read-only totals)
     * ========================================
     */
    public String getSummaryScope1KgCO2e() {
        page.waitForLoadState();
        return summaryScope1KgCO2e.textContent().trim();
    }

    public String getSummaryScope1MtCO2e() {
        page.waitForLoadState();
        return summaryScope1MtCO2e.textContent().trim();
    }

    public String getSummaryScope2KgCO2e() {
        page.waitForLoadState();
        return summaryScope2KgCO2e.textContent().trim();
    }

    public String getSummaryScope2MtCO2e() {
        page.waitForLoadState();
        return summaryScope2MtCO2e.textContent().trim();
    }

    public String getSummaryTotalKgCO2e() {
        page.waitForLoadState();
        return summaryTotalKgCO2e.textContent().trim();
    }

    public String getSummaryTotalMtCO2e() {
        page.waitForLoadState();
        return summaryTotalMtCO2e.textContent().trim();
    }

    /**
     * ========================================
     * TABLE ACCESSORS - Get table objects
     * ========================================
     */

    // Scope 1 Tables
    public Scope1TableA tableA() {
        return tableA;
    }

    public Scope1TableB tableB() {
        return tableB;
    }

    // Scope 2 Table
    public Scope2TableC tableC() {
        return tableC;
    }
}
