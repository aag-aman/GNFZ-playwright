package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope1TableC - Table C for Scope 1 Emissions (Mobile Combustion)
 * Extends base Scope1Table with specific column methods
 *
 * Columns: fuel, emission_factor_(kgco2e), consumption, units
 */
public class Scope1TableC extends Scope1Table {

    public Scope1TableC(Page page) {
        super(page, "mobile_combustion");
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterFuel(int rowIndex, String value) {
        enterField("fuel", rowIndex, value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        enterField("emission_factor_(kgco2e)", rowIndex, value);
    }

    public void enterConsumption(int rowIndex, String value) {
        enterField("consumption", rowIndex, value);
    }

    public void selectUnits(int rowIndex, String value) {
        selectField("units", rowIndex, value);
    }

    /**
     * Get values
     */
    public String getFuel(int rowIndex) {
        return getField("fuel", rowIndex);
    }

    public String getEmissionFactor(int rowIndex) {
        return getField("emission_factor_(kgco2e)", rowIndex);
    }

    public String getConsumption(int rowIndex) {
        return getField("consumption", rowIndex);
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
