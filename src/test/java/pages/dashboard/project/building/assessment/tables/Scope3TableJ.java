package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Scope3TableJ - Table J for Scope 3 Emissions (WTT - Well-to-Tank)
 *
 * Columns: fuel, emission_factor_(kgco2e), consumption, units
 */
public class Scope3TableJ {
    protected final Page page;

    public Scope3TableJ(Page page) {
        this.page = page;
    }

    /**
     * Enter methods for specific columns
     */
    public void enterFuel(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_wtt_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_wtt_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_wtt_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_wtt_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterConsumption(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_wtt_consumption_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_wtt_consumption_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_wtt_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_wtt_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).selectOption(value);
    }

    /**
     * Get values
     */
    public String getFuel(int rowIndex) {
        return page.locator(String.format("'scope3_wtt_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return page.locator(String.format("'scope3_wtt_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getConsumption(int rowIndex) {
        return page.locator(String.format("'scope3_wtt_consumption_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return page.locator(String.format("'scope3_wtt_rowtotal_%d'", rowIndex)).textContent();
    }

    public String getTableTotal() {
        return page.locator("'scope3_wtt_total'").textContent();
    }

    /**
     * Row operations
     */
    public void addRow() {
        page.locator("'scope3_wtt_add'").click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("'scope3_wtt_remove_%d'", rowIndex)).click();
    }

    public void attach() {
        page.locator("'scope3_wtt_attach'").click();
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String fuel, String emissionFactor, String consumption, String units) {
        enterFuel(rowIndex, fuel);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterConsumption(rowIndex, consumption);
        selectUnits(rowIndex, units);
    }
}
