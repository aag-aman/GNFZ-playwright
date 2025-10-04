package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

/**
 * Scope3TableM - Table M for Scope 3 Emissions (Flights)
 *
 * Columns: origin, destination, class, trip_type, no_of_passengers, total_emissions_(kgco2e)
 * Note: Origin and destination are autocomplete fields (IATA codes)
 * Note: Total emissions is manually entered (auto-calculation has issues)
 */
public class Scope3TableM {
    protected final Page page;

    public Scope3TableM(Page page) {
        this.page = page;
    }

    /**
     * Enter methods for specific columns - with humanlike delays for autocomplete
     */
    public void enterOrigin(int rowIndex, String iataCode) {
        page.locator(String.format("'scope3_flights_origin_%d'", rowIndex)).click();
        page.locator(String.format("'scope3_flights_origin_%d'", rowIndex)).pressSequentially(iataCode, new com.microsoft.playwright.Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500); // Wait for autocomplete suggestions
        page.keyboard().press("Enter");
        page.waitForTimeout(300);
    }

    public void enterDestination(int rowIndex, String iataCode) {
        page.locator(String.format("'scope3_flights_destination_%d'", rowIndex)).click();
        page.locator(String.format("'scope3_flights_destination_%d'", rowIndex)).pressSequentially(iataCode, new com.microsoft.playwright.Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500); // Wait for autocomplete suggestions
        page.keyboard().press("Enter");
        page.waitForTimeout(300);
    }

    public void selectClass(int rowIndex, String flightClass) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_flights_class_%d'", rowIndex)).click();
        page.waitForTimeout(200);
        page.locator(String.format("'scope3_flights_class_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).selectOption(flightClass);
        page.waitForTimeout(300);
    }

    public void selectTripType(int rowIndex, String tripType) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_flights_trip_type_%d'", rowIndex)).click();
        page.waitForTimeout(200);
        page.locator(String.format("'scope3_flights_trip_type_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).selectOption(tripType);
        page.waitForTimeout(300);
    }

    public void enterNoOfPassengers(int rowIndex, String passengers) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_flights_no_of_passengers_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_flights_no_of_passengers_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(passengers);
        page.waitForTimeout(300);
    }

    public void enterTotalEmissions(int rowIndex, String totalEmissions) {
        page.waitForLoadState();
        page.locator(String.format("'scope3_flights_total_emissions_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).waitFor();
        page.locator(String.format("'scope3_flights_total_emissions_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).fill(totalEmissions);
        page.waitForTimeout(300);
    }

    /**
     * Get values
     */
    public String getOrigin(int rowIndex) {
        return page.locator(String.format("'scope3_flights_origin_%d'", rowIndex)).inputValue();
    }

    public String getDestination(int rowIndex) {
        return page.locator(String.format("'scope3_flights_destination_%d'", rowIndex)).inputValue();
    }

    public String getNoOfPassengers(int rowIndex) {
        return page.locator(String.format("'scope3_flights_no_of_passengers_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getTotalEmissions(int rowIndex) {
        return page.locator(String.format("'scope3_flights_total_emissions_(kgco2e)_%d'", rowIndex)).getByRole(AriaRole.COMBOBOX).inputValue();
    }

    public String getRowTotal(int rowIndex) {
        return page.locator(String.format("'scope3_flights_rowtotal_%d'", rowIndex)).textContent();
    }

    public String getTableTotal() {
        return page.locator("'scope3_flights_total'").textContent();
    }

    /**
     * Row operations
     */
    public void addRow() {
        page.locator("'scope3_flights_add'").click();
    }

    public void removeRow(int rowIndex) {
        page.locator(String.format("'scope3_flights_remove_%d'", rowIndex)).click();
    }

    public void attach() {
        page.locator("'scope3_flights_attach'").click();
    }

    /**
     * Fill entire row at once - with humanlike delays
     */
    public void fillRow(int rowIndex, String origin, String destination, String flightClass,
                       String tripType, String passengers, String totalEmissions) {
        enterOrigin(rowIndex, origin);
        enterDestination(rowIndex, destination);
        selectClass(rowIndex, flightClass);
        selectTripType(rowIndex, tripType);
        enterNoOfPassengers(rowIndex, passengers);
        enterTotalEmissions(rowIndex, totalEmissions);
        page.waitForLoadState(LoadState.NETWORKIDLE); // Ensure all calculations done
    }
}
