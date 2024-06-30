package com.example.Projeto_Calculadora_RaioX.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @GetMapping(PATH)
    public String handleError() {
        return "error"; // nome do template de erro
    }
}