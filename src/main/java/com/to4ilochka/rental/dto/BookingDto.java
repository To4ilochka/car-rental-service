package com.to4ilochka.rental.dto;

import com.to4ilochka.rental.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class BookingDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на створення бронювання")
    public static class BookingCreateRequest {
        @NotNull(message = "Start date is required")
        @Schema(description = "Дата початку оренди", example = "2023-11-01", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate startDate;
        
        @NotNull(message = "End date is required")
        @Schema(description = "Дата завершення оренди", example = "2023-11-10", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate endDate;
        
        @NotNull(message = "Status is required")
        @Schema(description = "Статус бронювання", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
        private BookingStatus status;
        
        @NotNull(message = "Total price is required")
        @Positive(message = "Total price must be positive")
        @Schema(description = "Загальна вартість оренди", example = "450.00", requiredMode = Schema.RequiredMode.REQUIRED)
        private Double totalPrice;
        
        @NotNull(message = "Customer ID is required")
        @Schema(description = "ID клієнта", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long customerId;
        
        @NotNull(message = "Car ID is required")
        @Schema(description = "ID автомобіля", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long carId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Запит на оновлення бронювання")
    public static class BookingUpdateRequest {
        @Schema(description = "Дата початку оренди", example = "2023-11-02")
        private LocalDate startDate;
        
        @Schema(description = "Дата завершення оренди", example = "2023-11-12")
        private LocalDate endDate;
        
        @Schema(description = "Статус бронювання", example = "COMPLETED")
        private BookingStatus status;
        
        @Positive(message = "Total price must be positive")
        @Schema(description = "Загальна вартість оренди", example = "500.00")
        private Double totalPrice;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Відповідь з даними бронювання")
    public static class BookingResponse {
        @Schema(description = "Унікальний ідентифікатор", example = "1")
        private Long id;
        
        @Schema(description = "Дата початку оренди", example = "2023-11-01")
        private LocalDate startDate;
        
        @Schema(description = "Дата завершення оренди", example = "2023-11-10")
        private LocalDate endDate;
        
        @Schema(description = "Статус бронювання", example = "ACTIVE")
        private BookingStatus status;
        
        @Schema(description = "Загальна вартість оренди", example = "450.00")
        private Double totalPrice;
        
        @Schema(description = "ID клієнта", example = "1")
        private Long customerId;
        
        @Schema(description = "ID автомобіля", example = "1")
        private Long carId;
    }
}
