package com.example.Projeto_Calculadora_RaioX.Controller;

import com.example.Projeto_Calculadora_RaioX.Model.Calculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/calculos")
public class CalculoController {

    @GetMapping("/calcular")
    public String showForm(Model model) {
        model.addAttribute("calculo", new Calculo());
        return "form";
    }

    @PostMapping("/calcular")
    public String calcular(@ModelAttribute Calculo calculo, Model model) {
        calculo.Calcular();

        return "form";
    }
}
