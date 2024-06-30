package com.example.Projeto_Calculadora_RaioX.controller;

import com.example.Projeto_Calculadora_RaioX.models.dto.UserDto;
import com.example.Projeto_Calculadora_RaioX.models.entity.User;
import com.example.Projeto_Calculadora_RaioX.models.types.UserRole;
import com.example.Projeto_Calculadora_RaioX.service.UserService;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    static void isLoggedAsAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isMaster = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_MASTER"));

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));

            boolean isPending = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_PENDING"));

            List<UserRole> roles = Arrays.stream(UserRole.values())
                    .filter(role -> role != UserRole.MASTER)
                    .toList();

            model.addAttribute("username", username);
            model.addAttribute("isMaster", isMaster);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isUser", isUser);
            model.addAttribute("isPending", isPending);
            model.addAttribute("roles", roles);
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @ModelAttribute("user") UserDto dto) {
        try{
            dto.setRoles(UserRole.PENDING);
            User user = modelMapper.map(dto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return "redirect:/login";
        }catch (Exception ex) {
            if(ex.getMessage().toString().contains("ERRO: duplicar valor da chave")) {
                model.addAttribute("message", "Erro: Username já esta sendo utilizado!");
            }
            return "register";
        }
    }

    @GetMapping("/user-list")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        isLoggedAsAdmin(model);
        return "user-list";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @RequestParam String username, @RequestParam String role, @RequestParam(required = false) String novaSenha) {
        User user = userService.getUserById(id);

        if (user == null) {
            throw new RuntimeException("Usuario não encontrado!");
        }

        user.setUsername(username);
        user.setRoles(EnumUtils.getEnum(UserRole.class, role.toUpperCase()));

        if (novaSenha != null && !novaSenha.isEmpty()) {
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            user.setPassword(senhaCriptografada);
        }

        userService.saveUser(user);

        return "redirect:/user-list";
    }

    @PostMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            throw new RuntimeException("Usuario não encontrado!");
        }

        userService.deleteUser(id);

        return "redirect:/user-list";
    }
}