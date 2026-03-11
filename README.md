# common — Módulo Compartido

Librería interna que contiene los DTOs, excepciones y configuraciones comunes para todos los microservicios de la plataforma ValidarAuto.

**Artifact:** `mx.validarauto:common:1.0.0-SNAPSHOT`

---

## DTOs

### `ApiResponse<T>`

Envoltorio estándar para todas las respuestas HTTP de la API.

| Campo       | Tipo            | Descripción                                          | Ejemplo                       |
|-------------|-----------------|------------------------------------------------------|-------------------------------|
| `success`   | `boolean`       | `true` si la operación fue exitosa                   | `true`                        |
| `data`      | `T`             | Payload de la respuesta (nulo en error)              | `{ ... }`                     |
| `message`   | `String`        | Mensaje descriptivo de la operación                  | `"Reporte generado exitosamente"` |
| `errorCode` | `String`        | Código de error (nulo en éxito)                      | `"INVALID_PLACA_FORMAT"`      |
| `timestamp` | `LocalDateTime` | Marca de tiempo de la respuesta                      | `"2026-03-09T12:30:00"`       |

**Métodos de fábrica:**
- `ApiResponse.ok(data)` — respuesta exitosa sin mensaje
- `ApiResponse.ok(data, message)` — respuesta exitosa con mensaje
- `ApiResponse.error(message, code)` — respuesta de error

---

### `ReporteDTO`

Reporte vehicular completo con datos de todas las fuentes consultadas.

| Campo                | Tipo              | Descripción                                                     | Ejemplo                                        |
|----------------------|-------------------|-----------------------------------------------------------------|------------------------------------------------|
| `id`                 | `String`          | UUID único del reporte                                          | `"550e8400-e29b-41d4-a716-446655440000"`       |
| `placa`              | `String`          | Placa consultada                                                | `"ABC-123-D"`                                  |
| `vin`                | `String`          | Número de identificación vehicular (17 caracteres)              | `"3VWFE21C04M000001"`                          |
| `tipoConsulta`       | `String`          | Tipo de consulta: `PLACA` o `VIN`                               | `"PLACA"`                                      |
| `planReporte`        | `String`          | Plan: `GRATIS`, `BASICO`, `COMPLETO`, `PREMIUM`                 | `"BASICO"`                                     |
| `vehiculo`           | `VehicleInfoDTO`  | Datos de identificación del vehículo                            | `{ marca: "NISSAN", ... }`                     |
| `repuve`             | `RepuveDTO`       | Resultado REPUVE                                                | `{ estatus: "INSCRITO", ... }`                 |
| `adeudos`            | `AdeudosDTO`      | Resultado adeudos                                               | `{ tieneAdeudos: false, ... }`                 |
| `alertas`            | `List<String>`    | Alertas detectadas (robo, adeudos)                              | `["ALERTA: Vehículo reportado como ROBADO"]`   |
| `estado`             | `String`          | Estado del procesamiento: `COMPLETADO`, `PARCIAL`, `ERROR`      | `"COMPLETADO"`                                 |
| `fechaConsulta`      | `LocalDateTime`   | Fecha y hora de la consulta                                     | `"2026-03-09T12:30:00"`                        |
| `tiempoRespuestaMs`  | `long`            | Tiempo de respuesta en milisegundos                             | `1250`                                         |

---

### `VehicleInfoDTO`

Datos de identificación del vehículo provenientes del REPUVE.

| Campo                   | Tipo     | Descripción                                          | Ejemplo              |
|-------------------------|----------|------------------------------------------------------|----------------------|
| `marca`                 | `String` | Marca del vehículo                                   | `"NISSAN"`           |
| `submarca`              | `String` | Submarca o línea                                     | `"Versa"`            |
| `modelo`                | `String` | Año modelo                                           | `"2022"`             |
| `tipo`                  | `String` | Categoría del vehículo                               | `"SEDAN"`            |
| `color`                 | `String` | Color                                                | `"BLANCO"`           |
| `vin`                   | `String` | VIN (17 caracteres alfanuméricos)                    | `"3VWFE21C04M000001"`|
| `placa`                 | `String` | Placa                                                | `"ABC-123-D"`        |
| `estado`                | `String` | Estado donde está registrado                         | `"CDMX"`             |
| `paisOrigen`            | `String` | País de origen                                       | `"MEXICO"`           |
| `plantaEnsamblaje`      | `String` | Ciudad y estado de ensamblaje                        | `"TOLUCA, MEX"`      |
| `fechaInscripcionRepuve`| `String` | Fecha de inscripción en REPUVE (YYYY-MM-DD)          | `"2020-01-15"`       |

---

### `RepuveDTO`

Resultado de consulta en el Registro Público Vehicular.

| Campo          | Tipo             | Descripción                                              | Ejemplo          |
|----------------|------------------|----------------------------------------------------------|------------------|
| `disponible`   | `boolean`        | Si el servicio devolvió datos exitosamente               | `true`           |
| `mensajeError` | `String`         | Mensaje de error (nulo si disponible=true)               | —                |
| `estatus`      | `String`         | `INSCRITO`, `NO_INSCRITO`, `SIN_DATOS`                   | `"INSCRITO"`     |
| `reporteRobo`  | `boolean`        | Si tiene reporte de robo activo                          | `false`          |
| `fuenteRobo`   | `String`         | Fuente del reporte de robo: PGJ, OCRA, etc.              | `"PGJ"`          |
| `fechaReporte` | `String`         | Fecha del reporte de robo (YYYY-MM-DD)                   | `"2023-06-15"`   |
| `vehiculo`     | `VehicleInfoDTO` | Datos del vehículo                                       | `{ ... }`        |

**Métodos de fábrica:**
- `RepuveDTO.sinDatos(mensaje)` — sin datos disponibles
- `RepuveDTO.conError(error)` — con error
- `RepuveDTO.inscrito(vehiculo)` — vehículo inscrito sin robo

---

### `AdeudosDTO`

Adeudos vehiculares por estado.

| Campo          | Tipo                  | Descripción                                      | Ejemplo     |
|----------------|-----------------------|--------------------------------------------------|-------------|
| `disponible`   | `boolean`             | Si el servicio devolvió datos exitosamente       | `true`      |
| `mensajeError` | `String`              | Mensaje de error (nulo si disponible=true)       | —           |
| `estado`       | `String`              | Estado de la república consultado                | `"CDMX"`    |
| `tieneAdeudos` | `boolean`             | Si hay adeudos pendientes                        | `true`      |
| `adeudos`      | `List<AdeudoItemDTO>` | Listado de adeudos                               | `[ ... ]`   |
| `totalAdeudos` | `BigDecimal`          | Suma total en MXN                                | `4700.00`   |
| `moneda`       | `String`              | Siempre `"MXN"`                                  | `"MXN"`     |

---

### `AdeudoItemDTO`

Item individual de adeudo vehicular.

| Campo              | Tipo          | Descripción                                                                | Ejemplo                             |
|--------------------|---------------|----------------------------------------------------------------------------|-------------------------------------|
| `tipo`             | `String`      | Tipo: `TENENCIA`, `MULTA`, `REFRENDO`, `FOTOCIVICA`, `AMBIENTAL`           | `"TENENCIA"`                        |
| `descripcion`      | `String`      | Descripción del adeudo                                                     | `"Tenencia vehicular no pagada"`    |
| `monto`            | `BigDecimal`  | Monto en MXN                                                               | `2500.00`                           |
| `fechaVencimiento` | `String`      | Fecha de vencimiento (YYYY-MM-DD)                                          | `"2024-03-31"`                      |
| `anio`             | `String`      | Año al que corresponde el adeudo                                            | `"2023"`                            |

---

### `ReporteEventDTO`

Evento publicado en Kafka al generar un reporte.

| Campo          | Tipo            | Descripción                                  |
|----------------|-----------------|----------------------------------------------|
| `reporteId`    | `String`        | UUID del reporte generado                    |
| `userId`       | `String`        | ID del usuario que solicitó el reporte       |
| `email`        | `String`        | Email del usuario                            |
| `placa`        | `String`        | Placa consultada                             |
| `planReporte`  | `String`        | Plan utilizado                               |
| `estado`       | `String`        | Estado del reporte: COMPLETADO, PARCIAL, ERROR|
| `fechaEvento`  | `LocalDateTime` | Timestamp del evento                         |

---

## Excepciones

| Excepción                | Código HTTP | errorCode               | Descripción                                  |
|--------------------------|-------------|-------------------------|----------------------------------------------|
| `VehicleNotFoundException` | 404       | `VEHICLE_NOT_FOUND`     | Vehículo no encontrado                       |
| `InvalidPlacaException`  | 400         | `INVALID_PLACA_FORMAT`  | Formato de placa inválido                    |
| `ExternalApiException`   | 502 / 503   | `EXTERNAL_API_ERROR`    | Error al llamar a un servicio externo        |
| `MethodArgumentNotValidException` | 400 | `VALIDATION_ERROR`     | Error de validación de Bean Validation       |
| `IllegalArgumentException` | 400       | `INVALID_ARGUMENT`      | Argumento inválido                           |
| `Exception` (genérica)   | 500         | `INTERNAL_ERROR`        | Error inesperado del servidor                |

El `GlobalExceptionHandler` maneja todas estas excepciones de forma centralizada vía `@RestControllerAdvice`.

---

## Dependencia Maven

```xml
<dependency>
    <groupId>mx.validarauto</groupId>
    <artifactId>common</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
