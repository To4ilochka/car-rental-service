package com.to4ilochka.rental.controller;

import com.to4ilochka.rental.dto.ApiErrorResponse;
import com.to4ilochka.rental.dto.BookingDto.*;
import com.to4ilochka.rental.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Бронювання", description = "Управління процесом бронювання (створення, оновлення, скасування)")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Створити нове бронювання", description = "Оформлює нове бронювання на автомобіль для вказаного клієнта. Перевіряє наявність авто та клієнта, а також логіку дат та відсутність дублікатів активних бронювань.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Бронювання успішно створено",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дати або дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Клієнта або автомобіль не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Клієнт вже має активне бронювання на це авто",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public BookingResponse createBooking(@Valid @RequestBody BookingCreateRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping
    @Operation(summary = "Отримати список всіх бронювань", description = "Повертає історію всіх бронювань в системі.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/active")
    @Operation(summary = "Отримати активні бронювання", description = "Повертає список бронювань зі статусом ACTIVE.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    public List<BookingResponse> getActiveBookings() {
        return bookingService.getActiveBookings();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати інформацію про бронювання", description = "Отримує детальну інформацію про конкретне бронювання за його ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Бронювання знайдено",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Бронювання з вказаним ID не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public BookingResponse getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити бронювання", description = "Оновлює дати, статус або ціну бронювання.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дані успішно оновлено",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дати (наприклад, startDate > endDate)",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт статусів (вже є активне бронювання)",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public BookingResponse updateBooking(@PathVariable Long id, @Valid @RequestBody BookingUpdateRequest request) {
        return bookingService.updateBooking(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Скасувати бронювання", description = "Видаляє бронювання з системи (або скасовує його).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Бронювання успішно скасовано/видалено"),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
