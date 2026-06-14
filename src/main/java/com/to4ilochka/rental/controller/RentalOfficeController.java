package com.to4ilochka.rental.controller;

import com.to4ilochka.rental.dto.ApiErrorResponse;
import com.to4ilochka.rental.dto.CarDto.CarResponse;
import com.to4ilochka.rental.dto.RentalOfficeDto.*;
import com.to4ilochka.rental.service.RentalOfficeService;
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
@RequestMapping("/offices")
@RequiredArgsConstructor
@Tag(name = "Пункти прокату", description = "Управління пунктами прокату автомобілів")
public class RentalOfficeController {

    private final RentalOfficeService rentalOfficeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Створити пункт прокату", description = "Додає новий пункт прокату до системи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пункт прокату успішно створено",
                    content = @Content(schema = @Schema(implementation = RentalOfficeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public RentalOfficeResponse createOffice(@Valid @RequestBody RentalOfficeCreateRequest request) {
        return rentalOfficeService.createOffice(request);
    }

    @GetMapping
    @Operation(summary = "Отримати всі пункти прокату", description = "Повертає список всіх пунктів прокату.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    public List<RentalOfficeResponse> getAllOffices() {
        return rentalOfficeService.getAllOffices();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати пункт прокату за ID", description = "Повертає інформацію про конкретний пункт прокату.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пункт прокату знайдено",
                    content = @Content(schema = @Schema(implementation = RentalOfficeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Пункт прокату не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public RentalOfficeResponse getOfficeById(@PathVariable Long id) {
        return rentalOfficeService.getOfficeById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити пункт прокату", description = "Оновлює дані пункту прокату.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пункт прокату успішно оновлено",
                    content = @Content(schema = @Schema(implementation = RentalOfficeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Пункт прокату не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public RentalOfficeResponse updateOffice(@PathVariable Long id, @Valid @RequestBody RentalOfficeUpdateRequest request) {
        return rentalOfficeService.updateOffice(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Видалити пункт прокату", description = "Видаляє пункт прокату з системи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пункт прокату успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Пункт прокату не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public void deleteOffice(@PathVariable Long id) {
        rentalOfficeService.deleteOffice(id);
    }

    @GetMapping("/{id}/cars")
    @Operation(summary = "Отримати авто пункту прокату", description = "Повертає список всіх автомобілів, що належать до даного пункту прокату.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список автомобілів успішно отримано"),
            @ApiResponse(responseCode = "404", description = "Пункт прокату не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public List<CarResponse> getCarsByOfficeId(@PathVariable Long id) {
        return rentalOfficeService.getCarsByOfficeId(id);
    }
}
