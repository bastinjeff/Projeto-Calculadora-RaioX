package com.example.Projeto_Calculadora_RaioX.controller;

import com.example.Projeto_Calculadora_RaioX.models.entity.Calculo;
import com.example.Projeto_Calculadora_RaioX.repository.CalculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/calculos")
public class CalculoController {

    private static final Logger logger = LoggerFactory.getLogger(CalculoController.class);

    @Autowired
    private CalculoRepository calculoRepository;

    @GetMapping("/calcular")
    public String showForm(Model model) {
        try{
            UserController.isLoggedAsAdmin(model);
            model.addAttribute("calculo", new Calculo());
            return "form";
        }catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/calcular")
    public String calcular(@ModelAttribute Calculo calculo, Model model) {
        UserController.isLoggedAsAdmin(model);
        try {
            logger.info("Starting calculation for: " + calculo);
            calculo.Calcular();

            logger.info("Calculation completed, saving to repository.");
            calculoRepository.save(calculo);

            List<Calculo> calculos = calculoRepository.findAll();

            model.addAttribute("calculo", calculo);
            model.addAttribute("calculos", calculos);

            logger.info("Calculation and data handling successful, returning 'resultados' view.");

            return "resultados";
        }catch (Exception e) {
            logger.error("Erro ao calcular os valores: " + e.getMessage(), e);
            model.addAttribute("message", e.getMessage());
            return "error";
        }

    }

    @GetMapping("/logCalculo")
    public String showLogCalculo (Model model,Calculo calculo){
        UserController.isLoggedAsAdmin(model);
        try{
            List<Calculo> calculos = calculoRepository.findAll();
            model.addAttribute("calculos", calculos);
            return "logCalculo";
        }catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("jafeitos")
    public String showResultados(Model model){
        UserController.isLoggedAsAdmin(model);
        try {
            List<Calculo> calculos = calculoRepository.findAll();
            model.addAttribute("calculos", calculos);
            UserController.isLoggedAsAdmin(model);

            return "resultados";
        }catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }
}
