package com.to4ilochka.rental.controller;

import com.to4ilochka.rental.dto.CarDto.CarResponse;
import com.to4ilochka.rental.dto.CustomerDto.CustomerResponse;
import com.to4ilochka.rental.dto.RentalOfficeDto.RentalOfficeResponse;
import com.to4ilochka.rental.service.CarService;
import com.to4ilochka.rental.service.CustomerService;
import com.to4ilochka.rental.service.RentalOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Tag(name = "Пошук", description = "Повнотекстовий пошук по сутностях")
public class SearchController {

    private final CarService carService;
    private final CustomerService customerService;
    private final RentalOfficeService rentalOfficeService;

    @GetMapping("/cars")
    @Operation(summary = "Пошук автомобілів", description = "Пошук авто за моделлю або маркою.")
    @ApiResponse(responseCode = "200", description = "Результати пошуку успішно отримано")
    public List<CarResponse> searchCars(@RequestParam String query) {
        return carService.searchCars(query);
    }

    @GetMapping("/customers")
    @Operation(summary = "Пошук клієнтів", description = "Пошук клієнтів за ім'ям, прізвищем або email.")
    @ApiResponse(responseCode = "200", description = "Результати пошуку успішно отримано")
    public List<CustomerResponse> searchCustomers(@RequestParam String query) {
        return customerService.searchCustomers(query);
    }

    @GetMapping("/offices")
    @Operation(summary = "Пошук пунктів прокату", description = "Пошук пунктів прокату за назвою, містом або адресою.")
    @ApiResponse(responseCode = "200", description = "Результати пошуку успішно отримано")
    public List<RentalOfficeResponse> searchOffices(@RequestParam String query) {
        return rentalOfficeService.searchOffices(query);
    }
}
