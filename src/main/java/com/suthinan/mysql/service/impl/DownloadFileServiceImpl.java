package com.suthinan.mysql.service.impl;

import com.suthinan.mysql.config.rest.AllianzRestTemplate;
import com.suthinan.mysql.service.DownloadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;

@Service
public class DownloadFileServiceImpl implements DownloadFileService {
    @Autowired
    AllianzRestTemplate allianzRestTemplate;

    @Override
    public ByteArrayResource getResource(String url, String fileName) throws IOException {
        RestTemplate restTemplate = allianzRestTemplate.aztechRemoteRestTemplate();
        // RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Save file to local machine
            // Path filePath = Files.write(Paths.get(fileName), Objects.requireNonNull(response.getBody()));
            // ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));

            // Send stream to response directly
            ByteArrayResource resource = new ByteArrayResource(Objects.requireNonNull(response.getBody()));
            return resource;
        } else {
            return new ByteArrayResource("".getBytes());
        }
    }
}
