package com.to4ilochka.rental.repository;

import com.to4ilochka.rental.entity.Booking;
import com.to4ilochka.rental.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findByStatus(BookingStatus status);
    boolean existsByCustomerIdAndCarIdAndStatus(Long customerId, Long carId, BookingStatus status);
    boolean existsByCustomerIdAndStatus(Long customerId, BookingStatus status);
    boolean existsByCarIdAndStatus(Long carId, BookingStatus status);
}
