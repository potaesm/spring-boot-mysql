package com.suthinan.mysql.repository;

import com.suthinan.mysql.entity.ToDoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToDoRepository extends CrudRepository<ToDoEntity, Long> {
    public List<ToDoEntity> findByName(String name);
}
