package com.to4ilochka.rental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на створення нового клієнта")
    public static class CustomerCreateRequest {
        @NotBlank(message = "First name is required")
        @Schema(description = "Ім'я клієнта", example = "Іван", requiredMode = Schema.RequiredMode.REQUIRED)
        private String firstName;
        
        @NotBlank(message = "Last name is required")
        @Schema(description = "Прізвище клієнта", example = "Іванов", requiredMode = Schema.RequiredMode.REQUIRED)
        private String lastName;
        
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Schema(description = "Електронна пошта клієнта", example = "ivan.ivanov@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        private String email;
        
        @Schema(description = "Контактний телефон", example = "+380501234567")
        private String phone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на оновлення даних клієнта")
    public static class CustomerUpdateRequest {
        @Schema(description = "Ім'я клієнта", example = "Іван")
        private String firstName;
        
        @Schema(description = "Прізвище клієнта", example = "Іванов")
        private String lastName;
        
        @Email(message = "Invalid email format")
        @Schema(description = "Електронна пошта клієнта", example = "ivan.new@example.com")
        private String email;
        
        @Schema(description = "Контактний телефон", example = "+380501234567")
        private String phone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Відповідь з даними клієнта")
    public static class CustomerResponse {
        @Schema(description = "Унікальний ідентифікатор", example = "1")
        private Long id;
        
        @Schema(description = "Ім'я клієнта", example = "Іван")
        private String firstName;
        
        @Schema(description = "Прізвище клієнта", example = "Іванов")
        private String lastName;
        
        @Schema(description = "Електронна пошта клієнта", example = "ivan.ivanov@example.com")
        private String email;
        
        @Schema(description = "Контактний телефон", example = "+380501234567")
        private String phone;
    }
}
