package com.example.Projeto_Calculadora_RaioX.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @GetMapping(PATH)
    public String handleError(Model model) {
        UserController.isLoggedAsAdmin(model);
        return "error";
    }
}