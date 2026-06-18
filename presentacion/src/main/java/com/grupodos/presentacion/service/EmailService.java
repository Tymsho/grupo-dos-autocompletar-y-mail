package com.grupodos.presentacion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        String threadName = Thread.currentThread().getName();
        logger.info("[Hilo: {}] Iniciando simulación de envío de correo a: {}", threadName, to);

        try {
            // Simulamos un retraso para evidenciar la naturaleza asíncrona
            Thread.sleep(3000);
            
            // Envío real utilizando JavaMailSender
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);

            logger.info("[Hilo: {}] Correo enviado exitosamente a: {}. Asunto: '{}'", threadName, to, subject);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Hilo: {}] Error simulando el retraso", threadName, e);
        } catch (Exception e) {
            logger.error("[Hilo: {}] Error al enviar el correo (esperado si usas dummy SMTP): {}", threadName, e.getMessage());
        }
    }
}
