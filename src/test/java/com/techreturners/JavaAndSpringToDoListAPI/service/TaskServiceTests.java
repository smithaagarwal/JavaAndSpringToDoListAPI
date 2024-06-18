package com.techreturners.JavaAndSpringToDoListAPI.service;

import com.techreturners.JavaAndSpringToDoListAPI.model.Task;
import com.techreturners.JavaAndSpringToDoListAPI.repository.TaskRepository;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.util.Lists.list;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceTests {
    private TaskService taskService;
    private TaskRepository mockRepository;

    @BeforeEach
    void setUp() {
        //Arrange
        this.mockRepository = mock(TaskRepository.class);
        this.taskService = new TaskService(mockRepository);
    }

    @Test
    public void shouldReturnAllTasksFromDB() {
        //Arrange
        var mockTasks = List.of(Task.of("Shop groceries", false), Task.of("Make payment", true));

        when(mockRepository.findAll()).thenReturn(mockTasks);

        //Act
        var tasks = taskService.viewAllTasks();

        var shoppingTask = Streams.stream(tasks).filter(t -> t.description().equals("Shop groceries")).findFirst();

        //Assert
        assertTrue(shoppingTask.isPresent());
        assertFalse(shoppingTask.get().isComplete());
    }

    @Test
    public void shouldReturnEmptyIfNoTasksPresent() {
        //Arrange
        Iterable<Task> mockTasks = list();

        when(mockRepository.findAll()).thenReturn(mockTasks);
        //Act
        var tasks = taskService.viewAllTasks();
        //Assert
        assertThat(tasks).isEmpty();
    }
}
