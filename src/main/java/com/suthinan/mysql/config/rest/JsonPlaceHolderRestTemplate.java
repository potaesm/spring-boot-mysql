package com.suthinan.mysql.config.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suthinan.mysql.dto.ToDoDto;
import com.suthinan.mysql.model.JsonPlaceHolderModel;
import com.suthinan.mysql.model.ToDoModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.net.Proxy.Type;

@Configuration
public class JsonPlaceHolderRestTemplate {
    @Bean
    public ResponseEntity<?> getToDosFromJsonPlaceHolder() throws JsonProcessingException {
        // Allianz Proxy
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("10.186.208.15", 8080));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        // Rest Template With Proxy
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        // Rest Template Without Proxy
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
        return new ResponseEntity<>(toDoDtos, response.getStatusCode());
    }
}
