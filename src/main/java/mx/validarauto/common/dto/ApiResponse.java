package mx.validarauto.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Envoltorio estándar para todas las respuestas de la API")
public class ApiResponse<T> {

    @Schema(description = "Indica si la operación fue exitosa", example = "true")
    private boolean success;

    @Schema(description = "Payload de la respuesta (nulo en caso de error)")
    private T data;

    @Schema(description = "Mensaje descriptivo de la operación", example = "Reporte generado exitosamente")
    private String message;

    @Schema(description = "Código de error en caso de fallo (nulo en éxito)", example = "INVALID_PLACA_FORMAT")
    private String errorCode;

    @Schema(description = "Marca de tiempo de la respuesta", example = "2026-03-09T12:30:00")
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String code) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errorCode(code)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
