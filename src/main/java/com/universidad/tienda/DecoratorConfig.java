package com.universidad.tienda;

import com.universidad.tienda.decorator.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Spring para ensamblar la cadena de decoradores del servicio de órdenes.
 *
 * La cadena se ensambla de fuera hacia adentro:
 * AuditoriaDecorator -> ValidacionDecorator -> LoggingDecorator -> OrdenServicioBase
 *
 * Al invocar procesarOrden(), la ejecución fluye:
 * 1. AuditoriaDecorator (primera capa - registra inicio/fin con timestamp)
 * 2. ValidacionDecorator (valida monto y ordenId)
 * 3. LoggingDecorator (registra logs de inicio/fin)
 * 4. OrdenServicioBase (procesamiento real)
 */
@Configuration
public class DecoratorConfig {

    @Bean("ordenCompleto")
    public OrdenServicio ordenServicioCompleto(@Qualifier("ordenBase") OrdenServicio ordenBase) {
        return new AuditoriaDecorator(
            new ValidacionDecorator(
                new LoggingDecorator(ordenBase)
            )
        );
    }
}
