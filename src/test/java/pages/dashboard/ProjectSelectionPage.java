package pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * ProjectSelectionPage - Page object for selecting project type
 *
 * Project types available:
 * - Building
 * - Portfolio
 * - Home
 * - Community Center
 * - Campus
 * - Warehouse
 * - Community
 * - City
 * - Business
 * - Product
 * - Process
 * - Fleet
 * - Supply Chain
 * - Add new
 */
public class ProjectSelectionPage {
    private final Page page;

    // Page elements
    private final Locator pageHeader;

    // Project type locators
    private final Locator buildingOption;
    private final Locator portfolioOption;
    private final Locator homeOption;
    private final Locator communityCenterOption;
    private final Locator campusOption;
    private final Locator warehouseOption;
    private final Locator communityOption;
    private final Locator cityOption;
    private final Locator businessOption;
    private final Locator productOption;
    private final Locator processOption;
    private final Locator fleetOption;
    private final Locator supplyChainOption;
    private final Locator addNewOption;

    public ProjectSelectionPage(Page page) {
        this.page = page;

        // Initialize page locators
        this.pageHeader = page.locator("h3");

        // Initialize project type locators
        this.buildingOption = page.locator("//*[@id=\"project-categories\"]/div[1]/div/div");
        this.portfolioOption = page.locator("//*[@id=\"project-categories\"]/div[2]/div/div");
        this.homeOption = page.locator("//*[@id=\"project-categories\"]/div[3]/div/div");
        this.communityCenterOption = page.locator("//*[@id=\"project-categories\"]/div[4]/div/div");
        this.campusOption = page.locator("//*[@id=\"project-categories\"]/div[5]/div/div");
        this.warehouseOption = page.locator("//*[@id=\"project-categories\"]/div[6]/div/div");
        this.communityOption = page.locator("//*[@id=\"project-categories\"]/div[7]/div/div");
        this.cityOption = page.locator("//*[@id=\"project-categories\"]/div[8]/div/div");
        this.businessOption = page.locator("//*[@id=\"project-categories\"]/div[9]/div/div");
        this.productOption = page.locator("//*[@id=\"project-categories\"]/div[10]/div/div");
        this.processOption = page.locator("//*[@id=\"project-categories\"]/div[11]/div/div");
        this.fleetOption = page.locator("//*[@id=\"project-categories\"]/div[12]/div/div");
        this.supplyChainOption = page.locator("//*[@id=\"project-categories\"]/div[13]/div/div");
        this.addNewOption = page.locator("//*[@id=\"project-categories\"]/div[14]/div/div");
    }

    /**
     * Page Visibility
     */
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        pageHeader.waitFor();
        return pageHeader.isVisible();
    }

    public String getPageHeaderText() {
        return pageHeader.textContent();
    }

    /**
     * Building Project Type
     */
    public void selectBuilding() {
        page.waitForLoadState();
        buildingOption.click();
    }

    public boolean isBuildingOptionVisible() {
        return buildingOption.isVisible();
    }

    public String getBuildingOptionText() {
        return buildingOption.textContent();
    }

    /**
     * Portfolio Project Type
     */
    public void selectPortfolio() {
        page.waitForLoadState();
        portfolioOption.click();
    }

    public boolean isPortfolioOptionVisible() {
        return portfolioOption.isVisible();
    }

    public String getPortfolioOptionText() {
        return portfolioOption.textContent();
    }

    /**
     * Home Project Type
     */
    public void selectHome() {
        page.waitForLoadState();
        homeOption.click();
    }

    public boolean isHomeOptionVisible() {
        return homeOption.isVisible();
    }

    public String getHomeOptionText() {
        return homeOption.textContent();
    }

    /**
     * Community Center Project Type
     */
    public void selectCommunityCenter() {
        page.waitForLoadState();
        communityCenterOption.click();
    }

    public boolean isCommunityCenterOptionVisible() {
        return communityCenterOption.isVisible();
    }

    public String getCommunityCenterOptionText() {
        return communityCenterOption.textContent();
    }

    /**
     * Campus Project Type
     */
    public void selectCampus() {
        page.waitForLoadState();
        campusOption.click();
    }

    public boolean isCampusOptionVisible() {
        return campusOption.isVisible();
    }

    public String getCampusOptionText() {
        return campusOption.textContent();
    }

    /**
     * Warehouse Project Type
     */
    public void selectWarehouse() {
        page.waitForLoadState();
        warehouseOption.click();
    }

    public boolean isWarehouseOptionVisible() {
        return warehouseOption.isVisible();
    }

    public String getWarehouseOptionText() {
        return warehouseOption.textContent();
    }

    /**
     * Community Project Type
     */
    public void selectCommunity() {
        page.waitForLoadState();
        communityOption.click();
    }

    public boolean isCommunityOptionVisible() {
        return communityOption.isVisible();
    }

    public String getCommunityOptionText() {
        return communityOption.textContent();
    }

    /**
     * City Project Type
     */
    public void selectCity() {
        page.waitForLoadState();
        cityOption.click();
    }

    public boolean isCityOptionVisible() {
        return cityOption.isVisible();
    }

    public String getCityOptionText() {
        return cityOption.textContent();
    }

    /**
     * Business Project Type
     */
    public void selectBusiness() {
        page.waitForLoadState();
        businessOption.click();
    }

    public boolean isBusinessOptionVisible() {
        return businessOption.isVisible();
    }

    public String getBusinessOptionText() {
        return businessOption.textContent();
    }

    /**
     * Product Project Type
     */
    public void selectProduct() {
        page.waitForLoadState();
        productOption.click();
    }

    public boolean isProductOptionVisible() {
        return productOption.isVisible();
    }

    public String getProductOptionText() {
        return productOption.textContent();
    }

    /**
     * Process Project Type
     */
    public void selectProcess() {
        page.waitForLoadState();
        processOption.click();
    }

    public boolean isProcessOptionVisible() {
        return processOption.isVisible();
    }

    public String getProcessOptionText() {
        return processOption.textContent();
    }

    /**
     * Fleet Project Type
     */
    public void selectFleet() {
        page.waitForLoadState();
        fleetOption.click();
    }

    public boolean isFleetOptionVisible() {
        return fleetOption.isVisible();
    }

    public String getFleetOptionText() {
        return fleetOption.textContent();
    }

    /**
     * Supply Chain Project Type
     */
    public void selectSupplyChain() {
        page.waitForLoadState();
        supplyChainOption.click();
    }

    public boolean isSupplyChainOptionVisible() {
        return supplyChainOption.isVisible();
    }

    public String getSupplyChainOptionText() {
        return supplyChainOption.textContent();
    }

    /**
     * Add New Option
     */
    public void selectAddNew() {
        page.waitForLoadState();
        addNewOption.click();
    }

    public boolean isAddNewOptionVisible() {
        return addNewOption.isVisible();
    }

    public String getAddNewOptionText() {
        return addNewOption.textContent();
    }
}
