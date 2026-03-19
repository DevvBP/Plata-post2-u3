package com.universidad.tienda.decorator;

/**
 * Clase abstracta base para todos los decoradores del servicio de órdenes.
 * Implementa el patrón Decorator wrapping otro OrdenServicio.
 * Los decoradores concretos extienden esta clase para añadir comportamiento adicional.
 */
public abstract class OrdenServicioDecorator implements OrdenServicio {

    protected final OrdenServicio ordenServicioWrapped;

    public OrdenServicioDecorator(OrdenServicio ordenServicio) {
        this.ordenServicioWrapped = ordenServicio;
    }

    @Override
    public String procesarOrden(String ordenId, double monto) {
        return ordenServicioWrapped.procesarOrden(ordenId, monto);
    }
}
