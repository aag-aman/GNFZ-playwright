package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableG - Table G for Scope 3 Emissions (Composed Waste)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: type_of_waste, emission_factor_(kgco2e), quantity_of_waste_composted, unit
 */
public class Scope3TableG extends Scope3Table {

    public Scope3TableG(Page page) {
        super(page, "composed_waste");
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterTypeOfWaste(int rowIndex, String value) {
        enterField("type_of_waste", rowIndex, value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        enterField("emission_factor_(kgco2e)", rowIndex, value);
    }

    public void enterQuantityOfWasteComposted(int rowIndex, String value) {
        enterField("quantity_of_waste_composted", rowIndex, value);
    }

    public void selectUnit(int rowIndex, String value) {
        selectField("unit", rowIndex, value);
    }

    /**
     * Get values
     */
    public String getTypeOfWaste(int rowIndex) {
        return getField("type_of_waste", rowIndex);
    }

    public String getEmissionFactor(int rowIndex) {
        return getField("emission_factor_(kgco2e)", rowIndex);
    }

    public String getQuantityOfWasteComposted(int rowIndex) {
        return getField("quantity_of_waste_composted", rowIndex);
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String typeOfWaste, String emissionFactor, String quantityComposted, String unit) {
        enterTypeOfWaste(rowIndex, typeOfWaste);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterQuantityOfWasteComposted(rowIndex, quantityComposted);
        selectUnit(rowIndex, unit);
    }
}
