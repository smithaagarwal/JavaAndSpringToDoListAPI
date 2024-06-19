package com.techreturners.JavaAndSpringToDoListAPI.exceptions;

public class TenOrMoreIncompleteTaskException extends  RuntimeException{
    public TenOrMoreIncompleteTaskException() {
        super("Please complete existing tasks before adding new ones");
    }
}
