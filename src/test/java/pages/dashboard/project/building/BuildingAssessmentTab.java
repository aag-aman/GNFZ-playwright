package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingAssessmentTab - Assessment tab for Building project
 * Handles navigation between assessment sub-sections:
 * - Net Zero Emissions
 * - Net Zero Waste
 * - Net Zero Energy
 * - Net Zero Water
 */
public class BuildingAssessmentTab {
    private final Page page;

    // Sub-tab navigation locators
    private final Locator netZeroEmissionsSubTab;
    private final Locator netZeroWasteSubTab;
    private final Locator netZeroEnergySubTab;
    private final Locator netZeroWaterSubTab;


    public BuildingAssessmentTab(Page page) {
        this.page = page;

        // Initialize sub-tab locators - TODO: Update with actual selectors
        this.netZeroEmissionsSubTab = page.locator("#net-zero-emission-assessment");
        this.netZeroWasteSubTab = page.locator("#net-zero-waste-assessment");
        this.netZeroEnergySubTab = page.locator("#net-zero-energy-assessment");
        this.netZeroWaterSubTab = page.locator("#net-zero-water-assessment");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        netZeroEmissionsSubTab.waitFor();
        return netZeroEmissionsSubTab.isVisible();
    }

    /**
     * Sub-tab Navigation
     */
    public void goToNetZeroEmissions() {
        page.waitForLoadState();
        netZeroEmissionsSubTab.waitFor();
        netZeroEmissionsSubTab.click();
    }

    public void goToNetZeroWaste() {
        page.waitForLoadState();
        netZeroWasteSubTab.waitFor();
        netZeroWasteSubTab.click();
    }

    public void goToNetZeroEnergy() {
        page.waitForLoadState();
        netZeroEnergySubTab.waitFor();
        netZeroEnergySubTab.click();
    }

    public void goToNetZeroWater() {
        page.waitForLoadState();
        netZeroWaterSubTab.waitFor();
        netZeroWaterSubTab.click();
    }

    /**
     * Sub-tab Visibility
     */
    public boolean isNetZeroEmissionsSubTabVisible() {
        netZeroEmissionsSubTab.waitFor();
        return netZeroEmissionsSubTab.isVisible();
    }

    public boolean isNetZeroWasteSubTabVisible() {
        netZeroWasteSubTab.waitFor();
        return netZeroWasteSubTab.isVisible();
    }

    public boolean isNetZeroEnergySubTabVisible() {
        netZeroEnergySubTab.waitFor();
        return netZeroEnergySubTab.isVisible();
    }

    public boolean isNetZeroWaterSubTabVisible() {
        netZeroWaterSubTab.waitFor();
        return netZeroWaterSubTab.isVisible();
    }

    /**
     * Active Sub-tab State
     */
    public boolean isNetZeroEmissionsSubTabActive() {
        // TODO: Update with actual active state check
        netZeroEmissionsSubTab.waitFor();
        return netZeroEmissionsSubTab.getAttribute("class").contains("active");
    }

    public boolean isNetZeroWasteSubTabActive() {
        netZeroWasteSubTab.waitFor();
        return netZeroWasteSubTab.getAttribute("class").contains("active");
    }

    public boolean isNetZeroEnergySubTabActive() {
        netZeroEnergySubTab.waitFor();
        return netZeroEnergySubTab.getAttribute("class").contains("active");
    }

    public boolean isNetZeroWaterSubTabActive() {
        netZeroWaterSubTab.waitFor();
        return netZeroWaterSubTab.getAttribute("class").contains("active");
    }
}
