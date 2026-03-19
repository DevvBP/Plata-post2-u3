package com.universidad.tienda.decorator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Decorador de Auditoría para el servicio de órdenes.
 * Registra información de auditoría con timestamp para trazabilidad
 * de todas las operaciones realizadas sobre las órdenes.
 */
public class AuditoriaDecorator extends OrdenServicioDecorator {

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AuditoriaDecorator(OrdenServicio ordenServicio) {
        super(ordenServicio);
    }

    @Override
    public String procesarOrden(String ordenId, double monto) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println("[AUDITORIA] [" + timestamp + "] Inicio de procesamiento - Orden: " +
            ordenId + " | Monto: $" + monto);

        String resultado = super.procesarOrden(ordenId, monto);

        String timestampFin = LocalDateTime.now().format(FORMATTER);
        System.out.println("[AUDITORIA] [" + timestampFin + "] Fin de procesamiento - Resultado: " + resultado);

        return resultado;
    }
}
