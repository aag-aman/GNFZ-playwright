package pages;

import com.microsoft.playwright.Page;
import pages.authentication.LoginPage;
import pages.authentication.ForgotPasswordPage;
import pages.authentication.SignUpPage;
import pages.dashboard.ProjectListPage;
import pages.dashboard.ProjectSelectionPage;
import pages.dashboard.ActivityLogPage;
import pages.dashboard.project.building.BuildingProjectPage;
import pages.dashboard.project.building.BuildingOverviewTab;
import pages.dashboard.project.building.BuildingBasicInfoTab;
import pages.dashboard.project.building.BuildingAssessmentTab;
import pages.dashboard.project.building.BuildingNetZeroPlanTab;
import pages.dashboard.project.building.BuildingCarbonOffsetTab;
import pages.dashboard.project.building.BuildingNetZeroMilestoneTab;
import pages.dashboard.project.building.BuildingSummaryTab;
import pages.dashboard.project.building.BuildingProjectFilesTab;
import pages.dashboard.project.building.assessment.NetZeroEmissionsSection;
import pages.dashboard.project.building.assessment.NetZeroWasteSection;
import pages.dashboard.project.building.assessment.NetZeroEnergySection;
import pages.dashboard.project.building.assessment.NetZeroWaterSection;
import pages.common.NavbarPage;
import pages.notifications.NotificationsPage;
import pages.profile.ProfilePage;

/**
 * PageManager - Singleton pattern for Page Object instantiation
 * Ensures page objects are created once per Page instance and reused across tests
 */
public class PageManager {
    private final Page page;

    // Authentication page objects - lazy initialization
    private LoginPage loginPage;
    private ForgotPasswordPage forgotPasswordPage;
    private SignUpPage signUpPage;

    // Dashboard page objects - lazy initialization
    private ProjectListPage projectListPage;
    private ProjectSelectionPage projectSelectionPage;
    private ActivityLogPage activityLogPage;

    // Building project page objects - lazy initialization
    private BuildingProjectPage buildingProjectPage;
    private BuildingOverviewTab buildingOverviewTab;
    private BuildingBasicInfoTab buildingBasicInfoTab;
    private BuildingAssessmentTab buildingAssessmentTab;
    private BuildingNetZeroPlanTab buildingNetZeroPlanTab;
    private BuildingCarbonOffsetTab buildingCarbonOffsetTab;
    private BuildingNetZeroMilestoneTab buildingNetZeroMilestoneTab;
    private BuildingSummaryTab buildingSummaryTab;
    private BuildingProjectFilesTab buildingProjectFilesTab;

    // Building assessment sections - lazy initialization
    private NetZeroEmissionsSection netZeroEmissionsSection;
    private NetZeroWasteSection netZeroWasteSection;
    private NetZeroEnergySection netZeroEnergySection;
    private NetZeroWaterSection netZeroWaterSection;

    // Common page objects - lazy initialization
    private NavbarPage navbarPage;
    private NotificationsPage notificationsPage;
    private ProfilePage profilePage;

    public PageManager(Page page) {
        this.page = page;
    }

    /**
     * Authentication Pages
     */
    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(page);
        }
        return loginPage;
    }

    public ForgotPasswordPage getForgotPasswordPage() {
        if (forgotPasswordPage == null) {
            forgotPasswordPage = new ForgotPasswordPage(page);
        }
        return forgotPasswordPage;
    }

    public SignUpPage getSignUpPage() {
        if (signUpPage == null) {
            signUpPage = new SignUpPage(page);
        }
        return signUpPage;
    }

    /**
     * Dashboard Pages
     */
    public ProjectListPage getProjectListPage() {
        if (projectListPage == null) {
            projectListPage = new ProjectListPage(page);
        }
        return projectListPage;
    }

    public ProjectSelectionPage getProjectSelectionPage() {
        if (projectSelectionPage == null) {
            projectSelectionPage = new ProjectSelectionPage(page);
        }
        return projectSelectionPage;
    }

    public ActivityLogPage getActivityLogPage() {
        if (activityLogPage == null) {
            activityLogPage = new ActivityLogPage(page);
        }
        return activityLogPage;
    }

    /**
     * Building Project Pages
     */
    public BuildingProjectPage getBuildingProjectPage() {
        if (buildingProjectPage == null) {
            buildingProjectPage = new BuildingProjectPage(page);
        }
        return buildingProjectPage;
    }

    public BuildingOverviewTab getBuildingOverviewTab() {
        if (buildingOverviewTab == null) {
            buildingOverviewTab = new BuildingOverviewTab(page);
        }
        return buildingOverviewTab;
    }

    public BuildingBasicInfoTab getBuildingBasicInfoTab() {
        if (buildingBasicInfoTab == null) {
            buildingBasicInfoTab = new BuildingBasicInfoTab(page);
        }
        return buildingBasicInfoTab;
    }

    public BuildingAssessmentTab getBuildingAssessmentTab() {
        if (buildingAssessmentTab == null) {
            buildingAssessmentTab = new BuildingAssessmentTab(page);
        }
        return buildingAssessmentTab;
    }

    public BuildingNetZeroPlanTab getBuildingNetZeroPlanTab() {
        if (buildingNetZeroPlanTab == null) {
            buildingNetZeroPlanTab = new BuildingNetZeroPlanTab(page);
        }
        return buildingNetZeroPlanTab;
    }

    public BuildingCarbonOffsetTab getBuildingCarbonOffsetTab() {
        if (buildingCarbonOffsetTab == null) {
            buildingCarbonOffsetTab = new BuildingCarbonOffsetTab(page);
        }
        return buildingCarbonOffsetTab;
    }

    public BuildingNetZeroMilestoneTab getBuildingNetZeroMilestoneTab() {
        if (buildingNetZeroMilestoneTab == null) {
            buildingNetZeroMilestoneTab = new BuildingNetZeroMilestoneTab(page);
        }
        return buildingNetZeroMilestoneTab;
    }

    public BuildingSummaryTab getBuildingSummaryTab() {
        if (buildingSummaryTab == null) {
            buildingSummaryTab = new BuildingSummaryTab(page);
        }
        return buildingSummaryTab;
    }

    public BuildingProjectFilesTab getBuildingProjectFilesTab() {
        if (buildingProjectFilesTab == null) {
            buildingProjectFilesTab = new BuildingProjectFilesTab(page);
        }
        return buildingProjectFilesTab;
    }

    /**
     * Building Assessment Sections
     */
    public NetZeroEmissionsSection getNetZeroEmissionsSection() {
        if (netZeroEmissionsSection == null) {
            netZeroEmissionsSection = new NetZeroEmissionsSection(page);
        }
        return netZeroEmissionsSection;
    }

    public NetZeroWasteSection getNetZeroWasteSection() {
        if (netZeroWasteSection == null) {
            netZeroWasteSection = new NetZeroWasteSection(page);
        }
        return netZeroWasteSection;
    }

    public NetZeroEnergySection getNetZeroEnergySection() {
        if (netZeroEnergySection == null) {
            netZeroEnergySection = new NetZeroEnergySection(page);
        }
        return netZeroEnergySection;
    }

    public NetZeroWaterSection getNetZeroWaterSection() {
        if (netZeroWaterSection == null) {
            netZeroWaterSection = new NetZeroWaterSection(page);
        }
        return netZeroWaterSection;
    }

    /**
     * Common Pages
     */
    public NavbarPage getNavbarPage() {
        if (navbarPage == null) {
            navbarPage = new NavbarPage(page);
        }
        return navbarPage;
    }

    public NotificationsPage getNotificationsPage() {
        if (notificationsPage == null) {
            notificationsPage = new NotificationsPage(page);
        }
        return notificationsPage;
    }

    public ProfilePage getProfilePage() {
        if (profilePage == null) {
            profilePage = new ProfilePage(page);
        }
        return profilePage;
    }

    /**
     * Reset all page objects (useful for new test contexts)
     */
    public void resetPages() {
        loginPage = null;
        forgotPasswordPage = null;
        signUpPage = null;
        projectListPage = null;
        projectSelectionPage = null;
        activityLogPage = null;
        buildingProjectPage = null;
        buildingOverviewTab = null;
        buildingBasicInfoTab = null;
        buildingAssessmentTab = null;
        buildingNetZeroPlanTab = null;
        buildingCarbonOffsetTab = null;
        buildingNetZeroMilestoneTab = null;
        buildingSummaryTab = null;
        buildingProjectFilesTab = null;
        netZeroEmissionsSection = null;
        netZeroWasteSection = null;
        netZeroEnergySection = null;
        netZeroWaterSection = null;
        navbarPage = null;
        notificationsPage = null;
        profilePage = null;
    }

    /**
     * Check if any pages are initialized
     */
    public boolean hasInitializedPages() {
        return loginPage != null || forgotPasswordPage != null || signUpPage != null
            || projectListPage != null || projectSelectionPage != null || navbarPage != null;
    }

    /**
     * Reset specific page categories
     */
    public void resetAuthenticationPages() {
        loginPage = null;
        forgotPasswordPage = null;
        signUpPage = null;
    }

    public void resetDashboardPages() {
        projectListPage = null;
        projectSelectionPage = null;
        activityLogPage = null;
    }

    public void resetBuildingProjectPages() {
        buildingProjectPage = null;
        buildingOverviewTab = null;
        buildingBasicInfoTab = null;
        buildingAssessmentTab = null;
        buildingNetZeroPlanTab = null;
        buildingCarbonOffsetTab = null;
        buildingNetZeroMilestoneTab = null;
        buildingSummaryTab = null;
        buildingProjectFilesTab = null;
        netZeroEmissionsSection = null;
        netZeroWasteSection = null;
        netZeroEnergySection = null;
        netZeroWaterSection = null;
    }

    public void resetCommonPages() {
        navbarPage = null;
        notificationsPage = null;
        profilePage = null;
    }
}