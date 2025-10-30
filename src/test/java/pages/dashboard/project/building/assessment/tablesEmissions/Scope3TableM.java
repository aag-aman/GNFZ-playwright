package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.InputHelper;
import utils.WaitHelper;

import utils.AutoStep;
/**
 * Scope3TableM - Table M for Scope 3 Emissions (Flights)
 *
 * Columns: origin, destination, class, trip_type, no_of_passengers, total_emissions_(kgco2e)
 * Note: Origin and destination are autocomplete fields (IATA codes)
 * Note: Total emissions is manually entered (auto-calculation has issues)
 */
public class Scope3TableM {
    protected final Page page;

    // Locator patterns (defined once, reused for all rows)
    private static final String ORIGIN_INPUT_PATTERN = "input[ftestcaseref='scope3_flights_origin_%d']";
    private static final String DESTINATION_INPUT_PATTERN = "input[ftestcaseref='scope3_flights_destination_%d']";
    private static final String CLASS_SELECT_PATTERN = "select[ftestcaseref='scope3_flights_class_%d']";
    private static final String TRIP_TYPE_SELECT_PATTERN = "select[ftestcaseref='scope3_flights_trip_type_%d']";
    private static final String NO_OF_PASSENGERS_INPUT_PATTERN = "input[ftestcaseref='scope3_flights_no_of_passengers_%d']";
    private static final String TOTAL_EMISSIONS_INPUT_PATTERN = "input[ftestcaseref='scope3_flights_total_emissions_(kgco2e)_%d']";
    private static final String ROW_TOTAL_PATTERN = "input[ftestcaseref='scope3_flights_total_emissions_(kgco2e)_%d']";
    private static final String ADD_ROW_BUTTON_PATTERN = "#scope3_Flights_table_tr_row_add_%d";
    private static final String ATTACH_BUTTON_PATTERN = "#scope3_Flights_table_tr_row_attach_%d";
    private static final String REMOVE_ROW_BUTTON_PATTERN = "#scope3_Flights_table_tr_row_trash_%d";
    private static final String ALL_ROWS_PATTERN = "input[ftestcaseref^='scope3_flights_origin_']";

    // Table-level locators (not row-specific)
    private final Locator tableTotal;

    // Constructor
    public Scope3TableM(Page page) {
        this.page = page;
        this.tableTotal = page.locator("input[ftestcaseref='scope3_flights_total']");
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

    private Locator getAllRows() {
        return page.locator(ALL_ROWS_PATTERN);
    }

    /**
     * Helper methods to build dynamic locators based on row index
     */
    private Locator getOriginInput(int rowIndex) {
        return page.locator(String.format(ORIGIN_INPUT_PATTERN, rowIndex));
    }

    private Locator getDestinationInput(int rowIndex) {
        return page.locator(String.format(DESTINATION_INPUT_PATTERN, rowIndex));
    }

    private Locator getClassSelect(int rowIndex) {
        return page.locator(String.format(CLASS_SELECT_PATTERN, rowIndex));
    }

    private Locator getTripTypeSelect(int rowIndex) {
        return page.locator(String.format(TRIP_TYPE_SELECT_PATTERN, rowIndex));
    }

    private Locator getNoOfPassengersInput(int rowIndex) {
        return page.locator(String.format(NO_OF_PASSENGERS_INPUT_PATTERN, rowIndex));
    }

    private Locator getTotalEmissionsInput(int rowIndex) {
        return page.locator(String.format(TOTAL_EMISSIONS_INPUT_PATTERN, rowIndex));
    }

    private Locator getRowTotalLocator(int rowIndex) {
        return page.locator(String.format(ROW_TOTAL_PATTERN, rowIndex));
    }

    /**
     * Enter methods for specific columns - with humanlike delays for autocomplete
     */
    @AutoStep
    public void enterOrigin(int rowIndex, String iataCode) {
        InputHelper.humanizedInput(page, getOriginInput(rowIndex), iataCode);
    }

    @AutoStep
    public void enterDestination(int rowIndex, String iataCode) {
        InputHelper.humanizedInput(page, getDestinationInput(rowIndex), iataCode);
    }

    @AutoStep
    public void selectClass(int rowIndex, String flightClass) {
        page.waitForLoadState();
        Locator classSelect = getClassSelect(rowIndex);
        classSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        classSelect.scrollIntoViewIfNeeded();
        classSelect.selectOption(flightClass);
        page.waitForTimeout(500);
    }

    @AutoStep
    public void selectTripType(int rowIndex, String tripType) {
        page.waitForLoadState();
        Locator tripTypeSelect = getTripTypeSelect(rowIndex);
        tripTypeSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        tripTypeSelect.scrollIntoViewIfNeeded();
        tripTypeSelect.selectOption(tripType);
        page.waitForTimeout(500);
    }

    @AutoStep
    public void enterNoOfPassengers(int rowIndex, String passengers) {
        InputHelper.humanizedInput(page, getNoOfPassengersInput(rowIndex), passengers);
    }

    @AutoStep
    public void enterTotalEmissions(int rowIndex, String totalEmissions) {
        InputHelper.humanizedInput(page, getTotalEmissionsInput(rowIndex), totalEmissions);
    }

    /**
     * Get values
     */
    @AutoStep
    public String getOrigin(int rowIndex) {
        return getOriginInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getDestination(int rowIndex) {
        return getDestinationInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getNoOfPassengers(int rowIndex) {
        return getNoOfPassengersInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getTotalEmissions(int rowIndex) {
        return getTotalEmissionsInput(rowIndex).inputValue();
    }

    @AutoStep
    public String getRowTotal(int rowIndex) {
        return getRowTotalLocator(rowIndex).inputValue();
    }

    @AutoStep
    public String getTableTotal() {
        return this.tableTotal.inputValue();
    }

    /**
     * Row operations
     */
    @AutoStep
    public void addRow(int currentRowIndex) {
        page.waitForLoadState();
        int initialCount = getAllRows().count();
        Locator addButton = getAddRowButton(currentRowIndex);
        addButton.waitFor();
        addButton.click();
        // WaitHelper.waitForNewRow(page, getAllRows(), initialCount, 30000);
    }

    @AutoStep
    public void removeRow(int rowIndex) {
        page.waitForLoadState();
        Locator removeButton = getRemoveRowButton(rowIndex);
        removeButton.waitFor();
        removeButton.click();
    }

    @AutoStep
    public void attach(int rowIndex) {
        page.waitForLoadState();
        Locator attachButton = getAttachButton(rowIndex);
        attachButton.waitFor();
        attachButton.click();
    }

    /**
     * Fill entire row at once
     */
    public void fillRow(int rowIndex, String origin, String destination, String flightClass,
                       String tripType, String passengers, String totalEmissions) {
        enterOrigin(rowIndex, origin);
        enterDestination(rowIndex, destination);
        selectClass(rowIndex, flightClass);
        selectTripType(rowIndex, tripType);
        enterNoOfPassengers(rowIndex, passengers);
        enterTotalEmissions(rowIndex, totalEmissions);
    }
}
