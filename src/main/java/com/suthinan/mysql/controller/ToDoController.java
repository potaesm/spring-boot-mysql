package com.suthinan.mysql.controller;

import com.suthinan.mysql.model.ToDoModel;
import com.suthinan.mysql.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todo")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @GetMapping("")
    public ResponseEntity<?> getToDos() {
        return new ResponseEntity<>(toDoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoModel data) {
        return new ResponseEntity<>(toDoService.save(data), HttpStatus.CREATED);
    }
}
