package pages.dashboard.project.building;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

/**
 * BuildingOverviewTab - Overview tab for Building project
 * Displays the certification process with 4 main stages:
 * 1. Provide basic info (Building info, Team info)
 * 2. Complete assessment
 * 3. Validate and certify (Net Zero plans, Carbon offsets, Net Zero milestones)
 * 4. Achieve net zero certification
 */
public class BuildingOverviewTab {
    private final Page page;

    // Main content
    private final Locator tabContent;
    private final Locator processStatusContainer;
    private final Locator certificationProcessHeader;
    private final Locator activityLogButton;
    private final Locator certificationTable;

    // Stage 1: Provide basic info
    private final Locator buildingInfoLink;
    private final Locator buildingInfoSelect;
    private final Locator teamInfoLink;
    private final Locator teamInfoSelect;

    // Stage 2: Complete assessment
    private final Locator completeAssessmentLink;
    private final Locator completeAssessmentSelect;
    private final Locator assessmentTooltipIcon;
    private final Locator assessmentTooltipText;
    private final Locator assessmentChatIcon;

    // Stage 3: Validate and certify
    @SuppressWarnings("unused")
    private final Locator validateAndCertifyHeader;
    private final Locator netZeroPlanLink;
    private final Locator netZeroPlanSelect;
    private final Locator netZeroPlanChatIcon;
    private final Locator carbonOffsetLink;
    private final Locator carbonOffsetInput;
    private final Locator carbonOffsetNavigateIcon;
    private final Locator carbonOffsetChatIcon;
    private final Locator netZeroMilestoneLink;
    private final Locator netZeroMilestoneInput;
    private final Locator netZeroMilestoneNavigateIcon;
    private final Locator netZeroMilestoneChatIcon;

    // Stage 4: Achieve net zero certification
    private final Locator netZeroCertificationHeader;
    private final Locator netZeroCertificationStatus;

    // Platform demo link
    private final Locator platformDemoLink;

    // Modal dialogs
    private final Locator modalDialog;
    private final Locator modalCloseButton;

    // Validation error modal (missing required fields)
    private final Locator validationErrorTitle;
    private final Locator validationErrorList;
    private final Locator validationErrorCloseButton;

    // Status change confirmation modal
    private final Locator statusChangeConfirmationTitle;
    private final Locator statusChangeNoButton;
    private final Locator statusChangeYesButton;

    public BuildingOverviewTab(Page page) {
        this.page = page;

        // Main content
        this.tabContent = page.locator(".tab1.page-section.tab-container");
        this.processStatusContainer = page.locator("#processStatus");
        this.certificationProcessHeader = page.locator("#processStatus b:has-text('Certification Process')");
        this.activityLogButton = page.locator("button:has-text('Activity log')");
        this.certificationTable = page.locator("table.table");

        // Stage 1: Provide basic info
        this.buildingInfoLink = page.locator("td.pointer.text-underline-hover:has-text('Building information')");
        this.buildingInfoSelect = page.locator("#gnfz-building-info");
        this.teamInfoLink = page.locator("td.pointer.text-underline-hover:has-text('Team information')");
        this.teamInfoSelect = page.locator("#gnfz-team-info");

        // Stage 2: Complete assessment
        this.completeAssessmentLink = page.locator("td.pointer.text-underline-hover:has-text('Complete assessment')");
        this.completeAssessmentSelect = page.locator("#gnfz-complete-assessment");
        this.assessmentTooltipIcon = page.locator("td:has-text('Complete assessment') span.tooltipp i.bi-info-circle");
        this.assessmentTooltipText = page.locator("span.tooltiptext:has-text('Assessment review of emission sources')");
        this.assessmentChatIcon = page.locator("tr:has(#gnfz-complete-assessment) img.chat-image-pos");

        // Stage 3: Validate and certify
        this.validateAndCertifyHeader = page.locator("td:has-text('Validate and certify')");
        this.netZeroPlanLink = page.locator("td.pointer.text-underline-hover:has-text('Net Zero plans')");
        this.netZeroPlanSelect = page.locator("#gnfz-nzp");
        this.netZeroPlanChatIcon = page.locator("tr:has(#gnfz-nzp) img.chat-image-pos");
        this.carbonOffsetLink = page.locator("td.pointer.text-underline-hover:has-text('Carbon offsets')");
        this.carbonOffsetInput = page.locator("tr:has-text('Carbon offsets') input[type='text'][readonly]");
        this.carbonOffsetNavigateIcon = page.locator("tr:has-text('Carbon offsets') i.bi-three-dots");
        this.carbonOffsetChatIcon = page.locator("tr:has-text('Carbon offsets') img.chat-image-pos");
        this.netZeroMilestoneLink = page.locator("td.pointer.text-underline-hover:has-text('Net Zero milestone targets')");
        this.netZeroMilestoneInput = page.locator("tr:has-text('Net Zero milestone targets') input[type='text'][readonly]");
        this.netZeroMilestoneNavigateIcon = page.locator("tr:has-text('Net Zero milestone targets') i.bi-three-dots");
        this.netZeroMilestoneChatIcon = page.locator("tr:has-text('Net Zero milestone targets') img.chat-image-pos");

        // Stage 4: Achieve net zero certification
        this.netZeroCertificationHeader = page.locator("td:has-text('Achieve net zero certification')");
        this.netZeroCertificationStatus = page.locator("tr:has-text('Achieve net zero certification') td.ps-2.text-size-16px");

        // Platform demo link
        this.platformDemoLink = page.locator("a[href='/resources/playback']");

        // Modal dialogs
        this.modalDialog = page.locator("#ng-modal-generic");
        this.modalCloseButton = page.locator("#modal-generic-close");

        // Validation error modal (missing required fields)
        this.validationErrorTitle = page.locator("p.text-center.text-size-22px:has-text('Please provide values in the following fields before submission')");
        this.validationErrorList = page.locator("div.d-flex.justify-content-center ul");
        this.validationErrorCloseButton = page.locator("button.btn-outline-gnfz:has-text('CLOSE')");

        // Status change confirmation modal
        this.statusChangeConfirmationTitle = page.locator("p.text-center:has-text('Do you want to change status?')");
        this.statusChangeNoButton = page.locator("#simple-process-status-cancel-change-event-popup");
        this.statusChangeYesButton = page.locator("#simple-process-status-submit-change-event-popup");
    }

    /**
     * Tab visibility
     */
    public boolean isTabDisplayed() {
        page.waitForLoadState();
        return tabContent.isVisible();
    }

    public boolean isProcessStatusContainerVisible() {
        page.waitForLoadState();
        return processStatusContainer.isVisible();
    }

    public boolean isCertificationTableVisible() {
        page.waitForLoadState();
        return certificationTable.isVisible();
    }

    /**
     * Header elements
     */
    public String getCertificationProcessHeader() {
        page.waitForLoadState();
        return certificationProcessHeader.textContent().trim();
    }

    public boolean isActivityLogButtonVisible() {
        page.waitForLoadState();
        return activityLogButton.isVisible();
    }

    public void clickActivityLogButton() {
        page.waitForLoadState();
        activityLogButton.click();
        page.waitForTimeout(500);
    }

    /**
     * Stage 1: Provide basic info - Building Information
     */
    public void clickBuildingInfoLink() {
        page.waitForLoadState();
        buildingInfoLink.click();
        page.waitForTimeout(500);
    }

    public void selectBuildingInfoStatus(String status) {
        page.waitForLoadState();
        buildingInfoSelect.selectOption(new SelectOption().setLabel(status));
        page.waitForTimeout(300);
    }

    public String getBuildingInfoStatus() {
        page.waitForLoadState();
        return buildingInfoSelect.inputValue();
    }

    /**
     * Stage 1: Provide basic info - Team Information
     */
    public void clickTeamInfoLink() {
        page.waitForLoadState();
        teamInfoLink.click();
        page.waitForTimeout(500);
    }

    public void selectTeamInfoStatus(String status) {
        page.waitForLoadState();
        teamInfoSelect.selectOption(new SelectOption().setLabel(status));
        page.waitForTimeout(300);
    }

    public String getTeamInfoStatus() {
        page.waitForLoadState();
        return teamInfoSelect.inputValue();
    }

    /**
     * Stage 2: Complete assessment
     */
    public void clickCompleteAssessmentLink() {
        page.waitForLoadState();
        completeAssessmentLink.click();
        page.waitForTimeout(500);
    }

    public void selectCompleteAssessmentStatus(String status) {
        page.waitForLoadState();
        completeAssessmentSelect.selectOption(new SelectOption().setLabel(status));
        page.waitForTimeout(300);
    }

    public String getCompleteAssessmentStatus() {
        page.waitForLoadState();
        return completeAssessmentSelect.inputValue();
    }

    public void hoverOverAssessmentTooltip() {
        page.waitForLoadState();
        assessmentTooltipIcon.hover();
        page.waitForTimeout(300);
    }

    public String getAssessmentTooltipText() {
        page.waitForLoadState();
        hoverOverAssessmentTooltip();
        return assessmentTooltipText.textContent().trim();
    }

    public void clickAssessmentChatIcon() {
        page.waitForLoadState();
        assessmentChatIcon.click();
        page.waitForTimeout(500);
    }

    /**
     * Stage 3: Validate and certify - Net Zero Plans
     */
    public void clickNetZeroPlanLink() {
        page.waitForLoadState();
        netZeroPlanLink.click();
        page.waitForTimeout(500);
    }

    public void selectNetZeroPlanStatus(String status) {
        page.waitForLoadState();
        netZeroPlanSelect.selectOption(new SelectOption().setLabel(status));
        page.waitForTimeout(300);
    }

    public String getNetZeroPlanStatus() {
        page.waitForLoadState();
        return netZeroPlanSelect.inputValue();
    }

    public void clickNetZeroPlanChatIcon() {
        page.waitForLoadState();
        netZeroPlanChatIcon.click();
        page.waitForTimeout(500);
    }

    /**
     * Stage 3: Validate and certify - Carbon Offsets
     */
    public void clickCarbonOffsetLink() {
        page.waitForLoadState();
        carbonOffsetLink.click();
        page.waitForTimeout(500);
    }

    public void clickCarbonOffsetNavigateIcon() {
        page.waitForLoadState();
        carbonOffsetNavigateIcon.click();
        page.waitForTimeout(500);
    }

    public String getCarbonOffsetValue() {
        page.waitForLoadState();
        return carbonOffsetInput.inputValue();
    }

    public void clickCarbonOffsetChatIcon() {
        page.waitForLoadState();
        carbonOffsetChatIcon.click();
        page.waitForTimeout(500);
    }

    /**
     * Stage 3: Validate and certify - Net Zero Milestone Targets
     */
    public void clickNetZeroMilestoneLink() {
        page.waitForLoadState();
        netZeroMilestoneLink.click();
        page.waitForTimeout(500);
    }

    public void clickNetZeroMilestoneNavigateIcon() {
        page.waitForLoadState();
        netZeroMilestoneNavigateIcon.click();
        page.waitForTimeout(500);
    }

    public String getNetZeroMilestoneValue() {
        page.waitForLoadState();
        return netZeroMilestoneInput.inputValue();
    }

    public void clickNetZeroMilestoneChatIcon() {
        page.waitForLoadState();
        netZeroMilestoneChatIcon.click();
        page.waitForTimeout(500);
    }

    /**
     * Stage 4: Achieve net zero certification
     */
    public String getNetZeroCertificationStatus() {
        page.waitForLoadState();
        return netZeroCertificationStatus.textContent().trim();
    }

    public boolean isNetZeroCertificationHeaderVisible() {
        page.waitForLoadState();
        return netZeroCertificationHeader.isVisible();
    }

    /**
     * Platform demo link
     */
    public boolean isPlatformDemoLinkVisible() {
        page.waitForLoadState();
        return platformDemoLink.isVisible();
    }

    public void clickPlatformDemoLink() {
        page.waitForLoadState();
        platformDemoLink.click();
        page.waitForTimeout(500);
    }

    public String getPlatformDemoLinkText() {
        page.waitForLoadState();
        return platformDemoLink.textContent().trim();
    }

    /**
     * Modal dialogs - General
     */
    public boolean isModalDialogVisible() {
        page.waitForLoadState();
        return modalDialog.isVisible();
    }

    public void closeModalDialog() {
        page.waitForLoadState();
        if (modalCloseButton.isVisible()) {
            modalCloseButton.click();
            page.waitForTimeout(500);
        }
    }

    /**
     * Validation error modal - Appears when required fields are missing
     */
    public boolean isValidationErrorModalVisible() {
        page.waitForLoadState();
        return validationErrorTitle.isVisible();
    }

    public String getValidationErrorTitle() {
        page.waitForLoadState();
        return validationErrorTitle.textContent().trim();
    }

    public String[] getValidationErrorMessages() {
        page.waitForLoadState();
        int itemCount = validationErrorList.locator("li").count();
        String[] messages = new String[itemCount];
        for (int i = 0; i < itemCount; i++) {
            messages[i] = validationErrorList.locator("li").nth(i).textContent().trim();
        }
        return messages;
    }

    public int getValidationErrorCount() {
        page.waitForLoadState();
        return validationErrorList.locator("li").count();
    }

    public void clickValidationErrorCloseButton() {
        page.waitForLoadState();
        validationErrorCloseButton.click();
        page.waitForTimeout(500);
    }

    /**
     * Status change confirmation modal - Appears when changing status after filling required fields
     */
    public boolean isStatusChangeConfirmationModalVisible() {
        page.waitForLoadState();
        return statusChangeConfirmationTitle.isVisible();
    }

    public String getStatusChangeConfirmationTitle() {
        page.waitForLoadState();
        return statusChangeConfirmationTitle.textContent().trim();
    }

    public void clickStatusChangeNoButton() {
        page.waitForLoadState();
        statusChangeNoButton.click();
        page.waitForTimeout(500);
    }

    public void clickStatusChangeYesButton() {
        page.waitForLoadState();
        statusChangeYesButton.click();
        page.waitForTimeout(500);
    }

    public boolean isStatusChangeNoButtonVisible() {
        page.waitForLoadState();
        return statusChangeNoButton.isVisible();
    }

    public boolean isStatusChangeYesButtonVisible() {
        page.waitForLoadState();
        return statusChangeYesButton.isVisible();
    }

    /**
     * Convenience method to change status and handle confirmation
     * Returns true if status was successfully changed, false if validation error occurred
     */
    public boolean changeStatusWithConfirmation(String statusDropdownId, String newStatus) {
        page.waitForLoadState();

        // Select the new status from dropdown
        Locator statusSelect = page.locator(statusDropdownId);
        statusSelect.selectOption(new SelectOption().setLabel(newStatus));
        page.waitForTimeout(500);

        // Check if validation error modal appears
        if (isValidationErrorModalVisible()) {
            clickValidationErrorCloseButton();
            return false;
        }

        // Check if confirmation modal appears
        if (isStatusChangeConfirmationModalVisible()) {
            clickStatusChangeYesButton();
            page.waitForTimeout(500);
            return true;
        }

        return true; // Status changed without any modal
    }

    /**
     * Convenience method for Building Info status change
     */
    public boolean changeBuildingInfoStatusWithConfirmation(String newStatus) {
        return changeStatusWithConfirmation("#gnfz-building-info", newStatus);
    }

    /**
     * Convenience method for Team Info status change
     */
    public boolean changeTeamInfoStatusWithConfirmation(String newStatus) {
        return changeStatusWithConfirmation("#gnfz-team-info", newStatus);
    }

    /**
     * Convenience method for Complete Assessment status change
     */
    public boolean changeCompleteAssessmentStatusWithConfirmation(String newStatus) {
        return changeStatusWithConfirmation("#gnfz-complete-assessment", newStatus);
    }

    /**
     * Convenience method for Net Zero Plan status change
     */
    public boolean changeNetZeroPlanStatusWithConfirmation(String newStatus) {
        return changeStatusWithConfirmation("#gnfz-nzp", newStatus);
    }
}
