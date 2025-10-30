package pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.AutoStep;

import java.util.regex.Pattern;

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
        this.pageHeader = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(Pattern.compile("Net Zero certification", Pattern.CASE_INSENSITIVE)));

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
    @AutoStep
    public boolean isPageDisplayed() {
        page.waitForLoadState();
        pageHeader.waitFor();
        return pageHeader.isVisible();
    }

    @AutoStep
    public String getPageHeaderText() {
        return pageHeader.textContent();
    }

    /**
     * Building Project Type
     */
    @AutoStep
    public void selectBuilding() {
        page.waitForLoadState();
        buildingOption.click();
    }

    @AutoStep
    public boolean isBuildingOptionVisible() {
        return buildingOption.isVisible();
    }

    @AutoStep
    public String getBuildingOptionText() {
        return buildingOption.textContent();
    }

    /**
     * Portfolio Project Type
     */
    @AutoStep
    public void selectPortfolio() {
        page.waitForLoadState();
        portfolioOption.click();
    }

    @AutoStep
    public boolean isPortfolioOptionVisible() {
        return portfolioOption.isVisible();
    }

    @AutoStep
    public String getPortfolioOptionText() {
        return portfolioOption.textContent();
    }

    /**
     * Home Project Type
     */
    @AutoStep
    public void selectHome() {
        page.waitForLoadState();
        homeOption.click();
    }

    @AutoStep
    public boolean isHomeOptionVisible() {
        return homeOption.isVisible();
    }

    @AutoStep
    public String getHomeOptionText() {
        return homeOption.textContent();
    }

    /**
     * Community Center Project Type
     */
    @AutoStep
    public void selectCommunityCenter() {
        page.waitForLoadState();
        communityCenterOption.click();
    }

    @AutoStep
    public boolean isCommunityCenterOptionVisible() {
        return communityCenterOption.isVisible();
    }

    @AutoStep
    public String getCommunityCenterOptionText() {
        return communityCenterOption.textContent();
    }

    /**
     * Campus Project Type
     */
    @AutoStep
    public void selectCampus() {
        page.waitForLoadState();
        campusOption.click();
    }

    @AutoStep
    public boolean isCampusOptionVisible() {
        return campusOption.isVisible();
    }

    @AutoStep
    public String getCampusOptionText() {
        return campusOption.textContent();
    }

    /**
     * Warehouse Project Type
     */
    @AutoStep
    public void selectWarehouse() {
        page.waitForLoadState();
        warehouseOption.click();
    }

    @AutoStep
    public boolean isWarehouseOptionVisible() {
        return warehouseOption.isVisible();
    }

    @AutoStep
    public String getWarehouseOptionText() {
        return warehouseOption.textContent();
    }

    /**
     * Community Project Type
     */
    @AutoStep
    public void selectCommunity() {
        page.waitForLoadState();
        communityOption.click();
    }

    @AutoStep
    public boolean isCommunityOptionVisible() {
        return communityOption.isVisible();
    }

    @AutoStep
    public String getCommunityOptionText() {
        return communityOption.textContent();
    }

    /**
     * City Project Type
     */
    @AutoStep
    public void selectCity() {
        page.waitForLoadState();
        cityOption.click();
    }

    @AutoStep
    public boolean isCityOptionVisible() {
        return cityOption.isVisible();
    }

    @AutoStep
    public String getCityOptionText() {
        return cityOption.textContent();
    }

    /**
     * Business Project Type
     */
    @AutoStep
    public void selectBusiness() {
        page.waitForLoadState();
        businessOption.click();
    }

    @AutoStep
    public boolean isBusinessOptionVisible() {
        return businessOption.isVisible();
    }

    @AutoStep
    public String getBusinessOptionText() {
        return businessOption.textContent();
    }

    /**
     * Product Project Type
     */
    @AutoStep
    public void selectProduct() {
        page.waitForLoadState();
        productOption.click();
    }

    @AutoStep
    public boolean isProductOptionVisible() {
        return productOption.isVisible();
    }

    @AutoStep
    public String getProductOptionText() {
        return productOption.textContent();
    }

    /**
     * Process Project Type
     */
    @AutoStep
    public void selectProcess() {
        page.waitForLoadState();
        processOption.click();
    }

    @AutoStep
    public boolean isProcessOptionVisible() {
        return processOption.isVisible();
    }

    @AutoStep
    public String getProcessOptionText() {
        return processOption.textContent();
    }

    /**
     * Fleet Project Type
     */
    @AutoStep
    public void selectFleet() {
        page.waitForLoadState();
        fleetOption.click();
    }

    @AutoStep
    public boolean isFleetOptionVisible() {
        return fleetOption.isVisible();
    }

    @AutoStep
    public String getFleetOptionText() {
        return fleetOption.textContent();
    }

    /**
     * Supply Chain Project Type
     */
    @AutoStep
    public void selectSupplyChain() {
        page.waitForLoadState();
        supplyChainOption.click();
    }

    @AutoStep
    public boolean isSupplyChainOptionVisible() {
        return supplyChainOption.isVisible();
    }

    @AutoStep
    public String getSupplyChainOptionText() {
        return supplyChainOption.textContent();
    }

    /**
     * Add New Option
     */
    @AutoStep
    public void selectAddNew() {
        page.waitForLoadState();
        addNewOption.click();
    }

    @AutoStep
    public boolean isAddNewOptionVisible() {
        return addNewOption.isVisible();
    }

    @AutoStep
    public String getAddNewOptionText() {
        return addNewOption.textContent();
    }

    /**
     * General Methods
     */
    @AutoStep
    public boolean isProjectOptionsVisible() {
        page.waitForLoadState();
        return page.locator("#project-categories").isVisible();
    }

    @AutoStep
    public int getVisibleOptionsCount() {
        page.waitForLoadState();
        return page.locator("#project-categories > div").count();
    }
}
