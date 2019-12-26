package com.demo.controller;

import com.demo.model.ImageModel;
import com.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "check")
public class TestController {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    Environment evm;

    @PostMapping("/upload")
    public ImageModel uploadImage(@RequestParam("myFile") MultipartFile file) throws IOException {
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        final ImageModel savedImage = imageRepository.save(img);
        doUpload(Optional.of(file), savedImage);
        return savedImage;

    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageModel>> getImgList() {
        List<ImageModel> images = imageRepository.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    private void doUpload(Optional<MultipartFile> file, ImageModel imageModel) throws IOException {
        if (file.isPresent()) {
            String fileUpload = evm.getProperty("uploadPath").toString();
            String fileName = file.get().getOriginalFilename();
            String fileType = file.get().getContentType();
            byte[] filePics = file.get().getBytes();
            if (!fileName.equals("")) {
                try {
                    FileCopyUtils.copy(file.get().getBytes(), new File(fileUpload + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imageModel.setName(fileName);
                imageModel.setType(fileType);
                imageModel.setImgURL("http://localhost:8080/image/" + fileName);
                imageModel.setPic(filePics);
            }
        }
        imageRepository.save(imageModel);
    }

}
