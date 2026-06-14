package com.to4ilochka.rental.repository;

import com.to4ilochka.rental.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarCategoryRepository extends JpaRepository<CarCategory, Long> {
}
