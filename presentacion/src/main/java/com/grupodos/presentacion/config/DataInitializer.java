package com.grupodos.presentacion.config;

import com.grupodos.presentacion.model.Client;
import com.grupodos.presentacion.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración encargada de poblar la base de datos en memoria (H2)
 * con datos iniciales cada vez que la aplicación arranca.
 */
@Configuration
public class DataInitializer {

    /**
     * Bean de CommandLineRunner que se ejecuta automáticamente al iniciar Spring Boot.
     *
     * @param clientRepository El repositorio para realizar el guardado en la BD.
     * @return Una función que inserta los clientes si la tabla está vacía.
     */
    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository) {
        return args -> {
            // Solo insertamos datos si la tabla de clientes está vacía
            if (clientRepository.count() == 0) {
                clientRepository.save(new Client("Juan Perez", "juan.perez@example.com", "ACTIVE"));
                clientRepository.save(new Client("Maria Gomez", "maria.gomez@example.com", "INACTIVE"));
                clientRepository.save(new Client("Carlos Sanchez", "carlos.sanchez@example.com", "ACTIVE"));
                clientRepository.save(new Client("Ana Lopez", "ana.lopez@example.com", "PENDING"));
                clientRepository.save(new Client("Lucia Fernandez", "lucia.fernandez@example.com", "ACTIVE"));
                clientRepository.save(new Client("Marcos Ruiz", "marcos.ruiz@example.com", "INACTIVE"));
                clientRepository.save(new Client("Gonzalo Crespo", "gonzalocrespo02@gmail.com", "ACTIVE"));

                System.out.println("Base de datos inicializada con 7 clientes de prueba.");
            }
        };
    }
}
