package com.suthinan.mysql.config.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suthinan.mysql.dto.ToDoDto;
import com.suthinan.mysql.model.JsonPlaceHolderModel;
import com.suthinan.mysql.model.ToDoModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
public class JsonPlaceHolderRestTemplate {
    @Bean
    public ResponseEntity<?> getToDosFromJsonPlaceHolder() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://jsonplaceholder.typicode.com/todos", String.class);
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
        return new ResponseEntity<>(toDoDtos, response.getStatusCode());
    }
}
