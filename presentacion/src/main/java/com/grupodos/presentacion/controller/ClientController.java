package com.grupodos.presentacion.controller;

import com.grupodos.presentacion.dto.NotifyRequestDTO;
import com.grupodos.presentacion.model.Client;
import com.grupodos.presentacion.repository.ClientRepository;
import com.grupodos.presentacion.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST que expone los endpoints de la API para gestionar clientes.
 */
@RestController
@RequestMapping("/api/clients")
@CrossOrigin("*") // Permite peticiones desde cualquier origen (útil para desarrollo frontend local)
public class ClientController {

    private final ClientRepository clientRepository;
    private final EmailService emailService;

    // Inyección de dependencias mediante constructor
    public ClientController(ClientRepository clientRepository, EmailService emailService) {
        this.clientRepository = clientRepository;
        this.emailService = emailService;
    }

    /**
     * Endpoint para buscar clientes. Se usa para la función de autocompletar.
     * GET /api/clients/search?q={texto}
     *
     * @param q El texto parcial a buscar (opcional).
     * @return Lista de clientes coincidentes. Si no hay búsqueda, retorna hasta 10 resultados.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(@RequestParam(required = false, defaultValue = "") String q) {
        List<Client> results;
        
        // Si no mandan nada a buscar, devolvemos los primeros 10 para mostrar una lista inicial
        if (q == null || q.trim().isEmpty()) {
            results = clientRepository.findAll().stream().limit(10).collect(Collectors.toList());
        } else {
            // Utilizamos la query de autocompletado en la BD ignorando mayúsculas/minúsculas
            results = clientRepository.findByNameContainingIgnoreCase(q);
        }
        
        return ResponseEntity.ok(results);
    }

    /**
     * Endpoint para notificar (enviar un correo) a un cliente en particular.
     * POST /api/clients/{id}/notify
     *
     * @param id Identificador del cliente.
     * @param request Objeto JSON con "subject" y "body" para el correo.
     * @return 202 Accepted de inmediato (ya que el correo se envía de forma asíncrona).
     */
    @PostMapping("/{id}/notify")
    public ResponseEntity<Void> notifyClient(@PathVariable Long id, @RequestBody NotifyRequestDTO request) {
        // Buscamos si el cliente existe en la base de datos
        Optional<Client> clientOpt = clientRepository.findById(id);
        
        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found si no existe
        }

        Client client = clientOpt.get();
        
        // Llamada al método asíncrono para enviar el correo.
        // Al tener @Async en el servicio, esto NO bloqueará el hilo del controlador.
        emailService.sendEmail(client.getEmail(), request.getSubject(), request.getBody());
        
        // Retorna HTTP 202 (Accepted) inmediatamente, indicando que la solicitud fue recibida 
        // y se procesará en segundo plano.
        return ResponseEntity.accepted().build();
    }
}
