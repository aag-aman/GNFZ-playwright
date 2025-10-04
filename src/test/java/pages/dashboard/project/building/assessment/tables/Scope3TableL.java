package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Scope3TableL - Table L for Scope 3 Emissions (Business Travel)
 *
 * Columns: vehicle_type, vehicle_size, fuel, emission_factor_(kgco2e), total_distance, units
 */
public class Scope3TableL {
    protected final Page page;

    public Scope3TableL(Page page) {
        this.page = page;
    }

    /**
     * Enter methods for specific columns
     */
    public void enterVehicleType(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_business_travel_vehicle_type_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_business_travel_vehicle_type_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterVehicleSize(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_business_travel_vehicle_size_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_business_travel_vehicle_size_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterFuel(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_business_travel_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_business_travel_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_business_travel_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_business_travel_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterTotalDistance(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_business_travel_total_distance_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_business_travel_total_distance_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_business_travel_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_business_travel_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).selectOption(value);
    }

    /**
     * Get values
     */
    public String getVehicleType(int rowIndex) {
        return page.locator(String.format("'scope3_business_travel_vehicle_type_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getVehicleSize(int rowIndex) {
        return page.locator(String.format("'scope3_business_travel_vehicle_size_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getFuel(int rowIndex) {
        return page.locator(String.format("'scope3_business_travel_fuel_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return page.locator(String.format("'scope3_business_travel_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getTotalDistance(int rowIndex) {
        return page.locator(String.format("'scope3_business_travel_total_distance_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return page.locator(String.format("'scope3_business_travel_rowtotal_%d'", rowIndex)).textContent();
    }

    public String getTableTotal() {
        return page.locator("'scope3_business_travel_total'").textContent();
    }

    /**
     * Row operations
     */
    public void addRow() {
        page.locator("'scope3_business_travel_add'").click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("'scope3_business_travel_remove_%d'", rowIndex)).click();
    }

    public void attach() {
        page.locator("'scope3_business_travel_attach'").click();
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String vehicleType, String vehicleSize, String fuel,
                       String emissionFactor, String totalDistance, String units) {
        enterVehicleType(rowIndex, vehicleType);
        enterVehicleSize(rowIndex, vehicleSize);
        enterFuel(rowIndex, fuel);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterTotalDistance(rowIndex, totalDistance);
        selectUnits(rowIndex, units);
    }
}
