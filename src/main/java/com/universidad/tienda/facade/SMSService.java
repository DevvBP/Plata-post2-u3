package com.universidad.tienda.facade;

import org.springframework.stereotype.Component;

/**
 * Servicio especializado para envío de notificaciones SMS.
 * Parte del subsistema de notificaciones que la Facade encapsula.
 */
@Component
public class SMSService {

    public void enviarSMS(String numeroTelefono, String mensaje) {
        System.out.println("[SMS] Enviando SMS al número: " + numeroTelefono);
        System.out.println("[SMS] Mensaje: " + mensaje);
        System.out.println("[SMS] ✓ SMS enviado exitosamente.");
    }

    public void enviarSMSError(String numeroTelefono, String mensajeError) {
        System.out.println("[SMS] Enviando SMS de error al número: " + numeroTelefono);
        System.out.println("[SMS] Error: " + mensajeError);
        System.out.println("[SMS] ✓ SMS de error enviado.");
    }
}
