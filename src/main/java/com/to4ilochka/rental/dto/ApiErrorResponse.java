package com.to4ilochka.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    @Schema(description = "Час виникнення помилки", example = "2023-10-25T10:15:30")
    private LocalDateTime timestamp;
    
    @Schema(description = "HTTP-статус помилки", example = "400")
    private Integer status;
    
    @Schema(description = "Назва HTTP-помилки", example = "Bad Request")
    private String error;
    
    @Schema(description = "Загальне повідомлення про помилку", example = "Validation failed")
    private String message;
    
    @Schema(description = "URL запиту, під час якого виникла помилка", example = "/customers")
    private String path;
    
    @Schema(description = "Помилки валідації окремих полів (опціонально)", example = "{\"email\": \"Invalid email format\"}")
    private Map<String, String> errors;
}
