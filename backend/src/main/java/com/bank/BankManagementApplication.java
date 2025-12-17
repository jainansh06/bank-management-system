package com.bank;  // ← CRITICAL: Must match folder structure!

import com.bank.model.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BankManagementApplication.class, args);
        System.out.println("✅ Bank Management System Started!");
    }
    
    @Bean
    @Autowired
    public CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            // Create admin user
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", "admin123", "ADMIN", 
                    "admin@bank.com", "Bank Admin");
                userRepository.save(admin);
                System.out.println("✓ Admin user created");
            }
            
            // Create sample customer
            if (userRepository.findByUsername("customer1").isEmpty()) {
                User customer = new User("customer1", "customer123", "CUSTOMER", 
                    "customer@bank.com", "John Doe");
                userRepository.save(customer);
                System.out.println("✓ Sample customer created");
            }
        };
    }
}