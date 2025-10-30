package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.AutoStep;
/**
 * BuildingSummaryTab - Summary of Emissions tab for Building project
 * This tab provides a read-only summary view of all assessment data across:
 * - Net Zero Emissions (Scope 1, 2, 3, offsets, removal strategies)
 * - Net Zero Energy
 * - Net Zero Water
 * - Net Zero Waste
 */
public class BuildingSummaryTab {
    private final Page page;

    // Main content
    private final Locator tabContent;
    private final Locator pageTitle;
    // private final Locator titleUnderline;

    // Header actions
    private final Locator shareButton;
    // private final Locator shareDropdown;
    private final Locator copyLinkButton;
    private final Locator downloadButton;
    // private final Locator downloadDropdown;
    private final Locator downloadExcelButton;
    private final Locator downloadPdfButton;

    // Sub-tabs for different assessment types
    private final Locator emissionsTabButton;
    private final Locator energyTabButton;
    private final Locator waterTabButton;
    private final Locator wasteTabButton;

    // Summary table (Emissions)
    private final Locator emissionsTabContent;
    private final Locator emissionsSummaryTable;

    // Emissions table rows (clickable links to navigate to source)
    private final Locator scope1Link;
    private final Locator scope2Link;
    private final Locator scope3Link;
    private final Locator totalEmissionsLink;
    private final Locator carbonOffsetsLink;
    private final Locator carbonRemovalLink;
    // private final Locator emissionsToAvoidText;

    // Water summary table rows (clickable links to navigate to source)
    private final Locator waterConsumptionLink;
    private final Locator waterSupplyLink;
    private final Locator waterRainwaterLink;
    private final Locator waterFreshwaterRequirementLink;
    private final Locator waterNoRainDaysLink;
    private final Locator waterRainyDaysLink;
    private final Locator waterFreshwaterProvisionLink;

    // Energy summary table rows (clickable links to navigate to source)
    private final Locator energyScope1Link;
    private final Locator energyScope2Link;
    private final Locator energyTotalEmissionsLink;

    // Waste summary table rows (clickable links to navigate to source)
    private final Locator wasteGeneratedLink;
    private final Locator wasteLandfillLink;
    private final Locator wasteIncineratedLink;
    private final Locator wasteCompostedLink;
    private final Locator wasteRecycledLink;
    private final Locator wasteReusedLink;

    // Other assessment tab contents
    private final Locator energyTabContent;
    private final Locator waterTabContent;
    private final Locator wasteTabContent;

    public BuildingSummaryTab(Page page) {
        this.page = page;

        // Initialize locators
        this.tabContent = page.locator(".tab7.page-section.tab-container");
        this.pageTitle = page.locator("div.label-textt b:has-text('Net Zero Progress')");

        // Header actions
        this.shareButton = page.locator("button#summary_shareLink");
        this.copyLinkButton = page.locator("ul[aria-labelledby='summary_shareLink'] button:has-text('Copy link')");
        this.downloadButton = page.locator("button[id^='summary-dropdown-link-']");
        this.downloadExcelButton = page.locator("ul[id^='summary-dropdown-menu-'] button:has-text('Excel')");
        this.downloadPdfButton = page.locator("ul[id^='summary-dropdown-menu-'] button:has-text('Pdf')");

        // Sub-tabs
        this.emissionsTabButton = page.locator("button#net-zero-emission-assessment");
        this.energyTabButton = page.locator("button#net-zero-energy-assessment");
        this.waterTabButton = page.locator("button#net-zero-water-assessment");
        this.wasteTabButton = page.locator("button#net-zero-waste-assessment");

        // Summary table (Emissions)
        this.emissionsTabContent = page.locator("#summary-emission");
        this.emissionsSummaryTable = page.locator("table.summary-table");

        // Emissions table rows
        this.scope1Link = page.locator("span.pointer.text-underline-hover:has-text('Scope 1')");
        this.scope2Link = page.locator("span.pointer.text-underline-hover:has-text('Scope 2')");
        this.scope3Link = page.locator("span.pointer.text-underline-hover:has-text('Scope 3')");
        this.totalEmissionsLink = page.locator("span.pointer.text-underline-hover:has-text('Total emissions of the building')");
        this.carbonOffsetsLink = page.locator("span.pointer.text-underline-hover:has-text('Emissions avoided by carbon offsets')");
        this.carbonRemovalLink = page.locator("span.pointer.text-underline-hover:has-text('Emissions avoided by carbon removal strategies')");

        // Water table rows
        this.waterConsumptionLink = page.locator("#summary-water span.pointer.text-underline-hover:has-text('Consumption')");
        this.waterSupplyLink = page.locator("#summary-water span.pointer.text-underline-hover:has-text('Supply')");
        this.waterRainwaterLink = page.locator("#summary-water span.pointer.text-underline-hover:has-text('Rainwater')");
        this.waterFreshwaterRequirementLink = page.locator("#summary-water span.pointer.text-underline-hover:has-text('Freshwater Requirement')");
        this.waterNoRainDaysLink = page.locator("#summary-water span.pointer.text-underline-hover:has-text('During no-rain days, daily')");
        this.waterRainyDaysLink = page.locator("#summary-water span.pointer.text-underline-hover:has-text('During rainy days, daily')");
        this.waterFreshwaterProvisionLink = page.locator("#summary-water span.pointer.text-underline-hover:has-text('Freshwater Provision')");

        // Energy table rows (note: some items are not clickable links based on HTML)
        this.energyScope1Link = page.locator("#summary-energy span.pointer.text-underline-hover:has-text('Scope 1')");
        this.energyScope2Link = page.locator("#summary-energy span.pointer.text-underline-hover:has-text('Scope 2')");
        this.energyTotalEmissionsLink = page.locator("#summary-energy span.pointer.text-underline-hover:has-text('Total emissions of the building from energy')");

        // Waste table rows
        this.wasteGeneratedLink = page.locator("#summary-waste span.pointer.text-underline-hover:has-text('Generated')");
        this.wasteLandfillLink = page.locator("#summary-waste span.pointer.text-underline-hover:has-text('Sent to Landfill')");
        this.wasteIncineratedLink = page.locator("#summary-waste span.pointer.text-underline-hover:has-text('Incinerated')");
        this.wasteCompostedLink = page.locator("#summary-waste span.pointer.text-underline-hover:has-text('Composted')");
        this.wasteRecycledLink = page.locator("#summary-waste span.pointer.text-underline-hover:has-text('Recycled')");
        this.wasteReusedLink = page.locator("#summary-waste span.pointer.text-underline-hover:has-text('Reused')");

        // Other assessment tab contents
        this.energyTabContent = page.locator("#summary-energy");
        this.waterTabContent = page.locator("#summary-water");
        this.wasteTabContent = page.locator("#summary-waste");
    }

    /**
     * Tab visibility
     */
    @AutoStep
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return tabContent.isVisible();
    }

    @AutoStep
    public boolean isPageTitleVisible() {
        page.waitForLoadState();
        return pageTitle.isVisible();
    }

    @AutoStep
    public String getPageTitle() {
        page.waitForLoadState();
        return pageTitle.textContent().trim();
    }

    /**
     * Header action buttons
     */
    @AutoStep
    public boolean isShareButtonVisible() {
        page.waitForLoadState();
        return shareButton.isVisible();
    }

    @AutoStep
    public void clickShareButton() {
        page.waitForLoadState();
        shareButton.click();
        page.waitForTimeout(300);
    }

    @AutoStep
    public void clickCopyLink() {
        page.waitForLoadState();
        clickShareButton();
        copyLinkButton.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public boolean isDownloadButtonVisible() {
        page.waitForLoadState();
        return downloadButton.isVisible();
    }

    @AutoStep
    public void clickDownloadButton() {
        page.waitForLoadState();
        downloadButton.click();
        page.waitForTimeout(300);
    }

    @AutoStep
    public void downloadExcel() {
        page.waitForLoadState();
        clickDownloadButton();
        downloadExcelButton.click();
        page.waitForTimeout(1000);
    }

    @AutoStep
    public void downloadPdf() {
        page.waitForLoadState();
        clickDownloadButton();
        downloadPdfButton.click();
        page.waitForTimeout(1000);
    }

    /**
     * Sub-tab navigation
     */
    @AutoStep
    public void goToEmissionsTab() {
        page.waitForLoadState();
        emissionsTabButton.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void goToEnergyTab() {
        page.waitForLoadState();
        energyTabButton.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void goToWaterTab() {
        page.waitForLoadState();
        waterTabButton.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void goToWasteTab() {
        page.waitForLoadState();
        wasteTabButton.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public boolean isEmissionsTabActive() {
        page.waitForLoadState();
        return emissionsTabButton.getAttribute("class").contains("active");
    }

    @AutoStep
    public boolean isEnergyTabActive() {
        page.waitForLoadState();
        return energyTabButton.getAttribute("class").contains("active");
    }

    @AutoStep
    public boolean isWaterTabActive() {
        page.waitForLoadState();
        return waterTabButton.getAttribute("class").contains("active");
    }

    @AutoStep
    public boolean isWasteTabActive() {
        page.waitForLoadState();
        return wasteTabButton.getAttribute("class").contains("active");
    }

    /**
     * Emissions summary table visibility
     */
    @AutoStep
    public boolean isEmissionsSummaryTableVisible() {
        page.waitForLoadState();
        return emissionsSummaryTable.isVisible();
    }

    @AutoStep
    public boolean isEmissionsTabContentVisible() {
        page.waitForLoadState();
        return emissionsTabContent.isVisible();
    }

    /**
     * Get emission values from summary table
     * Returns value in the specified unit column (1 for KgCO2e, 2 for MtCO2e)
     */
    private String getEmissionValue(String rowLabel, int columnIndex) {
        page.waitForLoadState();
        Locator row = page.locator(String.format("tr:has(span:text-is('%s'))", rowLabel));
        Locator cell = row.locator(String.format("td:nth-child(%d)", columnIndex + 1)); // +1 because first column is label
        return cell.textContent().trim();
    }

    @AutoStep
    public String getScope1ValueKg() {
        return getEmissionValue("Scope 1", 2);
    }

    @AutoStep
    public String getScope1ValueMt() {
        return getEmissionValue("Scope 1", 3);
    }

    @AutoStep
    public String getScope2ValueKg() {
        return getEmissionValue("Scope 2", 2);
    }

    @AutoStep
    public String getScope2ValueMt() {
        return getEmissionValue("Scope 2", 3);
    }

    @AutoStep
    public String getScope3ValueKg() {
        return getEmissionValue("Scope 3", 2);
    }

    @AutoStep
    public String getScope3ValueMt() {
        return getEmissionValue("Scope 3", 3);
    }

    @AutoStep
    public String getTotalEmissionsValueKg() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(span:text('Total emissions of the building'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getTotalEmissionsValueMt() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(span:text('Total emissions of the building'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    @AutoStep
    public String getCarbonOffsetsValueKg() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(span:text('Emissions avoided by carbon offsets'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getCarbonOffsetsValueMt() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(span:text('Emissions avoided by carbon offsets'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    @AutoStep
    public String getCarbonRemovalValueKg() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(span:text('Emissions avoided by carbon removal strategies'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getCarbonRemovalValueMt() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(span:text('Emissions avoided by carbon removal strategies'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    @AutoStep
    public String getEmissionsToAvoidValueKg() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(div:text('Emissions to be avoided to get to net zero'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getEmissionsToAvoidValueMt() {
        page.waitForLoadState();
        Locator row = page.locator("tr:has(div:text('Emissions to be avoided to get to net zero'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    /**
     * Click on clickable links in summary table to navigate to source sections
     */
    @AutoStep
    public void clickScope1Link() {
        page.waitForLoadState();
        scope1Link.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickScope2Link() {
        page.waitForLoadState();
        scope2Link.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickScope3Link() {
        page.waitForLoadState();
        scope3Link.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickTotalEmissionsLink() {
        page.waitForLoadState();
        totalEmissionsLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickCarbonOffsetsLink() {
        page.waitForLoadState();
        carbonOffsetsLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickCarbonRemovalLink() {
        page.waitForLoadState();
        carbonRemovalLink.click();
        page.waitForTimeout(500);
    }

    /**
     * Check if other assessment tab contents are visible
     */
    @AutoStep
    public boolean isEnergyTabContentVisible() {
        page.waitForLoadState();
        return energyTabContent.isVisible();
    }

    @AutoStep
    public boolean isWaterTabContentVisible() {
        page.waitForLoadState();
        return waterTabContent.isVisible();
    }

    @AutoStep
    public boolean isWasteTabContentVisible() {
        page.waitForLoadState();
        return wasteTabContent.isVisible();
    }

    /**
     * Get all emission values as a formatted summary
     */
    @AutoStep
    public String getEmissionsSummary() {
        page.waitForLoadState();
        StringBuilder summary = new StringBuilder();
        summary.append("Scope 1: ").append(getScope1ValueKg()).append(" KgCO2e (").append(getScope1ValueMt()).append(" MtCO2e)\n");
        summary.append("Scope 2: ").append(getScope2ValueKg()).append(" KgCO2e (").append(getScope2ValueMt()).append(" MtCO2e)\n");
        summary.append("Scope 3: ").append(getScope3ValueKg()).append(" KgCO2e (").append(getScope3ValueMt()).append(" MtCO2e)\n");
        summary.append("Total: ").append(getTotalEmissionsValueKg()).append(" KgCO2e (").append(getTotalEmissionsValueMt()).append(" MtCO2e)\n");
        summary.append("Carbon Offsets: ").append(getCarbonOffsetsValueKg()).append(" KgCO2e (").append(getCarbonOffsetsValueMt()).append(" MtCO2e)\n");
        summary.append("Carbon Removal: ").append(getCarbonRemovalValueKg()).append(" KgCO2e (").append(getCarbonRemovalValueMt()).append(" MtCO2e)\n");
        summary.append("Emissions to Avoid: ").append(getEmissionsToAvoidValueKg()).append(" KgCO2e (").append(getEmissionsToAvoidValueMt()).append(" MtCO2e)");
        return summary.toString();
    }

    /**
     * Water summary table - Get values
     * All values are in KL/annum units
     */
    private String getWaterValue(String rowLabel) {
        page.waitForLoadState();
        Locator row = waterTabContent.locator(String.format("tr:has(span:text-is('%s'))", rowLabel));
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getWaterConsumptionValue() {
        return getWaterValue("Consumption");
    }

    @AutoStep
    public String getWaterSupplyValue() {
        return getWaterValue("Supply");
    }

    @AutoStep
    public String getWaterRainwaterValue() {
        return getWaterValue("Rainwater");
    }

    @AutoStep
    public String getWaterNoRainDaysValue() {
        page.waitForLoadState();
        Locator row = waterTabContent.locator("tr:has(span:text('During no-rain days, daily'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getWaterRainyDaysValue() {
        page.waitForLoadState();
        Locator row = waterTabContent.locator("tr:has(span:text('During rainy days, daily'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getWaterFreshwaterProvisionValue() {
        return getWaterValue("Freshwater Provision");
    }

    @AutoStep
    public String getAnnualFreshwaterRequirementValue() {
        page.waitForLoadState();
        Locator row = waterTabContent.locator("tr:has(span:text('Annual Freshwater Requirement'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    /**
     * Water summary table - Click navigation links
     */
    @AutoStep
    public void clickWaterConsumptionLink() {
        page.waitForLoadState();
        waterConsumptionLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWaterSupplyLink() {
        page.waitForLoadState();
        waterSupplyLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWaterRainwaterLink() {
        page.waitForLoadState();
        waterRainwaterLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWaterFreshwaterRequirementLink() {
        page.waitForLoadState();
        waterFreshwaterRequirementLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWaterNoRainDaysLink() {
        page.waitForLoadState();
        waterNoRainDaysLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWaterRainyDaysLink() {
        page.waitForLoadState();
        waterRainyDaysLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWaterFreshwaterProvisionLink() {
        page.waitForLoadState();
        waterFreshwaterProvisionLink.click();
        page.waitForTimeout(500);
    }

    /**
     * Get all water values as a formatted summary
     */
    @AutoStep
    public String getWaterSummary() {
        page.waitForLoadState();
        StringBuilder summary = new StringBuilder();
        summary.append("Consumption: ").append(getWaterConsumptionValue()).append(" KL/annum\n");
        summary.append("Supply: ").append(getWaterSupplyValue()).append(" KL/annum\n");
        summary.append("Rainwater: ").append(getWaterRainwaterValue()).append(" KL/annum\n");
        summary.append("Freshwater Requirement:\n");
        summary.append("  - No-rain days (daily): ").append(getWaterNoRainDaysValue()).append(" KL/annum\n");
        summary.append("  - Rainy days (daily): ").append(getWaterRainyDaysValue()).append(" KL/annum\n");
        summary.append("Freshwater Provision: ").append(getWaterFreshwaterProvisionValue()).append(" KL/annum\n");
        summary.append("Annual Freshwater Requirement (a-b-c): ").append(getAnnualFreshwaterRequirementValue()).append(" KL/annum");
        return summary.toString();
    }

    /**
     * Energy summary table - Get values
     * Values are in KgCO2e and MtCO2e units
     */
    private String getEnergyValue(String rowLabel, int columnIndex) {
        page.waitForLoadState();
        Locator row = energyTabContent.locator(String.format("tr:has(span:text-is('%s'))", rowLabel));
        return row.locator(String.format("td:nth-child(%d)", columnIndex + 1)).textContent().trim();
    }

    @AutoStep
    public String getEnergyScope1ValueKg() {
        return getEnergyValue("Scope 1", 2);
    }

    @AutoStep
    public String getEnergyScope1ValueMt() {
        return getEnergyValue("Scope 1", 3);
    }

    @AutoStep
    public String getEnergyScope2ValueKg() {
        return getEnergyValue("Scope 2", 2);
    }

    @AutoStep
    public String getEnergyScope2ValueMt() {
        return getEnergyValue("Scope 2", 3);
    }

    @AutoStep
    public String getEnergyTotalEmissionsValueKg() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Total emissions of the building from energy'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getEnergyTotalEmissionsValueMt() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Total emissions of the building from energy'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    @AutoStep
    public String getEnergyRenewableEnergyCertificatesValueKg() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Emissions reduced through Renewable Energy Certificates'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getEnergyRenewableEnergyCertificatesValueMt() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Emissions reduced through Renewable Energy Certificates'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    @AutoStep
    public String getEnergyReducedThroughStrategiesValueKg() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Emissions reduced through strategies'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getEnergyReducedThroughStrategiesValueMt() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Emissions reduced through strategies'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    @AutoStep
    public String getEnergyEmissionsToAvoidValueKg() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Emissions to be avoided to get to net zero'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getEnergyEmissionsToAvoidValueMt() {
        page.waitForLoadState();
        Locator row = energyTabContent.locator("tr:has(span:text('Emissions to be avoided to get to net zero'))");
        return row.locator("td:nth-child(3)").textContent().trim();
    }

    /**
     * Energy summary table - Click navigation links (only clickable ones)
     */
    @AutoStep
    public void clickEnergyScope1Link() {
        page.waitForLoadState();
        energyScope1Link.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickEnergyScope2Link() {
        page.waitForLoadState();
        energyScope2Link.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickEnergyTotalEmissionsLink() {
        page.waitForLoadState();
        energyTotalEmissionsLink.click();
        page.waitForTimeout(500);
    }

    /**
     * Get all energy values as a formatted summary
     */
    @AutoStep
    public String getEnergySummary() {
        page.waitForLoadState();
        StringBuilder summary = new StringBuilder();
        summary.append("Scope 1: ").append(getEnergyScope1ValueKg()).append(" KgCO2e (").append(getEnergyScope1ValueMt()).append(" MtCO2e)\n");
        summary.append("Scope 2: ").append(getEnergyScope2ValueKg()).append(" KgCO2e (").append(getEnergyScope2ValueMt()).append(" MtCO2e)\n");
        summary.append("Total emissions from energy (a+b): ").append(getEnergyTotalEmissionsValueKg()).append(" KgCO2e (").append(getEnergyTotalEmissionsValueMt()).append(" MtCO2e)\n");
        summary.append("Reduced through Renewable Energy Certificates: ").append(getEnergyRenewableEnergyCertificatesValueKg()).append(" KgCO2e (").append(getEnergyRenewableEnergyCertificatesValueMt()).append(" MtCO2e)\n");
        summary.append("Reduced through strategies: ").append(getEnergyReducedThroughStrategiesValueKg()).append(" KgCO2e (").append(getEnergyReducedThroughStrategiesValueMt()).append(" MtCO2e)\n");
        summary.append("Emissions to avoid (c-d-e): ").append(getEnergyEmissionsToAvoidValueKg()).append(" KgCO2e (").append(getEnergyEmissionsToAvoidValueMt()).append(" MtCO2e)");
        return summary.toString();
    }

    /**
     * Waste summary table - Get values
     * All values are in Tonnes units
     */
    private String getWasteValue(String rowLabel) {
        page.waitForLoadState();
        Locator row = wasteTabContent.locator(String.format("tr:has(span:text-is('%s'))", rowLabel));
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    @AutoStep
    public String getWasteGeneratedValue() {
        return getWasteValue("Generated");
    }

    @AutoStep
    public String getWasteLandfillValue() {
        return getWasteValue("Sent to Landfill");
    }

    @AutoStep
    public String getWasteIncineratedValue() {
        return getWasteValue("Incinerated");
    }

    @AutoStep
    public String getWasteCompostedValue() {
        return getWasteValue("Composted");
    }

    @AutoStep
    public String getWasteRecycledValue() {
        return getWasteValue("Recycled");
    }

    @AutoStep
    public String getWasteReusedValue() {
        return getWasteValue("Reused");
    }

    @AutoStep
    public String getWasteToBeReducedValue() {
        page.waitForLoadState();
        Locator row = wasteTabContent.locator("tr:has(span:text('Waste to be Reduced'))");
        return row.locator("td:nth-child(2)").textContent().trim();
    }

    /**
     * Waste summary table - Click navigation links
     */
    @AutoStep
    public void clickWasteGeneratedLink() {
        page.waitForLoadState();
        wasteGeneratedLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWasteLandfillLink() {
        page.waitForLoadState();
        wasteLandfillLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWasteIncineratedLink() {
        page.waitForLoadState();
        wasteIncineratedLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWasteCompostedLink() {
        page.waitForLoadState();
        wasteCompostedLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWasteRecycledLink() {
        page.waitForLoadState();
        wasteRecycledLink.click();
        page.waitForTimeout(500);
    }

    @AutoStep
    public void clickWasteReusedLink() {
        page.waitForLoadState();
        wasteReusedLink.click();
        page.waitForTimeout(500);
    }

    /**
     * Get all waste values as a formatted summary
     */
    @AutoStep
    public String getWasteSummary() {
        page.waitForLoadState();
        StringBuilder summary = new StringBuilder();
        summary.append("Generated: ").append(getWasteGeneratedValue()).append(" Tonnes\n");
        summary.append("Sent to Landfill: ").append(getWasteLandfillValue()).append(" Tonnes\n");
        summary.append("Incinerated: ").append(getWasteIncineratedValue()).append(" Tonnes\n");
        summary.append("Composted: ").append(getWasteCompostedValue()).append(" Tonnes\n");
        summary.append("Recycled: ").append(getWasteRecycledValue()).append(" Tonnes\n");
        summary.append("Reused: ").append(getWasteReusedValue()).append(" Tonnes\n");
        summary.append("Waste to be Reduced a-(d+e+f): ").append(getWasteToBeReducedValue()).append(" Tonnes");
        return summary.toString();
    }
}
