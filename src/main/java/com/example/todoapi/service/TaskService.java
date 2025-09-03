package com.example.todoapi.service;

import com.example.todoapi.dto.TaskRequest;
import com.example.todoapi.dto.TaskResponse;
import com.example.todoapi.entity.Task;
import com.example.todoapi.entity.TaskStatus;
import com.example.todoapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(taskRequest.getStatus() != null ? taskRequest.getStatus() : TaskStatus.PENDING);
        
        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }
    
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }
    
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return new TaskResponse(task);
    }
    
    public TaskResponse updateTask(UUID id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(taskRequest.getStatus());
        
        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(updatedTask);
    }
    
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}

