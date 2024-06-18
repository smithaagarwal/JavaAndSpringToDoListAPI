package com.techreturners.JavaAndSpringToDoListAPI.repository;

import com.techreturners.JavaAndSpringToDoListAPI.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
