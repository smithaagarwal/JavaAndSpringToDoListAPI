package com.techreturners.JavaAndSpringToDoListAPI.model;

import com.techreturners.JavaAndSpringToDoListAPI.domain.Task;

public record TaskResponse(
        Long id,
        String description,
        Boolean isComplete
) {
    public static TaskResponse of(Task task) {
        return new TaskResponse(task.id(), task.description(), task.isComplete());
    }
}
