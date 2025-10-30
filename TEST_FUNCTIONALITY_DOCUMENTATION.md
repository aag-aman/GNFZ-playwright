# Test Functionality Documentation

This document provides a comprehensive overview of all test functions in the test suite, organized by test class. Each test is broken down into clear step-by-step functionality.

**Last Updated:** Auto-generated based on current test files in `/src/test/java/tests/`

---

## Table of Contents

1. [Authentication Tests](#authentication-tests)
   - [Login Tests](#login-tests)
   - [Sign Up Tests](#sign-up-tests)
   - [Forgot Password Tests](#forgot-password-tests)
2. [Dashboard Tests](#dashboard-tests)
3. [Project Management Tests](#project-management-tests)
   - [Create Project Tests](#create-project-tests)
   - [Building Project Tests](#building-project-tests)

---

## Authentication Tests

### Login Tests

#### LoginTest.java

**1. Login Smoke Test - Valid Credentials Only**

   a. Open login page
   b. Enter valid email address
   c. Enter valid password
   d. Click Sign In button
   e. Wait for navigation to project list page (URL contains `/project/list`)
   f. Verify dashboard visible (take screenshot)

---

#### LoginRegressionTest.java

**1. Login Regression Tests - All Scenarios (Parameterized)**

   a. Open login page
   b. Enter email (from test data)
   c. Enter password (from test data)
   d. Click Sign In button
   e. If expected success = "List of projects":
      - Verify login successful (dashboard visible)
      - Take success screenshot
   f. If expected error:
      - Wait 2 seconds for error message
      - Verify error message displayed (check for error indicators)
      - Take error screenshot

**2. Valid Login - Happy Path**

   a. Open login page
   b. Enter valid email from regression test data
   c. Enter valid password from regression test data
   d. Click Sign In button
   e. Wait for navigation to project list page (URL contains `/project/list`)
   f. Take success screenshot

---

### Sign Up Tests

#### SignUpTest.java

**1. Sign Up Page Elements**

   a. Open sign-up page
   b. Verify sign-up page is displayed
   c. Verify first name field is visible
   d. Verify last name field is visible
   e. Verify email field is visible
   f. Verify password field is visible
   g. Verify confirm password field is visible
   h. Verify sign-up button is visible
   i. Verify sign-up button is enabled
   j. Take screenshot

**2. Sign Up Smoke Test**

   a. Open sign-up page
   b. Clear all form fields
   c. Enter first name
   d. Enter last name
   e. Enter email
   f. Enter password
   g. Enter confirm password
   h. Verify data entered correctly (check field values)
   i. Click sign-up button
   j. Wait 1.5 seconds for validation
   k. Verify captcha error message is displayed
   l. Take screenshot

---

#### SignUpRegressionTest.java

**1. Sign Up Positive Scenarios (Parameterized)**

   a. Open sign-up page
   b. Verify page is displayed
   c. Clear all fields
   d. Enter first name
   e. Enter last name
   f. Enter email
   g. Enter password
   h. Enter confirm password
   i. Verify data entered correctly
   j. Click sign-up button
   k. Wait 1.5 seconds for validation
   l. Verify no error for valid data
   m. Take screenshot

**2. Sign Up Negative Scenarios (Parameterized)**

   a. Open sign-up page
   b. Verify page is displayed
   c. Clear all fields
   d. Conditionally enter first name (if not empty)
   e. Conditionally enter last name (if not empty)
   f. Conditionally enter email (if not empty)
   g. Conditionally enter password (if not empty)
   h. Conditionally enter confirm password (if not empty)
   i. Click sign-up button with invalid data
   j. Wait 1.5 seconds for validation
   k. Check if error message is displayed
   l. Attach expected and actual error messages to report
   m. Take screenshot

**3. Sign Up Form Validation**

   a. Open sign-up page
   b. Enter first name "Test"
   c. Verify first name entered correctly
   d. Clear first name field
   e. Verify first name is cleared
   f. Enter password "Password123!"
   g. Enter confirm password "Password123!"
   h. Verify sign-up button is enabled
   i. Take screenshot

**4. Sign Up Page Navigation**

   a. Navigate directly to sign-up page
   b. Verify sign-up page is displayed
   c. Reload page
   d. Verify sign-up page still displayed after reload
   e. Take screenshot

---

### Forgot Password Tests

#### ForgotPasswordTest.java

**1. Forgot Password Smoke Test - Valid Email**

   a. Open forgot password page
   b. Verify forgot password page is displayed
   c. Verify email field is visible
   d. Verify submit button is visible
   e. Enter email from test data
   f. Click submit button
   g. Check if confirmation is displayed:
      - If yes: Verify confirmation header and message are present
      - If no: Verify no error message is displayed for valid email
   h. Take screenshot

**2. Forgot Password Page Elements Test**

   a. Open forgot password page
   b. Verify page header is visible
   c. Verify page header text is present
   d. Verify page sub-header text is present
   e. Verify email field is visible
   f. Verify submit button is visible
   g. Verify submit button is enabled
   h. If back to login link is visible:
      - Verify back to login message is present
   i. Take screenshot

---

#### ForgotPasswordRegressionTest.java

**1. Forgot Password Positive Scenarios (Parameterized)**

   a. Open forgot password page
   b. Verify page is displayed
   c. Clear email field
   d. Enter email (from test data)
   e. Verify email was entered correctly
   f. Click submit button
   g. Wait 1 second for validation
   h. Verify no error message is displayed for valid email format
   i. Take screenshot

**2. Forgot Password Negative Scenarios (Parameterized)**

   a. Open forgot password page
   b. Verify page is displayed
   c. Clear email field
   d. If email is not empty, enter email
   e. Click submit button with invalid data
   f. Wait 1.5 seconds for validation
   g. If error message is displayed:
      - Get actual error message
      - Verify error message is not null
      - Attach expected and actual error messages to report
   h. If no error message:
      - Verify confirmation is not displayed
   i. Take screenshot

**3. Forgot Password Form Validation**

   a. Open forgot password page
   b. Verify email field is initially empty
   c. Enter email "test@example.com"
   d. Verify email entered correctly
   e. Clear email field
   f. Verify email field is cleared
   g. Verify submit button is enabled
   h. If back to login link exists:
      - Verify back to login message is present
      - Click back to login link
   i. Take screenshot

**4. Forgot Password Page Navigation**

   a. Navigate directly to forgot password page
   b. Verify forgot password page is displayed
   c. Reload page
   d. Verify page still displayed after reload
   e. Take screenshot

---

## Dashboard Tests

### DashboardTest.java

**1. Verify Project List Page UI Elements**

   a. Login as project owner
   b. Wait 5 seconds for page load
   c. Verify project list is visible
   d. Verify Create New Project button is visible and enabled
   e. Verify search field is visible
   f. Verify Activity Log button is visible
   g. Verify all filter icons are visible

**2. Verify Project Table is Displayed with Data**

   a. Wait for projects to load
   b. Verify table is not empty
   c. Verify table has at least one row
   d. Get table row count

**3. Verify Pagination Controls**

   a. Wait for projects to load
   b. Check if View More button is visible
   c. If visible: Verify View More button displays count text

**4. Search Projects by Name - Valid Search**

   a. Get first visible project name from table
   b. Search for that project name
   c. Verify search results contain the project
   d. Verify search field contains the search text

**5. Search Projects - Clear Search**

   a. Get initial project count
   b. Search for "Building"
   c. Clear search
   d. Verify all projects are displayed again (count >= initial count or > 0)

**6. Search Projects - No Results**

   a. Search for non-existent project "NonExistentProject12345XYZ"
   b. Verify table is empty for non-existent project search

**7. Filter Projects by Project ID**

   a. Get first visible project ID
   b. Filter by that project ID
   c. Verify filtered project is displayed
   d. Clear project ID filter

**8. Filter Projects by Project Name**

   a. Get first visible project name
   b. Filter by that project name
   c. Verify filtered project is displayed
   d. Clear project name filter

**9. Filter Projects by Category - Building**

   a. Filter by "Building" category
   b. Verify Building category is selected
   c. Verify filtered results contain Building projects
   d. Verify table has at least one row

**10. Filter Projects by Status**

   a. Filter by "In progress" status
   b. Verify "In progress" status is selected

**11. Filter Projects by Date Range**

   a. Filter by date range (from: 01/01/2024, to: 12/31/2025)
   b. Verify projects are filtered by date range (execute without errors)

**12. Select All Categories Filter**

   a. Select all categories
   b. Verify projects are displayed when all categories are selected

**13. Select All Statuses Filter**

   a. Select all statuses
   b. Verify projects are displayed when all statuses are selected

**14. Get Project Details from Table**

   a. Get first visible project ID
   b. Get project name for that ID
   c. Get project category
   d. Get project status
   e. Get project date
   f. Verify all details are not empty

**15. Click Project by Name**

   a. Get first visible project name
   b. Click project by name
   c. Verify navigation occurred (URL changed to project details)

**16. Click Project by ID**

   a. Get first visible project ID
   b. Click project by ID
   c. Verify navigation occurred (URL changed to project details)

**17. Get All Visible Project IDs**

   a. Wait for projects to load
   b. Get all visible project IDs
   c. Verify at least one project ID exists
   d. Verify each ID is not empty

**18. Verify Project is Displayed by Name**

   a. Get first visible project name
   b. Verify project is displayed by name

**19. Verify Project is Displayed by ID**

   a. Get first visible project ID
   b. Verify project is displayed by ID

**20. Navigate to Create New Project**

   a. Click Create New Project button
   b. Verify navigation to project selection page

**21. Navigate to Activity Log**

   a. Click Activity Log button
   b. Verify navigation to activity log page

**22. Load More Projects with View More Button**

   a. If View More button is not visible, skip test
   b. Get initial project count
   c. Click View More button
   d. Verify more projects are loaded (new count > initial count)

**23. Verify Project Count Text Format**

   a. If View More button is not visible, skip test
   b. Get project count text
   c. Verify count text format contains "View more" and "/"

**24. Combined Search and Filter**

   a. Apply category filter "Building"
   b. Apply search "Building"
   c. Verify results are filtered and searched (execute without errors)
   d. Clear search and filter

**25. Multiple Filters Applied Together**

   a. Apply category filter "Building"
   b. Apply status filter "In progress"
   c. Verify multiple filters are applied (execute without errors)

**26. End-to-End Project List Workflow**

   a. Verify project list is displayed
   b. Get first project ID and name
   c. Search for that project name
   d. Filter by "Building" category
   e. Verify project is still displayed after filtering
   f. Clear filters and search
   g. Verify all projects are displayed again

---

## Project Management Tests

### Create Project Tests

#### CreateProjectTest.java

**1. Create Project Button Visible**

   a. Navigate to login page
   b. Enter email from smoke user data
   c. Enter password from smoke user data
   d. Click Sign In button
   e. Verify Create New Project button is visible
   f. Verify Create New Project button is enabled
   g. Take screenshot

**2. Navigate to Project Selection**

   a. Navigate to login page
   b. Enter email from smoke user data
   c. Enter password from smoke user data
   d. Click Sign In button
   e. Click Create New Project button
   f. Verify project selection page is displayed
   g. Verify project options are visible
   h. Take screenshot

**3. Project Selection Page Elements**

   a. Navigate to login page
   b. Enter email from smoke user data
   c. Enter password from smoke user data
   d. Click Sign In button
   e. Click Create New Project button
   f. Verify project selection page is displayed
   g. Get visible options count
   h. Verify at least one project option is visible
   i. Take screenshot

---

### Building Project Tests

#### BuildingProjectTest.java

**1. Building Option Visible**

   a. Login as project owner
   b. Wait 3 seconds
   c. Click Create New Project button
   d. Verify Building option is visible
   e. Take screenshot
   f. Select Building project type
   g. Verify Building project page is displayed
   h. Navigate to Basic Info tab
   i. Verify Basic Info tab form is displayed
   j. Enter project title with timestamp
   k. Click Save
   l. Wait for URL to update to `/project/building/projectId` format
   m. Get project ID from URL
   n. Verify Project ID field is visible
   o. Verify saved Project ID matches URL project ID
   p. Verify saved Project Title matches entered title
   q. Take screenshot
   r. Verify Building type breadcrumb text equals "Building"
   s. Verify Projects breadcrumb text equals "Projects"
   t. Verify project title breadcrumb matches entered project title

**2. Select Building Project**

   a. Login as project owner
   b. Click Create New Project button
   c. Select Building project type
   d. Verify project selection page is displayed
   e. Verify page header text contains "net zero certification"
   f. Verify Building type breadcrumb contains "building"
   g. Take screenshot

**3. Building Option Text**

   a. Login as project owner
   b. Click Create New Project button
   c. Get Building option text
   d. Verify option text is not null
   e. Verify option text contains "building"
   f. Take screenshot

**4. Create Building with All Sections**

   a. Login as project owner
   b. Click Create New Project button
   c. Select Building project type
   d. Verify Building project page is displayed
   e. Navigate to Basic Info tab
   f. Verify Basic Info tab form is displayed
   g. Enter project title with timestamp
   h. Verify project title entered correctly
   i. Click Save
   j. Wait for URL to update to `/project/building/projectId` format
   k. Get project ID from URL
   l. Verify Project ID field is visible after save
   m. Verify saved Project ID matches URL project ID
   n. Verify saved Project Title matches entered title
   o. Take screenshot
   
   **Assessment Tab - Net Zero Emissions:**
   
   p. Navigate to Assessment tab
   q. Verify Assessment tab is displayed
   r. Verify Net Zero Emissions sub-tab is active by default
   s. Enter Reporting Period From: "01/01/2024"
   t. Verify Reporting Period From date persists
   u. Enter Reporting Period To: "01/01/2025"
   v. Verify Reporting Period To date persists
   
   **Scope 1 - Table A (Fuels):**
   
   w. Expand Scope 1 section
   x. Enter fuel type: "Natural Gas"
   y. Verify fuel type persists
   z. Check emission factor auto-population (or enter manually: 2539.25)
   aa. Enter consumption: "100"
   bb. Verify consumption persists
   cc. Check units auto-population
   dd. Verify row total calculation (emission_factor × consumption)
   ee. Verify table total equals row total
   
   **Scope 1 - Table B (Refrigerants):**
   
   ff. Enter refrigerant type: "R-410A"
   gg. Verify refrigerant type persists
   hh. Check emission factor auto-population (or enter manually: 1182.0)
   ii. Enter consumption: "10"
   jj. Verify consumption persists
   kk. Check unit auto-population
   ll. Verify row total calculation with retries
   mm. Verify table total equals row total
   
   **Scope 1 - Table C (Mobile Combustion):**
   
   nn. Enter fuel type: "Gasoline"
   oo. Verify fuel type persists
   pp. Check emission factor auto-population (or enter manually: 34.0)
   qq. Enter consumption: "200"
   rr. Verify consumption persists
   ss. Check units auto-population
   tt. Verify row total calculation
   uu. Verify table total equals row total
   
   **Scope 1 Total Verification:**
   
   vv. Calculate Scope 1 total (Table A + Table B + Table C)
   ww. Get actual Scope 1 total from UI
   xx. Verify Scope 1 total calculation is correct
   
   **Scope 2 - Table D (Energy):**
   
   yy. Expand Scope 2 section
   zz. Enter activity: "Non Renewable Electricity from Grid​"
   aaa. Check emission factor auto-population (or enter manually: 0.149)
   bbb. Enter consumption: "100"
   ccc. Check units auto-population
   ddd. Wait 1.5 seconds for calculations
   eee. Verify row total calculation
   fff. Verify table total equals row total (with retries if needed)
   
   **Scope 2 Total Verification:**
   
   ggg. Wait 1 second for scope total calculation
   hhh. Get actual Scope 2 total from UI
   iii. Verify Scope 2 total equals Table D total
   
   **Additional Testing:** (Energy, Water, Waste sections continue with similar patterns)
   
   - Energy section testing
   - Water section testing
   - Waste section testing
   - Carbon Offset tab testing
   - Net Zero Milestone tab testing
   - Data persistence testing between sections

---

#### BuildingProjectRegressionTest.java

**1. Building Project Form Elements**

   a. Navigate to login page
   b. Enter email from smoke user data
   c. Enter password from smoke user data
   d. Click Sign In button
   e. Click Create New Project button
   f. Select Building project type
   g. Navigate to Basic Info tab
   h. Verify Basic Info tab is displayed
   i. Enter project title "Test"
   j. Verify project title field is functional
   k. Enter target certification area "100"
   l. Verify target certification area field is functional
   m. Enter gross area "500"
   n. Verify gross area field is functional
   o. Enter start date "01/01/2024"
   p. Verify start date field is functional
   q. Take screenshot

**2. Create Valid Building Project**

   a. Navigate to login page
   b. Enter email from smoke user data
   c. Enter password from smoke user data
   d. Click Sign In button
   e. Click Create New Project button
   f. Select Building project type
   g. Navigate to Basic Info tab
   h. Enter project title with timestamp
   i. Enter target certification area "1000"
   j. Enter gross area "5000"
   k. Enter start date "01/01/2025"
   l. Click Save
   m. Wait 1 second for save
   n. Verify project title persists after save
   o. Verify target certification area persists (formatted: "1,000")
   p. Verify gross area persists (formatted: "5,000")
   q. Verify start date persists
   r. Take screenshot

**3. Building Form Validation**

   a. Navigate to login page
   b. Enter email from smoke user data
   c. Enter password from smoke user data
   d. Click Sign In button
   e. Click Create New Project button
   f. Select Building project type
   g. Navigate to Basic Info tab
   h. Click Save without filling required field (title)
   i. Wait 0.5 seconds
   j. Verify title field remains empty
   k. Enter project title with timestamp
   l. Click Save
   m. Wait 1 second
   n. Verify save succeeded (project title persists)
   o. Take screenshot

**4. Building Data Persistence Between Tabs**

   a. Navigate to login page
   b. Enter email from smoke user data
   c. Enter password from smoke user data
   d. Click Sign In button
   e. Click Create New Project button
   f. Select Building project type
   g. Navigate to Basic Info tab
   h. Enter project title with timestamp
   i. Enter target certification area "2000"
   j. Navigate to Overview tab
   k. Wait 0.5 seconds
   l. Navigate back to Basic Info tab
   m. Wait 0.5 seconds
   n. Verify title field is accessible after navigation
   o. Verify area field is accessible after navigation
   p. Take screenshot

---

## Notes

- All tests extend `BaseTest` which provides browser setup, page management, and screenshot capabilities
- Tests use `@AutoStep` annotation for automatic Allure step creation
- Test data is managed through `TestDataManager` utility class
- Screenshots are automatically attached to Allure reports
- Tests use Playwright for browser automation
- Parameterized tests iterate through test data from JSON files

---

## Keeping Documentation Updated

**Important:** This documentation should be updated whenever test files in `/src/test/java/tests/` are modified.

### Manual Update Process

1. Review any new or modified test files in `/src/test/java/tests/`
2. Update this documentation file to reflect:
   - New test methods
   - Modified test steps
   - Removed tests
   - Changes in test functionality

### Recommended Workflow

- Update this documentation as part of your code review process
- Include documentation updates in pull requests when adding/modifying tests
- Check documentation accuracy before merging test changes

### File Structure to Monitor

```
src/test/java/tests/
├── authentication/
│   ├── login/
│   ├── signup/
│   └── forgot_password/
├── dashboard/
│   └── project/
│       └── building/
└── base/
```

---

**Last Updated:** This documentation should be reviewed whenever test files are modified.  
**Maintained by:** Development Team  
**Location:** `/src/test/java/tests/`

