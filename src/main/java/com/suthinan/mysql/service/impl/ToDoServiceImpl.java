package com.suthinan.mysql.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suthinan.mysql.config.rest.AllianzRestTemplateConfig;
import com.suthinan.mysql.dto.ToDoDto;
import com.suthinan.mysql.entity.ToDoEntity;
import com.suthinan.mysql.model.JsonPlaceHolderModel;
import com.suthinan.mysql.model.ToDoModel;
import com.suthinan.mysql.repository.ToDoRepository;
import com.suthinan.mysql.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ToDoServiceImpl implements ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    @Qualifier("aztechRemoteRestTemplate")
    @Autowired
    RestTemplate aztechRemoteRestTemplate;

    @Override
    public List<ToDoDto> findAll() {
        List<ToDoEntity> toDos = (List<ToDoEntity>) toDoRepository.findAll();
        return this.ToDoEntityToDto(toDos);
    }

    @Override
    public List<ToDoDto> findFromJsonPlaceHolder() throws JsonProcessingException {
        RestTemplate restTemplate = aztechRemoteRestTemplate;
        // RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://jsonplaceholder.typicode.com/todos", String.class);
        // Mapper
        ObjectMapper mapper = new ObjectMapper();
        List<JsonPlaceHolderModel> todosJsonPlaceHolderList = Arrays.asList(mapper.readValue(response.getBody(), JsonPlaceHolderModel[].class));
        List<ToDoDto> toDoDtos = new ArrayList<>();
        todosJsonPlaceHolderList.forEach((toDo) -> {
            ToDoModel toDoModel = new ToDoModel();
            toDoModel.setTitle(toDo.getTitle());
            toDoModel.setDetail("completed: " + toDo.isCompleted());
            toDoModel.setName("userId: " + toDo.getUserId());
            ToDoDto toDoDto = new ToDoDto(toDoModel, new Date());
            toDoDtos.add(toDoDto);
        });
        return toDoDtos;
    }

    @Override
    public ToDoDto save(ToDoModel data) throws Exception {
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setToDo(data);
        toDoDto.setDate(new Date());
        List<ToDoDto> toDoDtos = new ArrayList<>();
        toDoDtos.add(toDoDto);
        List<ToDoEntity> toDoEntities = this.DtoToToDoEntity(toDoDtos);
        try {
            ToDoEntity toDoEntity = toDoRepository.save(toDoEntities.get(0));
            List<ToDoEntity> toDos = new ArrayList<>();
            toDos.add(toDoEntity);
            return this.ToDoEntityToDto(toDos).get(0);
        } catch (Exception e) {
            throw new Exception("Duplicate data");
        }
    }

    @Override
    public ToDoDto update(String name, ToDoModel data) {
        List<ToDoEntity> oldToDoEntities = toDoRepository.findByName(name);
        ToDoDto oldToDoDto = this.ToDoEntityToDto(oldToDoEntities).get(0);
        oldToDoDto.setToDo(data);
        oldToDoDto.setDate(new Date());
        List<ToDoDto> toDoDtos = new ArrayList<>();
        toDoDtos.add(oldToDoDto);
        List<ToDoEntity> toDoEntities = this.DtoToToDoEntity(toDoDtos);
        toDoEntities.get(0).setId(oldToDoEntities.get(0).getId());
        ToDoEntity toDoEntity = toDoRepository.save(toDoEntities.get(0));
        List<ToDoEntity> toDos = new ArrayList<>();
        toDos.add(toDoEntity);
        return this.ToDoEntityToDto(toDos).get(0);
    }

    @Override
    public String delete(Optional<String> name) {
        if (name.orElse("").equals("")) {
            toDoRepository.deleteAll();
            return "Deleted all";
        } else {
            toDoRepository.deleteByName(name.get());
            return "Deleted " + name.get();
        }
    }

    @Override
    public List<ToDoDto> ToDoEntityToDto(List<ToDoEntity> toDoEntities) {
        List<ToDoDto> toDoDtos = new ArrayList<>();
        toDoEntities.forEach((toDo) -> {
            ToDoModel toDoModel = new ToDoModel();
            toDoModel.setTitle(toDo.getTitle());
            toDoModel.setDetail(toDo.getDetail());
            toDoModel.setName(toDo.getName());
            ToDoDto toDoDto = new ToDoDto(toDoModel, toDo.getDate());
            toDoDtos.add(toDoDto);
        });
        return toDoDtos;
    }

    @Override
    public List<ToDoEntity> DtoToToDoEntity(List<ToDoDto> toDoDtos) {
        List<ToDoEntity> toDoEntities = new ArrayList<>();
        toDoDtos.forEach((toDo) -> {
            ToDoModel toDoModel = toDo.getToDo();
            ToDoEntity toDoEntity = new ToDoEntity();
            toDoEntity.setTitle(toDoModel.getTitle());
            toDoEntity.setDetail(toDoModel.getDetail());
            toDoEntity.setName(toDoModel.getName());
            toDoEntity.setDate(toDo.getDate());
            toDoEntities.add(toDoEntity);
        });
        return toDoEntities;
    }
}
