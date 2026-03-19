package com.universidad.tienda.decorator;

/**
 * Decorador de Validación para el servicio de órdenes.
 * Valida que el monto esté dentro del rango permitido (1000 - 50000000)
 * y que el identificador de la orden no sea nulo ni vacío.
 */
public class ValidacionDecorator extends OrdenServicioDecorator {

    private static final double MONTO_MINIMO = 1000.0;
    private static final double MONTO_MAXIMO = 50_000_000.0;

    public ValidacionDecorator(OrdenServicio ordenServicio) {
        super(ordenServicio);
    }

    @Override
    public String procesarOrden(String ordenId, double monto) {
        System.out.println("[VALIDACION] Validando orden: " + ordenId);

        if (ordenId == null || ordenId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la orden no puede ser nulo o vacío");
        }

        if (monto < MONTO_MINIMO || monto > MONTO_MAXIMO) {
            throw new IllegalArgumentException(
                "El monto $" + monto + " está fuera del rango permitido [$" +
                MONTO_MINIMO + " - $" + MONTO_MAXIMO + "]"
            );
        }

        System.out.println("[VALIDACION] Orden " + ordenId + " aprobada. Monto $" + monto + " dentro del rango.");
        return super.procesarOrden(ordenId, monto);
    }
}
