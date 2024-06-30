package com.example.Projeto_Calculadora_RaioX.config.init;

import com.example.Projeto_Calculadora_RaioX.models.entity.User;
import com.example.Projeto_Calculadora_RaioX.models.types.UserRole;
import com.example.Projeto_Calculadora_RaioX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataLoader is running");
        Optional<User> user = userRepository.findByUsername("admin");
        if (user.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            admin.setRoles(UserRole.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user created.");
        }
    }

}