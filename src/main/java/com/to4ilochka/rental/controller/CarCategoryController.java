package com.to4ilochka.rental.controller;

import com.to4ilochka.rental.dto.ApiErrorResponse;
import com.to4ilochka.rental.dto.CarCategoryDto.*;
import com.to4ilochka.rental.service.CarCategoryService;
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
@RequestMapping("/car-categories")
@RequiredArgsConstructor
@Tag(name = "Категорії автомобілів", description = "Управління категоріями автомобілів (наприклад, SUV, Sedan, тощо)")
public class CarCategoryController {

    private final CarCategoryService carCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Створити категорію", description = "Створює нову категорію автомобілів.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категорію успішно створено",
                    content = @Content(schema = @Schema(implementation = CarCategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CarCategoryResponse createCategory(@Valid @RequestBody CarCategoryCreateRequest request) {
        return carCategoryService.createCategory(request);
    }

    @GetMapping
    @Operation(summary = "Отримати всі категорії", description = "Повертає список всіх категорій.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    public List<CarCategoryResponse> getAllCategories() {
        return carCategoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати категорію за ID", description = "Повертає інформацію про конкретну категорію.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію знайдено",
                    content = @Content(schema = @Schema(implementation = CarCategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CarCategoryResponse getCategoryById(@PathVariable Long id) {
        return carCategoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити категорію", description = "Оновлює назву або опис категорії.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію успішно оновлено",
                    content = @Content(schema = @Schema(implementation = CarCategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CarCategoryResponse updateCategory(@PathVariable Long id, @Valid @RequestBody CarCategoryUpdateRequest request) {
        return carCategoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Видалити категорію", description = "Видаляє категорію з системи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Категорію успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public void deleteCategory(@PathVariable Long id) {
        carCategoryService.deleteCategory(id);
    }
}
