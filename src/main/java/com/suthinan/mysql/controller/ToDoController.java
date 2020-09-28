package com.suthinan.mysql.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.suthinan.mysql.config.rest.FileDownloadRestTemplate;
import com.suthinan.mysql.config.rest.JsonPlaceHolderRestTemplate;
import com.suthinan.mysql.model.ToDoModel;
import com.suthinan.mysql.service.DownloadFileService;
import com.suthinan.mysql.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todo")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @Autowired
    private DownloadFileService downloadFileService;

    @GetMapping("/getPdf")
    public ResponseEntity<?> downloadFile(@RequestParam(name = "url") String url, @RequestParam(name = "file_name", required = false, defaultValue = "") String fileName) throws IOException {
        if (fileName.equalsIgnoreCase("")) {
            fileName = url.substring(url.lastIndexOf('/') + 1);
        }
        ByteArrayResource resource = downloadFileService.getResource(url, fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(resource.contentLength()));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @GetMapping("/getToDosJsonPlaceHolder")
    public ResponseEntity<?> getToDosFromJsonPlaceHolder() throws JsonProcessingException {
        return new ResponseEntity<>(toDoService.findFromJsonPlaceHolder(), HttpStatus.OK);
    }

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
