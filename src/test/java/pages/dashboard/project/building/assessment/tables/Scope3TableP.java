package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Scope3TableP - Table P for Scope 3 Emissions (Recycled Materials)
 *
 * Columns: type_of_material, emission_factor_(kgco2e), quantity, units
 */
public class Scope3TableP {
    protected final Page page;

    public Scope3TableP(Page page) {
        this.page = page;
    }

    /**
     * Enter methods for specific columns
     */
    public void enterTypeOfMaterial(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_recycled_materials_type_of_material_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_recycled_materials_type_of_material_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_recycled_materials_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_recycled_materials_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterQuantity(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_recycled_materials_quantity_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_recycled_materials_quantity_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_recycled_materials_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_recycled_materials_units_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).selectOption(value);
    }

    /**
     * Get values
     */
    public String getTypeOfMaterial(int rowIndex) {
        return page.locator(String.format("'scope3_recycled_materials_type_of_material_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return page.locator(String.format("'scope3_recycled_materials_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getQuantity(int rowIndex) {
        return page.locator(String.format("'scope3_recycled_materials_quantity_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return page.locator(String.format("'scope3_recycled_materials_rowtotal_%d'", rowIndex)).textContent();
    }

    public String getTableTotal() {
        return page.locator("'scope3_recycled_materials_total'").textContent();
    }

    /**
     * Row operations
     */
    public void addRow() {
        page.locator("'scope3_recycled_materials_add'").click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("'scope3_recycled_materials_remove_%d'", rowIndex)).click();
    }

    public void attach() {
        page.locator("'scope3_recycled_materials_attach'").click();
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
