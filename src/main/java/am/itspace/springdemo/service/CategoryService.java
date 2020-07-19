package am.itspace.springdemo.service;

import am.itspace.springdemo.model.Category;
import am.itspace.springdemo.model.Image;
import am.itspace.springdemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Value("C:\\Users\\Hov\\Desktop\\GIT JAVA\\image-gallery\\upload")
    private String uploadDir;

    public void save(Category category, MultipartFile file) throws IOException {
        String name = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(uploadDir, name);
        file.transferTo(picture);
        category.setPicUrl(name);
        categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
