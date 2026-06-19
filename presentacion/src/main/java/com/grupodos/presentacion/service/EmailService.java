package com.grupodos.presentacion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del envío de correos electrónicos.
 * Marcado con @Service para que Spring lo inyecte donde se necesite.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    // Inyectamos el JavaMailSender que Spring autoconfigura con los datos del application.properties
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Envía un correo electrónico de forma asíncrona.
     * La anotación @Async hace que este método se ejecute en un hilo secundario,
     * permitiendo que el hilo principal siga su curso sin bloquearse.
     *
     * @param to Destinatario del correo.
     * @param subject Asunto.
     * @param body Cuerpo del mensaje.
     */
    @Async
    public void sendEmail(String to, String subject, String body) {
        // Obtenemos el nombre del hilo actual para demostrar que es asíncrono en la consola
        String threadName = Thread.currentThread().getName();
        logger.info("[Hilo: {}] Iniciando simulación de envío de correo a: {}", threadName, to);

        try {
            // Simulamos un retraso (por ej. latencia de red) para evidenciar la naturaleza asíncrona
            Thread.sleep(3000);
            
            // Envío real utilizando JavaMailSender
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);

            logger.info("[Hilo: {}] Correo enviado exitosamente a: {}. Asunto: '{}'", threadName, to, subject);
        } catch (InterruptedException e) {
            // Restauramos el estado de interrupción del hilo si falla el sleep
            Thread.currentThread().interrupt();
            logger.error("[Hilo: {}] Error simulando el retraso", threadName, e);
        } catch (Exception e) {
            // Capturamos cualquier error de SMTP para que no rompa la aplicación si hay credenciales incorrectas
            logger.error("[Hilo: {}] Error al enviar el correo (esperado si usas dummy SMTP o sin app password): {}", threadName, e.getMessage());
        }
    }
}
