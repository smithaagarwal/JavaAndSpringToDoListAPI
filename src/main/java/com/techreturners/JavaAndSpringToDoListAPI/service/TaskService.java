package com.techreturners.JavaAndSpringToDoListAPI.service;

import com.techreturners.JavaAndSpringToDoListAPI.model.Task;
import com.techreturners.JavaAndSpringToDoListAPI.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    private final int maxLimit;


    public TaskService(TaskRepository taskRepository, @Value("${max.limit.incomplete}") Integer maxLimit) {
        this.taskRepository = taskRepository;
        this.maxLimit = maxLimit;
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task) {
        var allTasks = getAllTasks();
        var noOfIncompleteTasks = StreamSupport.stream(allTasks.spliterator(), true).filter(t -> !t.isComplete()).toList().size();

        if (noOfIncompleteTasks >= maxLimit) {
            throw new RuntimeException("Please complete existing tasks before adding new ones");
        }
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task editTaskDetails(Long taskId, Task task) {
        return taskRepository.findById(taskId).map(existingTask -> {
            var taskToUpdate = new Task(existingTask.id(), task.description(), task.isComplete(), existingTask.version());
            return taskRepository.save(taskToUpdate);
        }).orElseThrow();
    }

}
