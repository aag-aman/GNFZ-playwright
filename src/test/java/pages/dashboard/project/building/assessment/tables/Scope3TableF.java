package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableF - Table F for Scope 3 Emissions (Waste Disposal)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: type_of_waste, emission_factor_(kgco2e), quantity_of_waste_generated, quantity_of_waste_sent_to_landfill, unit
 */
public class Scope3TableF extends Scope3Table {

    public Scope3TableF(Page page) {
        super(page, "waste_disposal");
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

    public void enterQuantityOfWasteGenerated(int rowIndex, String value) {
        enterField("quantity_of_waste_generated", rowIndex, value);
    }

    public void enterQuantityOfWasteSentToLandfill(int rowIndex, String value) {
        enterField("quantity_of_waste_sent_to_landfill", rowIndex, value);
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

    public String getQuantityOfWasteGenerated(int rowIndex) {
        return getField("quantity_of_waste_generated", rowIndex);
    }

    public String getQuantityOfWasteSentToLandfill(int rowIndex) {
        return getField("quantity_of_waste_sent_to_landfill", rowIndex);
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String typeOfWaste, String emissionFactor, String quantityGenerated, String quantityToLandfill, String unit) {
        enterTypeOfWaste(rowIndex, typeOfWaste);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterQuantityOfWasteGenerated(rowIndex, quantityGenerated);
        enterQuantityOfWasteSentToLandfill(rowIndex, quantityToLandfill);
        selectUnit(rowIndex, unit);
    }
}
