package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;

import utils.AutoStep;
/**
 * Scope3TableL - Table L for Scope 3 Emissions (Business Travel)
 *
 * Columns: vehicle_type, vehicle_size, fuel, emission_factor_(kgco2e), total_distance, units
 */
public class Scope3TableL {
    protected final Page page;

    // Locator patterns defined once
    private static final String VEHICLE_TYPE_INPUT_PATTERN = "input[ftestcaseref='scope3_business_travel_vehicle_type_%d']";
    private static final String VEHICLE_SIZE_INPUT_PATTERN = "input[ftestcaseref='scope3_business_travel_vehicle_size_%d']";
    private static final String FUEL_INPUT_PATTERN = "input[ftestcaseref='scope3_business_travel_fuel_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope3_business_travel_emission_factor_(kgco2e)_%d']";
    private static final String TOTAL_DISTANCE_INPUT_PATTERN = "input[ftestcaseref='scope3_business_travel_total_distance_%d']";
    private static final String UNITS_SELECT_PATTERN = "select[ftestcaseref='scope3_business_travel_units_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope3_business_travel_total_emissions_(kgco2e)_%d']";

    // Table-level locators
    private final Locator tableTotal;

    public Scope3TableL(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope3_business_travel_total']");
    }

    // ========================================
    // Helper methods - return locators for dynamic rows
    // ========================================
    private Locator getVehicleTypeInput(int rowIndex) {
        return page.locator(String.format(VEHICLE_TYPE_INPUT_PATTERN, rowIndex));
    }

    private Locator getVehicleSizeInput(int rowIndex) {
        return page.locator(String.format(VEHICLE_SIZE_INPUT_PATTERN, rowIndex));
    }

    private Locator getFuelInput(int rowIndex) {
        return page.locator(String.format(FUEL_INPUT_PATTERN, rowIndex));
    }

    private Locator getEmissionFactorInput(int rowIndex) {
        return page.locator(String.format(EMISSION_FACTOR_INPUT_PATTERN, rowIndex));
    }

    private Locator getTotalDistanceInput(int rowIndex) {
        return page.locator(String.format(TOTAL_DISTANCE_INPUT_PATTERN, rowIndex));
    }

    private Locator getUnitsSelect(int rowIndex) {
        return page.locator(String.format(UNITS_SELECT_PATTERN, rowIndex));
    }

    private Locator getRowTotalLocator(int rowIndex) {
        return page.locator(String.format(ROW_TOTAL_PATTERN, rowIndex));
    }

    // ========================================
    // Public action methods (slower inputs with more wait time)
    // ========================================
    @AutoStep
    public void enterVehicleType(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getVehicleTypeInput(rowIndex), value);
    }

    @AutoStep
    public void enterVehicleSize(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getVehicleSizeInput(rowIndex), value);
    }

    @AutoStep
    public void enterFuel(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getFuelInput(rowIndex), value);
    }

    @AutoStep
    public void enterEmissionFactor(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getEmissionFactorInput(rowIndex), value);
    }

    @AutoStep
    public void enterTotalDistance(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTotalDistanceInput(rowIndex), value);
    }


    @AutoStep
    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        Locator unitsSelect = getUnitsSelect(rowIndex);
        unitsSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        unitsSelect.scrollIntoViewIfNeeded();
        unitsSelect.selectOption(value);
        page.waitForTimeout(500);
    }

    // ========================================
    // Public getter methods
    // ========================================
    @AutoStep
    public String getVehicleType(int rowIndex) {
        return getVehicleTypeInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getVehicleSize(int rowIndex) {
        return getVehicleSizeInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getFuel(int rowIndex) {
        return getFuelInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getEmissionFactor(int rowIndex) {
        return getEmissionFactorInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getTotalDistance(int rowIndex) {
        return getTotalDistanceInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getUnits(int rowIndex) {
        return getUnitsSelect(rowIndex).inputValue();
    }

    @AutoStep
    public String getRowTotal(int rowIndex) {
        return getRowTotalLocator(rowIndex).inputValue();
    }

    @AutoStep
    public String getTableTotal() {
        return tableTotal.inputValue();
    }

    // ========================================
    // Row operations
    // ========================================
    @AutoStep
    public void addRow(int rowIndex) {
        page.locator(String.format("[ftestcaseref='scope3_Business Travel_add_%d']", rowIndex)).click();
        page.waitForTimeout(500); // Wait for new row to be added
    }

    @AutoStep
    public void removeRow(int rowIndex) {
        page.locator(String.format("[ftestcaseref='scope3_Business Travel_remove_%d']", rowIndex)).click();
    }

    @AutoStep
    public void attach() {
        page.locator("[ftestcaseref='scope3_Business Travel_attach']").click();
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String vehicleType, String vehicleSize, String fuel,
                       String emissionFactor, String totalDistance, String units) {
        enterVehicleType(rowIndex, vehicleType);
        enterVehicleSize(rowIndex, vehicleSize);
        enterFuel(rowIndex, fuel);
        enterEmissionFactor(rowIndex, emissionFactor);
        enterTotalDistance(rowIndex, totalDistance);
        selectUnits(rowIndex, units);
    }
}
