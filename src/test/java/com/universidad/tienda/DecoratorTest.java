package com.universidad.tienda;

import com.universidad.tienda.decorator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas JUnit 5 para el patrón Decorator del servicio de órdenes.
 * Verifica el correcto comportamiento de la cadena de decoradores y casos borde.
 */
@DisplayName("Pruebas del Patrón Decorator - Servicio de Órdenes")
class DecoratorTest {

    private OrdenServicio ordenBase;
    private OrdenServicio ordenCompleto;

    @BeforeEach
    void setUp() {
        // Configuración de la cadena completa: Auditoria -> Validacion -> Logging -> Base
        ordenBase = new OrdenServicioBase();
        ordenCompleto = new AuditoriaDecorator(
            new ValidacionDecorator(
                new LoggingDecorator(ordenBase)
            )
        );
    }

    @Test
    @DisplayName("TC-01: Procesar una orden con datos válidos debe retornar resultado exitoso")
    void testOrdenValida() {
        // Given
        String ordenId = "ORD-001";
        double monto = 15000.0;

        // When
        String resultado = ordenCompleto.procesarOrden(ordenId, monto);

        // Then
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertTrue(resultado.contains("ORDEN_PROCESADA"), "El resultado debe contener 'ORDEN_PROCESADA'");
        assertTrue(resultado.contains(ordenId), "El resultado debe contener el ID de la orden");
        System.out.println("✓ TC-01 PASADO: Orden válida procesada. Resultado: " + resultado);
    }

    @Test
    @DisplayName("TC-02: Monto fuera del rango permitido debe lanzar IllegalArgumentException")
    void testMontoInvalido() {
        // Given - monto menor al mínimo permitido (1000)
        String ordenId = "ORD-002";
        double montoMuyBajo = 500.0;

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ordenCompleto.procesarOrden(ordenId, montoMuyBajo),
            "Debe lanzar IllegalArgumentException para monto fuera de rango"
        );
        assertTrue(exception.getMessage().contains("fuera del rango"),
            "El mensaje de error debe indicar que el monto está fuera del rango");
        System.out.println("✓ TC-02 PASADO: Excepcion correcta para monto invalido: " + exception.getMessage());

        // Also test with monto greater than maximum (50000000)
        double montoMuyAlto = 60_000_000.0;
        IllegalArgumentException exceptionAlto = assertThrows(
            IllegalArgumentException.class,
            () -> ordenCompleto.procesarOrden(ordenId, montoMuyAlto),
            "Debe lanzar IllegalArgumentException para monto superior al máximo"
        );
        assertTrue(exceptionAlto.getMessage().contains("fuera del rango"),
            "El mensaje de error debe indicar que el monto está fuera del rango");
        System.out.println("✓ TC-02b PASADO: Excepcion correcta para monto muy alto: " + exceptionAlto.getMessage());
    }

    @Test
    @DisplayName("TC-03: ID de orden vacío debe lanzar IllegalArgumentException")
    void testIdVacio() {
        // Given
        String ordenIdVacio = "";
        double montoValido = 25000.0;

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ordenCompleto.procesarOrden(ordenIdVacio, montoValido),
            "Debe lanzar IllegalArgumentException para ID de orden vacío"
        );
        assertTrue(exception.getMessage().contains("nulo o vacío"),
            "El mensaje de error debe indicar que el ID es vacío");
        System.out.println("✓ TC-03 PASADO: Excepcion correcta para ID vacio: " + exception.getMessage());

        // Also test with null ID
        IllegalArgumentException exceptionNull = assertThrows(
            IllegalArgumentException.class,
            () -> ordenCompleto.procesarOrden(null, montoValido),
            "Debe lanzar IllegalArgumentException para ID de orden nulo"
        );
        assertTrue(exceptionNull.getMessage().contains("nulo o vacío"),
            "El mensaje de error debe indicar que el ID es nulo");
        System.out.println("✓ TC-03b PASADO: Excepcion correcta para ID nulo: " + exceptionNull.getMessage());
    }

    @Test
    @DisplayName("TC-04: Decorador individual LoggingDecorator debe añadir logs sin alterar resultado")
    void testDecoradorIndividual() {
        // Given - solo el decorador de logging sin validación ni auditoría
        OrdenServicio soloConLogging = new LoggingDecorator(ordenBase);
        String ordenId = "ORD-004";
        double monto = 5000.0;

        // When
        String resultado = soloConLogging.procesarOrden(ordenId, monto);

        // Then
        assertNotNull(resultado, "El resultado no debe ser nulo con decorador individual");
        assertTrue(resultado.contains("ORDEN_PROCESADA"), "El resultado debe contener 'ORDEN_PROCESADA'");
        assertTrue(resultado.contains(ordenId), "El resultado debe contener el ID de la orden");
        System.out.println("✓ TC-04 PASADO: Decorador individual funciona. Resultado: " + resultado);
    }
}
