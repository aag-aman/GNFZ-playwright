package pages.dashboard.project.building.assessment.tablesEmissions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

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
    public void enterOrigin(int rowIndex, String iataCode) {
        page.waitForLoadState();
        Locator originInput = getOriginInput(rowIndex);
        originInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        originInput.scrollIntoViewIfNeeded();
        originInput.click();
        page.waitForTimeout(100);
        originInput.pressSequentially(iataCode, new Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500);
        page.keyboard().press("Enter");
        page.waitForTimeout(1500);
    }

    public void enterDestination(int rowIndex, String iataCode) {
        page.waitForLoadState();
        Locator destinationInput = getDestinationInput(rowIndex);
        destinationInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        destinationInput.scrollIntoViewIfNeeded();
        destinationInput.click();
        page.waitForTimeout(100);
        destinationInput.pressSequentially(iataCode, new Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500);
        page.keyboard().press("Enter");
        page.waitForTimeout(1500);
    }

    public void selectClass(int rowIndex, String flightClass) {
        page.waitForLoadState();
        Locator classSelect = getClassSelect(rowIndex);
        classSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        classSelect.scrollIntoViewIfNeeded();
        classSelect.selectOption(flightClass);
        page.waitForTimeout(500);
    }

    public void selectTripType(int rowIndex, String tripType) {
        page.waitForLoadState();
        Locator tripTypeSelect = getTripTypeSelect(rowIndex);
        tripTypeSelect.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        tripTypeSelect.scrollIntoViewIfNeeded();
        tripTypeSelect.selectOption(tripType);
        page.waitForTimeout(500);
    }

    public void enterNoOfPassengers(int rowIndex, String passengers) {
        page.waitForLoadState();
        Locator passengersInput = getNoOfPassengersInput(rowIndex);
        passengersInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        passengersInput.scrollIntoViewIfNeeded();
        passengersInput.fill(passengers);
        passengersInput.blur();
        page.waitForTimeout(1500);
    }

    public void enterTotalEmissions(int rowIndex, String totalEmissions) {
        page.waitForLoadState();
        Locator emissionsInput = getTotalEmissionsInput(rowIndex);
        emissionsInput.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));
        emissionsInput.scrollIntoViewIfNeeded();
        emissionsInput.fill(totalEmissions);
        emissionsInput.blur();
        page.waitForTimeout(1500);
    }

    /**
     * Get values
     */
    public String getOrigin(int rowIndex) {
        return getOriginInput(rowIndex).inputValue();
    }

    public String getDestination(int rowIndex) {
        return getDestinationInput(rowIndex).inputValue();
    }

    public String getNoOfPassengers(int rowIndex) {
        return getNoOfPassengersInput(rowIndex).inputValue();
    }

    public String getTotalEmissions(int rowIndex) {
        return getTotalEmissionsInput(rowIndex).inputValue();
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
