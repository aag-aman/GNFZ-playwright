package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Scope3TableN - Table N for Scope 3 Emissions (Logistics & Supply)
 *
 * Columns: vehicle, type, fuel, emission_factor_(kgco2e), weight_(tonnes), distance_(km), units
 */
public class Scope3TableN {
    protected final Page page;

    public Scope3TableN(Page page) {
        this.page = page;
    }

    /**
     * Enter methods for specific columns
     */
    public void enterVehicle(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_logistics_&_supply_vehicle_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_logistics_&_supply_vehicle_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterType(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_logistics_&_supply_type_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_logistics_&_supply_type_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterFuel(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_logistics_&_supply_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_logistics_&_supply_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_logistics_&_supply_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_logistics_&_supply_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterWeightTonnes(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_logistics_&_supply_weight_(tonnes)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_logistics_&_supply_weight_(tonnes)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterDistanceKm(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_logistics_&_supply_distance_(km)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_logistics_&_supply_distance_(km)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_logistics_&_supply_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_logistics_&_supply_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).selectOption(value);
    }

    /**
     * Get values
     */
    public String getVehicle(int rowIndex) {
        return page.locator(String.format("'scope3_logistics_&_supply_vehicle_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getType(int rowIndex) {
        return page.locator(String.format("'scope3_logistics_&_supply_type_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getFuel(int rowIndex) {
        return page.locator(String.format("'scope3_logistics_&_supply_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return page.locator(String.format("'scope3_logistics_&_supply_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getWeightTonnes(int rowIndex) {
        return page.locator(String.format("'scope3_logistics_&_supply_weight_(tonnes)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getDistanceKm(int rowIndex) {
        return page.locator(String.format("'scope3_logistics_&_supply_distance_(km)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return page.locator(String.format("'scope3_logistics_&_supply_rowtotal_%d'", rowIndex)).textContent();
    }

    public String getTableTotal() {
        return page.locator("'scope3_logistics_&_supply_total'").textContent();
    }

    /**
     * Row operations
     */
    public void addRow() {
        page.locator("'scope3_logistics_&_supply_add'").click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("'scope3_logistics_&_supply_remove_%d'", rowIndex)).click();
    }

    public void attach() {
        page.locator("'scope3_logistics_&_supply_attach'").click();
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String vehicle, String type, String fuel,
                       String emissionFactor, String weightTonnes, String distanceKm, String units) {
        enterVehicle(rowIndex, vehicle);
        enterType(rowIndex, type);
        enterFuel(rowIndex, fuel);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterWeightTonnes(rowIndex, weightTonnes);
        enterDistanceKm(rowIndex, distanceKm);
        selectUnits(rowIndex, units);
    }
}
