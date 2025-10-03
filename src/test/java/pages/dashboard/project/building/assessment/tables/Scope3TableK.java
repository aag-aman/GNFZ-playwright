package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableK - Table K for Scope 3 Emissions (Employee Commute)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: vehicle_type, vehicle_size, fuel, emission_factor_(kgco2e), total_distance, units
 */
public class Scope3TableK extends Scope3Table {

    public Scope3TableK(Page page) {
        super(page, "employee_commute");
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterVehicleType(int rowIndex, String value) {
        enterField("vehicle_type", rowIndex, value);
    }

    public void enterVehicleSize(int rowIndex, String value) {
        enterField("vehicle_size", rowIndex, value);
    }

    public void enterFuel(int rowIndex, String value) {
        enterField("fuel", rowIndex, value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        enterField("emission_factor_(kgco2e)", rowIndex, value);
    }

    public void enterTotalDistance(int rowIndex, String value) {
        enterField("total_distance", rowIndex, value);
    }

    public void selectUnits(int rowIndex, String value) {
        selectField("units", rowIndex, value);
    }

    /**
     * Get values
     */
    public String getVehicleType(int rowIndex) {
        return getField("vehicle_type", rowIndex);
    }

    public String getVehicleSize(int rowIndex) {
        return getField("vehicle_size", rowIndex);
    }

    public String getFuel(int rowIndex) {
        return getField("fuel", rowIndex);
    }

    public String getEmissionFactor(int rowIndex) {
        return getField("emission_factor_(kgco2e)", rowIndex);
    }

    public String getTotalDistance(int rowIndex) {
        return getField("total_distance", rowIndex);
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
