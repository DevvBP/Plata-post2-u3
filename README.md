# Tienda Patrones Estructurales: Decorator y Facade

**Laboratorio - Unidad 3: Patrones Estructurales**  
`groupId: com.universidad` | `artifactId: tienda-patrones-estructurales` | `Spring Boot 3.2.0`

---

## Estructura del Proyecto

```
tienda-patrones-estructurales/
├── pom.xml
├── evidencia_pruebas.txt
├── README.md
└── src/
    ├── main/java/com/universidad/tienda/
    │   ├── TiendaApplication.java          # Clase principal Spring Boot
    │   ├── DecoratorConfig.java            # Configuración de la cadena de decoradores
    │   ├── decorator/
    │   │   ├── OrdenServicio.java          # Interfaz del contrato
    │   │   ├── OrdenServicioBase.java      # Componente concreto (@Component "ordenBase")
    │   │   ├── OrdenServicioDecorator.java # Clase abstracta base de decoradores
    │   │   ├── LoggingDecorator.java       # Decorador: agrega logs
    │   │   ├── ValidacionDecorator.java    # Decorador: valida monto e ID
    │   │   └── AuditoriaDecorator.java     # Decorador: registra timestamps
    │   └── facade/
    │       ├── EmailService.java           # Subsistema: notificaciones por email
    │       ├── SMSService.java             # Subsistema: notificaciones por SMS
    │       ├── PushService.java            # Subsistema: notificaciones push
    │       └── NotificacionFacade.java     # Fachada unificada (@Service)
    └── test/java/com/universidad/tienda/
        └── DecoratorTest.java              # 4 pruebas JUnit 5
```

---

## Patrón 1: Decorator — Servicio de Órdenes

### ¿Qué hace?
Permite agregar comportamientos adicionales a un objeto (procesamiento de órdenes) de forma dinámica y transparente, sin modificar su código fuente. Cada decorador "envuelve" al anterior, añadiendo su capa de responsabilidad.

### Composición de la Cadena

```
[Cliente]
    ↓ llama procesarOrden()
[AuditoriaDecorator]      → Registra timestamp de inicio
    ↓ delega
[ValidacionDecorator]     → Valida ordenId no vacío y monto entre $1.000 - $50.000.000
    ↓ delega (si válido)
[LoggingDecorator]        → Loguea inicio y fin del proceso
    ↓ delega
[OrdenServicioBase]       → Procesamiento real de la orden
    ↑ retorna resultado
[LoggingDecorator]        → Loguea resultado
    ↑
[ValidacionDecorator]     → (ya validado, pasa)
    ↑
[AuditoriaDecorator]      → Registra timestamp de fin
    ↑ retorna al cliente
```

### Configuración del Bean en Spring (DecoratorConfig.java)

```java
@Bean("ordenCompleto")
public OrdenServicio ordenServicioCompleto(@Qualifier("ordenBase") OrdenServicio ordenBase) {
    return new AuditoriaDecorator(
        new ValidacionDecorator(
            new LoggingDecorator(ordenBase)
        )
    );
}
```

### Diagrama de Clases

```
          «interface»
          OrdenServicio
          + procesarOrden(id, monto): String
               ▲
    ┌──────────┴──────────────────┐
    │                             │
OrdenServicioBase       OrdenServicioDecorator (abstract)
@Component("ordenBase")   - wrapped: OrdenServicio
                                   ▲
                    ┌──────────────┼──────────────┐
                    │              │              │
           LoggingDecorator  ValidacionDecorator  AuditoriaDecorator
```

### Reglas de Validación (ValidacionDecorator)
| Campo    | Regla                            | Error lanzado                      |
|----------|----------------------------------|------------------------------------|
| `ordenId`| No puede ser `null` ni vacío     | `IllegalArgumentException`         |
| `monto`  | Entre `$1,000` y `$50,000,000`   | `IllegalArgumentException`         |

---

## Patrón 2: Facade — Sistema de Notificaciones

### ¿Qué hace?
Proporciona una interfaz simplificada y unificada para coordinar múltiples subsistemas de notificación. El cliente solo interactúa con la Facade, sin conocer los detalles internos de cada canal.

### Justificación del uso de Facade
- **Reduce el acoplamiento**: El cliente solo depende de `NotificacionFacade`, no de los 3 servicios individuales.
- **Simplifica la API**: En lugar de llamar a 3 servicios por separado, el cliente llama un único método.
- **Facilita el mantenimiento**: Si se agrega un nuevo canal (ej. WhatsApp), solo se modifica la Facade.
- **Centraliza la lógica de coordinación**: La decisión de qué canales usar para cada evento está encapsulada.

### Métodos de la Facade

| Método                      | Canales que activa             | Caso de uso              |
|-----------------------------|-------------------------------|--------------------------|
| `notificarCompraExitosa()`  | Email ✔ + SMS ✔ + Push ✔      | Compra procesada OK      |
| `notificarErrorPago()`      | Email ✔ + SMS ✔ + Push ✔      | Error al procesar pago   |

### Diagrama

```
[Cliente]
    ↓ notificarCompraExitosa() / notificarErrorPago()
[NotificacionFacade]  @Service
    ├──→ EmailService.enviarEmail()     @Component
    ├──→ SMSService.enviarSMS()         @Component
    └──→ PushService.enviarPush()       @Component
```

---

## Pruebas JUnit 5

Archivo: `src/test/java/com/universidad/tienda/DecoratorTest.java`

| Test | Descripción | Resultado |
|------|------------|-----------|
| TC-01 | Orden con datos válidos (ORD-001, $15.000) | ✅ PASSED |
| TC-02 | Monto inválido bajo ($500) y alto ($60.000.000) | ✅ PASSED |
| TC-03 | ID de orden vacío y nulo | ✅ PASSED |
| TC-04 | Decorador individual LoggingDecorator | ✅ PASSED |

**Resultado final:** `Tests run: 4, Failures: 0, Errors: 0, Skipped: 0` → **BUILD SUCCESS**

Ver evidencia completa en: [`evidencia_pruebas.txt`](evidencia_pruebas.txt)

---

## Instrucciones de Ejecución

### Prerrequisitos
- Java 17+
- Maven 3.8+

### Comandos

```bash
# Compilar y ejecutar pruebas
mvn clean test

# Generar JAR ejecutable
mvn clean package

# Ejecutar el JAR
java -jar target/tienda-patrones-estructurales-1.0.0.jar
```

### Verificar las pruebas
```bash
# La salida esperada es:
# Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
# BUILD SUCCESS
```

---

## Tecnologías Utilizadas

| Tecnología | Versión |
|-----------|---------|
| Java | 17 |
| Spring Boot | 3.2.0 |
| Maven | 3.8+ |
| JUnit 5 (Jupiter) | 5.10.1 |

---

## Repositorio

GitHub: [https://github.com/DevvBP/Plata-post2-u3](https://github.com/DevvBP/Plata-post2-u3)
