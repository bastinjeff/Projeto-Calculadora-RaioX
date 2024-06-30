package com.example.Projeto_Calculadora_RaioX.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception.getMessage().contains("User is disabled")) {
            request.getSession().setAttribute("error", "Erro: Sua conta está pendente de aprovação.");
        } else if (exception.getMessage().contains("Bad credentials")) {
            request.getSession().setAttribute("error", "Erro: Usuário não encontrado ou senha incorreta.");
        }
        response.sendRedirect("/api/login");
    }
}
