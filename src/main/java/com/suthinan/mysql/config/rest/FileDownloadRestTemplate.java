package com.suthinan.mysql.config.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suthinan.mysql.dto.ToDoDto;
import com.suthinan.mysql.model.JsonPlaceHolderModel;
import com.suthinan.mysql.model.ToDoModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class FileDownloadRestTemplate {

    public ResponseEntity<?> downloadFile() throws IOException {
        // Allianz Proxy
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.186.208.15", 8080));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        // Rest Template With Proxy
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        // Rest Template Without Proxy
        // RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        String url = "https://github.com/potaesm/course-technical-2020/raw/master/docker/DOCKER-WORKSHOP.pdf";
        String fileName = url.substring(url.lastIndexOf('/') + 1);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Path filePath = Files.write(Paths.get(fileName), Objects.requireNonNull(response.getBody()));
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(resource.contentLength()));
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            return ResponseEntity.ok().headers(headers).body(resource);
        } else {
            return new ResponseEntity<>("NOT FOUND", response.getStatusCode());
        }
    }
}
