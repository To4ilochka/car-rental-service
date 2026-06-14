package com.to4ilochka.rental.controller;

import com.to4ilochka.rental.dto.ApiErrorResponse;
import com.to4ilochka.rental.dto.CarDto.*;
import com.to4ilochka.rental.service.CarService;
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
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобілі", description = "Управління автопарком (додавання, оновлення, видалення, пошук авто)")
public class CarController {

    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Додати новий автомобіль", description = "Створює запис про новий автомобіль та може прив'язати його до існуючого пункту прокату.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Автомобіль успішно створено",
                    content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Вказаний пункт прокату не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CarResponse createCar(@Valid @RequestBody CarCreateRequest request) {
        return carService.createCar(request);
    }

    @GetMapping
    @Operation(summary = "Отримати список всіх автомобілів", description = "Повертає загальний список усіх авто, що є у системі.")
    @ApiResponse(responseCode = "200", description = "Список автомобілів успішно отримано")
    public List<CarResponse> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати інформацію про автомобіль", description = "Отримує детальну інформацію про конкретний автомобіль за його ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобіль знайдено",
                    content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "404", description = "Автомобіль з вказаним ID не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CarResponse getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити автомобіль", description = "Оновлює характеристики або прив'язку автомобіля до пункту прокату.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дані автомобіля успішно оновлено",
                    content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Автомобіль або пункт прокату не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CarResponse updateCar(@PathVariable Long id, @Valid @RequestBody CarUpdateRequest request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Видалити автомобіль", description = "Видаляє авто з бази даних. Операція недоступна, якщо на автомобіль є активні бронювання.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Автомобіль успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Автомобіль не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Видалення неможливе через активні бронювання",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @GetMapping("/office/{officeId}")
    @Operation(summary = "Отримати автомобілі пункту прокату", description = "Повертає список всіх автомобілів, що належать до вказаного пункту прокату.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список автомобілів успішно отримано"),
            @ApiResponse(responseCode = "404", description = "Пункт прокату не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public List<CarResponse> getCarsByOffice(@PathVariable Long officeId) {
        return carService.getCarsByOfficeId(officeId);
    }

    @PostMapping("/{id}/categories/{categoryId}")
    @Operation(summary = "Додати категорію до автомобіля", description = "Додає існуючу категорію до переліку категорій автомобіля.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію успішно додано"),
            @ApiResponse(responseCode = "404", description = "Автомобіль або категорію не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public void addCategoryToCar(@PathVariable Long id, @PathVariable Long categoryId) {
        carService.addCategoryToCar(id, categoryId);
    }

    @DeleteMapping("/{id}/categories/{categoryId}")
    @Operation(summary = "Видалити категорію у автомобіля", description = "Видаляє прив'язку категорії до автомобіля.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію успішно видалено з автомобіля"),
            @ApiResponse(responseCode = "404", description = "Автомобіль або категорію не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public void removeCategoryFromCar(@PathVariable Long id, @PathVariable Long categoryId) {
        carService.removeCategoryFromCar(id, categoryId);
    }
}
