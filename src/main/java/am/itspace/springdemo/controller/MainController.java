package am.itspace.springdemo.controller;


import am.itspace.springdemo.model.Category;
import am.itspace.springdemo.model.Image;
import am.itspace.springdemo.service.CategoryService;
import am.itspace.springdemo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {


    private final ImageService imageService;
    private final CategoryService categoryService;
    @Value("${file.upload.dir}")
    private String uploadDir;

    @GetMapping("/")
    public String homepage(Model model) {
        List<Image> images = imageService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("images", images);
        model.addAttribute("categories", categories);
        return "index";

    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Image> images = imageService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("images", images);
        return "adminPage";
    }

    @GetMapping("/categoryImage")
    public String categoryImage(Model model, @RequestParam("id") int id) {
        List<Image> images = imageService.findAllByCategoryId(id);
        model.addAttribute("images", images);
        return "imagePage";
    }

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )

    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }


}
