package com.example.Projeto_Calculadora_RaioX.Controller;

import com.example.Projeto_Calculadora_RaioX.Model.Calculo;
import com.example.Projeto_Calculadora_RaioX.Repository.CalculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/calculos")
public class CalculoController {

    @Autowired
    private CalculoRepository calculoRepository;

    @GetMapping("/calcular")
    public String showForm(Model model) {
        model.addAttribute("calculo", new Calculo());
        return "form";
    }

    @PostMapping("/calcular")
    public String calcular(@ModelAttribute Calculo calculo, Model model) {
        calculo.Calcular();

        calculoRepository.save(calculo);

        List<Calculo> calculos = calculoRepository.findAll();

        model.addAttribute("calculo", calculo);
        model.addAttribute("calculos", calculos);

        return "resultados";
    }

    @GetMapping("/")
    public String showHome (){
        return "home";
    }

    @GetMapping("jafeitos")
    public String showResultados(){
        return "resultados";
    }
}
