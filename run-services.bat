@echo off
REM run-services.bat
REM Equivalent to Makefile for Windows users to manage Spring Boot microservices

set USER_SERVICE=user-service
set WORKOUT_SERVICE=workout_service
set PROGRESS_SERVICE=workout_progress_tracking
set PORTS=8080 8081 8082 8083

REM Function to stop services and free ports
:stop
echo Stopping all running Spring Boot services and freeing ports...
REM Stop Maven processes (java.exe running mvn spring-boot:run)
taskkill /F /FI "IMAGENAME eq java.exe" /FI "WINDOWTITLE eq *mvn spring-boot:run*" >nul 2>&1
REM Free specified ports
for %%p in (%PORTS%) do (
    netstat -aon | findstr :%%p | findstr LISTENING >nul
    if not errorlevel 1 (
        for /f "tokens=5" %%i in ('netstat -aon ^| findstr :%%p ^| findstr LISTENING') do (
            taskkill /F /PID %%i >nul 2>&1
            echo Killed process using port %%p
        )
    )
)
goto :eof

REM Function to run a service
:start_service
set SERVICE_DIR=%1
echo Starting %SERVICE_DIR%...
cd %SERVICE_DIR%
start /B mvn spring-boot:run
cd ..
goto :eof

REM Function to clean a service
:clean_service
set SERVICE_DIR=%1
echo Cleaning %SERVICE_DIR%...
cd %SERVICE_DIR%
mvn clean
cd ..
goto :eof

REM Main script
if /i "%1"=="run" (
    call :stop
    call :start_service %USER_SERVICE%
    call :start_service %WORKOUT_SERVICE%
    call :start_service %PROGRESS_SERVICE%
    echo All services running. Press Ctrl+C to stop.
    goto :eof
)

if /i "%1"=="user" (
    call :start_service %USER_SERVICE%
    goto :eof
)

if /i "%1"=="workout" (
    call :start_service %WORKOUT_SERVICE%
    goto :eof
)

if /i "%1"=="progress" (
    call :start_service %PROGRESS_SERVICE%
    goto :eof
)

if /i "%1"=="clean" (
    call :clean_service %USER_SERVICE%
    call :clean_service %WORKOUT_SERVICE%
    call :clean_service %PROGRESS_SERVICE%
    goto :eof
)

if /i "%1"=="stop" (
    call :stop
    goto :eof
)

echo Usage: run-services.bat [run^|user^|workout^|progress^|clean^|stop]
echo   run    : Stop all services and start all microservices
echo   user   : Start user-service only
echo   workout: Start workout_service only
echo   progress: Start workout_progress_tracking only
echo   clean  : Clean all services
echo   stop   : Stop all services and free ports