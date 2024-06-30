package com.example.Projeto_Calculadora_RaioX.Controller;

import com.example.Projeto_Calculadora_RaioX.Model.Calculo;
import com.example.Projeto_Calculadora_RaioX.Repository.CalculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        UserController.isLoggedAsAdmin(model);
        model.addAttribute("calculo", new Calculo());
        return "form";
    }

    @PostMapping("/calcular")
    public String calcular(@ModelAttribute Calculo calculo, Model model) {
        try {
            calculo.Calcular();

            calculoRepository.save(calculo);

            List<Calculo> calculos = calculoRepository.findAll();

            model.addAttribute("calculo", calculo);
            model.addAttribute("calculos", calculos);
            UserController.isLoggedAsAdmin(model);

            return "resultados";
        }catch (Exception e) {
            logger.error("Erro ao calcular os valores: " + e.getMessage(), e);
            model.addAttribute("error", "Ocorreu um erro ao processar o cálculo. Por favor, tente novamente.");
            return "error";
        }

    }

    @GetMapping("/logCalculo")
    public String showLogCalculo (Model model,Calculo calculo){
        List<Calculo> calculos = calculoRepository.findAll();
        model.addAttribute("calculos", calculos);
        UserController.isLoggedAsAdmin(model);
        return "logCalculo";
    }

    @GetMapping("jafeitos")
    public String showResultados(Model model){

        List<Calculo> calculos = calculoRepository.findAll();
        model.addAttribute("calculos", calculos);
        UserController.isLoggedAsAdmin(model);

        return "resultados";
    }
}
