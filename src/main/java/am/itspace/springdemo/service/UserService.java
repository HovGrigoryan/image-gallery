package am.itspace.springdemo.service;

import am.itspace.springdemo.model.User;
import am.itspace.springdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    @Value("${file.upload.dir}")
    private String uploadDir;

    public void save(User user, MultipartFile file) throws IOException {
        String name = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(uploadDir, name);
        file.transferTo(image);
        user.setProfilePic(name);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        String link = "http://localhost:8080/activate?email=" + user.getUsername() + "&token=" + user.getToken();
        emailService.send(user.getUsername(),
                "Welcome", "Dear " + user.getName() + " You have successfully registered.Please activate your account by clicking on: " + link);

    }


    public User getOne(int id) {
        return userRepository.getOne(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

}
