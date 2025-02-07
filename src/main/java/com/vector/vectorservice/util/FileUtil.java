package com.vector.vectorservice.util;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUtil {

    @Value("${vector-service.picture.upload.directory}")
    private String uploadPath;

    public byte[] getFile(String fileName) throws IOException {
        File file = new File(uploadPath + fileName);
        if (file.exists()) {
            try (InputStream in = new FileInputStream(file)) {
                return IOUtils.toByteArray(in);
            }
        }
        return null;
    }
    public String saveImage(MultipartFile multipartFile) throws IOException {
        String imageName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File file = new File(uploadPath, imageName);
        multipartFile.transferTo(file);
        return imageName;
    }
    }

