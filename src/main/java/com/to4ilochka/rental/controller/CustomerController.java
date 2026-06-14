package com.to4ilochka.rental.controller;

import com.to4ilochka.rental.dto.ApiErrorResponse;
import com.to4ilochka.rental.dto.BookingDto.BookingResponse;
import com.to4ilochka.rental.dto.CustomerDto.*;
import com.to4ilochka.rental.service.BookingService;
import com.to4ilochka.rental.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Профілі користувачів", description = "Управління даними клієнтів (створення, оновлення, пошук)")
public class CustomerController {

    private final CustomerService customerService;
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Створити нового клієнта", description = "Створює новий профіль клієнта. Email повинен бути унікальним.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Клієнта успішно створено",
                    content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані (помилка валідації)",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Клієнт з таким email вже існує",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        return customerService.createCustomer(request);
    }

    @GetMapping
    @Operation(summary = "Отримати список всіх клієнтів", description = "Повертає повний список зареєстрованих клієнтів.")
    @ApiResponse(responseCode = "200", description = "Список клієнтів успішно отримано")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати клієнта за ID", description = "Повертає детальну інформацію про конкретного клієнта за його унікальним ідентифікатором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клієнта знайдено",
                    content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Клієнта з вказаним ID не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані клієнта", description = "Оновлює інформацію про клієнта. Якщо email змінюється, він повинен бути унікальним.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дані успішно оновлено",
                    content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Клієнта з вказаним ID не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Клієнт з новим email вже існує",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CustomerResponse updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest request) {
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Видалити клієнта", description = "Видаляє клієнта з системи. Видалення неможливе, якщо у клієнта є активні бронювання.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Клієнта успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Клієнта з вказаним ID не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Неможливо видалити клієнта через активні бронювання",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/{id}/bookings")
    @Operation(summary = "Отримати бронювання клієнта", description = "Повертає історію бронювань вказаного клієнта.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список бронювань успішно отримано"),
            @ApiResponse(responseCode = "404", description = "Клієнта з вказаним ID не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public List<BookingResponse> getCustomerBookings(@PathVariable Long id) {
        return bookingService.getBookingsByCustomer(id);
    }
}
