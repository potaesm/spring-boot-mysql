package com.suthinan.mysql;

import com.suthinan.mysql.entity.ToDoEntity;
import com.suthinan.mysql.repository.ToDoRepository;
import com.suthinan.mysql.service.ToDoService;
import com.suthinan.mysql.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToDoServiceTest with JUnit 5 + Mockito")
public class ToDoServiceTest {
    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    @BeforeEach
    void setUp() {
        ToDoEntity toDoEntity = new ToDoEntity(0, "Hello World", "Detail message", "Suthinan", null);
        List<ToDoEntity> toDoEntities = new ArrayList<>();
        toDoEntities.add(toDoEntity);
        when((List<ToDoEntity>) toDoRepository.findAll()).thenReturn(toDoEntities);
    }

    @Test
    @DisplayName("Mock output of the ToDoService using mockito")
    void toDoTest() {
        ToDoEntity toDoEntity = new ToDoEntity(0, "Hello World", "Detail message", "Suthinan", null);
        List<ToDoEntity> toDoEntities = new ArrayList<>();
        toDoEntities.add(toDoEntity);
        assertEquals(toDoEntities, toDoService.DtoToToDoEntity(toDoService.findAll()));
    }
}
