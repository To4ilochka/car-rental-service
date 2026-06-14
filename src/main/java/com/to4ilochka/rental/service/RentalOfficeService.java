package com.to4ilochka.rental.service;

import com.to4ilochka.rental.dto.CarDto.CarResponse;
import com.to4ilochka.rental.dto.RentalOfficeDto.*;
import com.to4ilochka.rental.entity.RentalOffice;
import com.to4ilochka.rental.exception.ResourceNotFoundException;
import com.to4ilochka.rental.repository.CarRepository;
import com.to4ilochka.rental.repository.RentalOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalOfficeService {

    private final RentalOfficeRepository rentalOfficeRepository;
    private final CarRepository carRepository;
    private final CarService carService;

    public RentalOfficeResponse createOffice(RentalOfficeCreateRequest request) {
        RentalOffice office = RentalOffice.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();
        return mapToResponse(rentalOfficeRepository.save(office));
    }

    public List<RentalOfficeResponse> getAllOffices() {
        return rentalOfficeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RentalOfficeResponse getOfficeById(Long id) {
        return mapToResponse(getOfficeEntity(id));
    }

    public RentalOfficeResponse updateOffice(Long id, RentalOfficeUpdateRequest request) {
        RentalOffice office = getOfficeEntity(id);
        if (request.getName() != null) office.setName(request.getName());
        if (request.getCity() != null) office.setCity(request.getCity());
        if (request.getAddress() != null) office.setAddress(request.getAddress());
        return mapToResponse(rentalOfficeRepository.save(office));
    }

    public void deleteOffice(Long id) {
        RentalOffice office = getOfficeEntity(id);
        rentalOfficeRepository.delete(office);
    }

    public List<CarResponse> getCarsByOfficeId(Long id) {
        getOfficeEntity(id); // Check existence
        return carRepository.findByRentalOfficeId(id).stream()
                .map(carService::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RentalOfficeResponse> searchOffices(String query) {
        String lowerQuery = query.toLowerCase();
        return rentalOfficeRepository.findAll().stream()
                .filter(office -> office.getName().toLowerCase().contains(lowerQuery)
                        || office.getCity().toLowerCase().contains(lowerQuery)
                        || office.getAddress().toLowerCase().contains(lowerQuery))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Map<String, Long> getOfficeWorkload() {
        return rentalOfficeRepository.findAll().stream()
                .collect(Collectors.toMap(
                        office -> office.getName() + " (" + office.getCity() + ")",
                        office -> (long) office.getCars().size()
                ));
    }

    private RentalOffice getOfficeEntity(Long id) {
        return rentalOfficeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental Office not found with id: " + id));
    }

    private RentalOfficeResponse mapToResponse(RentalOffice office) {
        return RentalOfficeResponse.builder()
                .id(office.getId())
                .name(office.getName())
                .city(office.getCity())
                .address(office.getAddress())
                .build();
    }
}
