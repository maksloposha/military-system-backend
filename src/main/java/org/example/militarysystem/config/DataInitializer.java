package org.example.militarysystem.config;

import org.example.militarysystem.model.Role;
import org.example.militarysystem.model.User;
import org.example.militarysystem.repository.RoleRepository;
import org.example.militarysystem.repository.UserRepository;
import org.example.militarysystem.utils.userUtils.UserStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository,
                                       RoleRepository roleRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername(adminUsername)) {
                Role adminRole = roleRepository.findByRoleName("ADMIN")
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setRoleName("ADMIN");
                            return roleRepository.save(newRole);
                        });

                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setStatus(UserStatus.APPROVED);
                admin.setRole(adminRole);

                userRepository.save(admin);
                System.out.println("✅ Адміністратора створено");
            } else {
                System.out.println("ℹ️ Адміністратор вже існує");
            }
        };
    }
}
