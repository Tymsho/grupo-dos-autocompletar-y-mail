package com.grupodos.presentacion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa a un Cliente en la base de datos.
 * Utiliza JPA para mapearse a la tabla "Client" en H2.
 */
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único autogenerado
    
    private String name;   // Nombre completo del cliente
    private String email;  // Correo electrónico de contacto
    private String status; // Estado del cliente (ej. ACTIVE, INACTIVE)

    // Constructor vacío requerido por JPA
    public Client() {}

    /**
     * Constructor con parámetros para facilitar la inicialización.
     */
    public Client(String name, String email, String status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }

    // Getters y Setters para acceder y modificar los datos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
