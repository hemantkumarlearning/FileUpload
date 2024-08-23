package com.hemant.controller;

import com.hemant.entity.imageData;
import com.hemant.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {

     //   boolean uploadFile = imageService.uploadFile(file);
        String imageData = imageService.uploadImage(file);
        return ResponseEntity.ok("Uploaded file successfully");
    }

    

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){

        byte[] imageData = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @PostMapping("/fileSystem")
    public ResponseEntity<?> uploadImageToSystem(@RequestParam("image")MultipartFile file) throws IOException {

        //   boolean uploadFile = imageService.uploadFile(file);
        String imageData = imageService.uploadImageToFileSystem(file);
        return ResponseEntity.ok("Uploaded file successfully");
    }



    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromSystem(@PathVariable String fileName) throws IOException {

        byte[] imageData = imageService.downloadImageFromSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }





}

