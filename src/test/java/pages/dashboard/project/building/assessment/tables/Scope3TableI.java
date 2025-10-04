package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Scope3TableI - Table I for Scope 3 Emissions (Waste Incinerated)
 *
 * Columns: type_of_waste, emission_factor_(kgco2e), quantity_of_waste_incinerated, unit
 */
public class Scope3TableI {
    protected final Page page;

    public Scope3TableI(Page page) {
        this.page = page;
    }

    /**
     * Enter methods for specific columns
     */
    public void enterTypeOfWaste(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_waste_incinerated_type_of_waste_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_waste_incinerated_type_of_waste_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_waste_incinerated_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_waste_incinerated_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void enterQuantityOfWasteIncinerated(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_waste_incinerated_quantity_of_waste_incinerated_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_waste_incinerated_quantity_of_waste_incinerated_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(value);
    }

    public void selectUnit(int rowIndex, String value) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_waste_incinerated_unit_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_waste_incinerated_unit_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).selectOption(value);
    }

    /**
     * Get values
     */
    public String getTypeOfWaste(int rowIndex) {
        return page.locator(String.format("'scope3_waste_incinerated_type_of_waste_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return page.locator(String.format("'scope3_waste_incinerated_emission_factor_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getQuantityOfWasteIncinerated(int rowIndex) {
        return page.locator(String.format("'scope3_waste_incinerated_quantity_of_waste_incinerated_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return page.locator(String.format("'scope3_waste_incinerated_rowtotal_%d'", rowIndex)).textContent();
    }

    public String getTableTotal() {
        return page.locator("'scope3_waste_incinerated_total'").textContent();
    }

    /**
     * Row operations
     */
    public void addRow() {
        page.locator("'scope3_waste_incinerated_add'").click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("'scope3_waste_incinerated_remove_%d'", rowIndex)).click();
    }

    public void attach() {
        page.locator("'scope3_waste_incinerated_attach'").click();
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
