package com.techreturners.JavaAndSpringToDoListAPI.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Task(
        @Id Long id,
        @Size(max = 255, message = "The task description cannot exceed 255 characters")
        @Size(min = 3, message = "The task should at least be 3 characters long")
        String description,

        @NotNull(message = "The isComplete boolean must be provided")
        Boolean isComplete,

        @Version int version) {
    public static Task of(String description, Boolean isComplete) {
        return new Task(null, description, isComplete, 0);
    }
}
