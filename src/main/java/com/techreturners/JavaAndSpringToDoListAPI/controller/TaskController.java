package com.techreturners.JavaAndSpringToDoListAPI.controller;

import com.techreturners.JavaAndSpringToDoListAPI.domain.Task;
import com.techreturners.JavaAndSpringToDoListAPI.model.TaskRequest;
import com.techreturners.JavaAndSpringToDoListAPI.model.TaskResponse;
import com.techreturners.JavaAndSpringToDoListAPI.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")

public class TaskController {
    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks")
    @GetMapping("tasks")
    public Iterable<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Add new task")
    @PostMapping(value = "tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse addTask(@Valid @RequestBody TaskRequest taskRequest) {
        return taskService.addTask(Task.of(taskRequest.description(), taskRequest.isComplete()));
    }

    @Operation(summary = "Delete task with id - taskid")
    @DeleteMapping("tasks/{taskid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long taskid) {
        taskService.deleteTask(taskid);
    }

    @Operation(summary = "Update task with id - taskid")
    @PutMapping("tasks/{taskid}")
    public TaskResponse editTask(@PathVariable Long taskid, @Valid @RequestBody TaskRequest taskRequest) {
        return taskService.editTaskDetails(taskid, Task.of(taskRequest.description(), taskRequest.isComplete()));
    }
}
