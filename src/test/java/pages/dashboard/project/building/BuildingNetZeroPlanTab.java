package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BuildingNetZeroPlanTab - Net Zero Plan tab for Building project
 * This tab allows users to upload a plan of their emission reduction strategies
 */
public class BuildingNetZeroPlanTab {
    private final Page page;

    // Main content
    private final Locator tabContent;
    private final Locator planTargetsSection;

    // Instructions and links
    private final Locator instructionText;
    private final Locator infoIcon;
    private final Locator tooltipText;
    private final Locator templateLink;
    private final Locator viewUploadLink;

    // Actions
    private final Locator saveButton;

    public BuildingNetZeroPlanTab(Page page) {
        this.page = page;

        // Initialize locators
        this.tabContent = page.locator(".tab4.page-section.tab-container");
        this.planTargetsSection = page.locator("#planTargets");

        // Instructions and links
        this.instructionText = page.locator("span.text-dark.label-text.font-weight-600");
        this.infoIcon = page.locator("span.tooltipp i.bi-info-circle");
        this.tooltipText = page.locator("span.tooltiptext.top-view");
        this.templateLink = page.locator("a[href*='acrobat.adobe.com']");
        this.viewUploadLink = page.locator("a.text-dark-primary.text-underline:has-text('View/Upload')");

        // Actions
        this.saveButton = page.locator("button#gnfz-save");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return tabContent.isVisible();
    }

    public boolean isPlanTargetsSectionVisible() {
        page.waitForLoadState();
        return planTargetsSection.isVisible();
    }

    /**
     * Get instruction text
     */
    public String getInstructionText() {
        page.waitForLoadState();
        return instructionText.textContent().trim();
    }

    /**
     * Check if info icon is visible
     */
    public boolean isInfoIconVisible() {
        page.waitForLoadState();
        return infoIcon.isVisible();
    }

    /**
     * Hover over info icon to show tooltip
     */
    public void hoverOverInfoIcon() {
        page.waitForLoadState();
        infoIcon.hover();
        page.waitForTimeout(300); // Wait for tooltip to appear
    }

    /**
     * Get tooltip text content
     */
    public String getTooltipText() {
        page.waitForLoadState();
        hoverOverInfoIcon();
        return tooltipText.textContent().trim();
    }

    /**
     * Check if template link is visible
     */
    public boolean isTemplateLinkVisible() {
        page.waitForLoadState();
        hoverOverInfoIcon();
        return templateLink.isVisible();
    }

    /**
     * Click on the template link to view GNFZ's net zero plan template
     * Note: This will open in a new tab
     */
    public void clickTemplateLink() {
        page.waitForLoadState();
        hoverOverInfoIcon();
        templateLink.click();
    }

    /**
     * Get template link URL
     */
    public String getTemplateLinkUrl() {
        page.waitForLoadState();
        hoverOverInfoIcon();
        return templateLink.getAttribute("href");
    }

    /**
     * Check if View/Upload link is visible
     */
    public boolean isViewUploadLinkVisible() {
        page.waitForLoadState();
        return viewUploadLink.isVisible();
    }

    /**
     * Click View/Upload link to open file upload dialog or view existing plan
     */
    public void clickViewUploadLink() {
        page.waitForLoadState();
        viewUploadLink.click();
        page.waitForTimeout(500);
    }

    /**
     * Check if save button is visible
     */
    public boolean isSaveButtonVisible() {
        page.waitForLoadState();
        return saveButton.isVisible();
    }

    /**
     * Check if save button is enabled
     */
    public boolean isSaveButtonEnabled() {
        page.waitForLoadState();
        return saveButton.isEnabled();
    }

    /**
     * Click save button
     */
    public void clickSave() {
        page.waitForLoadState();
        saveButton.scrollIntoViewIfNeeded();
        saveButton.click();
        page.waitForTimeout(1000);
    }

    /**
     * Get save button text
     */
    public String getSaveButtonText() {
        page.waitForLoadState();
        return saveButton.textContent().trim();
    }
}
