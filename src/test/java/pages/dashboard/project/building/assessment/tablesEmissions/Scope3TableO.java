package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;

/**
 * Scope3TableO - Table O for Scope 3 Emissions (Logistics & Supply)
 *
 * Columns: vehicle, type, fuel, emission_factor_(kgco2e), weight_(tonnes),
 * distance_(km), units
 */
public class Scope3TableO {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String VEHICLE_INPUT_PATTERN = "input[ftestcaseref='scope3_logistics_&_supply_vehicle_%d']";
    private static final String TYPE_INPUT_PATTERN = "input[ftestcaseref='scope3_logistics_&_supply_type_%d']";
    private static final String FUEL_INPUT_PATTERN = "input[ftestcaseref='scope3_logistics_&_supply_fuel_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope3_logistics_&_supply_emission_factor_(kgco2e)_%d']";
    private static final String WEIGHT_TONNES_INPUT_PATTERN = "input[ftestcaseref='scope3_logistics_&_supply_weight_(tonnes)_%d']";
    private static final String DISTANCE_KM_INPUT_PATTERN = "input[ftestcaseref='scope3_logistics_&_supply_distance_(km)_%d']";
    private static final String UNITS_SELECT_PATTERN = "select[ftestcaseref='scope3_logistics_&_supply_units_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope3_logistics_&_supply_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope3_Logistics_&_Supply_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope3_Logistics_&_Supply_table_tr_row_attach_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope3_Logistics_&_Supply_table_tr_row_trash_%d";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope3TableO(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope3_logistics_&_supply_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getVehicleInput(int rowIndex) {
        return page.locator(String.format(VEHICLE_INPUT_PATTERN, rowIndex));
    }

    private Locator getTypeInput(int rowIndex) {
        return page.locator(String.format(TYPE_INPUT_PATTERN, rowIndex));
    }

    private Locator getFuelInput(int rowIndex) {
        return page.locator(String.format(FUEL_INPUT_PATTERN, rowIndex));
    }

    private Locator getEmissionFactorInput(int rowIndex) {
        return page.locator(String.format(EMISSION_FACTOR_INPUT_PATTERN, rowIndex));
    }

    private Locator getWeightTonnesInput(int rowIndex) {
        return page.locator(String.format(WEIGHT_TONNES_INPUT_PATTERN, rowIndex));
    }

    private Locator getDistanceKmInput(int rowIndex) {
        return page.locator(String.format(DISTANCE_KM_INPUT_PATTERN, rowIndex));
    }

    private Locator getUnitsSelect(int rowIndex) {
        return page.locator(String.format(UNITS_SELECT_PATTERN, rowIndex));
    }

    private Locator getRowTotalLocator(int rowIndex) {
        return page.locator(String.format(ROW_TOTAL_PATTERN, rowIndex));
    }

    private Locator getAddRowButton(int rowIndex) {
        return page.locator(String.format(ADD_ROW_BUTTON_PATTERN, rowIndex));
    }

    private Locator getAttachButton(int rowIndex) {
        return page.locator(String.format(ATTACH_BUTTON_PATTERN, rowIndex));
    }

    private Locator getRemoveRowButton(int rowIndex) {
        return page.locator(String.format(REMOVE_ROW_BUTTON_PATTERN, rowIndex));
    }

    /**
     * Enter methods for specific columns
     */
    public void enterVehicle(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getVehicleInput(rowIndex), value);
    }

    public void enterType(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTypeInput(rowIndex), value);
    }

    public void enterFuel(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getFuelInput(rowIndex), value);
    }


    public void enterEmissionFactor(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getEmissionFactorInput(rowIndex), value);
    }

    public void enterWeightTonnes(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getWeightTonnesInput(rowIndex), value);
    }


    public void enterDistanceKm(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getDistanceKmInput(rowIndex), value);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        Locator unitsSelect = getUnitsSelect(rowIndex);
        unitsSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        unitsSelect.scrollIntoViewIfNeeded();
        unitsSelect.selectOption(value);
    }

    /**
     * Get values
     */
    public String getVehicle(int rowIndex) {
        return getVehicleInput(rowIndex).inputValue();
    }

    public String getType(int rowIndex) {
        return getTypeInput(rowIndex).inputValue();
    }

    public String getFuel(int rowIndex) {
        return getFuelInput(rowIndex).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return getEmissionFactorInput(rowIndex).inputValue();
    }

    public String getWeightTonnes(int rowIndex) {
        return getWeightTonnesInput(rowIndex).inputValue();
    }

    public String getDistanceKm(int rowIndex) {
        return getDistanceKmInput(rowIndex).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return getRowTotalLocator(rowIndex).inputValue();
    }

    public String getTableTotal() {
        return this.tableTotal.inputValue();
    }

    /**
     * Row operations
     */
    public void addRow(int currentRowIndex) {
        page.waitForLoadState();
        Locator addButton = getAddRowButton(currentRowIndex);
        addButton.waitFor();
        addButton.click();
        page.waitForTimeout(500);
    }

    public void removeRow(int rowIndex) {
        page.waitForLoadState();
        Locator removeButton = getRemoveRowButton(rowIndex);
        removeButton.waitFor();
        removeButton.click();
    }

    public void attach(int rowIndex) {
        page.waitForLoadState();
        Locator attachButton = getAttachButton(rowIndex);
        attachButton.waitFor();
        attachButton.click();
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String vehicle, String type, String fuel,
            String emissionFactor, String weightTonnes, String distanceKm, String units) {
        enterVehicle(rowIndex, vehicle);
        enterType(rowIndex, type);
        enterFuel(rowIndex, fuel);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterWeightTonnes(rowIndex, weightTonnes);
        enterDistanceKm(rowIndex, distanceKm);
        selectUnits(rowIndex, units);
    }
}
