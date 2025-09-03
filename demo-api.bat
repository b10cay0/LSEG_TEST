@echo off
REM Task Management API Demo Script for Windows
REM This script demonstrates how to use the Task Management API

set BASE_URL=http://localhost:8080

echo === Task Management API Demo ===
echo Base URL: %BASE_URL%
echo.

REM Test if the API is running
echo 1. Testing API connectivity...
curl -s -o nul -w "%%{http_code}" "%BASE_URL%/tasks" > temp_status.txt
set /p STATUS_CODE=<temp_status.txt
if "%STATUS_CODE%"=="200" (
    echo ✅ API is running!
) else (
    echo ❌ API is not running. Please start the application first.
    echo    Run: mvnw.cmd spring-boot:run
    del temp_status.txt
    pause
    exit /b 1
)
del temp_status.txt
echo.
echo.

REM Get all tasks
echo 2. Getting all tasks...
curl -s -X GET "%BASE_URL%/tasks"
echo.
echo.

REM Create a new task
echo 3. Creating a new task...
curl -s -X POST "%BASE_URL%/tasks" ^
  -H "Content-Type: application/json" ^
  -d "{\"title\": \"Demo Task\", \"description\": \"This is a demo task created by the script\", \"dueDate\": \"2024-12-31T23:59:59\", \"status\": \"pending\"}" > temp_task_response.txt

echo Created task:
type temp_task_response.txt
echo.
echo.

REM Extract task ID (simplified for Windows batch)
for /f "tokens=2 delims=:" %%a in ('findstr "id" temp_task_response.txt') do (
    set TASK_ID=%%a
    set TASK_ID=!TASK_ID:"=!
    set TASK_ID=!TASK_ID:,=!
    set TASK_ID=!TASK_ID: =!
)

if defined TASK_ID (
    echo Task ID: %TASK_ID%
    echo.
    
    REM Get the specific task
    echo 4. Getting task by ID...
    curl -s -X GET "%BASE_URL%/tasks/%TASK_ID%"
    echo.
    echo.
    
    REM Update the task
    echo 5. Updating task status to 'in-progress'...
    curl -s -X PUT "%BASE_URL%/tasks/%TASK_ID%" ^
      -H "Content-Type: application/json" ^
      -d "{\"title\": \"Demo Task (Updated)\", \"description\": \"This is an updated demo task\", \"dueDate\": \"2024-12-31T23:59:59\", \"status\": \"in-progress\"}"
    echo.
    echo.
    
    REM Get updated task
    echo 6. Getting updated task...
    curl -s -X GET "%BASE_URL%/tasks/%TASK_ID%"
    echo.
    echo.
    
    REM Delete the task
    echo 7. Deleting the demo task...
    curl -s -o nul -w "%%{http_code}" -X DELETE "%BASE_URL%/tasks/%TASK_ID%" > temp_delete_status.txt
    set /p DELETE_STATUS=<temp_delete_status.txt
    if "%DELETE_STATUS%"=="204" (
        echo ✅ Task deleted successfully!
    ) else (
        echo ❌ Failed to delete task. HTTP Status: %DELETE_STATUS%
    )
    del temp_delete_status.txt
    echo.
    
    REM Verify deletion
    echo 8. Verifying task deletion...
    curl -s -o nul -w "%%{http_code}" -X GET "%BASE_URL%/tasks/%TASK_ID%" > temp_get_status.txt
    set /p GET_STATUS=<temp_get_status.txt
    if "%GET_STATUS%"=="404" (
        echo ✅ Task successfully deleted (404 Not Found)
    ) else (
        echo ❌ Task still exists. HTTP Status: %GET_STATUS%
    )
    del temp_get_status.txt
) else (
    echo ❌ Could not extract task ID from response
)

del temp_task_response.txt
echo.
echo === Demo completed ===
echo.
echo 📚 Additional resources:
echo    - Swagger UI: %BASE_URL%/swagger-ui.html
echo    - H2 Console: %BASE_URL%/h2-console
echo    - API Docs: %BASE_URL%/api-docs
echo.
pause

