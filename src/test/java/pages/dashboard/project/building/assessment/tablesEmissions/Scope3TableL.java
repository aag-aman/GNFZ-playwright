package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

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
    public void enterVehicleType(int rowIndex, String value) {
        page.waitForLoadState();
        Locator vehicleTypeInput = getVehicleTypeInput(rowIndex);
        vehicleTypeInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        vehicleTypeInput.scrollIntoViewIfNeeded();
        vehicleTypeInput.click();
        page.waitForTimeout(100);
        vehicleTypeInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(100));
        page.waitForTimeout(500);
        page.keyboard().press("Enter");
        page.waitForTimeout(1500);
    }

    public void enterVehicleSize(int rowIndex, String value) {
        page.waitForLoadState();
        Locator vehicleSizeInput = getVehicleSizeInput(rowIndex);
        vehicleSizeInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        vehicleSizeInput.scrollIntoViewIfNeeded();
        vehicleSizeInput.click();
        page.waitForTimeout(100);
        vehicleSizeInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(100));
        page.waitForTimeout(500);
        page.keyboard().press("Enter");
        page.waitForTimeout(1500);
    }

    public void enterFuel(int rowIndex, String value) {
        page.waitForLoadState();
        Locator fuelInput = getFuelInput(rowIndex);
        fuelInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        fuelInput.scrollIntoViewIfNeeded();
        fuelInput.click();
        page.waitForTimeout(100);
        fuelInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(100));
        page.waitForTimeout(500);
        page.keyboard().press("Enter");
        page.waitForTimeout(1500);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        page.waitForLoadState();
        Locator emissionFactorInput = getEmissionFactorInput(rowIndex);
        emissionFactorInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        emissionFactorInput.scrollIntoViewIfNeeded();

        // Slower input
        emissionFactorInput.click();
        emissionFactorInput.clear();
        emissionFactorInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(50));

        // Defocus and wait longer
        emissionFactorInput.blur();
        page.waitForTimeout(1500);
    }

    public void enterTotalDistance(int rowIndex, String value) {
        page.waitForLoadState();
        Locator totalDistanceInput = getTotalDistanceInput(rowIndex);
        totalDistanceInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        totalDistanceInput.scrollIntoViewIfNeeded();

        // Slower input
        totalDistanceInput.click();
        totalDistanceInput.clear();
        totalDistanceInput.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(50));

        // Defocus and wait longer for calculation
        totalDistanceInput.blur();
        page.waitForTimeout(1500);
    }

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
    public String getVehicleType(int rowIndex) {
        return getVehicleTypeInput(rowIndex).inputValue();
    }

    public String getVehicleSize(int rowIndex) {
        return getVehicleSizeInput(rowIndex).inputValue();
    }

    public String getFuel(int rowIndex) {
        return getFuelInput(rowIndex).inputValue();
    }

    public String getEmissionFactor(int rowIndex) {
        return getEmissionFactorInput(rowIndex).inputValue();
    }

    public String getTotalDistance(int rowIndex) {
        return getTotalDistanceInput(rowIndex).inputValue();
    }

    public String getUnits(int rowIndex) {
        return getUnitsSelect(rowIndex).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return getRowTotalLocator(rowIndex).inputValue();
    }

    public String getTableTotal() {
        return tableTotal.inputValue();
    }

    // ========================================
    // Row operations
    // ========================================
    public void addRow(int rowIndex) {
        page.locator(String.format("[ftestcaseref='scope3_Business Travel_add_%d']", rowIndex)).click();
        page.waitForTimeout(500); // Wait for new row to be added
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("[ftestcaseref='scope3_Business Travel_remove_%d']", rowIndex)).click();
    }

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
