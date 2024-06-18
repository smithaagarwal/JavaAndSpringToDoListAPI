package com.techreturners.JavaAndSpringToDoListAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Task(@Id Long id, String description, Boolean isComplete, @Version int version) {
    public static Task of(String description, Boolean isComplete) {
        return new Task(null, description, isComplete, 0);
    }
}
