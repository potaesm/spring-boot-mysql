package com.suthinan.mysql.service;

import com.suthinan.mysql.dto.ToDoDto;
import com.suthinan.mysql.entity.ToDoEntity;
import com.suthinan.mysql.model.ToDoModel;

import java.util.List;

public interface ToDoService {
    public List<ToDoDto> findAll();
    public ToDoDto save(ToDoModel data);
    public List<ToDoDto> ToDoEntityToDto(List<ToDoEntity> toDoEntities);
    public List<ToDoEntity> DtoToToDoEntity(List<ToDoDto> toDoDtos);
}
