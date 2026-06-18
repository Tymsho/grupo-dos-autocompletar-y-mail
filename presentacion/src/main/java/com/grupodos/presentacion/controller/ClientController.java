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

@RestController
@RequestMapping("/api/clients")
@CrossOrigin("*")
public class ClientController {

    private final ClientRepository clientRepository;
    private final EmailService emailService;

    public ClientController(ClientRepository clientRepository, EmailService emailService) {
        this.clientRepository = clientRepository;
        this.emailService = emailService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(@RequestParam(required = false, defaultValue = "") String q) {
        List<Client> results;
        if (q == null || q.trim().isEmpty()) {
            results = clientRepository.findAll().stream().limit(10).collect(Collectors.toList());
        } else {
            results = clientRepository.findByNameContainingIgnoreCase(q);
        }
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{id}/notify")
    public ResponseEntity<Void> notifyClient(@PathVariable Long id, @RequestBody NotifyRequestDTO request) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        
        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Client client = clientOpt.get();
        
        // Llamada al método asíncrono, no bloqueará el hilo del controlador
        emailService.sendEmail(client.getEmail(), request.getSubject(), request.getBody());
        
        // Retorna 202 Accepted inmediatamente
        return ResponseEntity.accepted().build();
    }
}
