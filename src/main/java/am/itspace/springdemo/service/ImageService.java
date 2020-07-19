package am.itspace.springdemo.service;

import am.itspace.springdemo.model.Category;
import am.itspace.springdemo.model.Image;
import am.itspace.springdemo.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    @Value("C:\\Users\\Hov\\Desktop\\GIT JAVA\\image-gallery\\upload")
    private String uploadDir;


    public void save(Image image, MultipartFile file) throws IOException {
        String name = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(uploadDir, name);
        file.transferTo(picture);
        image.setPicUrl(name);
        imageRepository.save(image);
    }

    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    public List<Image> findAllByCategoryId(int id) {
        return imageRepository.findAllByCategory_Id(id);
    }
}