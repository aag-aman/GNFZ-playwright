package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableE - Table E for Scope 3 Emissions (Water)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: activity, emission_factor_(kgco2e), consumption, unit
 */
public class Scope3TableE extends Scope3Table {

    public Scope3TableE(Page page) {
        super(page, "water");
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterActivity(int rowIndex, String value) {
        enterField("activity", rowIndex, value);
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
    public String getActivity(int rowIndex) {
        return getField("activity", rowIndex);
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
    public void fillRow(int rowIndex, String activity, String emissionFactor, String consumption, String unit) {
        enterActivity(rowIndex, activity);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterConsumption(rowIndex, consumption);
        selectUnit(rowIndex, unit);
    }
}
