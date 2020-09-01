package com.suthinan.mysql.service;

import com.suthinan.mysql.dto.ToDoDto;
import com.suthinan.mysql.entity.ToDoEntity;
import com.suthinan.mysql.model.ToDoModel;

import java.util.List;
import java.util.Optional;

public interface ToDoService {
    List<ToDoDto> findAll();
    ToDoDto save(ToDoModel data) throws Exception;
    ToDoDto update(String name, ToDoModel data);
    List<ToDoDto> ToDoEntityToDto(List<ToDoEntity> toDoEntities);
    List<ToDoEntity> DtoToToDoEntity(List<ToDoDto> toDoDtos);
    String delete(Optional<String> name);
}
