# SonarQube Setup and Results Guide

## How to View SonarQube Test Results

There are two ways to run SonarQube analysis:

### Option 1: Using SonarQube Server (Local Installation)

#### Step 1: Install and Start SonarQube Server

1. **Download SonarQube**:
   - Go to https://www.sonarsource.com/products/sonarqube/downloads/
   - Download the Community Edition (free)
   - Extract to a folder (e.g., `C:\sonarqube`)

2. **Start SonarQube Server**:
   ```powershell
   cd C:\sonarqube\bin\windows-x86-64
   .\StartSonar.bat
   ```
   - Wait for the server to start (takes 1-2 minutes)
   - Default URL: http://localhost:9000
   - Default credentials: admin/admin

3. **Install SonarScanner**:
   - Download from: https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner/
   - Extract and add to PATH, or use the full path

#### Step 2: Run SonarQube Analysis

```powershell
cd "C:\Users\gandh\Documents\NCSU_Spring_2025\Software Analysis and Design\TrendTube\processing-video-service\processing-video-service"

# First, ensure tests are run and coverage is generated
mvn clean test

# Then run SonarQube scanner
sonar-scanner
```

#### Step 3: View Results

1. Open browser: http://localhost:9000
2. Login with admin/admin
3. Navigate to your project: "Processing Video Service"
4. View results in:
   - **Overview**: Summary of issues, coverage, code smells
   - **Issues**: Detailed list of code smells, bugs, vulnerabilities
   - **Measures**: Test coverage, code duplication, complexity metrics
   - **Code**: Source code with highlighted issues

---

### Option 2: Using SonarCloud (Cloud-based, Free for Public Repos)

#### Step 1: Create SonarCloud Account

1. Go to https://sonarcloud.io/
2. Sign up with GitHub/GitLab/Bitbucket
3. Create a new project

#### Step 2: Update Configuration

Update `sonar-project.properties`:
```properties
sonar.host.url=https://sonarcloud.io
sonar.organization=your-org-key
sonar.login=your-token
```

#### Step 3: Run Analysis

```powershell
mvn clean test
sonar-scanner
```

---

### Option 3: Using Maven SonarQube Plugin (Easiest)

#### Step 1: Add Plugin to pom.xml

The plugin is already configured. Just run:

```powershell
cd "C:\Users\gandh\Documents\NCSU_Spring_2025\Software Analysis and Design\TrendTube\processing-video-service\processing-video-service"

# Run tests and SonarQube analysis
mvn clean test sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin \
  -Dsonar.password=admin
```

---

## Quick Start (If SonarQube is Already Running)

If you already have SonarQube running on localhost:9000:

```powershell
cd "C:\Users\gandh\Documents\NCSU_Spring_2025\Software Analysis and Design\TrendTube\processing-video-service\processing-video-service"

# Generate test reports and coverage
mvn clean test

# Run SonarQube analysis
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
```

Then visit: **http://localhost:9000** and navigate to your project.

---

## What You'll See in SonarQube Results

### 1. **Overview Dashboard**
- Total Issues (Bugs, Vulnerabilities, Code Smells)
- Coverage percentage
- Duplicated lines
- Code quality rating (A-E)

### 2. **Issues Tab**
- **Code Smells**: The 5+ code smells we identified
- **Bugs**: Potential runtime errors
- **Vulnerabilities**: Security issues
- Each issue shows:
  - Severity (Blocker, Critical, Major, Minor, Info)
  - Location (file and line number)
  - Description and remediation suggestions

### 3. **Measures Tab**
- **Coverage**: Test coverage metrics
- **Tests**: Number of tests, success rate
- **Complexity**: Cyclomatic complexity
- **Duplications**: Code duplication percentage

### 4. **Code Tab**
- Source code with inline issue markers
- Click on issues to see details

---

## Current Configuration

The `sonar-project.properties` file is configured with:
- Project key: `processing-video-service`
- Source paths: `src/main/java`
- Test paths: `src/test/java`
- Coverage report: `target/site/jacoco/jacoco.xml`
- Test reports: `target/surefire-reports`

---

## Troubleshooting

### If SonarQube server is not running:
```powershell
# Check if port 9000 is in use
netstat -an | findstr :9000

# Start SonarQube (if installed)
cd C:\sonarqube\bin\windows-x86-64
.\StartSonar.bat
```

### If you get "sonar-scanner not found":
- Download SonarScanner from: https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner/
- Add to PATH, or use full path to `sonar-scanner.bat`

### Alternative: Use Maven plugin (no separate scanner needed):
```powershell
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000
```

---

## Expected Results After Refactoring

After our refactoring, you should see:
- ✅ **0** instances of "System.out.println should not be used"
- ✅ **0** instances of "printStackTrace() should not be called"
- ✅ **0** instances of "Missing @Autowired annotation"
- ✅ **0** instances of "RestTemplate should be injected"
- ✅ Improved code quality rating
- ✅ Test coverage metrics (80% instruction, 100% branch)

---

## Quick Command Reference

```powershell
# 1. Run tests and generate coverage
mvn clean test

# 2. Run SonarQube analysis (if server is running)
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin

# 3. Or use sonar-scanner directly
sonar-scanner

# 4. View results at
# http://localhost:9000
```


