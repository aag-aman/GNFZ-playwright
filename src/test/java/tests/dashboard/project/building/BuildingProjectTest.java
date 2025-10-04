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

            // ========================================
            // Table A (Fuels)
            // ========================================
            System.out.println("\n=== Testing Table A (Fuels) ===");

            // Define test data
            String fuelTypeA = "Natural Gas";
            String consumptionA = "100";
            double expectedEmissionFactorA = 2539.25;

            // Enter fuel
            buildingAssessmentTab.getNetZeroEmissionsSection().tableA().enterFuel(0, fuelTypeA);

            // Check emission factor auto-population
            String emissionFactorA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getEmissionFactor(0);
            if (emissionFactorA == null || emissionFactorA.trim().isEmpty() || emissionFactorA.equals("0")) {
                System.out.println("Table A: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorA);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableA().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorA));
                emissionFactorA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getEmissionFactor(0);
            } else {
                System.out.println("Table A: Emission factor auto-populated: " + emissionFactorA);
            }

            // Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableA().enterConsumption(0, consumptionA);

            // Check units auto-population
            String unitsA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getUnits(0);
            System.out.println("Table A: Units auto-populated: " + unitsA);

            // Wait for calculations
            page.waitForTimeout(1000);

            // Get and verify totals
            String rowTotalA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getRowTotal(0);
            String tableTotalA = buildingAssessmentTab.getNetZeroEmissionsSection().tableA().getTableTotal();
            System.out.println("Table A: Row total calculated: " + rowTotalA);
            System.out.println("Table A: Table total calculated: " + tableTotalA);

            // Verify calculation: emission_factor × consumption
            double actualEmissionFactorA = Double.parseDouble(emissionFactorA.replace(",", ""));
            double consumptionValueA = Double.parseDouble(consumptionA);
            double calculatedRowTotalA = actualEmissionFactorA * consumptionValueA;
            String expectedRowTotalA = String.format("%,.2f", calculatedRowTotalA);

            assertNotNull(rowTotalA, "Table A: Row total should be calculated");
            assertEquals(expectedRowTotalA, rowTotalA.trim(),
                String.format("Table A: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorA, consumptionValueA, expectedRowTotalA, rowTotalA));

            assertNotNull(tableTotalA, "Table A: Table total should be calculated");
            assertEquals(expectedRowTotalA, tableTotalA.trim(),
                "Table A: Table total should equal row total for single row");

            System.out.println(String.format("Table A verified: %s, EF=%.2f × Consumption=%.2f = %s ✓",
                fuelTypeA, actualEmissionFactorA, consumptionValueA, rowTotalA));

            // ========================================
            // Table B (Refrigerants)
            // ========================================
            System.out.println("\n=== Testing Table B (Refrigerants) ===");

            // Define test data
            String refrigerantTypeB = "R-410A";
            String consumptionB = "10";
            double expectedEmissionFactorB = 1182.0;

            // Enter refrigerant type
            buildingAssessmentTab.getNetZeroEmissionsSection().tableB().enterType(0, refrigerantTypeB);

            // Check emission factor auto-population
            String emissionFactorB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getEmissionFactor(0);
            if (emissionFactorB == null || emissionFactorB.trim().isEmpty() || emissionFactorB.equals("0")) {
                System.out.println("Table B: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorB);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableB().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorB));
                emissionFactorB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getEmissionFactor(0);
            } else {
                System.out.println("Table B: Emission factor auto-populated: " + emissionFactorB);
            }

            // Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableB().enterConsumption(0, consumptionB);

            // Check unit auto-population
            String unitB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getUnit(0);
            System.out.println("Table B: Unit auto-populated: " + unitB);

            // Wait for calculations
            page.waitForTimeout(1000);

            // Get and verify totals
            String rowTotalB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getRowTotal(0);
            String tableTotalB = buildingAssessmentTab.getNetZeroEmissionsSection().tableB().getTableTotal();
            System.out.println("Table B: Row total calculated: " + rowTotalB);
            System.out.println("Table B: Table total calculated: " + tableTotalB);

            // Verify calculation: emission_factor × consumption
            double actualEmissionFactorB = Double.parseDouble(emissionFactorB.replace(",", ""));
            double consumptionValueB = Double.parseDouble(consumptionB);
            double calculatedRowTotalB = actualEmissionFactorB * consumptionValueB;
            String expectedRowTotalB = String.format("%,.2f", calculatedRowTotalB);

            assertNotNull(rowTotalB, "Table B: Row total should be calculated");
            assertEquals(expectedRowTotalB, rowTotalB.trim(),
                String.format("Table B: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorB, consumptionValueB, expectedRowTotalB, rowTotalB));

            assertNotNull(tableTotalB, "Table B: Table total should be calculated");
            assertEquals(expectedRowTotalB, tableTotalB.trim(),
                    "Table B: Table total should equal row total for single row");

            System.out.println(String.format("Table B verified: %s, EF=%.2f × Consumption=%.2f = %s ✓",
                refrigerantTypeB, actualEmissionFactorB, consumptionValueB, rowTotalB));

            // ========================================
            // Table C (Mobile Combustion)
            // ========================================
            System.out.println("\n=== Testing Table C (Mobile Combustion) ===");

            // Define test data
            String fuelTypeC = "Gasoline";
            String consumptionC = "200";
            double expectedEmissionFactorC = 34.0;

            // Enter fuel type
            buildingAssessmentTab.getNetZeroEmissionsSection().tableC().enterFuel(0, fuelTypeC);

            // Check emission factor auto-population
            String emissionFactorC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getEmissionFactor(0);
            if (emissionFactorC == null || emissionFactorC.trim().isEmpty() || emissionFactorC.equals("0")) {
                System.out.println("Table C: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorC);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableC().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorC));
                emissionFactorC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getEmissionFactor(0);
            } else {
                System.out.println("Table C: Emission factor auto-populated: " + emissionFactorC);
            }

            // Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableC().enterConsumption(0, consumptionC);

            // Check units auto-population
            String unitsC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getUnits(0);
            System.out.println("Table C: Units auto-populated: " + unitsC);

            // Wait for calculations
            page.waitForTimeout(1000);

            // Get and verify totals
            String rowTotalC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getRowTotal(0);
            String tableTotalC = buildingAssessmentTab.getNetZeroEmissionsSection().tableC().getTableTotal();
            System.out.println("Table C: Row total calculated: " + rowTotalC);
            System.out.println("Table C: Table total calculated: " + tableTotalC);

            // Verify calculation: emission_factor × consumption
            double actualEmissionFactorC = Double.parseDouble(emissionFactorC.replace(",", ""));
            double consumptionValueC = Double.parseDouble(consumptionC);
            double calculatedRowTotalC = actualEmissionFactorC * consumptionValueC;
            String expectedRowTotalC = String.format("%,.2f", calculatedRowTotalC);

            assertNotNull(rowTotalC, "Table C: Row total should be calculated");
            assertEquals(expectedRowTotalC, rowTotalC.trim(),
                String.format("Table C: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorC, consumptionValueC, expectedRowTotalC, rowTotalC));

            assertNotNull(tableTotalC, "Table C: Table total should be calculated");
            assertEquals(expectedRowTotalC, tableTotalC.trim(),
                    "Table C: Table total should equal row total for single row");

            System.out.println(String.format("Table C verified: %s, EF=%.2f × Consumption=%.2f = %s ✓",
                fuelTypeC, actualEmissionFactorC, consumptionValueC, rowTotalC));

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
            page.waitForTimeout(500);

            // Define test data
            String activityD = "Non Renewable Electricity from Grid​";
            String consumptionD = "100";
            double expectedEmissionFactorD = 0.149;

            // Enter activity
            buildingAssessmentTab.getNetZeroEmissionsSection().tableD().enterActivity(0, activityD);

            // Check emission factor auto-population
            String emissionFactorD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getEmissionFactor(0);
            if (emissionFactorD == null || emissionFactorD.trim().isEmpty() || emissionFactorD.equals("0")) {
                System.out.println("Table D: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorD);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableD().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorD));
                emissionFactorD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getEmissionFactor(0);
            } else {
                System.out.println("Table D: Emission factor auto-populated: " + emissionFactorD);
            }

            // Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableD().enterConsumption(0, consumptionD);

            // Check units auto-population
            String unitsD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getUnits(0);
            System.out.println("Table D: Units auto-populated: " + unitsD);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getRowTotal(0);
            String tableTotalD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getTableTotal();
            System.out.println("Table D: Row total calculated: " + rowTotalD);
            System.out.println("Table D: Table total calculated: " + tableTotalD);

            // Verify calculation: emission_factor × consumption
            double actualEmissionFactorD = Double.parseDouble(emissionFactorD.replace(",", ""));
            double consumptionValueD = Double.parseDouble(consumptionD);
            double calculatedRowTotalD = actualEmissionFactorD * consumptionValueD;
            String expectedRowTotalD = String.format("%,.2f", calculatedRowTotalD);

            assertNotNull(rowTotalD, "Table D: Row total should be calculated");
            assertEquals(expectedRowTotalD, rowTotalD.trim(),
                String.format("Table D: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorD, consumptionValueD, expectedRowTotalD, rowTotalD));

            assertNotNull(tableTotalD, "Table D: Table total should be calculated");
            assertEquals(expectedRowTotalD, tableTotalD.trim(),
                    "Table D: Table total should equal row total for single row");

            System.out.println(String.format("Table D verified: %s, EF=%.2f × Consumption=%.2f = %s ✓",
                activityD, actualEmissionFactorD, consumptionValueD, rowTotalD));

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

            // Enter activity
            buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterActivity(0, activityE);

            // Check emission factor auto-population
            String emissionFactorE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getEmissionFactor(0);
            if (emissionFactorE == null || emissionFactorE.trim().isEmpty() || emissionFactorE.equals("0")) {
                System.out.println("Table E: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorE);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorE));
                emissionFactorE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getEmissionFactor(0);
            } else {
                System.out.println("Table E: Emission factor auto-populated: " + emissionFactorE);
            }

            // Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterConsumption(0, consumptionE);

            // Check unit auto-population
            String unitE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getUnit(0);
            System.out.println("Table E: Unit auto-populated: " + unitE);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getRowTotal(0);
            String tableTotalE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getTableTotal();
            System.out.println("Table E: Row total calculated: " + rowTotalE);
            System.out.println("Table E: Table total calculated: " + tableTotalE);

            // Verify calculation: emission_factor × consumption
            double actualEmissionFactorE = Double.parseDouble(emissionFactorE.replace(",", ""));
            double consumptionValueE = Double.parseDouble(consumptionE);
            double calculatedRowTotalE = actualEmissionFactorE * consumptionValueE;
            String expectedRowTotalE = String.format("%,.2f", calculatedRowTotalE);

            assertNotNull(rowTotalE, "Table E: Row total should be calculated");
            assertEquals(expectedRowTotalE, rowTotalE.trim(),
                String.format("Table E: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorE, consumptionValueE, expectedRowTotalE, rowTotalE));

            assertNotNull(tableTotalE, "Table E: Table total should be calculated");
            assertEquals(expectedRowTotalE, tableTotalE.trim(),
                    "Table E: Table total should equal row total for single row");

            System.out.println(String.format("Table E verified: %s, EF=%.2f × Consumption=%.2f = %s ✓",
                activityE, actualEmissionFactorE, consumptionValueE, rowTotalE));

            // ========================================
            // Table F (Waste Disposal)
            // ========================================
            System.out.println("\n=== Testing Table F (Waste Disposal) ===");

            // Define test data
            String typeOfWasteF = "Aggregates";
            String quantityGeneratedF = "150";
            String quantityLandfillF = "80";
            double expectedEmissionFactorF = 11.0;

            // Enter type of waste
            buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterTypeOfWaste(0, typeOfWasteF);

            // Check emission factor auto-population
            String emissionFactorF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getEmissionFactor(0);
            if (emissionFactorF == null || emissionFactorF.trim().isEmpty() || emissionFactorF.equals("0")) {
                System.out.println("Table F: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorF);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorF));
                emissionFactorF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getEmissionFactor(0);
            } else {
                System.out.println("Table F: Emission factor auto-populated: " + emissionFactorF);
            }

            // Enter quantities
            buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterQuantityOfWasteGenerated(0, quantityGeneratedF);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableF().enterQuantityOfWasteSentToLandfill(0, quantityLandfillF);

            // Check unit auto-population
            String unitF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getUnit(0);
            System.out.println("Table F: Unit auto-populated: " + unitF);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getRowTotal(0);
            String tableTotalF = buildingAssessmentTab.getNetZeroEmissionsSection().tableF().getTableTotal();
            System.out.println("Table F: Row total calculated: " + rowTotalF);
            System.out.println("Table F: Table total calculated: " + tableTotalF);

            // Verify calculation: emission_factor × quantity_sent_to_landfill
            double actualEmissionFactorF = Double.parseDouble(emissionFactorF.replace(",", ""));
            double quantityLandfillValueF = Double.parseDouble(quantityLandfillF);
            double calculatedRowTotalF = actualEmissionFactorF * quantityLandfillValueF;
            String expectedRowTotalF = String.format("%,.2f", calculatedRowTotalF);

            assertNotNull(rowTotalF, "Table F: Row total should be calculated");
            assertEquals(expectedRowTotalF, rowTotalF.trim(),
                String.format("Table F: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorF, quantityLandfillValueF, expectedRowTotalF, rowTotalF));

            assertNotNull(tableTotalF, "Table F: Table total should be calculated");
            assertEquals(expectedRowTotalF, tableTotalF.trim(),
                    "Table F: Table total should equal row total for single row");

            System.out.println(String.format("Table F verified: %s, EF=%.2f × Landfill Qty=%.2f = %s ✓",
                typeOfWasteF, actualEmissionFactorF, quantityLandfillValueF, rowTotalF));

            // ========================================
            // Table G (Composed Waste)
            // ========================================
            System.out.println("\n=== Testing Table G (Composed Waste) ===");

            // Define test data
            String typeOfWasteG = "Organic: mixed food and garden waste";
            String quantityCompostedG = "90";
            double expectedEmissionFactorG = 8.911;

            // Enter type of waste
            buildingAssessmentTab.getNetZeroEmissionsSection().tableG().enterTypeOfWaste(0, typeOfWasteG);

            // Check emission factor auto-population
            String emissionFactorG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getEmissionFactor(0);
            if (emissionFactorG == null || emissionFactorG.trim().isEmpty() || emissionFactorG.equals("0")) {
                System.out.println("Table G: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorG);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableG().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorG));
                emissionFactorG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getEmissionFactor(0);
            } else {
                System.out.println("Table G: Emission factor auto-populated: " + emissionFactorG);
            }

            // Enter quantity composted
            buildingAssessmentTab.getNetZeroEmissionsSection().tableG().enterQuantityOfWasteComposted(0, quantityCompostedG);

            // Check unit auto-population
            String unitG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getUnit(0);
            System.out.println("Table G: Unit auto-populated: " + unitG);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getRowTotal(0);
            String tableTotalG = buildingAssessmentTab.getNetZeroEmissionsSection().tableG().getTableTotal();
            System.out.println("Table G: Row total calculated: " + rowTotalG);
            System.out.println("Table G: Table total calculated: " + tableTotalG);

            // Verify calculation: emission_factor × quantity_composted
            double actualEmissionFactorG = Double.parseDouble(emissionFactorG.replace(",", ""));
            double quantityCompostedValueG = Double.parseDouble(quantityCompostedG);
            double calculatedRowTotalG = actualEmissionFactorG * quantityCompostedValueG;
            String expectedRowTotalG = String.format("%,.2f", calculatedRowTotalG);

            assertNotNull(rowTotalG, "Table G: Row total should be calculated");
            assertEquals(expectedRowTotalG, rowTotalG.trim(),
                String.format("Table G: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorG, quantityCompostedValueG, expectedRowTotalG, rowTotalG));

            assertNotNull(tableTotalG, "Table G: Table total should be calculated");
            assertEquals(expectedRowTotalG, tableTotalG.trim(),
                    "Table G: Table total should equal row total for single row");

            System.out.println(String.format("Table G verified: %s, EF=%.2f × Quantity Composted=%.2f = %s ✓",
                typeOfWasteG, actualEmissionFactorG, quantityCompostedValueG, rowTotalG));

            // ========================================
            // Table H (Waste Recycled)
            // ========================================
            System.out.println("\n=== Testing Table H (Waste Recycled) ===");

            // Define test data
            String typeOfWasteH = "Asphalt";
            String quantityRecycledH = "110";
            double expectedEmissionFactorH = 0.985;

            // Enter type of waste
            buildingAssessmentTab.getNetZeroEmissionsSection().tableH().enterTypeOfWaste(0, typeOfWasteH);

            // Check emission factor auto-population
            String emissionFactorH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getEmissionFactor(0);
            if (emissionFactorH == null || emissionFactorH.trim().isEmpty() || emissionFactorH.equals("0")) {
                System.out.println("Table H: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorH);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableH().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorH));
                emissionFactorH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getEmissionFactor(0);
            } else {
                System.out.println("Table H: Emission factor auto-populated: " + emissionFactorH);
            }

            // Enter quantity recycled
            buildingAssessmentTab.getNetZeroEmissionsSection().tableH().enterQuantityOfWasteRecycled(0, quantityRecycledH);

            // Check unit auto-population
            String unitH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getUnit(0);
            System.out.println("Table H: Unit auto-populated: " + unitH);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getRowTotal(0);
            String tableTotalH = buildingAssessmentTab.getNetZeroEmissionsSection().tableH().getTableTotal();
            System.out.println("Table H: Row total calculated: " + rowTotalH);
            System.out.println("Table H: Table total calculated: " + tableTotalH);

            // Verify calculation: emission_factor × quantity_recycled
            double actualEmissionFactorH = Double.parseDouble(emissionFactorH.replace(",", ""));
            double quantityRecycledValueH = Double.parseDouble(quantityRecycledH);
            double calculatedRowTotalH = actualEmissionFactorH * quantityRecycledValueH;
            String expectedRowTotalH = String.format("%,.2f", calculatedRowTotalH);

            assertNotNull(rowTotalH, "Table H: Row total should be calculated");
            assertEquals(expectedRowTotalH, rowTotalH.trim(),
                String.format("Table H: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorH, quantityRecycledValueH, expectedRowTotalH, rowTotalH));

            assertNotNull(tableTotalH, "Table H: Table total should be calculated");
            assertEquals(expectedRowTotalH, tableTotalH.trim(),
                    "Table H: Table total should equal row total for single row");

            System.out.println(String.format("Table H verified: %s, EF=%.2f × Quantity Recycled=%.2f = %s ✓",
                typeOfWasteH, actualEmissionFactorH, quantityRecycledValueH, rowTotalH));

            // ========================================
            // Table I (Waste Incinerated)
            // ========================================
            System.out.println("\n=== Testing Table I (Waste Incinerated) ===");

            // Define test data
            String typeOfWasteI = "Glass";
            String quantityIncineratedI = "85";
            double expectedEmissionFactorI = 21.28;

            // Enter type of waste
            buildingAssessmentTab.getNetZeroEmissionsSection().tableI().enterTypeOfWaste(0, typeOfWasteI);

            // Check emission factor auto-population
            String emissionFactorI = buildingAssessmentTab.getNetZeroEmissionsSection().tableI().getEmissionFactor(0);
            if (emissionFactorI == null || emissionFactorI.trim().isEmpty() || emissionFactorI.equals("0")) {
                System.out.println("Table I: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorI);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableI().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorI));
                emissionFactorI = buildingAssessmentTab.getNetZeroEmissionsSection().tableI().getEmissionFactor(0);
            } else {
                System.out.println("Table I: Emission factor auto-populated: " + emissionFactorI);
            }

            // Enter quantity incinerated
            buildingAssessmentTab.getNetZeroEmissionsSection().tableI().enterQuantityOfWasteIncinerated(0, quantityIncineratedI);

            // Check unit auto-population
            String unitI = buildingAssessmentTab.getNetZeroEmissionsSection().tableI().getUnit(0);
            System.out.println("Table I: Unit auto-populated: " + unitI);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalI = buildingAssessmentTab.getNetZeroEmissionsSection().tableI().getRowTotal(0);
            String tableTotalI = buildingAssessmentTab.getNetZeroEmissionsSection().tableI().getTableTotal();
            System.out.println("Table I: Row total calculated: " + rowTotalI);
            System.out.println("Table I: Table total calculated: " + tableTotalI);

            // Verify calculation: emission_factor × quantity_incinerated
            double actualEmissionFactorI = Double.parseDouble(emissionFactorI.replace(",", ""));
            double quantityIncineratedValueI = Double.parseDouble(quantityIncineratedI);
            double calculatedRowTotalI = actualEmissionFactorI * quantityIncineratedValueI;
            String expectedRowTotalI = String.format("%,.2f", calculatedRowTotalI);

            assertNotNull(rowTotalI, "Table I: Row total should be calculated");
            assertEquals(expectedRowTotalI, rowTotalI.trim(),
                String.format("Table I: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorI, quantityIncineratedValueI, expectedRowTotalI, rowTotalI));

            assertNotNull(tableTotalI, "Table I: Table total should be calculated");
            assertEquals(expectedRowTotalI, tableTotalI.trim(),
                    "Table I: Table total should equal row total for single row");

            System.out.println(String.format("Table I verified: %s, EF=%.2f × Quantity Incinerated=%.2f = %s ✓",
                typeOfWasteI, actualEmissionFactorI, quantityIncineratedValueI, rowTotalI));

            // ========================================
            // Table J (WTT - Well to Tank)
            // ========================================
            System.out.println("\n=== Testing Table J (WTT - Well to Tank) ===");

            // Define test data
            String fuelJ = "LNG";
            String consumptionJ = "150";
            double expectedEmissionFactorJ = 885.68706;

            // Enter fuel
            buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().enterFuel(0, fuelJ);

            // Check emission factor auto-population
            String emissionFactorJ = buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().getEmissionFactor(0);
            if (emissionFactorJ == null || emissionFactorJ.trim().isEmpty() || emissionFactorJ.equals("0")) {
                System.out.println("Table J: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorJ);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorJ));
                emissionFactorJ = buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().getEmissionFactor(0);
            } else {
                System.out.println("Table J: Emission factor auto-populated: " + emissionFactorJ);
            }

            // Enter consumption
            buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().enterConsumption(0, consumptionJ);

            // Check units auto-population
            String unitsJ = buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().getUnits(0);
            System.out.println("Table J: Units auto-populated: " + unitsJ);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalJ = buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().getRowTotal(0);
            String tableTotalJ = buildingAssessmentTab.getNetZeroEmissionsSection().tableJ().getTableTotal();
            System.out.println("Table J: Row total calculated: " + rowTotalJ);
            System.out.println("Table J: Table total calculated: " + tableTotalJ);

            // Verify calculation: emission_factor × consumption
            double actualEmissionFactorJ = Double.parseDouble(emissionFactorJ.replace(",", ""));
            double consumptionValueJ = Double.parseDouble(consumptionJ);
            double calculatedRowTotalJ = actualEmissionFactorJ * consumptionValueJ;
            String expectedRowTotalJ = String.format("%,.2f", calculatedRowTotalJ);

            assertNotNull(rowTotalJ, "Table J: Row total should be calculated");
            assertEquals(expectedRowTotalJ, rowTotalJ.trim(),
                String.format("Table J: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorJ, consumptionValueJ, expectedRowTotalJ, rowTotalJ));

            assertNotNull(tableTotalJ, "Table J: Table total should be calculated");
            assertEquals(expectedRowTotalJ, tableTotalJ.trim(),
                    "Table J: Table total should equal row total for single row");

            System.out.println(String.format("Table J verified: %s, EF=%.2f × Consumption=%.2f = %s ✓",
                fuelJ, actualEmissionFactorJ, consumptionValueJ, rowTotalJ));

            // ========================================
            // Table K (Employee Commute) - 2 rows
            // ========================================
            System.out.println("\n=== Testing Table K (Employee Commute) ===");

            // Define test data - Row 0
            String vehicleTypeK0 = "Ferry";
            String vehicleSizeK0 = "Foot passenger";
            String fuelK0 = "Gasoline";
            String distanceK0 = "500";
            double expectedEmissionFactorK0 = 0.01874;

            // Define test data - Row 1
            String vehicleTypeK1 = "Cars (by size)";
            String vehicleSizeK1 = "Small car";
            String fuelK1 = "Petrol";
            String distanceK1 = "800";
            double expectedEmissionFactorK1 = 0.14652;

            // Row 0: Enter vehicle details
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterVehicleType(0, vehicleTypeK0);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterVehicleSize(0, vehicleSizeK0);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterFuel(0, fuelK0);

            // Row 0: Check emission factor auto-population
            String emissionFactorK0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getEmissionFactor(0);
            if (emissionFactorK0 == null || emissionFactorK0.trim().isEmpty() || emissionFactorK0.equals("0")) {
                System.out.println("Table K Row 0: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorK0);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorK0));
                emissionFactorK0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getEmissionFactor(0);
            } else {
                System.out.println("Table K Row 0: Emission factor auto-populated: " + emissionFactorK0);
            }

            // Row 0: Enter distance
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterTotalDistance(0, distanceK0);

            // Row 0: Wait for calculations
            page.waitForTimeout(1500);

            // Row 0: Get and verify totals
            String rowTotalK0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getRowTotal(0);
            System.out.println("Table K Row 0: Row total calculated: " + rowTotalK0);

            // Row 0: Verify calculation: distance × emission_factor
            double actualEmissionFactorK0 = Double.parseDouble(emissionFactorK0.replace(",", ""));
            String actualDistanceK0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getTotalDistance(0);
            double distanceValueK0 = Double.parseDouble(actualDistanceK0);
            double calculatedRowTotalK0 = distanceValueK0 * actualEmissionFactorK0;
            String expectedRowTotalK0 = String.format("%,.2f", calculatedRowTotalK0);

            assertNotNull(rowTotalK0, "Table K Row 0: Row total should be calculated");
            assertEquals(expectedRowTotalK0, rowTotalK0.trim(),
                String.format("Table K Row 0: calculation incorrect (%.2f × %.5f = %s, Got: %s)",
                    distanceValueK0, actualEmissionFactorK0, expectedRowTotalK0, rowTotalK0));

            System.out.println(String.format("Table K Row 0 verified: %s/%s/%s, EF=%.5f × Distance=%.2f = %s ✓",
                vehicleTypeK0, vehicleSizeK0, fuelK0, actualEmissionFactorK0, distanceValueK0, rowTotalK0));

            // Add Row 1
            System.out.println("Table K: Adding row 1");
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().addRow(0);

            // Row 1: Enter vehicle details
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterVehicleType(1, vehicleTypeK1);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterVehicleSize(1, vehicleSizeK1);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterFuel(1, fuelK1);

            // Row 1: Check emission factor auto-population
            String emissionFactorK1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getEmissionFactor(1);
            if (emissionFactorK1 == null || emissionFactorK1.trim().isEmpty() || emissionFactorK1.equals("0")) {
                System.out.println("Table K Row 1: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorK1);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterEmissionFactor(1,
                    String.valueOf(expectedEmissionFactorK1));
                emissionFactorK1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getEmissionFactor(1);
            } else {
                System.out.println("Table K Row 1: Emission factor auto-populated: " + emissionFactorK1);
            }

            // Row 1: Enter distance
            buildingAssessmentTab.getNetZeroEmissionsSection().tableK().enterTotalDistance(1, distanceK1);

            // Row 1: Wait for calculations
            page.waitForTimeout(1500);

            // Row 1: Get and verify totals
            String rowTotalK1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getRowTotal(1);
            System.out.println("Table K Row 1: Row total calculated: " + rowTotalK1);

            // Row 1: Verify calculation: distance × emission_factor
            double actualEmissionFactorK1 = Double.parseDouble(emissionFactorK1.replace(",", ""));
            String actualDistanceK1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getTotalDistance(1);
            double distanceValueK1 = Double.parseDouble(actualDistanceK1);
            double calculatedRowTotalK1 = distanceValueK1 * actualEmissionFactorK1;
            String expectedRowTotalK1 = String.format("%,.2f", calculatedRowTotalK1);

            assertNotNull(rowTotalK1, "Table K Row 1: Row total should be calculated");
            assertEquals(expectedRowTotalK1, rowTotalK1.trim(),
                String.format("Table K Row 1: calculation incorrect (%.2f × %.5f = %s, Got: %s)",
                    distanceValueK1, actualEmissionFactorK1, expectedRowTotalK1, rowTotalK1));

            System.out.println(String.format("Table K Row 1 verified: %s/%s/%s, EF=%.5f × Distance=%.2f = %s ✓",
                vehicleTypeK1, vehicleSizeK1, fuelK1, actualEmissionFactorK1, distanceValueK1, rowTotalK1));

            // Verify Table K total = row0 + row1
            String tableTotalK = buildingAssessmentTab.getNetZeroEmissionsSection().tableK().getTableTotal();
            double row0ValueK = Double.parseDouble(rowTotalK0.replace(",", ""));
            double row1ValueK = Double.parseDouble(rowTotalK1.replace(",", ""));
            double calculatedTableTotalK = row0ValueK + row1ValueK;
            String expectedTableTotalK = String.format("%,.2f", calculatedTableTotalK);

            assertEquals(expectedTableTotalK, tableTotalK.trim(),
                String.format("Table K: Table total should equal sum of rows (%.2f + %.2f = %s, Got: %s)",
                    row0ValueK, row1ValueK, expectedTableTotalK, tableTotalK));

            System.out.println("Table K: Table total verified (sum of rows): " + tableTotalK);

            // ========================================
            // Table L (Business Travel) - 2 rows
            // ========================================
            System.out.println("\n=== Testing Table L (Business Travel) ===");

            // Define test data - Row 0
            String vehicleTypeL0 = "Cars (by size)";
            String vehicleSizeL0 = "Small car";
            String fuelL0 = "Petrol";
            String distanceL0 = "600";
            double expectedEmissionFactorL0 = 0.168004;

            // Define test data - Row 1
            String vehicleTypeL1 = "Motorbike";
            String vehicleSizeL1 = "Small";
            String fuelL1 = "Petrol";
            String distanceL1 = "450";
            double expectedEmissionFactorL1 = 0.08306;

            // Row 0: Enter vehicle details
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterVehicleType(0, vehicleTypeL0);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterVehicleSize(0, vehicleSizeL0);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterFuel(0, fuelL0);

            // Row 0: Check emission factor auto-population
            String emissionFactorL0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getEmissionFactor(0);
            if (emissionFactorL0 == null || emissionFactorL0.trim().isEmpty() || emissionFactorL0.equals("0")) {
                System.out.println("Table L Row 0: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorL0);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorL0));
                emissionFactorL0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getEmissionFactor(0);
            } else {
                System.out.println("Table L Row 0: Emission factor auto-populated: " + emissionFactorL0);
            }

            // Row 0: Enter distance
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterTotalDistance(0, distanceL0);

            // Row 0: Wait for calculations
            page.waitForTimeout(1500);

            // Row 0: Get and verify totals
            String rowTotalL0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getRowTotal(0);
            System.out.println("Table L Row 0: Row total calculated: " + rowTotalL0);

            // Row 0: Verify calculation: distance × emission_factor
            double actualEmissionFactorL0 = Double.parseDouble(emissionFactorL0.replace(",", ""));
            String actualDistanceL0 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getTotalDistance(0);
            double distanceValueL0 = Double.parseDouble(actualDistanceL0);
            double calculatedRowTotalL0 = distanceValueL0 * actualEmissionFactorL0;
            String expectedRowTotalL0 = String.format("%,.2f", calculatedRowTotalL0);

            assertNotNull(rowTotalL0, "Table L Row 0: Row total should be calculated");
            assertEquals(expectedRowTotalL0, rowTotalL0.trim(),
                String.format("Table L Row 0: calculation incorrect (%.2f × %.6f = %s, Got: %s)",
                    distanceValueL0, actualEmissionFactorL0, expectedRowTotalL0, rowTotalL0));

            System.out.println(String.format("Table L Row 0 verified: %s/%s/%s, EF=%.6f × Distance=%.2f = %s ✓",
                vehicleTypeL0, vehicleSizeL0, fuelL0, actualEmissionFactorL0, distanceValueL0, rowTotalL0));

            // Add Row 1
            System.out.println("Table L: Adding row 1");
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().addRow(0);

            // Row 1: Enter vehicle details
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterVehicleType(1, vehicleTypeL1);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterVehicleSize(1, vehicleSizeL1);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterFuel(1, fuelL1);

            // Row 1: Check emission factor auto-population
            String emissionFactorL1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getEmissionFactor(1);
            if (emissionFactorL1 == null || emissionFactorL1.trim().isEmpty() || emissionFactorL1.equals("0")) {
                System.out.println("Table L Row 1: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorL1);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterEmissionFactor(1,
                    String.valueOf(expectedEmissionFactorL1));
                emissionFactorL1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getEmissionFactor(1);
            } else {
                System.out.println("Table L Row 1: Emission factor auto-populated: " + emissionFactorL1);
            }

            // Row 1: Enter distance
            buildingAssessmentTab.getNetZeroEmissionsSection().tableL().enterTotalDistance(1, distanceL1);

            // Row 1: Wait for calculations
            page.waitForTimeout(1500);

            // Row 1: Get and verify totals
            String rowTotalL1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getRowTotal(1);
            System.out.println("Table L Row 1: Row total calculated: " + rowTotalL1);

            // Row 1: Verify calculation: distance × emission_factor
            double actualEmissionFactorL1 = Double.parseDouble(emissionFactorL1.replace(",", ""));
            String actualDistanceL1 = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getTotalDistance(1);
            double distanceValueL1 = Double.parseDouble(actualDistanceL1);
            double calculatedRowTotalL1 = distanceValueL1 * actualEmissionFactorL1;
            String expectedRowTotalL1 = String.format("%,.2f", calculatedRowTotalL1);

            assertNotNull(rowTotalL1, "Table L Row 1: Row total should be calculated");
            assertEquals(expectedRowTotalL1, rowTotalL1.trim(),
                String.format("Table L Row 1: calculation incorrect (%.2f × %.5f = %s, Got: %s)",
                    distanceValueL1, actualEmissionFactorL1, expectedRowTotalL1, rowTotalL1));

            System.out.println(String.format("Table L Row 1 verified: %s/%s/%s, EF=%.5f × Distance=%.2f = %s ✓",
                vehicleTypeL1, vehicleSizeL1, fuelL1, actualEmissionFactorL1, distanceValueL1, rowTotalL1));

            // Verify Table L total = row0 + row1
            String tableTotalL = buildingAssessmentTab.getNetZeroEmissionsSection().tableL().getTableTotal();
            double row0ValueL = Double.parseDouble(rowTotalL0.replace(",", ""));
            double row1ValueL = Double.parseDouble(rowTotalL1.replace(",", ""));
            double calculatedTableTotalL = row0ValueL + row1ValueL;
            String expectedTableTotalL = String.format("%,.2f", calculatedTableTotalL);

            assertEquals(expectedTableTotalL, tableTotalL.trim(),
                String.format("Table L: Table total should equal sum of rows (%.2f + %.2f = %s, Got: %s)",
                    row0ValueL, row1ValueL, expectedTableTotalL, tableTotalL));

            System.out.println("Table L: Table total verified (sum of rows): " + tableTotalL);

            System.out.println("\n=== Scope 3 Tables E-L Testing Complete ===");
        });
    }
}
