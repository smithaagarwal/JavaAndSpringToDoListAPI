package com.techreturners.JavaAndSpringToDoListAPI.service;

import com.techreturners.JavaAndSpringToDoListAPI.model.Task;
import com.techreturners.JavaAndSpringToDoListAPI.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> viewAllTasks() {
        return taskRepository.findAll();
    }

}
