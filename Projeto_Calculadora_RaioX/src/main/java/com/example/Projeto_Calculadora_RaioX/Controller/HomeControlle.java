package com.example.Projeto_Calculadora_RaioX.Controller;

import com.example.Projeto_Calculadora_RaioX.models.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeControlle {

    @GetMapping("/home")
    public String index(Model model) {
        UserController.isLoggedAsAdmin(model);
        return "home"; // Este é o nome do arquivo HTML sem a extensão
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
}