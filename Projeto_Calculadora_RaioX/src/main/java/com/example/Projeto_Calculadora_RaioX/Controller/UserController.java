package com.example.Projeto_Calculadora_RaioX.Controller;

import com.example.Projeto_Calculadora_RaioX.models.dto.UserDto;
import com.example.Projeto_Calculadora_RaioX.models.entity.User;
import com.example.Projeto_Calculadora_RaioX.repository.UserRepository;
import com.example.Projeto_Calculadora_RaioX.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto dto) {
        try{
            User user = modelMapper.map(dto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return "redirect:/login";
        }catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user-list")
    public String userListPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }
}