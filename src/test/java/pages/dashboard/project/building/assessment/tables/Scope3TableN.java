package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableN - Table N for Scope 3 Emissions (Logistics & Supply)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: vehicle, type, fuel, emission_factor_(kgco2e), weight_(tonnes), distance_(km), units
 */
public class Scope3TableN extends Scope3Table {

    public Scope3TableN(Page page) {
        super(page, "logistics_&_supply");
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterVehicle(int rowIndex, String value) {
        enterField("vehicle", rowIndex, value);
    }

    public void enterType(int rowIndex, String value) {
        enterField("type", rowIndex, value);
    }

    public void enterFuel(int rowIndex, String value) {
        enterField("fuel", rowIndex, value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        enterField("emission_factor_(kgco2e)", rowIndex, value);
    }

    public void enterWeightTonnes(int rowIndex, String value) {
        enterField("weight_(tonnes)", rowIndex, value);
    }

    public void enterDistanceKm(int rowIndex, String value) {
        enterField("distance_(km)", rowIndex, value);
    }

    public void selectUnits(int rowIndex, String value) {
        selectField("units", rowIndex, value);
    }

    /**
     * Get values
     */
    public String getVehicle(int rowIndex) {
        return getField("vehicle", rowIndex);
    }

    public String getType(int rowIndex) {
        return getField("type", rowIndex);
    }

    public String getFuel(int rowIndex) {
        return getField("fuel", rowIndex);
    }

    public String getEmissionFactor(int rowIndex) {
        return getField("emission_factor_(kgco2e)", rowIndex);
    }

    public String getWeightTonnes(int rowIndex) {
        return getField("weight_(tonnes)", rowIndex);
    }

    public String getDistanceKm(int rowIndex) {
        return getField("distance_(km)", rowIndex);
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
