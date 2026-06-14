package com.to4ilochka.rental.controller;

import com.to4ilochka.rental.dto.BookingDto.BookingResponse;
import com.to4ilochka.rental.service.BookingService;
import com.to4ilochka.rental.service.CarService;
import com.to4ilochka.rental.service.CustomerService;
import com.to4ilochka.rental.service.RentalOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
@Tag(name = "Аналітика", description = "Аналітичні запити та статистика системи")
public class AnalyticsController {

    private final CarService carService;
    private final CustomerService customerService;
    private final BookingService bookingService;
    private final RentalOfficeService rentalOfficeService;

    @GetMapping("/cars/count")
    @Operation(summary = "Загальна кількість автомобілів", description = "Повертає загальну кількість автомобілів у системі.")
    @ApiResponse(responseCode = "200", description = "Кількість успішно отримано")
    public Map<String, Long> getCarCount() {
        return Map.of("count", carService.getTotalCount());
    }

    @GetMapping("/customers/count")
    @Operation(summary = "Загальна кількість клієнтів", description = "Повертає загальну кількість зареєстрованих клієнтів.")
    @ApiResponse(responseCode = "200", description = "Кількість успішно отримано")
    public Map<String, Long> getCustomerCount() {
        return Map.of("count", customerService.getTotalCount());
    }

    @GetMapping("/bookings/active")
    @Operation(summary = "Активні бронювання", description = "Повертає список всіх поточних активних бронювань.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    public List<BookingResponse> getActiveBookings() {
        return bookingService.getActiveBookings();
    }

    @GetMapping("/cars/by-category")
    @Operation(summary = "Кількість авто за категоріями", description = "Повертає статистику розподілу автомобілів за категоріями.")
    @ApiResponse(responseCode = "200", description = "Статистику успішно отримано")
    public Map<String, Long> getCarCountByCategory() {
        return carService.getCarCountByCategory();
    }

    @GetMapping("/offices/workload")
    @Operation(summary = "Завантаженість пунктів прокату", description = "Повертає інформацію про кількість авто в кожному пункті прокату.")
    @ApiResponse(responseCode = "200", description = "Статистику успішно отримано")
    public Map<String, Long> getOfficeWorkload() {
        return rentalOfficeService.getOfficeWorkload();
    }
}

