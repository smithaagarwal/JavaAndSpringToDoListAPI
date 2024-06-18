package com.techreturners.JavaAndSpringToDoListAPI.service;

import com.techreturners.JavaAndSpringToDoListAPI.model.Task;
import com.techreturners.JavaAndSpringToDoListAPI.repository.TaskRepository;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.Lists.list;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceTests {
    private TaskService taskService;
    private TaskRepository mockRepository;

    @BeforeEach
    void setUp() {
        //Arrange
        this.mockRepository = mock(TaskRepository.class);
        this.taskService = new TaskService(mockRepository, 10);
    }

    @Test
    public void shouldReturnAllTasksFromDB() {
        //Arrange
        var mockTasks = List.of(Task.of("Shop groceries", false), Task.of("Make payment", true));

        when(mockRepository.findAll()).thenReturn(mockTasks);

        //Act
        var tasks = taskService.getAllTasks();

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
        var tasks = taskService.getAllTasks();
        //Assert
        assertThat(tasks).isEmpty();
    }

    @Test
    public void shouldAddNewTaskToDB() {
        //Arrange
        var mockTask = Task.of("Gardening", false);
        when(mockRepository.save(mockTask)).thenReturn(mockTask);
        //Act
        var task = taskService.addTask(mockTask);
        //Assert
        assertEquals(task.description(), mockTask.description());
    }

    @Test
    public void shouldThrowExceptionForAddNewTaskIfTenOrMoreInCompleteTasksPresentInDB() {
        //Arrange
        var mockListOfExistingTask = List.of(Task.of("Shop groceries", false), Task.of("Make payment", true), Task.of("Do Task1", false), Task.of("Do Task2", true), Task.of("Do Task3", false), Task.of("Do Task4", false), Task.of("Do Task5", false), Task.of("Do Task6", false), Task.of("Do Task7", false), Task.of("Do Task8", false), Task.of("Do Task9", false), Task.of("Do Task10", false));
        var mockTaskToAdd = Task.of("Gardening", false);
        when(mockRepository.findAll()).thenReturn(mockListOfExistingTask);
        when(mockRepository.save(mockTaskToAdd)).thenReturn(mockTaskToAdd);

        //Act Assert
        var re = assertThrows(RuntimeException.class, () -> taskService.addTask(mockTaskToAdd));
        assertEquals("Please complete existing tasks before adding new ones", re.getMessage());
    }

    @Test
    public void shouldUpdateCompletedStatusOfAnExistingTask() {
        //Arrange
        var mockTaskBeforeUpdate = new Task(1L, "Cooking", false, 1);
        var mockTaskWithUpdatedDetails = new Task(1L, "Cooking", true, 1);
        when(mockRepository.findById(mockTaskBeforeUpdate.id())).thenReturn(Optional.of(mockTaskBeforeUpdate));
        when(mockRepository.save(mockTaskWithUpdatedDetails)).thenReturn(mockTaskWithUpdatedDetails);

        //Act
        var task = taskService.editTaskDetails(mockTaskBeforeUpdate.id(), mockTaskWithUpdatedDetails);

        //Assert
        assertEquals(task.isComplete(), mockTaskWithUpdatedDetails.isComplete());
    }

    @Test
    public void shouldUpdateDescriptionOfAnExistingTask() {
        //Arrange
        var mockTaskBeforeUpdate = new Task(1L, "Cooking", false, 1);
        var mockTaskWithUpdatedDetails = new Task(1L, "Bake cake", true, 1);
        when(mockRepository.findById(mockTaskBeforeUpdate.id())).thenReturn(Optional.of(mockTaskBeforeUpdate));
        when(mockRepository.save(mockTaskWithUpdatedDetails)).thenReturn(mockTaskWithUpdatedDetails);

        //Act
        var task = taskService.editTaskDetails(mockTaskBeforeUpdate.id(), mockTaskWithUpdatedDetails);

        //Assert
        assertEquals(task.description(), mockTaskWithUpdatedDetails.description());
    }

}

