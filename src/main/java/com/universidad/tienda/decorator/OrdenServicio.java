package com.universidad.tienda.decorator;

/**
 * Interfaz que define el contrato para el servicio de procesamiento de órdenes.
 * Implementada tanto por la clase base como por todos los decoradores.
 */
public interface OrdenServicio {

    /**
     * Procesa una orden de compra.
     *
     * @param ordenId identificador único de la orden
     * @param monto   monto total de la orden
     * @return resultado del procesamiento
     */
    String procesarOrden(String ordenId, double monto);
}
