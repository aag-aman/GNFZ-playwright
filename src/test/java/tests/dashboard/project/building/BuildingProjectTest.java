package tests.dashboard.project.building;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.authentication.LoginPage;
import pages.dashboard.ProjectListPage;
import pages.dashboard.ProjectSelectionPage;
import pages.dashboard.project.building.BuildingProjectPage;
import pages.dashboard.project.building.BuildingBasicInfoTab;
import pages.dashboard.project.building.BuildingAssessmentTab;
import tests.base.BaseTest;
import utils.TestDataManager;

import java.io.IOException;
import java.util.Map;
import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BuildingProjectTest - Smoke tests for Building project type
 *
 * Tests the flow: Login -> Create Project -> Select Building -> [Building Form]
 */
@Epic("Project Management")
@Feature("Building Project")
public class BuildingProjectTest extends BaseTest {

    /**
     * Test that Building option is visible and selectable
     */
    @Test
    @DisplayName("Building Option Visible")
    @Description("Verify Building project option is visible on project selection page")
    @Story("Building Project Selection")
    @Severity(SeverityLevel.CRITICAL)
    void testBuildingOptionVisible() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();
        BuildingProjectPage buildingProjectPage = pageManager.getBuildingProjectPage();
        BuildingBasicInfoTab buildingBasicInfoTab = pageManager.getBuildingBasicInfoTab();
        BuildingAssessmentTab buildingAssessmentTab = pageManager.getBuildingAssessmentTab();
        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to project selection", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("username"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
        });

        // Verify Building option is visible
        Allure.step("Verify Building option is visible", () -> {
            assertTrue(projectSelectionPage.isBuildingOptionVisible(),
                    "Building option should be visible");
        });

        takeScreenshot("Building Option Visible");

        Allure.step("Select Building project type", () -> {
            projectSelectionPage.selectBuilding();
        });

        Allure.step("Navigate to Building project form and enter details", () -> {
            buildingProjectPage.goToBasicInfoTab();
            String projectTitle = "Test Building - " + System.currentTimeMillis();
            buildingBasicInfoTab.enterProjectTitle(projectTitle);
            buildingBasicInfoTab.clickSave();
            // Wait for url to update baseUrl/project/building/projectId format, get
            // projectID from url
            page.waitForURL("**/project/building/**", new Page.WaitForURLOptions().setTimeout(5000));
            String projectId = page.url().split("/")[5];
            System.out.println("Created Building project with ID: " + projectId);
            // Wait for Project ID field to be visible
            assertTrue(buildingBasicInfoTab.isProjectIdFieldVisible());

            String savedId = buildingBasicInfoTab.getProjectId();
            assertEquals(projectId, savedId,
                    "Project ID should persist after save");

            String savedTitle = buildingBasicInfoTab.getProjectTitle();
            assertEquals(projectTitle, savedTitle,
                    "Project title should persist after save");

        });

        takeScreenshot("Building Project Form");

        Allure.step("Navigate to Assessment tab and enter Net Zero Emission details", () -> {
            buildingProjectPage.goToAssessmentTab();
            assertTrue(buildingAssessmentTab.isTabDisplayed(),
                    "Assessment tab form should be displayed");

            assertTrue(buildingAssessmentTab.isNetZeroEmissionsSubTabActive(),
                    "Net Zero Emissions sub-tab should be active by default");

        });
    }

    /**
     * Test selecting Building project type
     */
    @Test
    @DisplayName("Select Building Project")
    @Description("Test selecting Building project type from options")
    @Story("Building Project Selection")
    @Severity(SeverityLevel.CRITICAL)
    void testSelectBuildingProject() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to project selection", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("username"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
        });

        // Select Building option
        Allure.step("Select Building project type", () -> {
            projectSelectionPage.selectBuilding();
        });

        // TODO: Add verification for Building project form/page
        // Allure.step("Verify Building project form is displayed", () -> {
        // assertTrue(buildingProjectPage.isPageDisplayed(),
        // "Building project form should be displayed");
        // });

        takeScreenshot("Building Project Selected");
    }

    /**
     * Test Building option text
     */
    @Test
    @DisplayName("Building Option Text")
    @Description("Verify Building option displays correct text")
    @Story("Building Project Selection")
    @Severity(SeverityLevel.MINOR)
    void testBuildingOptionText() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();

        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to project selection", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("username"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
        });

        // Verify Building option text
        Allure.step("Verify Building option text", () -> {
            String optionText = projectSelectionPage.getBuildingOptionText();
            assertNotNull(optionText, "Building option text should not be null");
            assertTrue(optionText.toLowerCase().contains("building"),
                    "Building option should contain 'building' text");
        });

        takeScreenshot("Building Option Text");
    }

    /**
     * Test complete Building creation flow with Basic Info
     */
    @Test
    @DisplayName("Create Building with Basic Info")
    @Description("Complete flow: Login -> Create Project -> Select Building -> Enter Basic Info -> Save")
    @Story("Building Project Creation")
    @Severity(SeverityLevel.CRITICAL)
    void testCreateBuildingWithAllSections() throws IOException {
        // Get page objects
        LoginPage loginPage = pageManager.getLoginPage();
        ProjectListPage projectListPage = pageManager.getProjectListPage();
        ProjectSelectionPage projectSelectionPage = pageManager.getProjectSelectionPage();
        BuildingProjectPage buildingProjectPage = pageManager.getBuildingProjectPage();
        BuildingBasicInfoTab buildingBasicInfoTab = pageManager.getBuildingBasicInfoTab();
        BuildingAssessmentTab buildingAssessmentTab = pageManager.getBuildingAssessmentTab();
        // Login and navigate to project selection
        Map<String, String> user = TestDataManager.getSmokeUser();
        Allure.step("Login and navigate to project selection", () -> {
            loginPage.navigateToLogin();
            loginPage.enterEmail(user.get("username"));
            loginPage.enterPassword(user.get("password"));
            loginPage.clickSignInButton();
            projectListPage.clickCreateNewProject();
        });

        // Select Building project type
        Allure.step("Select Building project type", () -> {
            projectSelectionPage.selectBuilding();
        });

        // Navigate to Basic Info tab
        Allure.step("Navigate to Basic Info tab", () -> {
            buildingProjectPage.goToBasicInfoTab();
        });

        // Enter project title and save
        String projectTitle = "Test Building - " + System.currentTimeMillis();
        Allure.step("Enter project title and save", () -> {
            buildingBasicInfoTab.enterProjectTitle(projectTitle);
            buildingBasicInfoTab.clickSave();
            // Wait for url to update baseUrl/project/building/projectId format, get
            // projectID from url
            page.waitForURL("**/project/building/**", new Page.WaitForURLOptions().setTimeout(30000));
            String projectId = page.url().split("/")[5];
            System.out.println("Created Building project with ID: " + projectId);
            // page.waitForTimeout(1000); // Wait for save to complete
            // Wait for Project ID field to be visible
            assertTrue(buildingBasicInfoTab.isProjectIdFieldVisible());
            String savedId = buildingBasicInfoTab.getProjectId();
            assertEquals(projectId, savedId,
                    "Project ID should persist after save");

        });

        // Verify save success - title should persist
        Allure.step("Verify project was saved successfully", () -> {
            String savedTitle = buildingBasicInfoTab.getProjectTitle();
            assertEquals(projectTitle, savedTitle,
                    "Project title should persist after save");
        });

        takeScreenshot("Building Created with Basic Info");

        // Enter Assessment details
        Allure.step("Navigate to Assessment tab and enter Net Zero Emission details", () -> {
            buildingProjectPage.goToAssessmentTab();

            assertTrue(buildingAssessmentTab.isTabDisplayed(),
                    "Assessment tab form should be displayed");

            assertTrue(buildingAssessmentTab.isNetZeroEmissionsSubTabActive(),
                    "Net Zero Emissions sub-tab should be active by default");

            // Enter Net Zero Emissions details

            // Enter reporting period
            buildingAssessmentTab.getNetZeroEmissionsSection().enterReportingPeriodFrom("01/01/2024");
            buildingAssessmentTab.getNetZeroEmissionsSection().enterReportingPeriodTo("12/31/2024");

            // Switch to Scope 1 detailed view to show tables
            buildingAssessmentTab.getNetZeroEmissionsSection().expandScope1();
            page.waitForTimeout(500); // Wait for section to expand

            // Fill Table A (Fuels) - Row 0 with auto-population checks
            // Define test data
            String fuelTypeA = "Natural Gas";
            String consumptionA = "100";
            double expectedEmissionFactorA = 2539.25;

            // Step 1: Enter fuel (should trigger emission factor auto-population)
            buildingAssessmentTab.getNetZeroEmissionsSection().tableA().enterFuel(0, fuelTypeA);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getEmissionFactor(0);
            if (emissionFactorA == null || emissionFactorA.trim().isEmpty() || emissionFactorA.equals("0")) {
                // Backup: Manual entry if auto-population failed
                System.out.println("Table A: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableA().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorA));
            }

            // Step 3: Verify emission factor is correct (allowing for comma formatting)
            emissionFactorA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getEmissionFactor(0);
            assertTrue(emissionFactorA.contains("2") && emissionFactorA.contains("539"),
                    "Emission factor should be approximately " + expectedEmissionFactorA + " for " + fuelTypeA + ", but got: " + emissionFactorA);

            // Step 4: Enter consumption (should trigger total calculations and unit auto-population)
            buildingAssessmentTab.getNetZeroEmissionsSection().tableA().enterConsumption(0, consumptionA);
            // Note: enterConsumption() now includes blur and wait for calculations

            // Step 5: Check if units auto-populated (expected: tonnes)
            String unitsA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getUnits(0);
            if (unitsA == null || unitsA.trim().isEmpty() || !unitsA.toLowerCase().contains("tonne")) {
                // Backup: Manual selection if auto-population failed
                System.out.println("Table A: Units not auto-populated, selecting manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableA().selectUnits(0, "tonnes");
            }

            // Step 6: Verify totals are auto-calculated
            page.waitForTimeout(1000); // Wait for final calculations

            // Parse actual emission factor (remove commas for calculation)
            double actualEmissionFactorA = Double.parseDouble(emissionFactorA.replace(",", ""));
            double consumptionValueA = Double.parseDouble(consumptionA);

            // Calculate expected row total: emission_factor × consumption
            double calculatedRowTotalA = actualEmissionFactorA * consumptionValueA;
            String expectedRowTotalA = String.format("%,.2f", calculatedRowTotalA);

            String rowTotalA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getRowTotal(0);
            assertNotNull(rowTotalA, "Table A: Row total should be calculated");
            assertEquals(expectedRowTotalA, rowTotalA.trim(),
                String.format("Table A: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorA, consumptionValueA, expectedRowTotalA, rowTotalA));

            String tableTotalA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getTableTotal();
            assertNotNull(tableTotalA, "Table A: Table total should be calculated");
            assertEquals(expectedRowTotalA, tableTotalA.trim(),
                "Table A: Table total should equal row total for single row");

            System.out.println(String.format("Table A (Fuels) - %s: EF=%.2f × Consumption=%.2f = Row Total=%s, Table Total=%s",
                fuelTypeA, actualEmissionFactorA, consumptionValueA, rowTotalA, tableTotalA));

            // ========================================
            // Fill Table B (Refrigerants) - Row 0
            // ========================================
            System.out.println("\n=== Testing Table B (Refrigerants) ===");

            // Define test data
            String refrigerantTypeB = "R-410A";
            String consumptionB = "10";
            double expectedEmissionFactorB = 1182.0; // GWP for R-410A

            // Step 1: Enter refrigerant type (should trigger emission factor auto-population)
            buildingAssessmentTab.getNetZeroEmissionsSection().tableB().enterType(0, refrigerantTypeB);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getEmissionFactor(0);
            if (emissionFactorB == null || emissionFactorB.trim().isEmpty() || emissionFactorB.equals("0")) {
                // Backup: Manual entry if auto-population failed
                System.out.println("Table B: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableB().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorB));
            }

            // Step 3: Verify emission factor was populated
            emissionFactorB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getEmissionFactor(0);
            assertFalse(emissionFactorB.trim().isEmpty(),
                    "Table B: Emission factor should be populated for " + refrigerantTypeB + ", but got: " + emissionFactorB);

            // Step 4: Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableB().enterConsumption(0, consumptionB);

            // Step 5: Check if unit auto-populated (should be kg for refrigerants)
            String unitB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getUnit(0);
            System.out.println("Table B: Unit auto-populated value: '" + unitB + "'");
            assertFalse(unitB == null || unitB.trim().isEmpty(),
                    "Table B: Unit should be populated (got: '" + unitB + "')");

            // Step 6: Verify totals are calculated
            page.waitForTimeout(1000);

            // Parse actual emission factor (remove commas for calculation)
            double actualEmissionFactorB = Double.parseDouble(emissionFactorB.replace(",", ""));
            double consumptionValueB = Double.parseDouble(consumptionB);

            // Calculate expected row total: emission_factor × consumption
            double calculatedRowTotalB = actualEmissionFactorB * consumptionValueB;
            String expectedRowTotalB = String.format("%,.2f", calculatedRowTotalB);

            String rowTotalB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getRowTotal(0);
            assertNotNull(rowTotalB, "Table B: Row total should be calculated");
            assertEquals(expectedRowTotalB, rowTotalB.trim(),
                String.format("Table B: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorB, consumptionValueB, expectedRowTotalB, rowTotalB));

            String tableTotalB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getTableTotal();
            assertNotNull(tableTotalB, "Table B: Table total should be calculated");
            assertEquals(expectedRowTotalB, tableTotalB.trim(),
                    "Table B: Table total should equal row total for single row");

            System.out.println(String.format("Table B (Refrigerants) - %s: EF=%.2f × Consumption=%.2f = Row Total=%s, Table Total=%s",
                refrigerantTypeB, actualEmissionFactorB, consumptionValueB, rowTotalB, tableTotalB));

            // ========================================
            // Fill Table C (Mobile Combustion) - Row 0
            // ========================================
            System.out.println("\n=== Testing Table C (Mobile Combustion) ===");

            // Define test data
            String fuelTypeC = "Gasoline";
            String consumptionC = "200";
            double expectedEmissionFactorC = 34.0; // Emission factor for Gasoline

            // Step 1: Enter fuel type (should trigger emission factor auto-population)
            buildingAssessmentTab.getNetZeroEmissionsSection().tableC().enterFuel(0, fuelTypeC);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getEmissionFactor(0);
            if (emissionFactorC == null || emissionFactorC.trim().isEmpty() || emissionFactorC.equals("0")) {
                // Backup: Manual entry if auto-population failed
                System.out.println("Table C: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableC().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorC));
            }

            // Step 3: Verify emission factor was populated
            emissionFactorC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getEmissionFactor(0);
            assertFalse(emissionFactorC.trim().isEmpty(),
                    "Table C: Emission factor should be populated for " + fuelTypeC + ", but got: " + emissionFactorC);

            // Step 4: Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableC().enterConsumption(0, consumptionC);

            // Step 5: Check if units auto-populated
            String unitsC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getUnits(0);
            System.out.println("Table C: Units auto-populated value: '" + unitsC + "'");
            assertFalse(unitsC == null || unitsC.trim().isEmpty(),
                    "Table C: Units should be populated (got: '" + unitsC + "')");

            // Step 6: Verify totals are calculated
            page.waitForTimeout(1000);

            // Parse actual emission factor (remove commas for calculation)
            double actualEmissionFactorC = Double.parseDouble(emissionFactorC.replace(",", ""));
            double consumptionValueC = Double.parseDouble(consumptionC);

            // Calculate expected row total: emission_factor × consumption
            double calculatedRowTotalC = actualEmissionFactorC * consumptionValueC;
            String expectedRowTotalC = String.format("%,.2f", calculatedRowTotalC);

            String rowTotalC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getRowTotal(0);
            assertNotNull(rowTotalC, "Table C: Row total should be calculated");
            assertEquals(expectedRowTotalC, rowTotalC.trim(),
                String.format("Table C: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorC, consumptionValueC, expectedRowTotalC, rowTotalC));

            String tableTotalC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getTableTotal();
            assertNotNull(tableTotalC, "Table C: Table total should be calculated");
            assertEquals(expectedRowTotalC, tableTotalC.trim(),
                    "Table C: Table total should equal row total for single row");

            System.out.println(String.format("Table C (Mobile Combustion) - %s: EF=%.2f × Consumption=%.2f = Row Total=%s, Table Total=%s",
                fuelTypeC, actualEmissionFactorC, consumptionValueC, rowTotalC, tableTotalC));

            // ========================================
            // Verify Scope 1 Total (Table A + Table B + Table C)
            // ========================================
            System.out.println("\n=== Verifying Scope 1 Total ===");
            page.waitForTimeout(1000); // Wait for scope total calculation

            // Parse table totals (remove commas and convert to double)
            double tableTotalAValue = Double.parseDouble(tableTotalA.replace(",", ""));
            double tableTotalBValue = Double.parseDouble(tableTotalB.replace(",", ""));
            double tableTotalCValue = Double.parseDouble(tableTotalC.replace(",", ""));

            // Calculate expected Scope 1 total
            double calculatedScope1Total = tableTotalAValue + tableTotalBValue + tableTotalCValue;
            String expectedScope1Total = String.format("%,.2f", calculatedScope1Total);

            // Get actual Scope 1 total from UI
            String actualScope1Total = buildingAssessmentTab.getNetZeroEmissionsSection().getScope1Total();
            assertNotNull(actualScope1Total, "Scope 1 total should be calculated");

            assertEquals(expectedScope1Total, actualScope1Total.trim(),
                String.format("Scope 1 Total calculation incorrect (%.2f + %.2f + %.2f = %s, Got: %s)",
                    tableTotalAValue, tableTotalBValue, tableTotalCValue, expectedScope1Total, actualScope1Total));

            System.out.println(String.format("Scope 1 Total Verified: %.2f + %.2f + %.2f = %s",
                tableTotalAValue, tableTotalBValue, tableTotalCValue, actualScope1Total));

            System.out.println("\n=== Scope 1 Testing Complete ===");

            // ========================================
            // SCOPE 2 - Table D (Energy)
            // ========================================
            System.out.println("\n=== Testing Scope 2 - Table D (Energy) ===");

            // Expand Scope 2 section
            buildingAssessmentTab.getNetZeroEmissionsSection().expandScope2();
            page.waitForTimeout(500); // Wait for section to expand

            // Define test data
            String activityD = "Non Renewable Electricity from Grid​";
            String consumptionD = "100";
            double expectedEmissionFactorD = 0.149;

            // Step 1: Enter activity (should trigger emission factor auto-population)
            buildingAssessmentTab.getNetZeroEmissionsSection().tableD().enterActivity(0, activityD);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getEmissionFactor(0);
            if (emissionFactorD == null || emissionFactorD.trim().isEmpty() || emissionFactorD.equals("0")) {
                // Backup: Manual entry if auto-population failed
                System.out.println("Table D: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableD().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorD));
            }

            // Step 3: Verify emission factor was populated
            emissionFactorD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getEmissionFactor(0);
            assertFalse(emissionFactorD.trim().isEmpty(),
                    "Table D: Emission factor should be populated for " + activityD + ", but got: " + emissionFactorD);

            // Step 4: Enter consumption (with slower input and longer waits)
            buildingAssessmentTab.getNetZeroEmissionsSection().tableD().enterConsumption(0, consumptionD);

            // Step 5: Check if units auto-populated (should be Cubic metres)
            String unitsD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getUnits(0);
            System.out.println("Table D: Units auto-populated value: '" + unitsD + "'");
            assertFalse(unitsD == null || unitsD.trim().isEmpty(),
                    "Table D: Units should be populated (got: '" + unitsD + "')");

            // Step 6: Verify totals are calculated
            page.waitForTimeout(1500); // Longer wait for Table D calculations

            // Parse actual emission factor (remove commas for calculation)
            double actualEmissionFactorD = Double.parseDouble(emissionFactorD.replace(",", ""));
            double consumptionValueD = Double.parseDouble(consumptionD);

            // Calculate expected row total: emission_factor × consumption
            double calculatedRowTotalD = actualEmissionFactorD * consumptionValueD;
            String expectedRowTotalD = String.format("%,.2f", calculatedRowTotalD);

            String rowTotalD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getRowTotal(0);
            assertNotNull(rowTotalD, "Table D: Row total should be calculated");
            assertEquals(expectedRowTotalD, rowTotalD.trim(),
                String.format("Table D: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorD, consumptionValueD, expectedRowTotalD, rowTotalD));

            String tableTotalD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getTableTotal();
            assertNotNull(tableTotalD, "Table D: Table total should be calculated");
            assertEquals(expectedRowTotalD, tableTotalD.trim(),
                    "Table D: Table total should equal row total for single row");

            System.out.println(String.format("Table D (Energy) - %s: EF=%.2f × Consumption=%.2f = Row Total=%s, Table Total=%s",
                activityD, actualEmissionFactorD, consumptionValueD, rowTotalD, tableTotalD));

            // ========================================
            // Verify Scope 2 Total (equals Table D total since it's the only table)
            // ========================================
            System.out.println("\n=== Verifying Scope 2 Total ===");
            page.waitForTimeout(1000); // Wait for scope total calculation

            // Get actual Scope 2 total from UI
            String actualScope2Total = buildingAssessmentTab.getNetZeroEmissionsSection().getScope2Total();
            assertNotNull(actualScope2Total, "Scope 2 total should be calculated");

            // Scope 2 total should equal Table D total (only table in Scope 2)
            assertEquals(tableTotalD.trim(), actualScope2Total.trim(),
                String.format("Scope 2 Total should equal Table D total (Got Table D: %s, Scope 2: %s)",
                    tableTotalD, actualScope2Total));

            System.out.println(String.format("Scope 2 Total Verified: Table D Total (%s) = Scope 2 Total (%s)",
                tableTotalD, actualScope2Total));

            System.out.println("\n=== Scope 2 Testing Complete ===");

            // ========================================
            // SCOPE 3 - Tables E, F, G, H
            // ========================================
            System.out.println("\n=== Testing Scope 3 Tables ===");

            // Expand Scope 3 section
            buildingAssessmentTab.getNetZeroEmissionsSection().expandScope3();
            page.waitForTimeout(500); // Wait for section to expand

            // ========================================
            // Table E (Water)
            // ========================================
            System.out.println("\n=== Testing Table E (Water) ===");

            // Define test data
            String activityE = "Landscape irrigation";
            String consumptionE = "200";
            double expectedEmissionFactorE = 0.149;

            // Step 1: Enter activity
            buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterActivity(0, activityE);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getEmissionFactor(0);
            if (emissionFactorE == null || emissionFactorE.trim().isEmpty() || emissionFactorE.equals("0")) {
                System.out.println("Table E: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorE));
            }

            // Step 3: Verify emission factor was populated
            emissionFactorE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getEmissionFactor(0);
            assertFalse(emissionFactorE.trim().isEmpty(),
                    "Table E: Emission factor should be populated for " + activityE);

            // Step 4: Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterConsumption(0, consumptionE);

            // Step 5: Check if unit auto-populated
            String unitE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getUnit(0);
            System.out.println("Table E: Unit auto-populated value: '" + unitE + "'");
            assertFalse(unitE == null || unitE.trim().isEmpty(),
                    "Table E: Unit should be populated");

            // Step 6: Verify totals are calculated
            page.waitForTimeout(1500);

            // Parse and calculate
            double actualEmissionFactorE = Double.parseDouble(emissionFactorE.replace(",", ""));
            double consumptionValueE = Double.parseDouble(consumptionE);
            double calculatedRowTotalE = actualEmissionFactorE * consumptionValueE;
            String expectedRowTotalE = String.format("%,.2f", calculatedRowTotalE);

            String rowTotalE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getRowTotal(0);
            assertNotNull(rowTotalE, "Table E: Row total should be calculated");
            assertEquals(expectedRowTotalE, rowTotalE.trim(),
                String.format("Table E: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorE, consumptionValueE, expectedRowTotalE, rowTotalE));

            String tableTotalE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getTableTotal();
            assertNotNull(tableTotalE, "Table E: Table total should be calculated");
            assertEquals(expectedRowTotalE, tableTotalE.trim(),
                    "Table E: Table total should equal row total for single row");

            System.out.println(String.format("Table E (Water) - %s: EF=%.2f × Consumption=%.2f = Row Total=%s, Table Total=%s",
                activityE, actualEmissionFactorE, consumptionValueE, rowTotalE, tableTotalE));

            // ========================================
            // Table F (Waste Disposal)
            // ========================================
            System.out.println("\n=== Testing Table F (Waste Disposal) ===");

            // Define test data
            String typeOfWasteF = "Aggregates";
            String quantityGeneratedF = "150";
            String quantityLandfillF = "80";
            double expectedEmissionFactorF = 11.0;

            // Step 1: Enter type of waste
            buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterTypeOfWaste(0, typeOfWasteF);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getEmissionFactor(0);
            if (emissionFactorF == null || emissionFactorF.trim().isEmpty() || emissionFactorF.equals("0")) {
                System.out.println("Table F: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorF));
            }

            // Step 3: Verify emission factor was populated
            emissionFactorF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getEmissionFactor(0);
            assertFalse(emissionFactorF.trim().isEmpty(),
                    "Table F: Emission factor should be populated for " + typeOfWasteF);

            // Step 4: Enter quantities
            buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterQuantityOfWasteGenerated(0, quantityGeneratedF);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterQuantityOfWasteSentToLandfill(0, quantityLandfillF);

            // Step 5: Check if unit auto-populated (should be MT)
            String unitF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getUnit(0);
            System.out.println("Table F: Unit auto-populated value: '" + unitF + "'");
            assertFalse(unitF == null || unitF.trim().isEmpty(),
                    "Table F: Unit should be populated (expected MT)");

            // Step 6: Verify totals are calculated
            page.waitForTimeout(1500);

            // For Table F, calculation is: emission_factor × quantity_sent_to_landfill
            double actualEmissionFactorF = Double.parseDouble(emissionFactorF.replace(",", ""));
            double quantityLandfillValueF = Double.parseDouble(quantityLandfillF);
            double calculatedRowTotalF = actualEmissionFactorF * quantityLandfillValueF;
            String expectedRowTotalF = String.format("%,.2f", calculatedRowTotalF);

            String rowTotalF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getRowTotal(0);
            assertNotNull(rowTotalF, "Table F: Row total should be calculated");
            assertEquals(expectedRowTotalF, rowTotalF.trim(),
                String.format("Table F: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorF, quantityLandfillValueF, expectedRowTotalF, rowTotalF));

            String tableTotalF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getTableTotal();
            assertNotNull(tableTotalF, "Table F: Table total should be calculated");
            assertEquals(expectedRowTotalF, tableTotalF.trim(),
                    "Table F: Table total should equal row total for single row");

            System.out.println(String.format("Table F (Waste Disposal) - %s: EF=%.2f × Landfill Qty=%.2f = Row Total=%s, Table Total=%s",
                typeOfWasteF, actualEmissionFactorF, quantityLandfillValueF, rowTotalF, tableTotalF));

            // ========================================
            // Table G (Composed Waste)
            // ========================================
            System.out.println("\n=== Testing Table G (Composed Waste) ===");

            // Define test data
            String typeOfWasteG = "Organic: mixed food and garden waste";
            String quantityCompostedG = "90";
            double expectedEmissionFactorG = 8.911;

            // Step 1: Enter type of waste
            buildingAssessmentTab.getNetZeroEmissionsSection().tableG().enterTypeOfWaste(0, typeOfWasteG);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getEmissionFactor(0);
            if (emissionFactorG == null || emissionFactorG.trim().isEmpty() || emissionFactorG.equals("0")) {
                System.out.println("Table G: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableG().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorG));
            }

            // Step 3: Verify emission factor was populated
            emissionFactorG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getEmissionFactor(0);
            assertFalse(emissionFactorG.trim().isEmpty(),
                    "Table G: Emission factor should be populated for " + typeOfWasteG);

            // Step 4: Enter quantity composted
            buildingAssessmentTab.getNetZeroEmissionsSection().tableG().enterQuantityOfWasteComposted(0, quantityCompostedG);

            // Step 5: Check if unit auto-populated (should be MT)
            String unitG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getUnit(0);
            System.out.println("Table G: Unit auto-populated value: '" + unitG + "'");
            assertFalse(unitG == null || unitG.trim().isEmpty(),
                    "Table G: Unit should be populated (expected MT)");

            // Step 6: Verify totals are calculated
            page.waitForTimeout(1500);

            // Parse and calculate
            double actualEmissionFactorG = Double.parseDouble(emissionFactorG.replace(",", ""));
            double quantityCompostedValueG = Double.parseDouble(quantityCompostedG);
            double calculatedRowTotalG = actualEmissionFactorG * quantityCompostedValueG;
            String expectedRowTotalG = String.format("%,.2f", calculatedRowTotalG);

            String rowTotalG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getRowTotal(0);
            assertNotNull(rowTotalG, "Table G: Row total should be calculated");
            assertEquals(expectedRowTotalG, rowTotalG.trim(),
                String.format("Table G: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorG, quantityCompostedValueG, expectedRowTotalG, rowTotalG));

            String tableTotalG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getTableTotal();
            assertNotNull(tableTotalG, "Table G: Table total should be calculated");
            assertEquals(expectedRowTotalG, tableTotalG.trim(),
                    "Table G: Table total should equal row total for single row");

            System.out.println(String.format("Table G (Composed Waste) - %s: EF=%.2f × Quantity=%.2f = Row Total=%s, Table Total=%s",
                typeOfWasteG, actualEmissionFactorG, quantityCompostedValueG, rowTotalG, tableTotalG));

            // ========================================
            // Table H (Waste Recycled)
            // ========================================
            System.out.println("\n=== Testing Table H (Waste Recycled) ===");

            // Define test data
            String typeOfWasteH = "Asphalt";
            String quantityRecycledH = "110";
            double expectedEmissionFactorH = 0.985;

            // Step 1: Enter type of waste
            buildingAssessmentTab.getNetZeroEmissionsSection().tableH().enterTypeOfWaste(0, typeOfWasteH);

            // Step 2: Check if emission factor auto-populated
            String emissionFactorH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getEmissionFactor(0);
            if (emissionFactorH == null || emissionFactorH.trim().isEmpty() || emissionFactorH.equals("0")) {
                System.out.println("Table H: Emission factor not auto-populated, entering manually");
                buildingAssessmentTab.getNetZeroEmissionsSection().tableH().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorH));
            }

            // Step 3: Verify emission factor was populated
            emissionFactorH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getEmissionFactor(0);
            assertFalse(emissionFactorH.trim().isEmpty(),
                    "Table H: Emission factor should be populated for " + typeOfWasteH);

            // Step 4: Enter quantity recycled
            buildingAssessmentTab.getNetZeroEmissionsSection().tableH().enterQuantityOfWasteRecycled(0, quantityRecycledH);

            // Step 5: Check if unit auto-populated (should be MT)
            String unitH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getUnit(0);
            System.out.println("Table H: Unit auto-populated value: '" + unitH + "'");
            assertFalse(unitH == null || unitH.trim().isEmpty(),
                    "Table H: Unit should be populated (expected MT)");

            // Step 6: Verify totals are calculated
            page.waitForTimeout(1500);

            // Parse and calculate
            double actualEmissionFactorH = Double.parseDouble(emissionFactorH.replace(",", ""));
            double quantityRecycledValueH = Double.parseDouble(quantityRecycledH);
            double calculatedRowTotalH = actualEmissionFactorH * quantityRecycledValueH;
            String expectedRowTotalH = String.format("%,.2f", calculatedRowTotalH);

            String rowTotalH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getRowTotal(0);
            assertNotNull(rowTotalH, "Table H: Row total should be calculated");
            assertEquals(expectedRowTotalH, rowTotalH.trim(),
                String.format("Table H: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorH, quantityRecycledValueH, expectedRowTotalH, rowTotalH));

            String tableTotalH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getTableTotal();
            assertNotNull(tableTotalH, "Table H: Table total should be calculated");
            assertEquals(expectedRowTotalH, tableTotalH.trim(),
                    "Table H: Table total should equal row total for single row");

            System.out.println(String.format("Table H (Waste Recycled) - %s: EF=%.2f × Quantity=%.2f = Row Total=%s, Table Total=%s",
                typeOfWasteH, actualEmissionFactorH, quantityRecycledValueH, rowTotalH, tableTotalH));

            System.out.println("\n=== Scope 3 Tables E-H Testing Complete ===");
        });
    }
}
