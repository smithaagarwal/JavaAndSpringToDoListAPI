package com.techreturners.JavaAndSpringToDoListAPI.controller;

import com.techreturners.JavaAndSpringToDoListAPI.model.Task;
import com.techreturners.JavaAndSpringToDoListAPI.service.TaskService;
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

    @GetMapping("tasks")
    public Iterable<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping(value = "tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @DeleteMapping("tasks/{taskid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long taskid) {
        taskService.deleteTask(taskid);
    }

    @PutMapping("tasks/{taskid}")
    public Task editTask(@PathVariable Long taskid, @RequestBody Task task) {
        return taskService.editTaskDetails(taskid, task);
    }

}
