package com.suthinan.mysql.controller;

import com.suthinan.mysql.model.ToDoModel;
import com.suthinan.mysql.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todo")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @GetMapping("/getAllToDos")
    public ResponseEntity<?> getToDos() {
        return new ResponseEntity<>(toDoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/postToDo")
    public ResponseEntity<?> saveToDo(@Valid @RequestBody ToDoModel data) throws Exception {
        return new ResponseEntity<>(toDoService.save(data), HttpStatus.CREATED);
    }

    @PutMapping(value = {"/putToDo/{name}"})
    public ResponseEntity<?> updateToDo(@PathVariable String name, @Valid @RequestBody ToDoModel data) {
        return new ResponseEntity<>(toDoService.update(name, data), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleteToDo/", "/deleteToDo/{name}"})
    public ResponseEntity<?> updateToDo(@PathVariable(required = false) Optional<String> name) {
        return new ResponseEntity<>(toDoService.delete(name), HttpStatus.OK);
    }
}
