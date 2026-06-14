package com.to4ilochka.rental.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

public class RentalOfficeDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на створення пункту прокату")
    public static class RentalOfficeCreateRequest {
        @NotBlank(message = "Name is required")
        @Schema(description = "Назва пункту", example = "Центральний офіс", requiredMode = Schema.RequiredMode.REQUIRED)
        private String name;
        
        @NotBlank(message = "City is required")
        @Schema(description = "Місто", example = "Київ", requiredMode = Schema.RequiredMode.REQUIRED)
        private String city;
        
        @NotBlank(message = "Address is required")
        @Schema(description = "Адреса", example = "вул. Хрещатик, 1", requiredMode = Schema.RequiredMode.REQUIRED)
        private String address;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на оновлення пункту прокату")
    public static class RentalOfficeUpdateRequest {
        @Schema(description = "Назва пункту", example = "Головний офіс")
        private String name;
        
        @Schema(description = "Місто", example = "Київ")
        private String city;
        
        @Schema(description = "Адреса", example = "вул. Хрещатик, 10")
        private String address;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Відповідь з даними пункту прокату")
    public static class RentalOfficeResponse {
        @Schema(description = "Унікальний ідентифікатор", example = "1")
        private Long id;
        
        @Schema(description = "Назва пункту", example = "Центральний офіс")
        private String name;
        
        @Schema(description = "Місто", example = "Київ")
        private String city;
        
        @Schema(description = "Адреса", example = "вул. Хрещатик, 1")
        private String address;
    }
}
