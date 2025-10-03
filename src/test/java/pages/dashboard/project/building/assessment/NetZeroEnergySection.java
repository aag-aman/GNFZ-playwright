package pages.dashboard.project.building.assessment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * NetZeroEnergySection - Net Zero Energy section in Assessment tab
 * Handles energy-related data entry
 */
public class NetZeroEnergySection {
    private final Page page;

    // Form field locators - TODO: Add actual fields
    private final Locator sectionContainer;
    private final Locator energySourceField;
    private final Locator energyConsumptionField;
    private final Locator energyUnitDropdown;
    private final Locator saveButton;

    public NetZeroEnergySection(Page page) {
        this.page = page;

        // Initialize locators - TODO: Update with actual selectors
        this.sectionContainer = page.locator("BOILERPLATE_ENERGY_SECTION");
        this.energySourceField = page.locator("BOILERPLATE_ENERGY_SOURCE");
        this.energyConsumptionField = page.locator("BOILERPLATE_ENERGY_CONSUMPTION");
        this.energyUnitDropdown = page.locator("BOILERPLATE_ENERGY_UNIT");
        this.saveButton = page.locator("BOILERPLATE_SAVE_BUTTON");
    }

    /**
     * Section visibility
     */
    public boolean isSectionDisplayed() {
        page.waitForLoadState();
        return sectionContainer.isVisible();
    }

    /**
     * Data entry methods
     */
    public void enterEnergySource(String source) {
        page.waitForLoadState();
        energySourceField.fill(source);
    }

    public void enterEnergyConsumption(String consumption) {
        page.waitForLoadState();
        energyConsumptionField.fill(consumption);
    }

    public void selectEnergyUnit(String unit) {
        page.waitForLoadState();
        energyUnitDropdown.selectOption(unit);
    }

    public void clickSave() {
        page.waitForLoadState();
        saveButton.click();
    }

    /**
     * Field visibility
     */
    public boolean isEnergySourceFieldVisible() {
        return energySourceField.isVisible();
    }

    public boolean isEnergyConsumptionFieldVisible() {
        return energyConsumptionField.isVisible();
    }

    // TODO: Add more energy-specific methods as needed
}
