package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableP - Table P for Scope 3 Emissions (Recycled Materials)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: type_of_material, emission_factor_(kgco2e), quantity, units
 */
public class Scope3TableP extends Scope3Table {

    public Scope3TableP(Page page) {
        super(page, "recycled_materials");
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterTypeOfMaterial(int rowIndex, String value) {
        enterField("type_of_material", rowIndex, value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        enterField("emission_factor_(kgco2e)", rowIndex, value);
    }

    public void enterQuantity(int rowIndex, String value) {
        enterField("quantity", rowIndex, value);
    }

    public void selectUnits(int rowIndex, String value) {
        selectField("units", rowIndex, value);
    }

    /**
     * Get values
     */
    public String getTypeOfMaterial(int rowIndex) {
        return getField("type_of_material", rowIndex);
    }

    public String getEmissionFactor(int rowIndex) {
        return getField("emission_factor_(kgco2e)", rowIndex);
    }

    public String getQuantity(int rowIndex) {
        return getField("quantity", rowIndex);
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String typeOfMaterial, String emissionFactor, String quantity, String units) {
        enterTypeOfMaterial(rowIndex, typeOfMaterial);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterQuantity(rowIndex, quantity);
        selectUnits(rowIndex, units);
    }
}
