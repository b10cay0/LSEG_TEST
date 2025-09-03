package com.example.todoapi.entity;

public enum TaskStatus {
    PENDING("pending"),
    IN_PROGRESS("in-progress"),
    COMPLETED("completed");
    
    private final String value;
    
    TaskStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}

