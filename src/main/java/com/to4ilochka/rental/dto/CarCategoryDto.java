package com.to4ilochka.rental.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

public class CarCategoryDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на створення категорії автомобіля")
    public static class CarCategoryCreateRequest {
        @NotBlank(message = "Name is required")
        @Schema(description = "Назва категорії", example = "SUV", requiredMode = Schema.RequiredMode.REQUIRED)
        private String name;
        
        @Schema(description = "Опис категорії", example = "Позашляховик для активного відпочинку")
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на оновлення категорії")
    public static class CarCategoryUpdateRequest {
        @Schema(description = "Назва категорії", example = "SUV")
        private String name;
        
        @Schema(description = "Опис категорії", example = "Великий позашляховик")
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Відповідь з даними категорії")
    public static class CarCategoryResponse {
        @Schema(description = "Унікальний ідентифікатор", example = "1")
        private Long id;
        
        @Schema(description = "Назва категорії", example = "SUV")
        private String name;
        
        @Schema(description = "Опис категорії", example = "Позашляховик")
        private String description;
    }
}
