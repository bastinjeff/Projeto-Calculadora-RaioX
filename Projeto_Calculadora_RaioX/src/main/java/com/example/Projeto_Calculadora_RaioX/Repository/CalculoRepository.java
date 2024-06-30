package com.example.Projeto_Calculadora_RaioX.repository;

import com.example.Projeto_Calculadora_RaioX.models.entity.Calculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculoRepository extends JpaRepository<Calculo, Long> {
}
