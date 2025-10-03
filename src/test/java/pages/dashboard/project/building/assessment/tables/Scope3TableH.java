package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableH - Table H for Scope 3 Emissions (Waste Recycled)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: type_of_waste, emission_factor_(kgco2e), quantity_of_waste_recycled, unit
 */
public class Scope3TableH extends Scope3Table {

    public Scope3TableH(Page page) {
        super(page, "waste_recycled");
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

    public void enterQuantityOfWasteRecycled(int rowIndex, String value) {
        enterField("quantity_of_waste_recycled", rowIndex, value);
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

    public String getQuantityOfWasteRecycled(int rowIndex) {
        return getField("quantity_of_waste_recycled", rowIndex);
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String typeOfWaste, String emissionFactor, String quantityRecycled, String unit) {
        enterTypeOfWaste(rowIndex, typeOfWaste);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterQuantityOfWasteRecycled(rowIndex, quantityRecycled);
        selectUnit(rowIndex, unit);
    }
}
