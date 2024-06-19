package com.techreturners.JavaAndSpringToDoListAPI.exceptions;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class TaskNotFoundException extends NoSuchElementException {
    public TaskNotFoundException(Long taskid) {
        super("The task with ID " + taskid + " was not found");
    }
}
