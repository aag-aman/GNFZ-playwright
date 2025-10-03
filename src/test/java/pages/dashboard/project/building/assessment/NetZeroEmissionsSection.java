package pages.dashboard.project.building.assessment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.dashboard.project.building.BuildingAssessmentTab;
import pages.dashboard.project.building.assessment.tables.*;

/**
 * NetZeroEmissionsSection - Net Zero Emissions section in Assessment tab
 * Handles emissions-related data entry for Scope 1, 2, and 3
 *
 * Structure:
 * - Scope 1: Tables A, B, C
 * - Scope 2: Table D
 * - Scope 3: Tables E through R
 *
 * Usage:
 * emissions.tableA().enterFuel(0, "Diesel");
 * emissions.tableA().enterEmissionFactor(0, "2.68");
 * emissions.tableB().enterFuel(0, "Coal");
 */
public class NetZeroEmissionsSection extends BuildingAssessmentTab {
    private final Page page;

    // Header and common locators
    private final Locator sectionHeader;
    private final Locator reportingPeriodFrom;
    private final Locator reportingPeriodTo;
    private final Locator baselineCheck;

    // Scope sections
    private final Locator scope1Section;
    private final Locator scope2Section;
    private final Locator scope3Section;

    // Scope view toggles
    private final Locator scope1SummaryView;
    private final Locator scope1DetailedView;
    private final Locator scope2SummaryView;
    private final Locator scope2DetailedView;
    private final Locator scope3SummaryView;
    private final Locator scope3DetailedView;

    // Scope totals (auto-populated)
    private final Locator scope1TotalEmissions;
    private final Locator scope2TotalEmissions;
    private final Locator scope3TotalEmissions;

    // Table objects - initialized once and reused
    private final Scope1TableA tableA;
    private final Scope1TableB tableB;
    private final Scope1TableC tableC;
    private final Scope2TableD tableD;
    private final Scope3TableE tableE;

    // Scope 3 tables (F through R)
    private final Scope3TableF tableF;
    private final Scope3TableG tableG;
    private final Scope3TableH tableH;
    private final Scope3TableI tableI;
    private final Scope3TableJ tableJ;
    private final Scope3TableK tableK;
    private final Scope3TableL tableL;
    private final Scope3TableM tableM;
    private final Scope3TableN tableN;
    private final Scope3TableO tableO;
    private final Scope3TableP tableP;
    private final Scope3TableQ tableQ;
    private final Scope3TableR tableR;

    public NetZeroEmissionsSection(Page page) {
        this.page = page;

        // Initialize common locators - using ftestcaseref attributes
        this.sectionHeader = page.locator("[ftestcaseref='net_zero_emissions_btn']");
        this.reportingPeriodFrom = page.locator("[ftestcaseref='reporting_period_from']"); // TODO: Get actual ftestcaseref
        this.reportingPeriodTo = page.locator("[ftestcaseref='reporting_period_to']"); // TODO: Get actual ftestcaseref
        this.baselineCheck = page.locator("[ftestcaseref='baseline_checkbox']"); // TODO: Get actual ftestcaseref

        // Scope sections
        this.scope1Section = page.locator("[ftestcaseref='scope_1']");
        this.scope2Section = page.locator("[ftestcaseref='scope_2']");
        this.scope3Section = page.locator("[ftestcaseref='scope_3']");

        // View toggles - TODO: Get actual ftestcaseref values for these
        this.scope1SummaryView = page.locator("[ftestcaseref='scope1_summary_view']");
        this.scope1DetailedView = page.locator("[ftestcaseref='scope1_detailed_view']");
        this.scope2SummaryView = page.locator("[ftestcaseref='scope2_summary_view']");
        this.scope2DetailedView = page.locator("[ftestcaseref='scope2_detailed_view']");
        this.scope3SummaryView = page.locator("[ftestcaseref='scope3_summary_view']");
        this.scope3DetailedView = page.locator("[ftestcaseref='scope3_detailed_view']");

        // Scope totals (auto-populated, read-only)
        this.scope1TotalEmissions = page.locator("[ftestcaseref='scope_1_total']");
        this.scope2TotalEmissions = page.locator("[ftestcaseref='scope_2_total']");
        this.scope3TotalEmissions = page.locator("[ftestcaseref='scope_3_total']");

        // Initialize table objects - each table knows its own structure
        this.tableA = new Scope1TableA(page);
        this.tableB = new Scope1TableB(page);
        this.tableC = new Scope1TableC(page);
        this.tableD = new Scope2TableD(page);
        this.tableE = new Scope3TableE(page);

        // Initialize Scope 3 tables (F through R)
        this.tableF = new Scope3TableF(page);
        this.tableG = new Scope3TableG(page);
        this.tableH = new Scope3TableH(page);
        this.tableI = new Scope3TableI(page);
        this.tableJ = new Scope3TableJ(page);
        this.tableK = new Scope3TableK(page);
        this.tableL = new Scope3TableL(page);
        this.tableM = new Scope3TableM(page);
        this.tableN = new Scope3TableN(page);
        this.tableO = new Scope3TableO(page);
        this.tableP = new Scope3TableP(page);
        this.tableQ = new Scope3TableQ(page);
        this.tableR = new Scope3TableR(page);
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

    public void switchToScope3DetailedView() {
        scope3DetailedView.click();
    }

    public void switchToScope3SummaryView() {
        scope3SummaryView.click();
    }

    /**
     * ========================================
     * SCOPE TOTALS (Read-only, auto-populated)
     * ========================================
     */
    public String getScope1Total() {
        return scope1TotalEmissions.textContent();
    }

    public String getScope2Total() {
        return scope2TotalEmissions.textContent();
    }

    public String getScope3Total() {
        return scope3TotalEmissions.textContent();
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

    public Scope1TableC tableC() {
        return tableC;
    }

    // Scope 2 Table
    public Scope2TableD tableD() {
        return tableD;
    }

    // Scope 3 Tables
    public Scope3TableE tableE() {
        return tableE;
    }

    public Scope3TableF tableF() {
        return tableF;
    }

    public Scope3TableG tableG() {
        return tableG;
    }

    public Scope3TableH tableH() {
        return tableH;
    }

    public Scope3TableI tableI() {
        return tableI;
    }

    public Scope3TableJ tableJ() {
        return tableJ;
    }

    public Scope3TableK tableK() {
        return tableK;
    }

    public Scope3TableL tableL() {
        return tableL;
    }

    public Scope3TableM tableM() {
        return tableM;
    }

    public Scope3TableN tableN() {
        return tableN;
    }

    public Scope3TableO tableO() {
        return tableO;
    }

    public Scope3TableP tableP() {
        return tableP;
    }

    public Scope3TableQ tableQ() {
        return tableQ;
    }

    public Scope3TableR tableR() {
        return tableR;
    }

    /**
     * ========================================
     * HELPER METHOD - Get any Scope 3 table by letter
     * ========================================
     */
    public Scope3Table getScope3Table(char tableLetter) {
        return switch (Character.toLowerCase(tableLetter)) {
            case 'e' -> tableE;
            case 'f' -> tableF;
            case 'g' -> tableG;
            case 'h' -> tableH;
            case 'i' -> tableI;
            case 'j' -> tableJ;
            case 'k' -> tableK;
            case 'l' -> tableL;
            case 'm' -> tableM;
            case 'n' -> tableN;
            case 'o' -> tableO;
            case 'p' -> tableP;
            case 'q' -> tableQ;
            case 'r' -> tableR;
            default -> throw new IllegalArgumentException("Invalid table letter: " + tableLetter);
        };
    }
}
