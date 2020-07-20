package am.itspace.springdemo.controller;


import am.itspace.springdemo.model.Category;
import am.itspace.springdemo.model.Image;
import am.itspace.springdemo.service.CategoryService;
import am.itspace.springdemo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final ImageService imageService;


    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category, @RequestParam("image") MultipartFile file) throws IOException {
        categoryService.save(category, file);
        return "redirect:/admin";
    }

    @PostMapping("/addImage")
    public String addImage(@ModelAttribute Image image, @RequestParam("image") MultipartFile file) throws IOException {
        imageService.save(image, file);
        return "redirect:/admin";
    }

    @GetMapping("/deleteImage")
    public String deleteimage(@RequestParam("id") int id) {
        imageService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/deleteCategory")
    public String deleteCategory(@RequestParam("id") int id) {
        categoryService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/editCategory")
    public String editCategory(@RequestParam("id") int id, ModelMap modelMap) {
        Optional<Category> one = categoryService.findById(id);
        if (!one.isPresent()) {
            return "redirect:/";
        }

        modelMap.addAttribute("category", one.get());
        return "editCategory";
    }

    @GetMapping("/editImage")
    public String editImage(@RequestParam("id") int id, ModelMap modelMap) {
        Optional<Image> one = imageService.findById(id);
        if (!one.isPresent()) {
            return "redirect:/";
        }
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("image", one.get());
        return "editImage";
    }


}
