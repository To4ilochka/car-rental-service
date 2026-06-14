package com.to4ilochka.rental.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

public class CarDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на додавання нового автомобіля")
    public static class CarCreateRequest {
        @NotBlank(message = "Model is required")
        @Schema(description = "Модель автомобіля", example = "Camry", requiredMode = Schema.RequiredMode.REQUIRED)
        private String model;
        
        @NotBlank(message = "Brand is required")
        @Schema(description = "Марка автомобіля", example = "Toyota", requiredMode = Schema.RequiredMode.REQUIRED)
        private String brand;
        
        @NotNull(message = "Year is required")
        @Min(value = 1886, message = "Year must be valid")
        @Schema(description = "Рік випуску", example = "2022", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer year;
        
        @NotNull(message = "Price per day is required")
        @Positive(message = "Price per day must be positive")
        @Schema(description = "Вартість оренди за один день", example = "45.50", requiredMode = Schema.RequiredMode.REQUIRED)
        private Double pricePerDay;
        
        @Schema(description = "ID пункту прокату, до якого належить авто", example = "1")
        private Long rentalOfficeId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на оновлення інформації про автомобіль")
    public static class CarUpdateRequest {
        @Schema(description = "Модель автомобіля", example = "Camry")
        private String model;
        
        @Schema(description = "Марка автомобіля", example = "Toyota")
        private String brand;
        
        @Min(value = 1886, message = "Year must be valid")
        @Schema(description = "Рік випуску", example = "2023")
        private Integer year;
        
        @Positive(message = "Price per day must be positive")
        @Schema(description = "Вартість оренди за один день", example = "50.00")
        private Double pricePerDay;
        
        @Schema(description = "ID пункту прокату, до якого належить авто", example = "1")
        private Long rentalOfficeId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Відповідь з даними автомобіля")
    public static class CarResponse {
        @Schema(description = "Унікальний ідентифікатор", example = "1")
        private Long id;
        
        @Schema(description = "Модель автомобіля", example = "Camry")
        private String model;
        
        @Schema(description = "Марка автомобіля", example = "Toyota")
        private String brand;
        
        @Schema(description = "Рік випуску", example = "2022")
        private Integer year;
        
        @Schema(description = "Вартість оренди за один день", example = "45.50")
        private Double pricePerDay;
    }
}
