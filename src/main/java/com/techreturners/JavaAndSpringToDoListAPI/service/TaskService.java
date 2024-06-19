package com.techreturners.JavaAndSpringToDoListAPI.service;

import com.techreturners.JavaAndSpringToDoListAPI.exceptions.TaskNotFoundException;
import com.techreturners.JavaAndSpringToDoListAPI.exceptions.TenOrMoreIncompleteTaskException;
import com.techreturners.JavaAndSpringToDoListAPI.domain.Task;
import com.techreturners.JavaAndSpringToDoListAPI.model.TaskResponse;
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

    public Iterable<TaskResponse> getAllTasks() {
        var tasks = taskRepository.findAll();
        return StreamSupport.stream(tasks.spliterator(), true)
                .map(task -> TaskResponse.of(task)).toList();
    }

    public TaskResponse addTask(Task task) {
        var allTasks = getAllTasks();
        var noOfIncompleteTasks = StreamSupport.stream(allTasks.spliterator(), true).filter(t -> !t.isComplete()).toList().size();

        if (noOfIncompleteTasks >= maxLimit) {
            throw new TenOrMoreIncompleteTaskException();
        }
        return TaskResponse.of(taskRepository.save(task));
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public TaskResponse editTaskDetails(Long taskId, Task task) {
        var updateTask = taskRepository.findById(taskId).map(existingTask -> {
            var taskToUpdate = new Task(existingTask.id(), task.description(), task.isComplete(), existingTask.version());
            return taskRepository.save(taskToUpdate);
        }).orElseThrow(() -> new TaskNotFoundException(taskId));
        return TaskResponse.of(updateTask);
    }
}
