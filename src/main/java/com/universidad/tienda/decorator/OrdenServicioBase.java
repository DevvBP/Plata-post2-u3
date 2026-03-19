package com.universidad.tienda.decorator;

import org.springframework.stereotype.Component;

/**
 * Implementación base del servicio de órdenes.
 * Procesa la orden de manera simple sin capas adicionales.
 * Actúa como el "ConcreteComponent" en el patrón Decorator.
 */
@Component("ordenBase")
public class OrdenServicioBase implements OrdenServicio {

    @Override
    public String procesarOrden(String ordenId, double monto) {
        System.out.println("[BASE] Procesando orden " + ordenId + " por monto: $" + monto);
        return "ORDEN_PROCESADA:" + ordenId + ":$" + monto;
    }
}
