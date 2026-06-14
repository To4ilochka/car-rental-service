package com.to4ilochka.rental.repository;

import com.to4ilochka.rental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByRentalOfficeId(Long officeId);
}
