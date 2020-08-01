package am.itspace.springdemo.controller;


import am.itspace.springdemo.model.Category;
import am.itspace.springdemo.model.Image;
import am.itspace.springdemo.model.User;
import am.itspace.springdemo.repository.UserRepository;
import am.itspace.springdemo.service.CategoryService;
import am.itspace.springdemo.service.EmailService;
import am.itspace.springdemo.service.ImageService;
import am.itspace.springdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final EmailService emailService;
    private final UserService userService;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;
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
        List<User> users = userService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("images", images);
        model.addAttribute("users", users);
        return "adminPage";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {
        userService.save(user, file);
        return "redirect:/admin";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("email") String email, @RequestParam("token") String token) {
        emailService.activate(email, token);
        return "redirect:/";
    }


    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/categoryImage")
    public String categoryImage(Model model, @RequestParam("id") int id) {
        List<Image> images = imageService.findAllByCategoryId(id);
        model.addAttribute("images", images);
        return "imagePage";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
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
