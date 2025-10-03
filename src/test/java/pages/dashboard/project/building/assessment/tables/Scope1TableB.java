package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope1TableB - Table B for Scope 1 Emissions (Refrigerants)
 * Extends base Scope1Table with specific column methods
 *
 * Columns: type, emission_factor_(kgco2e), consumption, unit
 */
public class Scope1TableB extends Scope1Table {

    public Scope1TableB(Page page) {
        super(page, "refrigerants");
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterType(int rowIndex, String value) {
        enterField("type", rowIndex, value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        enterField("emission_factor_(kgco2e)", rowIndex, value);
    }

    public void enterConsumption(int rowIndex, String value) {
        enterField("consumption", rowIndex, value);
    }

    public void selectUnit(int rowIndex, String value) {
        selectField("unit", rowIndex, value);
    }

    /**
     * Get values
     */
    public String getType(int rowIndex) {
        return getField("type", rowIndex);
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
    public void fillRow(int rowIndex, String type, String emissionFactor, String consumption, String unit) {
        enterType(rowIndex, type);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterConsumption(rowIndex, consumption);
        selectUnit(rowIndex, unit);
    }
}
