package com.to4ilochka.rental.service;

import com.to4ilochka.rental.dto.BookingDto.*;
import com.to4ilochka.rental.entity.Booking;
import com.to4ilochka.rental.entity.BookingStatus;
import com.to4ilochka.rental.entity.Car;
import com.to4ilochka.rental.entity.Customer;
import com.to4ilochka.rental.exception.BadRequestException;
import com.to4ilochka.rental.exception.ConflictOperationException;
import com.to4ilochka.rental.exception.ResourceNotFoundException;
import com.to4ilochka.rental.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerService customerService;
    private final CarService carService;

    public BookingResponse createBooking(BookingCreateRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BadRequestException("Start date must be before or equal to end date");
        }

        Customer customer = customerService.getCustomerEntity(request.getCustomerId());
        Car car = carService.getCarEntity(request.getCarId());

        if (request.getStatus() == BookingStatus.ACTIVE &&
            bookingRepository.existsByCustomerIdAndCarIdAndStatus(customer.getId(), car.getId(), BookingStatus.ACTIVE)) {
            throw new ConflictOperationException("Customer already has an active booking for this car");
        }

        Booking booking = Booking.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .totalPrice(request.getTotalPrice())
                .customer(customer)
                .car(car)
                .build();

        return mapToResponse(bookingRepository.save(booking));
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        return mapToResponse(getBookingEntity(id));
    }

    public BookingResponse updateBooking(Long id, BookingUpdateRequest request) {
        Booking booking = getBookingEntity(id);

        if (request.getStartDate() != null) booking.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) booking.setEndDate(request.getEndDate());
        
        if (booking.getStartDate().isAfter(booking.getEndDate())) {
            throw new BadRequestException("Start date must be before or equal to end date");
        }

        if (request.getStatus() != null) {
            if (request.getStatus() == BookingStatus.ACTIVE && booking.getStatus() != BookingStatus.ACTIVE) {
                if (bookingRepository.existsByCustomerIdAndCarIdAndStatus(
                        booking.getCustomer().getId(), booking.getCar().getId(), BookingStatus.ACTIVE)) {
                    throw new ConflictOperationException("Customer already has an active booking for this car");
                }
            }
            booking.setStatus(request.getStatus());
        }

        if (request.getTotalPrice() != null) booking.setTotalPrice(request.getTotalPrice());

        return mapToResponse(bookingRepository.save(booking));
    }

    public void deleteBooking(Long id) {
        Booking booking = getBookingEntity(id);
        bookingRepository.delete(booking);
    }

    public List<BookingResponse> getBookingsByCustomer(Long customerId) {
        customerService.getCustomerEntity(customerId);
        return bookingRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getActiveBookings() {
        return bookingRepository.findByStatus(BookingStatus.ACTIVE).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Booking getBookingEntity(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .status(booking.getStatus())
                .totalPrice(booking.getTotalPrice())
                .customerId(booking.getCustomer().getId())
                .carId(booking.getCar().getId())
                .build();
    }
}
