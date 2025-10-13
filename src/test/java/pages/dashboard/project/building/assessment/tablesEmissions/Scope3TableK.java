package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;

/**
 * Scope3TableK - Table K for Scope 3 Emissions (Employee Commute)
 *
 * Columns: vehicle_type, vehicle_size, fuel, emission_factor_(kgco2e),
 * total_distance, units
 */
public class Scope3TableK {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String VEHICLE_TYPE_INPUT_PATTERN = "input[ftestcaseref='scope3_employee_commute_vehicle_type_%d']";
    private static final String VEHICLE_SIZE_INPUT_PATTERN = "input[ftestcaseref='scope3_employee_commute_vehicle_size_%d']";
    private static final String FUEL_INPUT_PATTERN = "input[ftestcaseref='scope3_employee_commute_fuel_%d']";
    private static final String EMISSION_FACTOR_INPUT_PATTERN = "input[ftestcaseref='scope3_employee_commute_emission_factor_(kgco2e)_%d']";
    private static final String TOTAL_DISTANCE_INPUT_PATTERN = "input[ftestcaseref='scope3_employee_commute_total_distance_%d']";
    private static final String UNITS_SELECT_PATTERN = "select[ftestcaseref='scope3_employee_commute_units_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope3_employee_commute_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "[ftestcaseref='scope3_Employee Commute_add_%d']";
    private static final String ATTACH_BUTTON_PATTERN = "[ftestcaseref='scope3_Employee Commute_attach_%d']";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "[ftestcaseref='scope3_Employee Commute_remove_%d']";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope3TableK(Page page) {
        this.page = page;
        // Initialize only table-level locators (table total is shared across all rows)
        this.tableTotal = page.locator("input[ftestcaseref='scope3_employee_commute_total']");
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
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
    public void enterVehicleType(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getVehicleTypeInput(rowIndex), value);
    }

    public void enterVehicleSize(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getVehicleSizeInput(rowIndex), value);
    }

    public void enterFuel(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getFuelInput(rowIndex), value);
    }

    public void enterEmissionFactor(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getEmissionFactorInput(rowIndex), value);
    }

    public void enterTotalDistance(int rowIndex, String value) {
        InputHelper.humanizedInput(page, getTotalDistanceInput(rowIndex), value);
    }

    public void selectUnits(int rowIndex, String value) {
        page.waitForLoadState();
        Locator unitsSelect = getUnitsSelect(rowIndex);
        unitsSelect.waitFor(
                new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        unitsSelect.scrollIntoViewIfNeeded();
        unitsSelect.selectOption(value);
        page.waitForTimeout(500);
    }

    /**
     * Get values
     */
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
        page.waitForTimeout(500); // Wait for new row to be added
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
