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
            User master = new User();
            master.setUsername("master");
            master.setPassword(passwordEncoder.encode("master2024"));
            master.setRoles(UserRole.MASTER);

            System.out.println("Master created.");
            userRepository.save(master);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin2024"));
            admin.setRoles(UserRole.ADMIN);
            System.out.println("Admin created.");

            userRepository.save(admin);

            User newUser = new User();
            newUser.setUsername("user");
            newUser.setPassword(passwordEncoder.encode("user2024"));
            newUser.setRoles(UserRole.USER);
            System.out.println("User created.");

            userRepository.save(newUser);

            User pending = new User();
            pending.setUsername("pending");
            pending.setPassword(passwordEncoder.encode("pending2024"));
            pending.setRoles(UserRole.PENDING);
            System.out.println("Pending created.");

            userRepository.save(pending);
        }
    }

}