#!/bin/bash

# Task Management API Demo Script
# This script demonstrates how to use the Task Management API

BASE_URL="http://localhost:8080"

echo "=== Task Management API Demo ==="
echo "Base URL: $BASE_URL"
echo ""

# Test if the API is running
echo "1. Testing API connectivity..."
curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/tasks" > /tmp/status_code
if [ "$(cat /tmp/status_code)" = "200" ]; then
    echo "✅ API is running!"
else
    echo "❌ API is not running. Please start the application first."
    echo "   Run: ./mvnw spring-boot:run"
    exit 1
fi
echo ""

# Get all tasks
echo "2. Getting all tasks..."
curl -s -X GET "$BASE_URL/tasks" | jq '.' 2>/dev/null || curl -s -X GET "$BASE_URL/tasks"
echo ""
echo ""

# Create a new task
echo "3. Creating a new task..."
TASK_RESPONSE=$(curl -s -X POST "$BASE_URL/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Demo Task",
    "description": "This is a demo task created by the script",
    "dueDate": "2024-12-31T23:59:59",
    "status": "pending"
  }')

echo "Created task:"
echo "$TASK_RESPONSE" | jq '.' 2>/dev/null || echo "$TASK_RESPONSE"
echo ""

# Extract task ID from response
TASK_ID=$(echo "$TASK_RESPONSE" | jq -r '.id' 2>/dev/null || echo "$TASK_RESPONSE" | grep -o '"id":"[^"]*"' | cut -d'"' -f4)

if [ -n "$TASK_ID" ] && [ "$TASK_ID" != "null" ]; then
    echo "Task ID: $TASK_ID"
    echo ""
    
    # Get the specific task
    echo "4. Getting task by ID..."
    curl -s -X GET "$BASE_URL/tasks/$TASK_ID" | jq '.' 2>/dev/null || curl -s -X GET "$BASE_URL/tasks/$TASK_ID"
    echo ""
    echo ""
    
    # Update the task
    echo "5. Updating task status to 'in-progress'..."
    curl -s -X PUT "$BASE_URL/tasks/$TASK_ID" \
      -H "Content-Type: application/json" \
      -d '{
        "title": "Demo Task (Updated)",
        "description": "This is an updated demo task",
        "dueDate": "2024-12-31T23:59:59",
        "status": "in-progress"
      }' | jq '.' 2>/dev/null || curl -s -X PUT "$BASE_URL/tasks/$TASK_ID" \
      -H "Content-Type: application/json" \
      -d '{
        "title": "Demo Task (Updated)",
        "description": "This is an updated demo task",
        "dueDate": "2024-12-31T23:59:59",
        "status": "in-progress"
      }'
    echo ""
    echo ""
    
    # Get updated task
    echo "6. Getting updated task..."
    curl -s -X GET "$BASE_URL/tasks/$TASK_ID" | jq '.' 2>/dev/null || curl -s -X GET "$BASE_URL/tasks/$TASK_ID"
    echo ""
    echo ""
    
    # Delete the task
    echo "7. Deleting the demo task..."
    DELETE_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE "$BASE_URL/tasks/$TASK_ID")
    if [ "$DELETE_RESPONSE" = "204" ]; then
        echo "✅ Task deleted successfully!"
    else
        echo "❌ Failed to delete task. HTTP Status: $DELETE_RESPONSE"
    fi
    echo ""
    
    # Verify deletion
    echo "8. Verifying task deletion..."
    GET_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X GET "$BASE_URL/tasks/$TASK_ID")
    if [ "$GET_RESPONSE" = "404" ]; then
        echo "✅ Task successfully deleted (404 Not Found)"
    else
        echo "❌ Task still exists. HTTP Status: $GET_RESPONSE"
    fi
else
    echo "❌ Could not extract task ID from response"
fi

echo ""
echo "=== Demo completed ==="
echo ""
echo "📚 Additional resources:"
echo "   - Swagger UI: $BASE_URL/swagger-ui.html"
echo "   - H2 Console: $BASE_URL/h2-console"
echo "   - API Docs: $BASE_URL/api-docs"

