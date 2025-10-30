package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import pages.dashboard.project.building.assessment.NetZeroEmissionsSection;
import pages.dashboard.project.building.assessment.NetZeroEnergySection;
import pages.dashboard.project.building.assessment.NetZeroWaterSection;
import pages.dashboard.project.building.assessment.NetZeroWasteSection;

import utils.AutoStep;
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

    private final NetZeroEmissionsSection emissionsSection;
    private final NetZeroEnergySection energySection;
    private final NetZeroWaterSection waterSection;
    private final NetZeroWasteSection wasteSection;


    public BuildingAssessmentTab(Page page) {
        this.page = page;

        // Initialize sub-tab locators - TODO: Update with actual selectors
        this.netZeroEmissionsSubTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Net Zero Emissions"));
        this.netZeroWasteSubTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Net Zero Waste"));
        this.netZeroEnergySubTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Net Zero Energy"));
        this.netZeroWaterSubTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Net Zero Water"));

        // Initialize section objects
        this.emissionsSection = new NetZeroEmissionsSection(page);
        this.energySection = new NetZeroEnergySection(page);
        this.waterSection = new NetZeroWaterSection(page);
        this.wasteSection = new NetZeroWasteSection(page);

        
    }

    /**
     * Tab visibility
     */
    @AutoStep
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        netZeroEmissionsSubTab.waitFor();
        return netZeroEmissionsSubTab.isVisible();
    }

    /**
     * Sub-tab Navigation
     */
    @AutoStep
    public void goToNetZeroEmissions() {
        page.waitForLoadState();
        netZeroEmissionsSubTab.waitFor();
        netZeroEmissionsSubTab.click();
    }

    @AutoStep
    public void goToNetZeroWaste() {
        page.waitForLoadState();
        netZeroWasteSubTab.waitFor();
        netZeroWasteSubTab.click();
    }

    @AutoStep
    public void goToNetZeroEnergy() {
        page.waitForLoadState();
        netZeroEnergySubTab.waitFor();
        netZeroEnergySubTab.click();
    }

    @AutoStep
    public void goToNetZeroWater() {
        page.waitForLoadState();
        netZeroWaterSubTab.waitFor();
        netZeroWaterSubTab.click();
    }

    /**
     * Sub-tab Visibility
     */
    @AutoStep
    public boolean isNetZeroEmissionsSubTabVisible() {
        netZeroEmissionsSubTab.waitFor();
        return netZeroEmissionsSubTab.isVisible();
    }

    @AutoStep
    public boolean isNetZeroWasteSubTabVisible() {
        netZeroWasteSubTab.waitFor();
        return netZeroWasteSubTab.isVisible();
    }

    @AutoStep
    public boolean isNetZeroEnergySubTabVisible() {
        netZeroEnergySubTab.waitFor();
        return netZeroEnergySubTab.isVisible();
    }

    @AutoStep
    public boolean isNetZeroWaterSubTabVisible() {
        netZeroWaterSubTab.waitFor();
        return netZeroWaterSubTab.isVisible();
    }

    /**
     * Active Sub-tab State
     */
    @AutoStep
    public boolean isNetZeroEmissionsSubTabActive() {
        // TODO: Update with actual active state check
        netZeroEmissionsSubTab.waitFor();
        return netZeroEmissionsSubTab.getAttribute("class").contains("active");
    }

    @AutoStep
    public boolean isNetZeroWasteSubTabActive() {
        netZeroWasteSubTab.waitFor();
        return netZeroWasteSubTab.getAttribute("class").contains("active");
    }

    @AutoStep
    public boolean isNetZeroEnergySubTabActive() {
        netZeroEnergySubTab.waitFor();
        return netZeroEnergySubTab.getAttribute("class").contains("active");
    }

    @AutoStep
    public boolean isNetZeroWaterSubTabActive() {
        netZeroWaterSubTab.waitFor();
        return netZeroWaterSubTab.getAttribute("class").contains("active");
    }

    /**
     * Get section objects for interacting with sub-tab content
     */
    @AutoStep
    public NetZeroEmissionsSection getNetZeroEmissionsSection() {
        return emissionsSection;
    }

    @AutoStep
    public NetZeroEnergySection getNetZeroEnergySection() {
        return energySection;
    }

    @AutoStep
    public NetZeroWaterSection getNetZeroWaterSection() {
        return waterSection;
    }

    @AutoStep
    public NetZeroWasteSection getNetZeroWasteSection() {
        return wasteSection;
    }
}
