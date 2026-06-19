package com.grupodos.presentacion.repository;

import com.grupodos.presentacion.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio de Spring Data JPA para la entidad Client.
 * Nos provee automáticamente de métodos para guardar, eliminar y buscar.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    /**
     * Busca clientes cuyo nombre contenga el texto ingresado, ignorando mayúsculas/minúsculas.
     * Ideal para un autocompletado en el frontend.
     * 
     * @param name Texto parcial a buscar en el nombre del cliente.
     * @return Lista de clientes que coinciden.
     */
    List<Client> findByNameContainingIgnoreCase(String name);
}
