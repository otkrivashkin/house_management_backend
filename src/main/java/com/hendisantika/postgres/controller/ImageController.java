package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.entity.Image;
import com.hendisantika.postgres.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("images")
@CrossOrigin(origins = "http://localhost:4200")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping(consumes = "multipart/form-data", value = "/uploadImage")
    public Long uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        image.setSize(file.getSize());

        Image savedImage = imageRepository.save(image);
        return savedImage.getId();
    }

    @Transactional
    @PostMapping(consumes = "multipart/form-data", value = "/uploadImages")
    public List<Long> uploadImages(@RequestPart("files") MultipartFile[] files) throws IOException {
        List<Long> imageIds = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = new Image();
            image.setName(StringUtils.cleanPath(file.getOriginalFilename()));
            image.setContentType(file.getContentType());
            image.setData(file.getBytes());
            image.setSize(file.getSize());
            Image savedImage = imageRepository.save(image);
            imageIds.add(savedImage.getId());
        }

        return imageIds;
    }
}
