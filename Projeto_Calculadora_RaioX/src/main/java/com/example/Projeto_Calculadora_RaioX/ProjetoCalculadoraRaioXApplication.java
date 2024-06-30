package com.example.Projeto_Calculadora_RaioX;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.Projeto_Calculadora_RaioX.repository")
public class ProjetoCalculadoraRaioXApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoCalculadoraRaioXApplication.class, args);
	}

}
