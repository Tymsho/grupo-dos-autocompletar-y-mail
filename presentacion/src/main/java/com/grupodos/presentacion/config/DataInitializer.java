package com.grupodos.presentacion.config;

import com.grupodos.presentacion.model.Client;
import com.grupodos.presentacion.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository) {
        return args -> {
            if (clientRepository.count() == 0) {
                clientRepository.save(new Client("Juan Perez", "juan.perez@example.com", "ACTIVE"));
                clientRepository.save(new Client("Maria Gomez", "maria.gomez@example.com", "INACTIVE"));
                clientRepository.save(new Client("Carlos Sanchez", "carlos.sanchez@example.com", "ACTIVE"));
                clientRepository.save(new Client("Ana Lopez", "ana.lopez@example.com", "PENDING"));
                clientRepository.save(new Client("Lucia Fernandez", "lucia.fernandez@example.com", "ACTIVE"));
                clientRepository.save(new Client("Marcos Ruiz", "marcos.ruiz@example.com", "INACTIVE"));
                
                System.out.println("Base de datos inicializada con 6 clientes de prueba.");
            }
        };
    }
}
