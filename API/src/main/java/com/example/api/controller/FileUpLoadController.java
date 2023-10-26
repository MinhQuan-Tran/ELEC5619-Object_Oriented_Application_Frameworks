package com.example.api.controller;

import com.example.api.model.Result;
import jakarta.annotation.Resource;
import lombok.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class FileUpLoadController {

    @PostMapping("/upload")
    public ResponseEntity<Result> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir: " + currentDir);
        Result result = new Result();
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") &&
                !contentType.equals("image/gif") && !contentType.equals("image/bmp") &&
                !contentType.equals("image/tiff") && !contentType.equals("image/svg+xml") &&
                !contentType.equals("image/webp")) {
            result.setResultFailed(8);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }

        String originalFilename = file.getOriginalFilename();
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        int dotIndex = originalFilename.lastIndexOf('.');
        String baseName = (dotIndex == -1) ? originalFilename : originalFilename.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "" : originalFilename.substring(dotIndex + 1);
//        String newFilename = URLEncoder.encode(baseName, "UTF-8") + "_" + timestamp + "." + extension;
        String newFilename = Base64.getEncoder().encodeToString(baseName.getBytes(StandardCharsets.UTF_8))+ "_" + timestamp + "." + extension;
        Path path = Paths.get("image/" + newFilename);
        Files.createDirectories(path.getParent());
        file.transferTo(path);
        String fileUrl = "http:localhost:8082/image/" + newFilename;
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("imageURL",fileUrl);
        result.setResultSuccess(0,resultMap);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
