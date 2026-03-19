package com.universidad.tienda.facade;

import org.springframework.stereotype.Service;

/**
 * Fachada unificada para el sistema de notificaciones multicanal.
 *
 * Simplifica la interacción con múltiples servicios de notificación
 * (Email, SMS, Push) exponiendo métodos de alto nivel que coordinan
 * todos los canales necesarios según el evento de negocio.
 *
 * Justificación del uso de Facade:
 * - El cliente no necesita conocer la complejidad de cada servicio
 * - Reduce el acoplamiento entre el cliente y el subsistema
 * - Centraliza la lógica de coordinación de notificaciones
 * - Facilita el mantenimiento y la evolución del subsistema
 */
@Service
public class NotificacionFacade {

    private final EmailService emailService;
    private final SMSService smsService;
    private final PushService pushService;

    public NotificacionFacade(EmailService emailService,
                               SMSService smsService,
                               PushService pushService) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.pushService = pushService;
    }

    /**
     * Notifica por todos los canales disponibles cuando una compra es exitosa.
     *
     * @param email          correo del cliente
     * @param telefono       teléfono del cliente
     * @param dispositivoId  ID del dispositivo móvil del cliente
     * @param ordenId        identificador de la orden
     * @param monto          monto de la compra
     */
    public void notificarCompraExitosa(String email, String telefono,
                                        String dispositivoId, String ordenId,
                                        double monto) {
        System.out.println("\n=== NOTIFICACION: Compra Exitosa ===");
        System.out.println("Orden: " + ordenId + " | Monto: $" + monto);
        System.out.println("Coordinando envío multicanal...\n");

        emailService.enviarEmail(
            email,
            "✅ Compra exitosa - Orden #" + ordenId,
            "Su compra por $" + monto + " fue procesada exitosamente. ID de orden: " + ordenId
        );

        smsService.enviarSMS(
            telefono,
            "Tienda: Compra exitosa! Orden #" + ordenId + " por $" + monto + " confirmada."
        );

        pushService.enviarPush(
            dispositivoId,
            "✅ Compra Exitosa",
            "Orden #" + ordenId + " confirmada por $" + monto
        );

        System.out.println("\n=== Notificaciones de compra exitosa enviadas a todos los canales ===\n");
    }

    /**
     * Notifica por todos los canales cuando ocurre un error en el pago.
     *
     * @param email          correo del cliente
     * @param telefono       teléfono del cliente
     * @param dispositivoId  ID del dispositivo móvil del cliente
     * @param ordenId        identificador de la orden
     * @param mensajeError   descripción del error ocurrido
     */
    public void notificarErrorPago(String email, String telefono,
                                    String dispositivoId, String ordenId,
                                    String mensajeError) {
        System.out.println("\n=== NOTIFICACION: Error de Pago ===");
        System.out.println("Orden: " + ordenId + " | Error: " + mensajeError);
        System.out.println("Coordinando envío multicanal de error...\n");

        emailService.enviarEmailError(
            email,
            "Error al procesar la orden #" + ordenId + ": " + mensajeError
        );

        smsService.enviarSMSError(
            telefono,
            "Error en su orden #" + ordenId + ". " + mensajeError
        );

        pushService.enviarPushError(
            dispositivoId,
            "❌ Error en orden #" + ordenId + ": " + mensajeError
        );

        System.out.println("\n=== Notificaciones de error enviadas a todos los canales ===\n");
    }
}
