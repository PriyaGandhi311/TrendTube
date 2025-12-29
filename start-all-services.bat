@echo off
echo Starting TrendTube Microservices and Frontend...
echo.

REM Start Upload Service (Port 8080)
start "Upload Service (8080)" cmd /k "cd upload-video-service\upload-video-service && mvnw.cmd spring-boot:run"

REM Wait a bit before starting next service
timeout /t 3 /nobreak >nul

REM Start Processing Service (Port 8081)
start "Processing Service (8081)" cmd /k "cd processing-video-service\processing-video-service && mvnw.cmd spring-boot:run"

REM Wait a bit before starting next service
timeout /t 3 /nobreak >nul

REM Start Analytics Service (Port 8083)
start "Analytics Service (8083)" cmd /k "cd analytics-service\analytics-service && mvnw.cmd spring-boot:run"

REM Wait a bit before starting frontend
timeout /t 3 /nobreak >nul

REM Start Frontend (Port 3000)
start "Frontend (3000)" cmd /k "cd upload-video-service\frontend\trendtube-ui && npm start"

echo.
echo All services are starting in separate windows...
echo.
echo Services:
echo   - Upload Service:      http://localhost:8080
echo   - Processing Service:  http://localhost:8081
echo   - Analytics Service:    http://localhost:8083
echo   - Frontend:            http://localhost:3000
echo.
echo Press any key to exit this window (services will continue running)...
pause >nul

