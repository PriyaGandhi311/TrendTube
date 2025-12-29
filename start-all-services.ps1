Write-Host "Starting TrendTube Microservices and Frontend..." -ForegroundColor Green
Write-Host ""

# Start Upload Service (Port 8080)
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\upload-video-service\upload-video-service'; .\mvnw.cmd spring-boot:run" -WindowStyle Normal
Start-Sleep -Seconds 3

# Start Processing Service (Port 8081)
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\processing-video-service\processing-video-service'; .\mvnw.cmd spring-boot:run" -WindowStyle Normal
Start-Sleep -Seconds 3

# Start Analytics Service (Port 8083)
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\analytics-service\analytics-service'; .\mvnw.cmd spring-boot:run" -WindowStyle Normal
Start-Sleep -Seconds 3

# Start Frontend (Port 3000)
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\upload-video-service\frontend\trendtube-ui'; npm start" -WindowStyle Normal

Write-Host ""
Write-Host "All services are starting in separate windows..." -ForegroundColor Yellow
Write-Host ""
Write-Host "Services:" -ForegroundColor Cyan
Write-Host "  - Upload Service:      http://localhost:8080" -ForegroundColor White
Write-Host "  - Processing Service:  http://localhost:8081" -ForegroundColor White
Write-Host "  - Analytics Service:    http://localhost:8083" -ForegroundColor White
Write-Host "  - Frontend:            http://localhost:3000" -ForegroundColor White
Write-Host ""

