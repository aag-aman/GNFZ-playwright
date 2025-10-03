package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;

/**
 * Scope3TableR - Table R for Scope 3 Emissions
 * Extends base Scope3Table with specific column methods
 *
 * TODO: CLARIFY - User provided mixed ftestcaseref prefixes:
 * - type_of_material uses: scope3_reused_materials_type_of_material_0
 * - Other fields use: scope3_recycled_materials_*_0
 * - Table total: scope3_recycled_materials_total
 *
 * Currently using "recycled_materials" as table prefix based on majority of references.
 * This may need adjustment based on actual application behavior.
 *
 * Columns: type_of_material, emission_factor_(kgco2e), quantity, units
 */
public class Scope3TableR extends Scope3Table {

    public Scope3TableR(Page page) {
        super(page, "recycled_materials");  // Using this prefix - may need adjustment
    }

    /**
     * Convenience methods for specific columns
     */
    public void enterTypeOfMaterial(int rowIndex, String value) {
        // Note: User data shows this might use "reused_materials" prefix
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
