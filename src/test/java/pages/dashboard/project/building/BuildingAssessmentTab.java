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
        this.netZeroEmissionsSubTab = page.locator("BOILERPLATE_NET_ZERO_EMISSIONS_SUBTAB");
        this.netZeroWasteSubTab = page.locator("BOILERPLATE_NET_ZERO_WASTE_SUBTAB");
        this.netZeroEnergySubTab = page.locator("BOILERPLATE_NET_ZERO_ENERGY_SUBTAB");
        this.netZeroWaterSubTab = page.locator("BOILERPLATE_NET_ZERO_WATER_SUBTAB");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return netZeroEmissionsSubTab.isVisible();
    }

    /**
     * Sub-tab Navigation
     */
    public void goToNetZeroEmissions() {
        page.waitForLoadState();
        netZeroEmissionsSubTab.click();
    }

    public void goToNetZeroWaste() {
        page.waitForLoadState();
        netZeroWasteSubTab.click();
    }

    public void goToNetZeroEnergy() {
        page.waitForLoadState();
        netZeroEnergySubTab.click();
    }

    public void goToNetZeroWater() {
        page.waitForLoadState();
        netZeroWaterSubTab.click();
    }

    /**
     * Sub-tab Visibility
     */
    public boolean isNetZeroEmissionsSubTabVisible() {
        return netZeroEmissionsSubTab.isVisible();
    }

    public boolean isNetZeroWasteSubTabVisible() {
        return netZeroWasteSubTab.isVisible();
    }

    public boolean isNetZeroEnergySubTabVisible() {
        return netZeroEnergySubTab.isVisible();
    }

    public boolean isNetZeroWaterSubTabVisible() {
        return netZeroWaterSubTab.isVisible();
    }

    /**
     * Active Sub-tab State
     */
    public boolean isNetZeroEmissionsSubTabActive() {
        // TODO: Update with actual active state check
        return netZeroEmissionsSubTab.getAttribute("class").contains("active");
    }

    public boolean isNetZeroWasteSubTabActive() {
        return netZeroWasteSubTab.getAttribute("class").contains("active");
    }

    public boolean isNetZeroEnergySubTabActive() {
        return netZeroEnergySubTab.getAttribute("class").contains("active");
    }

    public boolean isNetZeroWaterSubTabActive() {
        return netZeroWaterSubTab.getAttribute("class").contains("active");
    }
}
