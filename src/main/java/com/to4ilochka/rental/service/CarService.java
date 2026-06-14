package com.to4ilochka.rental.service;

import com.to4ilochka.rental.dto.CarDto.*;
import com.to4ilochka.rental.entity.BookingStatus;
import com.to4ilochka.rental.entity.Car;
import com.to4ilochka.rental.entity.CarCategory;
import com.to4ilochka.rental.entity.RentalOffice;
import com.to4ilochka.rental.exception.ConflictOperationException;
import com.to4ilochka.rental.exception.ResourceNotFoundException;
import com.to4ilochka.rental.repository.BookingRepository;
import com.to4ilochka.rental.repository.CarCategoryRepository;
import com.to4ilochka.rental.repository.CarRepository;
import com.to4ilochka.rental.repository.RentalOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final RentalOfficeRepository rentalOfficeRepository;
    private final CarCategoryRepository carCategoryRepository;

    public CarResponse createCar(CarCreateRequest request) {
        Car car = Car.builder()
                .model(request.getModel())
                .brand(request.getBrand())
                .year(request.getYear())
                .pricePerDay(request.getPricePerDay())
                .build();

        if (request.getRentalOfficeId() != null) {
            RentalOffice office = rentalOfficeRepository.findById(request.getRentalOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("RentalOffice not found"));
            car.setRentalOffice(office);
        }

        return mapToResponse(carRepository.save(car));
    }

    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CarResponse getCarById(Long id) {
        return mapToResponse(getCarEntity(id));
    }

    public CarResponse updateCar(Long id, CarUpdateRequest request) {
        Car car = getCarEntity(id);

        if (request.getModel() != null) car.setModel(request.getModel());
        if (request.getBrand() != null) car.setBrand(request.getBrand());
        if (request.getYear() != null) car.setYear(request.getYear());
        if (request.getPricePerDay() != null) car.setPricePerDay(request.getPricePerDay());

        if (request.getRentalOfficeId() != null) {
            RentalOffice office = rentalOfficeRepository.findById(request.getRentalOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("RentalOffice not found"));
            car.setRentalOffice(office);
        }

        return mapToResponse(carRepository.save(car));
    }

    public void deleteCar(Long id) {
        Car car = getCarEntity(id);

        if (bookingRepository.existsByCarIdAndStatus(id, BookingStatus.ACTIVE)) {
            throw new ConflictOperationException("Cannot delete car with active bookings");
        }

        carRepository.delete(car);
    }

    public void addCategoryToCar(Long carId, Long categoryId) {
        Car car = getCarEntity(carId);
        CarCategory category = carCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        
        car.getCategories().add(category);
        carRepository.save(car);
    }

    public void removeCategoryFromCar(Long carId, Long categoryId) {
        Car car = getCarEntity(carId);
        CarCategory category = carCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        
        car.getCategories().remove(category);
        carRepository.save(car);
    }

    public List<CarResponse> searchCars(String query) {
        String lowerQuery = query.toLowerCase();
        return carRepository.findAll().stream()
                .filter(car -> car.getModel().toLowerCase().contains(lowerQuery)
                        || car.getBrand().toLowerCase().contains(lowerQuery))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CarResponse> getCarsByOfficeId(Long officeId) {
        rentalOfficeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("RentalOffice not found with id: " + officeId));
        return carRepository.findByRentalOfficeId(officeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public long getTotalCount() {
        return carRepository.count();
    }

    public Map<String, Long> getCarCountByCategory() {
        Map<String, Long> result = new HashMap<>();
        carRepository.findAll().forEach(car ->
                car.getCategories().forEach(category ->
                        result.merge(category.getName(), 1L, Long::sum)));
        return result;
    }

    public Car getCarEntity(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
    }

    public CarResponse mapToResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .model(car.getModel())
                .brand(car.getBrand())
                .year(car.getYear())
                .pricePerDay(car.getPricePerDay())
                .build();
    }
}
