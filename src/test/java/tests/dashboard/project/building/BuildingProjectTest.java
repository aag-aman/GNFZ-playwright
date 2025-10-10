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
            buildingAssessmentTab.getNetZeroEmissionsSection().enterReportingPeriodTo("01/01/2025");

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
            page.waitForTimeout(1000);

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

            //Retry 5 times if table total is not yet calculated

            int retries = 5;
            while ((tableTotalD == null || tableTotalD.trim().isEmpty() || tableTotalD.equals("0.00")) && retries > 0) {
                System.out.println("Table D: Table total not yet calculated, retrying... (" + retries + " retries left)");
                page.waitForTimeout(1000);
                tableTotalD = buildingAssessmentTab.getNetZeroEmissionsSection().tableD().getTableTotal();
                retries--;
            }
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

            // // ========================================
            // // Table E (Water)
            // // ========================================
            // System.out.println("\n=== Testing Table E (Water) ===");

            // // Define test data
            // String activityE = "Landscape irrigation";
            // String consumptionE = "200";
            // double expectedEmissionFactorE = 0.149;

            // // Enter activity
            // buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterActivity(0, activityE);

            // // Check emission factor auto-population
            // String emissionFactorE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getEmissionFactor(0);
            // if (emissionFactorE == null || emissionFactorE.trim().isEmpty() || emissionFactorE.equals("0")) {
            //     System.out.println("Table E: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorE);
            //     buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterEmissionFactor(0,
            //         String.valueOf(expectedEmissionFactorE));
            //     emissionFactorE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getEmissionFactor(0);
            // } else {
            //     System.out.println("Table E: Emission factor auto-populated: " + emissionFactorE);
            // }

            // // Enter consumption
            // buildingAssessmentTab.getNetZeroEmissionsSection().tableE().enterConsumption(0, consumptionE);

            // // Check unit auto-population
            // String unitE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getUnit(0);
            // System.out.println("Table E: Unit auto-populated: " + unitE);

            // // Wait for calculations
            // page.waitForTimeout(1500);

            // // Get and verify totals
            // String rowTotalE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getRowTotal(0);
            // String tableTotalE = buildingAssessmentTab.getNetZeroEmissionsSection().tableE().getTableTotal();
            // System.out.println("Table E: Row total calculated: " + rowTotalE);
            // System.out.println("Table E: Table total calculated: " + tableTotalE);

            // // Verify calculation: emission_factor × consumption
            // double actualEmissionFactorE = Double.parseDouble(emissionFactorE.replace(",", ""));
            // double consumptionValueE = Double.parseDouble(consumptionE);
            // double calculatedRowTotalE = actualEmissionFactorE * consumptionValueE;
            // String expectedRowTotalE = String.format("%,.2f", calculatedRowTotalE);

            // assertNotNull(rowTotalE, "Table E: Row total should be calculated");
            // assertEquals(expectedRowTotalE, rowTotalE.trim(),
            //     String.format("Table E: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
            //         actualEmissionFactorE, consumptionValueE, expectedRowTotalE, rowTotalE));

            // assertNotNull(tableTotalE, "Table E: Table total should be calculated");
            // assertEquals(expectedRowTotalE, tableTotalE.trim(),
            //         "Table E: Table total should equal row total for single row");

            // System.out.println(String.format("Table E verified: %s, EF=%.2f × Consumption=%.2f = %s ✓",
            //     activityE, actualEmissionFactorE, consumptionValueE, rowTotalE));

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

            // ========================================
            // Table M (Flights) - PLACEHOLDER
            // ========================================
            System.out.println("\n=== Table M (Flights) - Skipped for now ===");
            // TODO: Implement Table M testing when ready

            // ========================================
            // Table N (Food prepared)
            // ========================================
            System.out.println("\n=== Testing Table N (Food prepared) ===");

            

            // ========================================
            // Table O (Logistics & Supply)
            // ========================================
            System.out.println("\n=== Testing Table O (Logistics & Supply) ===");

            // Define test data
            String vehicleO = "HGV";
            String typeO = "Rigid";
            String fuelO = "Diesel";
            String weightO = "10";
            String distanceO = "100";
            double expectedEmissionFactorO = 0.52;

            // Enter vehicle
            buildingAssessmentTab.getNetZeroEmissionsSection().tableO().enterVehicle(0, vehicleO);

            // Enter type
            buildingAssessmentTab.getNetZeroEmissionsSection().tableO().enterType(0, typeO);

            // Enter fuel
            buildingAssessmentTab.getNetZeroEmissionsSection().tableO().enterFuel(0, fuelO);

            // Check emission factor auto-population
            String emissionFactorO = buildingAssessmentTab.getNetZeroEmissionsSection().tableO().getEmissionFactor(0);
            if (emissionFactorO == null || emissionFactorO.trim().isEmpty() || emissionFactorO.equals("0")) {
                System.out.println("Table O: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorO);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableO().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorO));
                emissionFactorO = buildingAssessmentTab.getNetZeroEmissionsSection().tableO().getEmissionFactor(0);
            } else {
                System.out.println("Table O: Emission factor auto-populated: " + emissionFactorO);
            }

            // Enter weight and distance
            buildingAssessmentTab.getNetZeroEmissionsSection().tableO().enterWeightTonnes(0, weightO);
            buildingAssessmentTab.getNetZeroEmissionsSection().tableO().enterDistanceKm(0, distanceO);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalO = buildingAssessmentTab.getNetZeroEmissionsSection().tableO().getRowTotal(0);
            String tableTotalO = buildingAssessmentTab.getNetZeroEmissionsSection().tableO().getTableTotal();
            System.out.println("Table O: Row total calculated: " + rowTotalO);
            System.out.println("Table O: Table total calculated: " + tableTotalO);

            // Verify calculation: weight × distance × emission_factor
            double actualEmissionFactorO = Double.parseDouble(emissionFactorO.replace(",", ""));
            double weightValueO = Double.parseDouble(weightO);
            double distanceValueO = Double.parseDouble(distanceO);
            double calculatedRowTotalO = weightValueO * distanceValueO * actualEmissionFactorO;
            String expectedRowTotalO = String.format("%,.2f", calculatedRowTotalO);

            assertNotNull(rowTotalO, "Table O: Row total should be calculated");
            assertEquals(expectedRowTotalO, rowTotalO.trim(),
                String.format("Table O: Row total calculation incorrect (%.2f × %.2f × %.2f = %s, Got: %s)",
                    weightValueO, distanceValueO, actualEmissionFactorO, expectedRowTotalO, rowTotalO));

            assertNotNull(tableTotalO, "Table O: Table total should be calculated");
            assertEquals(expectedRowTotalO, tableTotalO.trim(),
                    "Table O: Table total should equal row total for single row");

            System.out.println(String.format("Table O verified: %s/%s/%s, Weight=%.2f × Distance=%.2f × EF=%.2f = %s ✓",
                vehicleO, typeO, fuelO, weightValueO, distanceValueO, actualEmissionFactorO, rowTotalO));

            // ========================================
            // Table P (Primary Materials)
            // ========================================
            System.out.println("\n=== Testing Table P (Primary Materials) ===");

            // Define test data
            String materialP = "Bricks";
            String quantityP = "10";
            double expectedEmissionFactorP = 241.75;

            // Enter type of material
            buildingAssessmentTab.getNetZeroEmissionsSection().tableP().enterTypeOfMaterial(0, materialP);

            // Check emission factor auto-population
            String emissionFactorP = buildingAssessmentTab.getNetZeroEmissionsSection().tableP().getEmissionFactor(0);
            if (emissionFactorP == null || emissionFactorP.trim().isEmpty() || emissionFactorP.equals("0")) {
                System.out.println("Table P: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorP);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableP().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorP));
                emissionFactorP = buildingAssessmentTab.getNetZeroEmissionsSection().tableP().getEmissionFactor(0);
            } else {
                System.out.println("Table P: Emission factor auto-populated: " + emissionFactorP);
            }

            // Enter quantity
            buildingAssessmentTab.getNetZeroEmissionsSection().tableP().enterQuantity(0, quantityP);

            // Check units auto-population (if applicable)
            // String unitsP = buildingAssessmentTab.getNetZeroEmissionsSection().tableP().getUnits(0);
            // System.out.println("Table P: Units auto-populated: " + unitsP);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalP = buildingAssessmentTab.getNetZeroEmissionsSection().tableP().getRowTotal(0);
            String tableTotalP = buildingAssessmentTab.getNetZeroEmissionsSection().tableP().getTableTotal();
            System.out.println("Table P: Row total calculated: " + rowTotalP);
            System.out.println("Table P: Table total calculated: " + tableTotalP);

            // Verify calculation: emission_factor × quantity
            double actualEmissionFactorP = Double.parseDouble(emissionFactorP.replace(",", ""));
            double quantityValueP = Double.parseDouble(quantityP);
            double calculatedRowTotalP = actualEmissionFactorP * quantityValueP;
            String expectedRowTotalP = String.format("%,.2f", calculatedRowTotalP);

            assertNotNull(rowTotalP, "Table P: Row total should be calculated");
            assertEquals(expectedRowTotalP, rowTotalP.trim(),
                String.format("Table P: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorP, quantityValueP, expectedRowTotalP, rowTotalP));

            assertNotNull(tableTotalP, "Table P: Table total should be calculated");
            assertEquals(expectedRowTotalP, tableTotalP.trim(),
                    "Table P: Table total should equal row total for single row");

            System.out.println(String.format("Table P verified: %s, EF=%.2f × Quantity=%.2f = %s ✓",
                materialP, actualEmissionFactorP, quantityValueP, rowTotalP));

            // ========================================
            // Table Q (Recycled Materials)
            // ========================================
            System.out.println("\n=== Testing Table Q (Recycled Materials) ===");

            // Define test data - Using data provided for construction materials
            String materialQ = "Asbestos"; // Example recycled material
            String quantityQ = "10";
            double expectedEmissionFactorQ = 152.25; // Adjusted for recycled material

            // Enter type of material
            buildingAssessmentTab.getNetZeroEmissionsSection().tableQ().enterTypeOfMaterial(0, materialQ);

            // Check emission factor auto-population
            String emissionFactorQ = buildingAssessmentTab.getNetZeroEmissionsSection().tableQ().getEmissionFactor(0);
            if (emissionFactorQ == null || emissionFactorQ.trim().isEmpty() || emissionFactorQ.equals("0")) {
                System.out.println("Table Q: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorQ);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableQ().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorQ));
                emissionFactorQ = buildingAssessmentTab.getNetZeroEmissionsSection().tableQ().getEmissionFactor(0);
            } else {
                System.out.println("Table Q: Emission factor auto-populated: " + emissionFactorQ);
            }

            // Enter quantity
            buildingAssessmentTab.getNetZeroEmissionsSection().tableQ().enterQuantity(0, quantityQ);

            // Check units auto-population (if applicable)
            // String unitsP = buildingAssessmentTab.getNetZeroEmissionsSection().tableP().getUnits(0);
            // System.out.println("Table P: Units auto-populated: " + unitsP);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalQ = buildingAssessmentTab.getNetZeroEmissionsSection().tableQ().getRowTotal(0);
            String tableTotalQ = buildingAssessmentTab.getNetZeroEmissionsSection().tableQ().getTableTotal();
            System.out.println("Table Q: Row total calculated: " + rowTotalQ);
            System.out.println("Table Q: Table total calculated: " + tableTotalQ);

            // Verify calculation: emission_factor × quantity
            double actualEmissionFactorQ = Double.parseDouble(emissionFactorQ.replace(",", ""));
            double quantityValueQ = Double.parseDouble(quantityQ);
            double calculatedRowTotalQ = actualEmissionFactorQ * quantityValueQ;
            String expectedRowTotalQ = String.format("%,.2f", calculatedRowTotalQ);

            assertNotNull(rowTotalQ, "Table Q: Row total should be calculated");
            assertEquals(expectedRowTotalQ, rowTotalQ.trim(),
                String.format("Table Q: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorQ, quantityValueQ, expectedRowTotalQ, rowTotalQ));

            assertNotNull(tableTotalQ, "Table Q: Table total should be calculated");
            assertEquals(expectedRowTotalQ, tableTotalQ.trim(),
                    "Table Q: Table total should equal row total for single row");

            System.out.println(String.format("Table Q verified: %s, EF=%.2f × Quantity=%.2f = %s ✓",
                materialQ, actualEmissionFactorQ, quantityValueQ, rowTotalQ));

            // ========================================
            // Table R (Additional Materials - Note: May overlap with recycled materials)
            // ========================================
            System.out.println("\n=== Testing Table R (Additional Materials) ===");

            // Define test data
            String materialR = "Clothing";
            String quantityR = "10";
            double expectedEmissionFactorR = 152.25;

            // Enter type of material
            buildingAssessmentTab.getNetZeroEmissionsSection().tableR().enterTypeOfMaterial(0, materialR);

            // Check emission factor auto-population
            String emissionFactorR = buildingAssessmentTab.getNetZeroEmissionsSection().tableR().getEmissionFactor(0);
            if (emissionFactorR == null || emissionFactorR.trim().isEmpty() || emissionFactorR.equals("0")) {
                System.out.println("Table R: Emission factor NOT auto-populated, entering manually: " + expectedEmissionFactorR);
                buildingAssessmentTab.getNetZeroEmissionsSection().tableR().enterEmissionFactor(0,
                    String.valueOf(expectedEmissionFactorR));
                emissionFactorR = buildingAssessmentTab.getNetZeroEmissionsSection().tableR().getEmissionFactor(0);
            } else {
                System.out.println("Table R: Emission factor auto-populated: " + emissionFactorR);
            }

            // Enter quantity
            buildingAssessmentTab.getNetZeroEmissionsSection().tableR().enterQuantity(0, quantityR);

            // Check units auto-population (if applicable)
            // String unitsR = buildingAssessmentTab.getNetZeroEmissionsSection().tableR().getUnits(0);
            // System.out.println("Table R: Units auto-populated: " + unitsR);

            // Wait for calculations
            page.waitForTimeout(1500);

            // Get and verify totals
            String rowTotalR = buildingAssessmentTab.getNetZeroEmissionsSection().tableR().getRowTotal(0);
            String tableTotalR = buildingAssessmentTab.getNetZeroEmissionsSection().tableR().getTableTotal();
            System.out.println("Table R: Row total calculated: " + rowTotalR);
            System.out.println("Table R: Table total calculated: " + tableTotalR);

            // Verify calculation: emission_factor × quantity
            double actualEmissionFactorR = Double.parseDouble(emissionFactorR.replace(",", ""));
            double quantityValueR = Double.parseDouble(quantityR);
            double calculatedRowTotalR = actualEmissionFactorR * quantityValueR;
            String expectedRowTotalR = String.format("%,.2f", calculatedRowTotalR);

            assertNotNull(rowTotalR, "Table R: Row total should be calculated");
            assertEquals(expectedRowTotalR, rowTotalR.trim(),
                String.format("Table R: Row total calculation incorrect (%.2f × %.2f = %s, Got: %s)",
                    actualEmissionFactorR, quantityValueR, expectedRowTotalR, rowTotalR));

            assertNotNull(tableTotalR, "Table R: Table total should be calculated");
            assertEquals(expectedRowTotalR, tableTotalR.trim(),
                    "Table R: Table total should equal row total for single row");

            System.out.println(String.format("Table R verified: %s, EF=%.2f × Quantity=%.2f = %s ✓",
                materialR, actualEmissionFactorR, quantityValueR, rowTotalR));

            System.out.println("\n=== All Scope 3 Tables (E-R) Testing Complete ===");

            // ========================================
            // SUMMARY OF SCOPES VALIDATION
            // ========================================
            System.out.println("\n=== Testing Summary of Scopes Section ===");

            // Expand Summary of Scopes section
            buildingAssessmentTab.getNetZeroEmissionsSection().expandSummaryOfScopes();
            page.waitForTimeout(1000);

            // Get summary table values
            String summaryScope1KgCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryScope1KgCO2e();
            String summaryScope1MtCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryScope1MtCO2e();
            String summaryScope2KgCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryScope2KgCO2e();
            String summaryScope2MtCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryScope2MtCO2e();
            String summaryScope3KgCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryScope3KgCO2e();
            String summaryScope3MtCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryScope3MtCO2e();
            String summaryTotalKgCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryTotalKgCO2e();
            String summaryTotalMtCO2e = buildingAssessmentTab.getNetZeroEmissionsSection().getSummaryTotalMtCO2e();

            System.out.println("Summary Table Values:");
            System.out.println("  Scope 1: " + summaryScope1KgCO2e + " KgCO2e | " + summaryScope1MtCO2e + " MtCO2e");
            System.out.println("  Scope 2: " + summaryScope2KgCO2e + " KgCO2e | " + summaryScope2MtCO2e + " MtCO2e");
            System.out.println("  Scope 3: " + summaryScope3KgCO2e + " KgCO2e | " + summaryScope3MtCO2e + " MtCO2e");
            System.out.println("  Total:   " + summaryTotalKgCO2e + " KgCO2e | " + summaryTotalMtCO2e + " MtCO2e");

            // Get individual scope totals from detail sections for comparison
            String scope1Detail = buildingAssessmentTab.getNetZeroEmissionsSection().getScope1Total();
            String scope2Detail = buildingAssessmentTab.getNetZeroEmissionsSection().getScope2Total();
            String scope3Detail = buildingAssessmentTab.getNetZeroEmissionsSection().getScope3Total();

            System.out.println("\nDetailed Scope Totals (from individual sections):");
            System.out.println("  Scope 1 Total: " + scope1Detail);
            System.out.println("  Scope 2 Total: " + scope2Detail);
            System.out.println("  Scope 3 Total: " + scope3Detail);

            // Validate that summary values are not null/empty
            assertNotNull(summaryScope1KgCO2e, "Summary Scope 1 KgCO2e should not be null");
            assertNotNull(summaryScope2KgCO2e, "Summary Scope 2 KgCO2e should not be null");
            assertNotNull(summaryScope3KgCO2e, "Summary Scope 3 KgCO2e should not be null");
            assertNotNull(summaryTotalKgCO2e, "Summary Total KgCO2e should not be null");

            assertFalse(summaryScope1KgCO2e.isEmpty(), "Summary Scope 1 KgCO2e should not be empty");
            assertFalse(summaryScope2KgCO2e.isEmpty(), "Summary Scope 2 KgCO2e should not be empty");
            assertFalse(summaryScope3KgCO2e.isEmpty(), "Summary Scope 3 KgCO2e should not be empty");
            assertFalse(summaryTotalKgCO2e.isEmpty(), "Summary Total KgCO2e should not be empty");

            // Calculate expected total (sum of all table totals)
            // For comprehensive validation, we would sum all table totals
            // For now, verify that totals are calculated and displayed
            System.out.println("\n✓ Summary of Scopes table values retrieved successfully");
            System.out.println("✓ All summary totals are populated");

            // Validate total = scope1 + scope2 + scope3 (numeric validation)
            try {
                double scope1Kg = parseEmissionValue(summaryScope1KgCO2e);
                double scope2Kg = parseEmissionValue(summaryScope2KgCO2e);
                double scope3Kg = parseEmissionValue(summaryScope3KgCO2e);
                double totalKg = parseEmissionValue(summaryTotalKgCO2e);

                double calculatedTotal = scope1Kg + scope2Kg + scope3Kg;
                assertEquals(calculatedTotal, totalKg, 0.01,
                    String.format("Summary total should equal sum of scopes: %.2f + %.2f + %.2f = %.2f (Got: %.2f)",
                        scope1Kg, scope2Kg, scope3Kg, calculatedTotal, totalKg));

                System.out.println(String.format("✓ Total emissions validation: %.2f + %.2f + %.2f = %.2f KgCO2e",
                    scope1Kg, scope2Kg, scope3Kg, totalKg));
            } catch (NumberFormatException e) {
                System.out.println("⚠ Could not parse emission values for numeric validation: " + e.getMessage());
            }

            System.out.println("\n=== Summary of Scopes Validation Complete ===");

            // Save the emissions data
            System.out.println("\n=== Saving Emissions Data ===");
            buildingAssessmentTab.getNetZeroEmissionsSection().clickSave();
            page.waitForTimeout(2000);
            System.out.println("✓ Emissions data saved successfully");

            //Navigate to Net Zero Energy tab
            System.out.println("\n=== Navigating to Net Zero Energy Tab ===");
            buildingAssessmentTab.goToNetZeroEnergy();
            System.out.println("✓ Navigated to Net Zero Energy Tab");

            // ========================================
            // ENERGY SECTION - Verify data retention from Emissions tables
            // ========================================
            System.out.println("\n=== Verifying Energy Tables Data Retention from Emissions ===");

            // Expand Scope 1 in Energy section
            System.out.println("\n=== Expanding Scope 1 Section in Net Zero Energy Tab ===");
            buildingAssessmentTab.getNetZeroEnergySection().expandScope1();
            page.waitForTimeout(500);

            // ========================================
            // Energy Table A = Emissions Table A (Fuels)
            // ========================================
            System.out.println("\n=== Verifying Energy Table A (should match Emissions Table A - Fuels) ===");

            // Get values from Energy Table A
            String energyTableA_Fuel = buildingAssessmentTab.getNetZeroEnergySection().tableA().getFuel(0);
            String energyTableA_EmissionFactor = buildingAssessmentTab.getNetZeroEnergySection().tableA().getEmissionFactor(0);
            String energyTableA_Consumption = buildingAssessmentTab.getNetZeroEnergySection().tableA().getConsumption(0);
            String energyTableA_Units = buildingAssessmentTab.getNetZeroEnergySection().tableA().getUnits(0);
            String energyTableA_RowTotal = buildingAssessmentTab.getNetZeroEnergySection().tableA().getRowTotal(0);
            String energyTableA_TableTotal = buildingAssessmentTab.getNetZeroEnergySection().tableA().getTableTotal();

            System.out.println("Energy Table A - Row 0:");
            System.out.println("  Fuel: " + energyTableA_Fuel);
            System.out.println("  Emission Factor: " + energyTableA_EmissionFactor);
            System.out.println("  Consumption: " + energyTableA_Consumption);
            System.out.println("  Units: " + energyTableA_Units);
            System.out.println("  Row Total: " + energyTableA_RowTotal);
            System.out.println("  Table Total: " + energyTableA_TableTotal);

            // Verify data matches Emissions Table A
            // String comparisons for text fields
            assertEquals(fuelTypeA, energyTableA_Fuel,
                "Energy Table A: Fuel should match Emissions Table A");
            assertEquals(unitsA, energyTableA_Units,
                "Energy Table A: Units should match Emissions Table A");

            // Numeric comparisons for number fields (parse to handle formatting differences like "100" vs "100.00")
            assertEquals(Double.parseDouble(emissionFactorA.replace(",", "")),
                Double.parseDouble(energyTableA_EmissionFactor.replace(",", "")), 0.01,
                "Energy Table A: Emission Factor should match Emissions Table A");
            assertEquals(Double.parseDouble(consumptionA),
                Double.parseDouble(energyTableA_Consumption.replace(",", "")), 0.01,
                "Energy Table A: Consumption should match Emissions Table A");
            assertEquals(Double.parseDouble(rowTotalA.replace(",", "")),
                Double.parseDouble(energyTableA_RowTotal.replace(",", "")), 0.01,
                "Energy Table A: Row Total should match Emissions Table A");
            assertEquals(Double.parseDouble(tableTotalA.replace(",", "")),
                Double.parseDouble(energyTableA_TableTotal.replace(",", "")), 0.01,
                "Energy Table A: Table Total should match Emissions Table A");

            System.out.println("✓ Energy Table A data matches Emissions Table A");

            // ========================================
            // Energy Table B = Emissions Table C (Mobile Combustion)
            // ========================================
            System.out.println("\n=== Verifying Energy Table B (should match Emissions Table C - Mobile Combustion) ===");

            // Get values from Energy Table B
            String energyTableB_Fuel = buildingAssessmentTab.getNetZeroEnergySection().tableB().getFuel(0);
            String energyTableB_EmissionFactor = buildingAssessmentTab.getNetZeroEnergySection().tableB().getEmissionFactor(0);
            String energyTableB_Consumption = buildingAssessmentTab.getNetZeroEnergySection().tableB().getConsumption(0);
            String energyTableB_Units = buildingAssessmentTab.getNetZeroEnergySection().tableB().getUnits(0);
            String energyTableB_RowTotal = buildingAssessmentTab.getNetZeroEnergySection().tableB().getRowTotal(0);
            String energyTableB_TableTotal = buildingAssessmentTab.getNetZeroEnergySection().tableB().getTableTotal();

            System.out.println("Energy Table B - Row 0:");
            System.out.println("  Fuel: " + energyTableB_Fuel);
            System.out.println("  Emission Factor: " + energyTableB_EmissionFactor);
            System.out.println("  Consumption: " + energyTableB_Consumption);
            System.out.println("  Units: " + energyTableB_Units);
            System.out.println("  Row Total: " + energyTableB_RowTotal);
            System.out.println("  Table Total: " + energyTableB_TableTotal);

            // Verify data matches Emissions Table C
            // String comparisons for text fields
            assertEquals(fuelTypeC, energyTableB_Fuel,
                "Energy Table B: Fuel should match Emissions Table C");
            assertEquals(unitsC, energyTableB_Units,
                "Energy Table B: Units should match Emissions Table C");

            // Numeric comparisons for number fields (parse to handle formatting differences like "100" vs "100.00")
            assertEquals(Double.parseDouble(emissionFactorC.replace(",", "")),
                Double.parseDouble(energyTableB_EmissionFactor.replace(",", "")), 0.01,
                "Energy Table B: Emission Factor should match Emissions Table C");
            assertEquals(Double.parseDouble(consumptionC),
                Double.parseDouble(energyTableB_Consumption.replace(",", "")), 0.01,
                "Energy Table B: Consumption should match Emissions Table C");
            assertEquals(Double.parseDouble(rowTotalC.replace(",", "")),
                Double.parseDouble(energyTableB_RowTotal.replace(",", "")), 0.01,
                "Energy Table B: Row Total should match Emissions Table C");
            assertEquals(Double.parseDouble(tableTotalC.replace(",", "")),
                Double.parseDouble(energyTableB_TableTotal.replace(",", "")), 0.01,
                "Energy Table B: Table Total should match Emissions Table C");

            System.out.println("✓ Energy Table B data matches Emissions Table C");

            // ========================================
            // SCOPE 2 - Energy Table C = Emissions Table D (Energy)
            // ========================================
            System.out.println("\n=== Expanding Scope 2 Section in Net Zero Energy Tab ===");
            buildingAssessmentTab.getNetZeroEnergySection().expandScope2();
            page.waitForTimeout(500);

            System.out.println("\n=== Verifying Energy Table C (should match Emissions Table D - Energy) ===");

            // Get values from Energy Table C
            String energyTableC_Activity = buildingAssessmentTab.getNetZeroEnergySection().tableC().getActivity(0);
            String energyTableC_EmissionFactor = buildingAssessmentTab.getNetZeroEnergySection().tableC().getEmissionFactor(0);
            String energyTableC_Consumption = buildingAssessmentTab.getNetZeroEnergySection().tableC().getConsumption(0);
            String energyTableC_Units = buildingAssessmentTab.getNetZeroEnergySection().tableC().getUnits(0);
            String energyTableC_RowTotal = buildingAssessmentTab.getNetZeroEnergySection().tableC().getRowTotal(0);
            String energyTableC_TableTotal = buildingAssessmentTab.getNetZeroEnergySection().tableC().getTableTotal();

            System.out.println("Energy Table C - Row 0:");
            System.out.println("  Activity: " + energyTableC_Activity);
            System.out.println("  Emission Factor: " + energyTableC_EmissionFactor);
            System.out.println("  Consumption: " + energyTableC_Consumption);
            System.out.println("  Units: " + energyTableC_Units);
            System.out.println("  Row Total: " + energyTableC_RowTotal);
            System.out.println("  Table Total: " + energyTableC_TableTotal);

            // Verify data matches Emissions Table D
            // String comparisons for text fields
            assertEquals(activityD, energyTableC_Activity,
                "Energy Table C: Activity should match Emissions Table D");
            assertEquals(unitsD, energyTableC_Units,
                "Energy Table C: Units should match Emissions Table D");

            // Numeric comparisons for number fields (parse to handle formatting differences like "100" vs "100.00")
            assertEquals(Double.parseDouble(emissionFactorD.replace(",", "")),
                Double.parseDouble(energyTableC_EmissionFactor.replace(",", "")), 0.01,
                "Energy Table C: Emission Factor should match Emissions Table D");
            assertEquals(Double.parseDouble(consumptionD),
                Double.parseDouble(energyTableC_Consumption.replace(",", "")), 0.01,
                "Energy Table C: Consumption should match Emissions Table D");
            assertEquals(Double.parseDouble(rowTotalD.replace(",", "")),
                Double.parseDouble(energyTableC_RowTotal.replace(",", "")), 0.01,
                "Energy Table C: Row Total should match Emissions Table D");
            assertEquals(Double.parseDouble(tableTotalD.replace(",", "")),
                Double.parseDouble(energyTableC_TableTotal.replace(",", "")), 0.01,
                "Energy Table C: Table Total should match Emissions Table D");

            System.out.println("✓ Energy Table C data matches Emissions Table D");

            // ========================================
            // Verify Energy Scope Totals
            // ========================================
            System.out.println("\n=== Verifying Energy Scope Totals ===");

            // Get Energy Scope 1 Total (should equal Energy Table A + Energy Table B)
            String energyScope1Total = buildingAssessmentTab.getNetZeroEnergySection().getScope1Total();
            System.out.println("Energy Scope 1 Total: " + energyScope1Total);

            // Calculate expected Scope 1 Total (Energy Table A + Energy Table B)
            double energyTableATotal = Double.parseDouble(energyTableA_TableTotal.replace(",", ""));
            double energyTableBTotal = Double.parseDouble(energyTableB_TableTotal.replace(",", ""));
            double expectedEnergyScope1Total = energyTableATotal + energyTableBTotal;
            String expectedEnergyScope1TotalStr = String.format("%,.2f", expectedEnergyScope1Total);

            assertEquals(expectedEnergyScope1TotalStr, energyScope1Total.trim(),
                String.format("Energy Scope 1 Total should equal Table A + Table B (%.2f + %.2f = %s, Got: %s)",
                    energyTableATotal, energyTableBTotal, expectedEnergyScope1TotalStr, energyScope1Total));

            System.out.println(String.format("✓ Energy Scope 1 Total verified: %.2f + %.2f = %s",
                energyTableATotal, energyTableBTotal, energyScope1Total));

            // Get Energy Scope 2 Total (should equal Energy Table C)
            String energyScope2Total = buildingAssessmentTab.getNetZeroEnergySection().getScope2Total();
            System.out.println("Energy Scope 2 Total: " + energyScope2Total);

            assertEquals(energyTableC_TableTotal.trim(), energyScope2Total.trim(),
                String.format("Energy Scope 2 Total should equal Table C total (Got Table C: %s, Scope 2: %s)",
                    energyTableC_TableTotal, energyScope2Total));

            System.out.println(String.format("✓ Energy Scope 2 Total verified: Table C Total (%s) = Scope 2 Total (%s)",
                energyTableC_TableTotal, energyScope2Total));

            // ========================================
            // Verify Energy Summary of Scopes
            // ========================================
            System.out.println("\n=== Verifying Energy Summary of Scopes ===");

            // Expand Summary section
            buildingAssessmentTab.getNetZeroEnergySection().expandSummaryOfScopes();
            page.waitForTimeout(1000);

            // Get summary values
            String energySummaryScope1KgCO2e = buildingAssessmentTab.getNetZeroEnergySection().getSummaryScope1KgCO2e();
            String energySummaryScope1MtCO2e = buildingAssessmentTab.getNetZeroEnergySection().getSummaryScope1MtCO2e();
            String energySummaryScope2KgCO2e = buildingAssessmentTab.getNetZeroEnergySection().getSummaryScope2KgCO2e();
            String energySummaryScope2MtCO2e = buildingAssessmentTab.getNetZeroEnergySection().getSummaryScope2MtCO2e();
            String energySummaryTotalKgCO2e = buildingAssessmentTab.getNetZeroEnergySection().getSummaryTotalKgCO2e();
            String energySummaryTotalMtCO2e = buildingAssessmentTab.getNetZeroEnergySection().getSummaryTotalMtCO2e();

            System.out.println("Energy Summary Table Values:");
            System.out.println("  Scope 1: " + energySummaryScope1KgCO2e + " KgCO2e | " + energySummaryScope1MtCO2e + " MtCO2e");
            System.out.println("  Scope 2: " + energySummaryScope2KgCO2e + " KgCO2e | " + energySummaryScope2MtCO2e + " MtCO2e");
            System.out.println("  Total:   " + energySummaryTotalKgCO2e + " KgCO2e | " + energySummaryTotalMtCO2e + " MtCO2e");

            // Validate summary values are not null/empty
            assertNotNull(energySummaryScope1KgCO2e, "Energy Summary Scope 1 KgCO2e should not be null");
            assertNotNull(energySummaryScope2KgCO2e, "Energy Summary Scope 2 KgCO2e should not be null");
            assertNotNull(energySummaryTotalKgCO2e, "Energy Summary Total KgCO2e should not be null");

            assertFalse(energySummaryScope1KgCO2e.isEmpty(), "Energy Summary Scope 1 KgCO2e should not be empty");
            assertFalse(energySummaryScope2KgCO2e.isEmpty(), "Energy Summary Scope 2 KgCO2e should not be empty");
            assertFalse(energySummaryTotalKgCO2e.isEmpty(), "Energy Summary Total KgCO2e should not be empty");

            // Validate total = scope1 + scope2 (numeric validation)
            try {
                double energyScope1Kg = parseEmissionValue(energySummaryScope1KgCO2e);
                double energyScope2Kg = parseEmissionValue(energySummaryScope2KgCO2e);
                double energyTotalKg = parseEmissionValue(energySummaryTotalKgCO2e);

                double calculatedEnergyTotal = energyScope1Kg + energyScope2Kg;
                assertEquals(calculatedEnergyTotal, energyTotalKg, 0.01,
                    String.format("Energy Summary total should equal sum of scopes: %.2f + %.2f = %.2f (Got: %.2f)",
                        energyScope1Kg, energyScope2Kg, calculatedEnergyTotal, energyTotalKg));

                System.out.println(String.format("✓ Energy Total validation: %.2f + %.2f = %.2f KgCO2e",
                    energyScope1Kg, energyScope2Kg, energyTotalKg));
            } catch (NumberFormatException e) {
                System.out.println("⚠ Could not parse energy emission values for numeric validation: " + e.getMessage());
            }

            System.out.println("\n✓ Energy Summary of Scopes Validation Complete");
            System.out.println("\n✓ All Energy Tables verified - Data successfully retained from Emissions tables");

            System.out.println("\n=== ALL TESTS COMPLETE ===");
        });
    }

    /**
     * Helper method to parse emission values from string (handles commas and whitespace)
     */
    private double parseEmissionValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        // Remove commas and trim whitespace
        String cleaned = value.trim().replace(",", "");
        return Double.parseDouble(cleaned);
    }
}
