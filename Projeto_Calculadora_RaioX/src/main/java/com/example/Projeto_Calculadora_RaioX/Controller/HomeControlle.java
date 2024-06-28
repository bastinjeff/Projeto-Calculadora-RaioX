package com.example.Projeto_Calculadora_RaioX.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControlle {

    @GetMapping("/")
    public String index() {
        return "home"; // O nome do arquivo HTML sem a extensão
    }
}