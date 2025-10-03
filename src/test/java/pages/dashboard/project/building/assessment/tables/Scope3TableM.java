package pages.dashboard.project.building.assessment.tables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * Scope3TableM - Table M for Scope 3 Emissions (Flights)
 * Extends base Scope3Table with specific column methods
 *
 * Columns: origin, destination, class, trip_type, no_of_passengers, total_emissions_(kgco2e)
 * Note: Origin and destination are autocomplete fields (IATA codes)
 * Note: Total emissions is manually entered (auto-calculation has issues)
 */
public class Scope3TableM extends Scope3Table {

    public Scope3TableM(Page page) {
        super(page, "flights");
    }

    /**
     * Convenience methods for specific columns - with humanlike delays
     */
    public void enterOrigin(int rowIndex, String iataCode) {
        page.locator(buildFieldSelector("origin", rowIndex)).click();
        page.locator(buildFieldSelector("origin", rowIndex)).pressSequentially(iataCode, new com.microsoft.playwright.Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500); // Wait for autocomplete suggestions
        page.keyboard().press("Enter");
        page.waitForTimeout(300);
    }

    public void enterDestination(int rowIndex, String iataCode) {
        page.locator(buildFieldSelector("destination", rowIndex)).click();
        page.locator(buildFieldSelector("destination", rowIndex)).pressSequentially(iataCode, new com.microsoft.playwright.Locator.PressSequentiallyOptions().setDelay(150));
        page.waitForTimeout(500); // Wait for autocomplete suggestions
        page.keyboard().press("Enter");
        page.waitForTimeout(300);
    }

    public void selectClass(int rowIndex, String flightClass) {
        page.locator(buildFieldSelector("class", rowIndex)).click();
        page.waitForTimeout(200);
        selectField("class", rowIndex, flightClass);
        page.waitForTimeout(300);
    }

    public void selectTripType(int rowIndex, String tripType) {
        page.locator(buildFieldSelector("trip_type", rowIndex)).click();
        page.waitForTimeout(200);
        selectField("trip_type", rowIndex, tripType);
        page.waitForTimeout(300);
    }

    public void enterNoOfPassengers(int rowIndex, String passengers) {
        enterField("no_of_passengers", rowIndex, passengers);
        page.waitForTimeout(300);
    }

    public void enterTotalEmissions(int rowIndex, String totalEmissions) {
        enterField("total_emissions_(kgco2e)", rowIndex, totalEmissions);
        page.waitForTimeout(300);
    }

    /**
     * Get values
     */
    public String getOrigin(int rowIndex) {
        return getField("origin", rowIndex);
    }

    public String getDestination(int rowIndex) {
        return getField("destination", rowIndex);
    }

    public String getNoOfPassengers(int rowIndex) {
        return getField("no_of_passengers", rowIndex);
    }

    public String getTotalEmissions(int rowIndex) {
        return getField("total_emissions_(kgco2e)", rowIndex);
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
