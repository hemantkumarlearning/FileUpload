package com.hemant.service;

import com.hemant.entity.FileData;
import com.hemant.entity.imageData;
import com.hemant.repository.FileRepo;
import com.hemant.repository.ImageRepo;
import com.hemant.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {

    public final String IMG_URL = "E:\\images/";

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private FileRepo fileRepo;

    public boolean uploadFile(MultipartFile file) {
        File dir = new File(IMG_URL);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(IMG_URL + File.separator + file.getOriginalFilename()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {

        imageData image = imageRepo.save(imageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .data(ImageUtils.compressImage(file.getBytes()))
                .build());
        if (image != null) {
            return "Image uploaded successfully" + file.getOriginalFilename();
        } else {
            return null;
        }

//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        imageData FileDB = new imageData(fileName, file.getContentType(), file.getBytes());
//
//        return imageRepo.save(FileDB);
    }

    public byte[] downloadImage(String fileName) {
        Optional<imageData> imageData = imageRepo.findByName(fileName);
        byte[] image = ImageUtils.decompressImage(imageData.get().getData());
        return image;

    }

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {

        String path = IMG_URL + file.getOriginalFilename();
        FileData fileData = fileRepo.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(path)
                .build());
        file.transferTo(new File(path));

        if (fileData != null) {
            return "Image uploaded successfully" + path;
        } else {
            return null;
        }
    }

    public byte[] downloadImageFromSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileRepo.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;

    }
}