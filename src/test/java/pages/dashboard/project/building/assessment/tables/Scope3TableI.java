package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableI - Table I for Scope 3 Emissions (Waste Incinerated)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: type_of_waste, emission_factor_(kgco2e), quantity_of_waste_incinerated, unit
 */
public class Scope3TableI extends Scope3Table {

    public Scope3TableI(Page page) {
        super(page, "waste_incinerated");
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

    public void enterQuantityOfWasteIncinerated(int rowIndex, String value) {
        enterField("quantity_of_waste_incinerated", rowIndex, value);
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

    public String getQuantityOfWasteIncinerated(int rowIndex) {
        return getField("quantity_of_waste_incinerated", rowIndex);
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String typeOfWaste, String emissionFactor, String quantityIncinerated, String unit) {
        enterTypeOfWaste(rowIndex, typeOfWaste);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterQuantityOfWasteIncinerated(rowIndex, quantityIncinerated);
        selectUnit(rowIndex, unit);
    }
}
