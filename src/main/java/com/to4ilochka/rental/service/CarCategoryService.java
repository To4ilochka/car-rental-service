package com.to4ilochka.rental.service;

import com.to4ilochka.rental.dto.CarCategoryDto.*;
import com.to4ilochka.rental.entity.CarCategory;
import com.to4ilochka.rental.exception.ResourceNotFoundException;
import com.to4ilochka.rental.repository.CarCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryResponse createCategory(CarCategoryCreateRequest request) {
        CarCategory category = CarCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return mapToResponse(carCategoryRepository.save(category));
    }

    public List<CarCategoryResponse> getAllCategories() {
        return carCategoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CarCategoryResponse getCategoryById(Long id) {
        return mapToResponse(getCategoryEntity(id));
    }

    public CarCategoryResponse updateCategory(Long id, CarCategoryUpdateRequest request) {
        CarCategory category = getCategoryEntity(id);
        if (request.getName() != null) category.setName(request.getName());
        if (request.getDescription() != null) category.setDescription(request.getDescription());
        return mapToResponse(carCategoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        CarCategory category = getCategoryEntity(id);
        carCategoryRepository.delete(category);
    }

    private CarCategory getCategoryEntity(Long id) {
        return carCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private CarCategoryResponse mapToResponse(CarCategory category) {
        return CarCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
