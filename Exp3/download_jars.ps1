# PowerShell Script to automatically download CloudSim 3.0.3 and Commons-Math JARs
$ErrorActionPreference = "Stop"

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$jarsDir = Join-Path $scriptDir "jars"

if (-not (Test-Path $jarsDir)) {
    New-Item -ItemType Directory -Path $jarsDir -Force | Out-Null
}

$commonsMathJar = Join-Path $jarsDir "commons-math3-3.6.1.jar"
$cloudSimJar = Join-Path $jarsDir "cloudsim-3.0.3.jar"

Write-Host "=== CloudSim Dependency Downloader ===" -ForegroundColor Cyan

# 1. Download commons-math3
if (-not (Test-Path $commonsMathJar)) {
    Write-Host "Downloading commons-math3-3.6.1.jar..." -ForegroundColor Yellow
    $mathUrl = "https://repo1.maven.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar"
    curl.exe -L -o $commonsMathJar $mathUrl
    Write-Host "Downloaded commons-math3-3.6.1.jar successfully." -ForegroundColor Green
} else {
    Write-Host "commons-math3-3.6.1.jar already exists." -ForegroundColor Green
}

# 2. Download CloudSim 3.0.3
if (-not (Test-Path $cloudSimJar)) {
    Write-Host "Downloading cloudsim-3.0.3.zip from Cloudslab GitHub..." -ForegroundColor Yellow
    $zipPath = Join-Path $scriptDir "cloudsim-3.0.3.zip"
    $tempExtract = Join-Path $scriptDir "temp_cloudsim_extract"
    $cloudSimUrl = "https://github.com/Cloudslab/cloudsim/releases/download/cloudsim-3.0.3/cloudsim-3.0.3.zip"
    
    curl.exe -L -o $zipPath $cloudSimUrl
    Write-Host "Extracting CloudSim JARs..." -ForegroundColor Yellow
    Expand-Archive -Path $zipPath -DestinationPath $tempExtract -Force
    
    # Copy all jar files into jars directory
    Get-ChildItem -Path $tempExtract -Filter "*.jar" -Recurse | ForEach-Object {
        Copy-Item -Path $_.FullName -Destination $jarsDir -Force
        Write-Host "Copied: $($_.Name)" -ForegroundColor Green
    }
    
    # Clean up temporary zip and folder
    Remove-Item -Path $zipPath -Force -ErrorAction SilentlyContinue
    Remove-Item -Path $tempExtract -Recurse -Force -ErrorAction SilentlyContinue
    Write-Host "CloudSim 3.0.3 extracted successfully into $jarsDir" -ForegroundColor Green
} else {
    Write-Host "cloudsim-3.0.3.jar already exists." -ForegroundColor Green
}

Write-Host "=== All required JAR files are ready in Exp3_CloudSim_Scheduling/jars/ ===" -ForegroundColor Cyan
Get-ChildItem $jarsDir | Select-Object Name, Length
