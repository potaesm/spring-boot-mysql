package com.suthinan.mysql.service;

import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public interface DownloadFileService {
    ByteArrayResource getResource(String url, String fileName) throws IOException;
}
