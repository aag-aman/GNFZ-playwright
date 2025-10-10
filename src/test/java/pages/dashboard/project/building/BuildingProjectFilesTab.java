package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingProjectFilesTab - Project Files tab for Building project
 * This tab organizes files into categories:
 * - Building Info
 * - Inventory of Emissions
 * - Net Zero Plan
 * - Carbon Offset
 * - Net Zero Milestone
 * - Certificates
 * - Reports Generated
 */
public class BuildingProjectFilesTab {
    private final Page page;

    // Main content
    private final Locator tabContent;
    private final Locator filePanel;

    // Left sidebar - Category filters
    private final Locator categoryMenuList;
    private final Locator buildingInfoCategory;
    private final Locator inventoryOfEmissionsCategory;
    private final Locator netZeroPlanCategory;
    private final Locator carbonOffsetCategory;
    private final Locator netZeroMilestoneCategory;
    private final Locator certificatesCategory;
    private final Locator reportsGeneratedCategory;

    // Breadcrumbs navigation
    private final Locator breadcrumbsContainer;
    private final Locator filesRootBreadcrumb;
    private final Locator activeFolderBreadcrumb;

    // File list section
    private final Locator folderContainerTable;
    private final Locator selectAllCheckbox;
    private final Locator activeFolderNameText;
    private final Locator addFilesButton;
    private final Locator fileListBody;
    private final Locator noRecordsMessage;

    public BuildingProjectFilesTab(Page page) {
        this.page = page;

        // Initialize locators
        this.tabContent = page.locator(".tab8.page-section.tab-container");
        this.filePanel = page.locator("#file-panel");

        // Category filters on left sidebar
        this.categoryMenuList = page.locator("#menu-upload-list ul");
        this.buildingInfoCategory = page.locator("#gnfz-Building\\ Info");
        this.inventoryOfEmissionsCategory = page.locator("#gnfz-Inventory\\ of\\ Emissions");
        this.netZeroPlanCategory = page.locator("#gnfz-Net\\ Zero\\ Plan");
        this.carbonOffsetCategory = page.locator("#gnfz-Carbon\\ Offset");
        this.netZeroMilestoneCategory = page.locator("#gnfz-Net\\ Zero\\ Milestone");
        this.certificatesCategory = page.locator("#gnfz-Certificates");
        this.reportsGeneratedCategory = page.locator("#gnfz-Reports\\ Generated");

        // Breadcrumbs
        this.breadcrumbsContainer = page.locator("div.breadcrumbs");
        this.filesRootBreadcrumb = page.locator("div.breadcrumbs span:has-text('Files')").first();
        this.activeFolderBreadcrumb = page.locator("span.active-folder-name.folderName");

        // File list section
        this.folderContainerTable = page.locator("table.table");
        this.selectAllCheckbox = page.locator("#file-select-all");
        this.activeFolderNameText = page.locator("span.active-folder-name");
        this.addFilesButton = page.locator("#gnfz-files-add-more");
        this.fileListBody = page.locator("table.table tbody");
        this.noRecordsMessage = page.locator("div:has-text('No records found.')");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return tabContent.isVisible();
    }

    public boolean isFilePanelVisible() {
        page.waitForLoadState();
        return filePanel.isVisible();
    }

    public boolean isFileListVisible() {
        page.waitForLoadState();
        return folderContainerTable.isVisible();
    }

    /**
     * Category navigation - Click on categories
     */
    public void clickBuildingInfoCategory() {
        page.waitForLoadState();
        buildingInfoCategory.click();
        page.waitForTimeout(500);
    }

    public void clickInventoryOfEmissionsCategory() {
        page.waitForLoadState();
        inventoryOfEmissionsCategory.click();
        page.waitForTimeout(500);
    }

    public void clickNetZeroPlanCategory() {
        page.waitForLoadState();
        netZeroPlanCategory.click();
        page.waitForTimeout(500);
    }

    public void clickCarbonOffsetCategory() {
        page.waitForLoadState();
        carbonOffsetCategory.click();
        page.waitForTimeout(500);
    }

    public void clickNetZeroMilestoneCategory() {
        page.waitForLoadState();
        netZeroMilestoneCategory.click();
        page.waitForTimeout(500);
    }

    public void clickCertificatesCategory() {
        page.waitForLoadState();
        certificatesCategory.click();
        page.waitForTimeout(500);
    }

    public void clickReportsGeneratedCategory() {
        page.waitForLoadState();
        reportsGeneratedCategory.click();
        page.waitForTimeout(500);
    }

    /**
     * Check which category is active
     */
    public boolean isBuildingInfoCategoryActive() {
        page.waitForLoadState();
        return buildingInfoCategory.getAttribute("class").contains("bg-readonly");
    }

    public boolean isInventoryOfEmissionsCategoryActive() {
        page.waitForLoadState();
        return inventoryOfEmissionsCategory.getAttribute("class").contains("bg-readonly");
    }

    public boolean isNetZeroPlanCategoryActive() {
        page.waitForLoadState();
        return netZeroPlanCategory.getAttribute("class").contains("bg-readonly");
    }

    public boolean isCarbonOffsetCategoryActive() {
        page.waitForLoadState();
        return carbonOffsetCategory.getAttribute("class").contains("bg-readonly");
    }

    public boolean isNetZeroMilestoneCategoryActive() {
        page.waitForLoadState();
        return netZeroMilestoneCategory.getAttribute("class").contains("bg-readonly");
    }

    public boolean isCertificatesCategoryActive() {
        page.waitForLoadState();
        return certificatesCategory.getAttribute("class").contains("bg-readonly");
    }

    public boolean isReportsGeneratedCategoryActive() {
        page.waitForLoadState();
        return reportsGeneratedCategory.getAttribute("class").contains("bg-readonly");
    }

    /**
     * Breadcrumbs navigation
     */
    public boolean isBreadcrumbsVisible() {
        page.waitForLoadState();
        return breadcrumbsContainer.isVisible();
    }

    public String getActiveFolderName() {
        page.waitForLoadState();
        return activeFolderBreadcrumb.last().textContent().trim();
    }

    public void clickFilesRootBreadcrumb() {
        page.waitForLoadState();
        filesRootBreadcrumb.click();
        page.waitForTimeout(500);
    }

    /**
     * File operations
     */
    public boolean isAddFilesButtonVisible() {
        page.waitForLoadState();
        return addFilesButton.isVisible();
    }

    public void clickAddFilesButton() {
        page.waitForLoadState();
        addFilesButton.click();
        page.waitForTimeout(500);
    }

    public boolean isSelectAllCheckboxVisible() {
        page.waitForLoadState();
        return selectAllCheckbox.isVisible();
    }

    public void clickSelectAllCheckbox() {
        page.waitForLoadState();
        selectAllCheckbox.click();
        page.waitForTimeout(300);
    }

    /**
     * File list checks
     */
    public boolean isNoRecordsMessageVisible() {
        page.waitForLoadState();
        return noRecordsMessage.isVisible();
    }

    public String getNoRecordsMessage() {
        page.waitForLoadState();
        return noRecordsMessage.textContent().trim();
    }

    public boolean hasFiles() {
        page.waitForLoadState();
        return !isNoRecordsMessageVisible();
    }

    public int getFileCount() {
        page.waitForLoadState();
        if (isNoRecordsMessageVisible()) {
            return 0;
        }
        // Count file rows (excluding the header and "no records" row)
        return fileListBody.locator("tr").count();
    }

    /**
     * Get active folder name from the Files label
     */
    public String getActiveFolder() {
        page.waitForLoadState();
        return activeFolderNameText.last().textContent().trim();
    }

    /**
     * Upload file (when file input is available)
     * Note: The actual file input selector may vary based on implementation
     */
    public void uploadFile(String filePath) {
        page.waitForLoadState();
        // This assumes a file input exists after clicking add files
        // The actual selector may need to be adjusted based on the upload dialog
        Locator fileInput = page.locator("input[type='file']");
        if (fileInput.isVisible()) {
            fileInput.setInputFiles(java.nio.file.Paths.get(filePath));
            page.waitForTimeout(1000);
        }
    }

    /**
     * Check if a specific category is visible
     */
    public boolean isCategoryVisible(String categoryName) {
        page.waitForLoadState();
        Locator category = page.locator(String.format("#gnfz-%s", categoryName));
        return category.isVisible();
    }

    /**
     * Click a category by name
     */
    public void clickCategory(String categoryName) {
        page.waitForLoadState();
        Locator category = page.locator(String.format("#gnfz-%s", categoryName));
        category.click();
        page.waitForTimeout(500);
    }

    /**
     * Get all category names
     */
    public int getCategoryCount() {
        page.waitForLoadState();
        return categoryMenuList.locator("li").count();
    }
}
