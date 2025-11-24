# 🚀 Cricket Tracker - Setup Guide

## Current System Status
✅ Java 24.0.2 is installed  
❌ Maven is not installed or not in PATH

## Option 1: Install Maven (Recommended)

### Download and Install Maven
1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract to a folder (e.g., `C:\Program Files\Apache\maven`)
3. Add Maven to PATH:
   - Open System Properties → Environment Variables
   - Add `C:\Program Files\Apache\maven\bin` to PATH
   - Restart your terminal

### Verify Installation
```powershell
mvn -version
```

### Build and Run
```powershell
cd c:\Users\232067\Documents\cricket_tracker
mvn clean install
mvn spring-boot:run
```

## Option 2: Use Maven Wrapper (No Installation Required)

The project includes Maven wrapper files. Use these commands:

### Windows (PowerShell)
```powershell
cd c:\Users\232067\Documents\cricket_tracker
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

## Option 3: Use IDE (Easiest)

### IntelliJ IDEA
1. Open IntelliJ IDEA
2. File → Open → Select `cricket_tracker` folder
3. Wait for Maven to download dependencies
4. Right-click on `CricketTrackerApplication.java`
5. Select "Run 'CricketTrackerApplication'"

### Eclipse
1. Open Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Browse to `cricket_tracker` folder
4. Right-click on project → Run As → Spring Boot App

### VS Code
1. Install "Extension Pack for Java" and "Spring Boot Extension Pack"
2. Open `cricket_tracker` folder
3. Press F5 or click "Run" above the main method in `CricketTrackerApplication.java`

## Option 4: Manual Compilation (Advanced)

If you want to compile manually without Maven:

```powershell
# Download dependencies manually (not recommended)
# This is complex and not advised - use Maven or IDE instead
```

## After Running

Once the application starts, you'll see:
```
==============================================
🏏 Cricket Tracker Application Started!
📊 Access the application at: http://localhost:8080
==============================================
```

Open your browser and navigate to: **http://localhost:8080**

## Troubleshooting

### Port 8080 Already in Use
If port 8080 is busy, edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Java Version Issues
The project requires Java 17+. You have Java 24, which should work fine.

### Maven Dependencies Download Slowly
First build may take time as Maven downloads dependencies. Be patient!

## Next Steps

1. Choose one of the options above to run the application
2. Open http://localhost:8080 in your browser
3. Start adding cricket matches!
4. Explore the dashboard, statistics, and match history

## Quick Start Recommendation

**For beginners**: Use Option 3 (IDE) - IntelliJ IDEA Community Edition is free and easiest  
**For developers**: Use Option 1 (Install Maven) - most flexible and standard approach

---

Need help? Check the README.md for more information about the application features!
