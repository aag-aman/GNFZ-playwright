# Test Reporting Guide

## Available Reports

### 1. Basic Maven Surefire Reports
**Location**: `target/surefire-reports/` (XML) and `target/site/` (HTML)

**Files Generated**:
- `target/surefire-reports/TEST-*.xml` - JUnit XML format
- `target/surefire-reports/*.txt` - Text summary
- `target/site/surefire-report.html` - Basic HTML report

**View**: Open `target/site/surefire-report.html` in browser

### 2. Allure Reports (Comprehensive)
**Location**: `target/allure-results/` (raw) → `allure-report/` (HTML)

**Features**:
- ✅ Detailed test steps
- ✅ Screenshots on success/failure
- ✅ Playwright traces attached
- ✅ Test categorization (Epic/Feature/Story)
- ✅ Timeline and trends
- ✅ Environment info
- ✅ Test data visibility

## Commands to Generate Reports

### Run Tests and Generate Reports
```bash
# Run tests (generates XML reports)
mvn clean test

# Generate HTML Surefire report
mvn surefire-report:report
# View: target/site/surefire-report.html

# Generate full site with HTML reports
mvn clean test site
# View: target/site/index.html

# Generate Allure report
mvn allure:report

# Serve Allure report (opens browser)
mvn allure:serve

# Alternative: Generate and open
allure generate target/allure-results --clean && allure open
```

### Test Category Reports
```bash
# Smoke test reports
mvn clean test -P smoke
mvn surefire-report:report      # HTML report
mvn allure:serve               # Allure report

# Regression test reports
mvn clean test -P regression
mvn surefire-report:report      # HTML report
mvn allure:serve               # Allure report

# Combined: Run tests and generate all reports
mvn clean test site allure:report
```

## Report Contents

### Allure Report Structure
```
📊 Allure Report
├── 📈 Overview
│   ├── Test execution statistics
│   ├── Success/failure rates
│   └── Duration metrics
├── 📋 Categories
│   ├── Product defects
│   ├── Test defects
│   └── Broken tests
├── 🏷️ Suites
│   ├── Smoke tests
│   └── Regression tests
├── 📊 Graphs
│   ├── Status breakdown
│   ├── Severity distribution
│   └── Duration trends
├── ⏱️ Timeline
│   └── Test execution order
├── 🔍 Behaviors (BDD-style)
│   ├── Epic: Login Functionality
│   ├── Feature: User Authentication
│   └── Story: Valid Login
└── 📦 Packages
    └── Test class organization
```

### Test Step Details
Each test shows:
- **Test description and metadata**
- **Step-by-step execution** with timestamps
- **Screenshots** at key points
- **Playwright traces** (downloadable .zip)
- **Test data** used
- **Environment information**
- **Execution time**

## Sample Report Annotations

### Test Level Annotations
```java
@Epic("Login Functionality")        // High-level feature group
@Feature("User Authentication")     // Specific feature
@Story("Valid Login")              // User story
@Severity(SeverityLevel.CRITICAL)  // Business impact
@Description("Detailed test description")
```

### Step Level Annotations
```java
Allure.step("Navigate to login page", () -> {
    loginPage.navigateToLogin();
});

Allure.step("Enter credentials", () -> {
    loginPage.enterEmail(email);
    loginPage.enterPassword(password);
});
```

### Attachments
```java
// Screenshot
byte[] screenshot = page.screenshot();
Allure.addAttachment("Login Success", "image/png", screenshot, "png");

// Trace file
byte[] trace = Files.readAllBytes(Paths.get("trace.zip"));
Allure.addAttachment("Playwright Trace", "application/zip", trace, "zip");

// Text data
Allure.addAttachment("Test Data", testData.toString(), "text/plain");
```

## Report Features

### 1. Test Execution Summary
- Total tests run
- Pass/fail/skip counts
- Execution duration
- Success rate percentage

### 2. Detailed Test Results
- Individual test status
- Step-by-step execution
- Failure reasons and stack traces
- Screenshots at failure points

### 3. Historical Trends
- Test execution history
- Flaky test identification
- Performance trends
- Success rate over time

### 4. Categorization
- **Epics**: High-level features (Login, Registration)
- **Features**: Specific functionality (Authentication, Validation)
- **Stories**: User scenarios (Valid login, Invalid password)
- **Severity**: Critical, High, Medium, Low

### 5. Attachments
- **Screenshots**: Visual proof of test execution
- **Traces**: Full Playwright debugging info
- **Logs**: Console outputs and errors
- **Test Data**: Input parameters used

## Installation Requirements

### Install Allure CLI (Optional - for command line)
```bash
# macOS
brew install allure

# Windows (Chocolatey)
choco install allure

# Manual download
# Download from: https://github.com/allure-framework/allure2/releases
```

## CI/CD Integration

### Jenkins
```groovy
pipeline {
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}
```

### GitHub Actions
```yaml
- name: Run tests
  run: mvn clean test

- name: Generate Allure Report
  uses: simple-elf/allure-report-action@master
  with:
    allure_results: target/allure-results
    allure_report: allure-report
    allure_history: allure-history
```

## Report Benefits

### For Developers
- **Quick failure diagnosis** with screenshots
- **Step-by-step execution** visibility
- **Trace files** for deep debugging
- **Historical trends** for stability tracking

### For QA Teams
- **Comprehensive test coverage** overview
- **Test categorization** by feature/severity
- **Flaky test identification**
- **Execution timeline** analysis

### For Management
- **Quality metrics** and trends
- **Test automation ROI** visibility
- **Risk assessment** via severity breakdown
- **Release readiness** indicators

## Current Setup Status

✅ **Configured**:
- Maven Surefire basic reporting
- Allure framework integration
- Automatic screenshot capture
- Playwright trace attachment
- Test categorization (Epic/Feature/Story)

✅ **Commands Available**:
```bash
mvn clean test                    # Run tests (XML reports)
mvn surefire-report:report       # Generate HTML report
mvn clean test site              # Run tests + full HTML site
mvn allure:serve                 # Generate and view Allure report
mvn test -P smoke                # Smoke tests only
mvn test -P regression           # Regression tests only
```

✅ **Report Locations**:
- Basic HTML: `target/site/surefire-report.html`
- Full Site: `target/site/index.html`
- Allure: `mvn allure:serve` (auto-opens browser)