package com.universidad.tienda.decorator;

/**
 * Decorador de Logging para el servicio de órdenes.
 * Añade registro de logs antes y después del procesamiento de la orden.
 */
public class LoggingDecorator extends OrdenServicioDecorator {

    public LoggingDecorator(OrdenServicio ordenServicio) {
        super(ordenServicio);
    }

    @Override
    public String procesarOrden(String ordenId, double monto) {
        System.out.println("[LOGGING] Iniciando procesamiento de orden: " + ordenId + " | Monto: $" + monto);
        String resultado = super.procesarOrden(ordenId, monto);
        System.out.println("[LOGGING] Orden procesada exitosamente: " + resultado);
        return resultado;
    }
}
