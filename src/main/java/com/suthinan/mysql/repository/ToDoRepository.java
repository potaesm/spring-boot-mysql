package com.suthinan.mysql.repository;

import com.suthinan.mysql.entity.ToDoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ToDoRepository extends CrudRepository<ToDoEntity, Long> {
    List<ToDoEntity> findByName(String name);

    @Transactional
    void deleteByName(String name);
}
