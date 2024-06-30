package com.example.Projeto_Calculadora_RaioX.models.dto;

import com.example.Projeto_Calculadora_RaioX.models.types.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private UserRole roles;
}
