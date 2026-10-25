# PowerShell script to compile and run CloudSim Scheduling Simulation
$ErrorActionPreference = "Stop"

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$jarsDir = Join-Path $scriptDir "jars"
$srcFile = Join-Path $scriptDir "src\org\cloudbus\cloudsim\examples\CloudSimExample1.java"
$binDir = Join-Path $scriptDir "bin"

# 1. Check if jars exist; if not, trigger download
if (-not (Test-Path (Join-Path $jarsDir "cloudsim-3.0.3.jar"))) {
    Write-Host "JARs missing. Running download_jars.ps1..." -ForegroundColor Yellow
    & (Join-Path $scriptDir "download_jars.ps1")
}

# 2. Create bin directory
if (-not (Test-Path $binDir)) {
    New-Item -ItemType Directory -Path $binDir -Force | Out-Null
}

# 3. Compile
Write-Host "Compiling CloudSimExample1.java..." -ForegroundColor Cyan
$classpath = "$jarsDir\*"
javac -cp "$classpath" -d "$binDir" "$srcFile"

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation SUCCESSFUL!" -ForegroundColor Green
} else {
    Write-Host "Compilation FAILED with code $LASTEXITCODE" -ForegroundColor Red
    exit 1
}

# 4. Run Simulation
Write-Host "`nRunning CloudSim Scheduling Simulation..." -ForegroundColor Cyan
Write-Host "--------------------------------------------------------" -ForegroundColor Gray
$runClasspath = "$binDir;$jarsDir\*"
java -cp "$runClasspath" org.cloudbus.cloudsim.examples.CloudSimExample1
Write-Host "--------------------------------------------------------" -ForegroundColor Gray
Write-Host "Simulation Execution Completed." -ForegroundColor Green
