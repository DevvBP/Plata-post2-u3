package com.universidad.tienda.facade;

import org.springframework.stereotype.Component;

/**
 * Servicio especializado para envío de notificaciones push.
 * Parte del subsistema de notificaciones que la Facade encapsula.
 */
@Component
public class PushService {

    public void enviarPush(String dispositivoId, String titulo, String mensaje) {
        System.out.println("[PUSH] Enviando notificación push al dispositivo: " + dispositivoId);
        System.out.println("[PUSH] Título: " + titulo);
        System.out.println("[PUSH] Mensaje: " + mensaje);
        System.out.println("[PUSH] ✓ Notificación push enviada exitosamente.");
    }

    public void enviarPushError(String dispositivoId, String mensajeError) {
        System.out.println("[PUSH] Enviando push de error al dispositivo: " + dispositivoId);
        System.out.println("[PUSH] Error: " + mensajeError);
        System.out.println("[PUSH] ✓ Push de error enviado.");
    }
}
