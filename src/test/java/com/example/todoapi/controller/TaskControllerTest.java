package com.example.todoapi.controller;

import com.example.todoapi.dto.TaskRequest;
import com.example.todoapi.dto.TaskResponse;
import com.example.todoapi.entity.TaskStatus;
import com.example.todoapi.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        // Given
        UUID taskId = UUID.randomUUID();
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setStatus(TaskStatus.PENDING);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(taskId);
        taskResponse.setTitle("Test Task");
        taskResponse.setDescription("Test Description");
        taskResponse.setStatus(TaskStatus.PENDING);
        taskResponse.setCreatedAt(LocalDateTime.now());

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(taskResponse);

        // When & Then
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() throws Exception {
        // Given
        UUID taskId1 = UUID.randomUUID();
        UUID taskId2 = UUID.randomUUID();

        TaskResponse task1 = new TaskResponse();
        task1.setId(taskId1);
        task1.setTitle("Task 1");
        task1.setStatus(TaskStatus.PENDING);

        TaskResponse task2 = new TaskResponse();
        task2.setId(taskId2);
        task2.setTitle("Task 2");
        task2.setStatus(TaskStatus.COMPLETED);

        List<TaskResponse> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(taskId1.toString()))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].id").value(taskId2.toString()))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void getTaskById_ShouldReturnTask() throws Exception {
        // Given
        UUID taskId = UUID.randomUUID();
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(taskId);
        taskResponse.setTitle("Test Task");
        taskResponse.setStatus(TaskStatus.PENDING);

        when(taskService.getTaskById(taskId)).thenReturn(taskResponse);

        // When & Then
        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        // Given
        UUID taskId = UUID.randomUUID();
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("Updated Task");
        taskRequest.setDescription("Updated Description");
        taskRequest.setStatus(TaskStatus.IN_PROGRESS);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(taskId);
        taskResponse.setTitle("Updated Task");
        taskResponse.setDescription("Updated Description");
        taskResponse.setStatus(TaskStatus.IN_PROGRESS);
        taskResponse.setUpdatedAt(LocalDateTime.now());

        when(taskService.updateTask(eq(taskId), any(TaskRequest.class))).thenReturn(taskResponse);

        // When & Then
        mockMvc.perform(put("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void deleteTask_ShouldReturnNoContent() throws Exception {
        // Given
        UUID taskId = UUID.randomUUID();

        // When & Then
        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }
}

