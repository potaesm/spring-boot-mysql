package com.suthinan.mysql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suthinan.mysql.dto.ToDoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoServiceIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Integration test of the ToDoService")
    void toDoTest() throws MalformedURLException, JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/v1/todo/getAllToDos").toString(), String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<ToDoDto> toDoDtoList = Arrays.asList(mapper.readValue(response.getBody(), ToDoDto[].class));
        assertEquals("Eat lunch", toDoDtoList.get(0).getToDo().getTitle());
    }
}
