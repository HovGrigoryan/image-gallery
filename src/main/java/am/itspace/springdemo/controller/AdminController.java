package am.itspace.springdemo.controller;


import am.itspace.springdemo.model.Category;
import am.itspace.springdemo.model.Image;
import am.itspace.springdemo.service.CategoryService;
import am.itspace.springdemo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final ImageService imageService;


    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category, @RequestParam("image") MultipartFile file) throws IOException {
        categoryService.save(category, file);
        return "redirect:/";
    }

    @PostMapping("/addImage")
    public String addImage(@ModelAttribute Image image, @RequestParam("image") MultipartFile file) throws IOException {
        imageService.save(image, file);
        return "redirect:/";
    }



}
