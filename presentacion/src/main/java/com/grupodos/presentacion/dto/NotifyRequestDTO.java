package com.grupodos.presentacion.dto;

/**
 * Data Transfer Object (DTO) utilizado para recibir el payload JSON
 * cuando se realiza una petición POST para enviar un correo de notificación.
 */
public class NotifyRequestDTO {
    
    private String subject; // Asunto del correo a enviar
    private String body;    // Cuerpo del correo a enviar

    // Getters y Setters
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
}
