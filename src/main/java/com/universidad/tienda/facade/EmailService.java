package com.universidad.tienda.facade;

import org.springframework.stereotype.Component;

/**
 * Servicio especializado para envío de notificaciones por correo electrónico.
 * Parte del subsistema de notificaciones que la Facade encapsula.
 */
@Component
public class EmailService {

    public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        System.out.println("[EMAIL] Enviando correo a: " + destinatario);
        System.out.println("[EMAIL] Asunto: " + asunto);
        System.out.println("[EMAIL] Cuerpo: " + cuerpo);
        System.out.println("[EMAIL] ✓ Correo enviado exitosamente.");
    }

    public void enviarEmailError(String destinatario, String mensajeError) {
        System.out.println("[EMAIL] Enviando correo de error a: " + destinatario);
        System.out.println("[EMAIL] Error: " + mensajeError);
        System.out.println("[EMAIL] ✓ Correo de error enviado.");
    }
}
