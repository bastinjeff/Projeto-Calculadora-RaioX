package com.example.Projeto_Calculadora_RaioX.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        String errorMessage = (String) request.getSession().getAttribute("error");
        if (errorMessage != null) {
            model.addAttribute("message", errorMessage);
            request.getSession().removeAttribute("error");
        }
        return "login";
    }
}
