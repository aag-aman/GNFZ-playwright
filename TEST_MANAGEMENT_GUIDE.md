# 🧪 Test Management Guide

This guide covers everything you need to know about running tests, viewing traces, generating reports, and managing test artifacts efficiently.

## 📁 Directory Structure

```
java-playwright/
├── src/test/java/          # Test source code
├── test-results/           # Organized test artifacts
│   └── traces/            # Playwright traces
│       ├── run_20241001_143022/  # Each run gets its own folder
│       │   ├── LoginTest_143023_456.zip
│       │   └── ForgotPasswordTest_143025_789.zip
│       └── run_20241001_150030/
│           └── LoginRegressionTest_150031_123.zip
├── allure-results/         # Allure test results (JSON files)
├── target/                 # Maven build artifacts
│   └── surefire-reports/   # Maven test reports
└── .allure/               # Allure configuration
```

## 🏃‍♂️ Running Tests

### Individual Tests
```bash
# Run a single test method
mvn test -Dtest="LoginTest#testSuccessfulLogin"

# Run a single test class
mvn test -Dtest="LoginTest"

# Run multiple test classes
mvn test -Dtest="LoginTest,ForgotPasswordTest"
```

### Test Suites by Package
```bash
# Run all authentication tests
mvn test -Dtest="tests.authentication.**.*"

# Run all login tests (smoke + regression)
mvn test -Dtest="*Login*"

# Run all forgot password tests
mvn test -Dtest="*ForgotPassword*"
```

### Test Profiles (if configured)
```bash
# Run smoke tests
mvn test -P smoke

# Run regression tests
mvn test -P regression

# Run all tests
mvn test
```

## 🔍 Viewing Playwright Traces

### 1. Find Your Trace Files
Traces are automatically organized in `test-results/traces/run_TIMESTAMP/`:
```bash
# List all test runs
ls test-results/traces/

# List traces from latest run
ls test-results/traces/run_*/

# Get the most recent trace file
LATEST_TRACE=$(find test-results/traces -name "*.zip" | sort | tail -1)
echo "Latest trace: $LATEST_TRACE"
```

### 2. Open Trace in Playwright Trace Viewer
```bash
# Method 1: Using npx (recommended - no installation needed)
npx playwright show-trace test-results/traces/run_20241001_143022/LoginTest_143023_456.zip

# Quick open latest trace
LATEST_TRACE=$(find test-results/traces -name "*.zip" | sort | tail -1)
npx playwright show-trace "$LATEST_TRACE"

# Method 2: If you have Playwright CLI installed
playwright show-trace test-results/traces/run_20241001_143022/LoginTest_143023_456.zip

# Method 3: Using the web viewer (no CLI needed)
# Visit: https://trace.playwright.dev/
# Then drag and drop your .zip file
```

### 3. Trace Viewer Features
- **Timeline**: See all actions in chronological order
- **Screenshots**: Visual progression of your test
- **Network**: See all network requests
- **Console**: View console logs and errors
- **Source**: See the actual test code that was executed

## 📊 Allure Reports

### 1. Install Allure (one-time setup)
```bash
# macOS (using Homebrew)
brew install allure

# Windows (using Scoop)
scoop install allure

# Manual installation (all platforms)
# Download from: https://github.com/allure-framework/allure2/releases
# Extract and add to PATH
```

### 2. Generate Allure Report

**Option A: Quick serve (recommended)**
```bash
# Generate and serve report in one command (opens browser automatically)
allure serve target/allure-results
```

**Option B: Generate static report**
```bash
# Step 1: Generate static report to allure-report/ folder
allure generate target/allure-results --clean

# Step 2: Open the generated report
allure open allure-report
```

**Option C: Manual viewing (if Allure not installed)**
```bash
# View results manually using online viewer:
# Visit: https://allure.report/
# Drag and drop the target/allure-results folder
```

### 2. Allure Report Features
- **Test Results Overview**: Pass/fail statistics
- **Suites**: Organized by test packages
- **Graphs**: Trend analysis and timing
- **Categories**: Failed tests categorization
- **Attachments**: Screenshots and traces
- **Timeline**: Test execution timeline

### 3. Cleaning Allure Results
```bash
# Clean old results before new test run
rm -rf allure-results/*

# Or run with clean
mvn clean test
```

## 📈 Maven Surefire Reports

### 1. Generate Surefire Reports
```bash
# Generate basic Surefire report
mvn surefire-report:report

# Generate full site with reports
mvn clean test site

# View reports
open target/site/surefire-report.html
```

### 2. Surefire Report Location
- **HTML Report**: `target/site/surefire-report.html`
- **Raw Results**: `target/surefire-reports/`

## 🧹 Cleanup Commands

### Clean Old Test Artifacts
```bash
# Clean all Maven artifacts
mvn clean

# Clean old Allure results
rm -rf allure-results/*

# Clean old traces (keep last 5 runs)
cd test-results/traces
ls -t | tail -n +6 | xargs rm -rf

# Clean everything and start fresh
mvn clean && rm -rf allure-results/* test-results/traces/*
```

### Disk Space Management
```bash
# Check trace folder sizes
du -sh test-results/traces/run_*

# Find large trace files
find test-results/traces -name "*.zip" -size +10M

# Archive old traces (optional)
tar -czf traces_archive_$(date +%Y%m%d).tar.gz test-results/traces/run_*
```

## 🎯 Quick Demo

### Try It Now!
```bash
# 1. Run a quick test to generate traces and reports
mvn test -Dtest="ForgotPasswordTest#testForgotPasswordPageElements"

# 2. View the trace (opens in browser)
LATEST_TRACE=$(find test-results/traces -name "*.zip" | sort | tail -1)
npx playwright show-trace "$LATEST_TRACE"

# 3. Install and view Allure report (if not already installed)
brew install allure  # macOS
allure serve target/allure-results

# 4. View Surefire report
open target/site/surefire-report.html
```

## 🚀 Common Workflows

### 1. Full Test Run with Reports
```bash
# Clean, test, and generate all reports
mvn clean test
allure serve allure-results
```

### 2. Debug Failed Test
```bash
# Run specific failing test
mvn test -Dtest="ForgotPasswordTest#testForgotPasswordSmoke"

# Find the trace file
ls test-results/traces/run_*/ForgotPasswordTest_*

# Open trace viewer
npx playwright show-trace test-results/traces/run_*/ForgotPasswordTest_*.zip
```

### 3. Quick Smoke Test Check
```bash
# Run all smoke tests
mvn test -Dtest="*Test" -Dtest.groups="smoke"

# Or run specific smoke test classes
mvn test -Dtest="LoginTest,ForgotPasswordTest"
```

### 4. CI/CD Pipeline Commands
```bash
# Headless test run for CI
mvn test -Dheadless=true

# Generate portable reports
mvn clean test
allure generate allure-results --clean
tar -czf test-reports.tar.gz allure-report/
```

## 🔧 Configuration Tips

### Browser Configuration
```bash
# Run tests in different browsers
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=webkit
```

### Parallel Execution
```bash
# Run tests in parallel (if configured)
mvn test -Dparallel=methods
mvn test -DforkCount=4
```

### Environment Configuration
```bash
# Run against different environments
mvn test -DbaseUrl=https://staging.example.com
mvn test -Denvironment=staging
```

## 📱 Helpful Scripts

### Create Test Runner Script
```bash
# Create run-tests.sh
cat > run-tests.sh << 'EOF'
#!/bin/bash
echo "🧪 Starting test run..."
mvn clean test
echo "📊 Generating Allure report..."
allure serve allure-results
EOF
chmod +x run-tests.sh
```

### Create Cleanup Script
```bash
# Create cleanup.sh
cat > cleanup.sh << 'EOF'
#!/bin/bash
echo "🧹 Cleaning up test artifacts..."
mvn clean
rm -rf allure-results/*
echo "📁 Keeping last 3 trace runs..."
cd test-results/traces
ls -t | tail -n +4 | xargs rm -rf
echo "✅ Cleanup complete!"
EOF
chmod +x cleanup.sh
```

## 🚨 Troubleshooting

### Common Issues

1. **"No tests found"**
   ```bash
   # Check test file locations
   find src/test -name "*Test.java"

   # Verify test naming conventions
   # Tests should end with Test.java or Tests.java
   ```

2. **"Cannot open trace"**
   ```bash
   # Install Playwright CLI
   npm install -g @playwright/test

   # Or use npx (no installation needed)
   npx playwright show-trace path/to/trace.zip
   ```

3. **"Allure command not found"**
   ```bash
   # Install Allure
   brew install allure  # macOS
   # or download from: https://docs.qameta.io/allure/#_installing_a_commandline
   ```

4. **"Tests timing out"**
   ```bash
   # Increase timeout
   mvn test -Dtest.timeout=60000
   ```

5. **"404 Not Found when opening Allure report"**
   ```bash
   # Problem: allure-report folder doesn't exist
   # Solution: Generate the report first
   allure generate target/allure-results --clean
   allure open allure-report

   # Or use the one-command approach:
   allure serve target/allure-results
   ```

## 📚 Additional Resources

- **Playwright Trace Viewer**: https://playwright.dev/docs/trace-viewer
- **Allure Framework**: https://docs.qameta.io/allure/
- **Maven Surefire Plugin**: https://maven.apache.org/surefire/maven-surefire-plugin/
- **JUnit 5 Documentation**: https://junit.org/junit5/docs/current/user-guide/

## ❓ FAQ - Your Questions Answered

### Q: What happened to the other test results?
**A:** All test artifacts are organized in different locations:
- **Traces**: `test-results/traces/run_TIMESTAMP/` (organized by run)
- **Allure Results**: `target/allure-results/` (JSON files for Allure reports)
- **Surefire Reports**: `target/surefire-reports/` (Maven test reports)
- **Screenshots**: Embedded in Allure results and traces

### Q: How do I run/view a trace?
**A:** Use the Playwright trace viewer:
```bash
# Find your trace
find test-results/traces -name "*.zip" | sort | tail -1

# Open latest trace (recommended)
npx playwright show-trace $(find test-results/traces -name "*.zip" | sort | tail -1)

# Or open specific trace
npx playwright show-trace test-results/traces/run_20241001_200141/ForgotPasswordTest_200144_767.zip
```

### Q: How do I show Allure reports?
**A:** Install Allure and generate reports:
```bash
# Install Allure (one-time)
brew install allure  # macOS

# Generate and view report
allure serve target/allure-results  # Opens browser automatically

# Alternative: Generate static report
allure generate target/allure-results --clean
allure open allure-report
```

### Q: What commands do I need for efficiency?
**A:** Here are the most commonly used commands:

**Quick Test & Debug:**
```bash
# Run single test with trace
mvn test -Dtest="ForgotPasswordTest#testForgotPasswordPageElements"

# View latest trace immediately
npx playwright show-trace $(find test-results/traces -name "*.zip" | sort | tail -1)
```

**Full Test Run:**
```bash
# Complete test run with reports
mvn clean test
allure serve target/allure-results
```

**Cleanup:**
```bash
# Clean everything
mvn clean && rm -rf target/allure-results/* test-results/traces/*
```

---

## 📋 Current Test Artifacts Status

Your current test artifacts:
- **🔍 Traces**: 3 trace files available
- **📈 Allure Results**: 20 JSON result files
- **🗂️ Surefire Reports**: 4 XML report files

---
*Last updated: October 2024*
*Framework version: 1.0.0*