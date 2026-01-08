package com.naveen.easycar.service;

import com.naveen.easycar.entity.Car;
import com.naveen.easycar.entity.Customer;
import com.naveen.easycar.entity.Reservation;
import com.naveen.easycar.enums.ReservationStatus;
import com.naveen.easycar.exception.BookingConflictException;
import com.naveen.easycar.exception.EntityNotFoundException;
import com.naveen.easycar.exception.MaintenanceWindowException;
import com.naveen.easycar.repository.CarRepository;
import com.naveen.easycar.repository.CustomerRepository;
import com.naveen.easycar.repository.MaintenanceWindowRepository;
import com.naveen.easycar.repository.ReservationRepository;
import com.naveen.easycar.utility.DateUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final MaintenanceWindowRepository maintenanceRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            CarRepository carRepository,
            CustomerRepository customerRepository,
            MaintenanceWindowRepository maintenanceRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    private void checkMaintenance(Long carId, LocalDate start, LocalDate end) {

        boolean conflict = maintenanceRepository.findByCarId(carId)
                .stream()
                .anyMatch(m ->
                        start.isBefore(m.getEndDate()) &&
                                end.isAfter(m.getStartDate())
                );

        if (conflict) {
            throw new MaintenanceWindowException(
                    "Car is under maintenance for selected dates"
            );
        }
    }

    private void checkReservationConflict(
            Long carId,
            LocalDate start,
            LocalDate end
    ) {
        boolean conflict = reservationRepository
                .findByCarIdAndStatus(carId, ReservationStatus.ACTIVE)
                .stream()
                .anyMatch(r ->
                        start.isBefore(r.getEndDate()) &&
                                end.isAfter(r.getStartDate())
                );

        if (conflict) {
            throw new BookingConflictException(
                    "Car already booked for selected dates"
            );
        }
    }

    private BigDecimal calculateCost(
            BigDecimal dailyRate,
            LocalDate start,
            LocalDate end
    ) {
        long days = ChronoUnit.DAYS.between(start, end);
        return dailyRate.multiply(BigDecimal.valueOf(days));
    }


    public Reservation createReservation(
            Long carId,
            Long customerId,
            LocalDate start,
            LocalDate end
    ) {
        DateUtils.validateDates(start, end);

        Car car = carRepository.findById(carId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Car not found")
                );

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found")
                );

        if (!car.isActive()) {
            throw new BookingConflictException("Car is not active");
        }

        checkMaintenance(carId, start, end);
        checkReservationConflict(carId, start, end);

        BigDecimal cost = calculateCost(
                car.getDailyRate(), start, end
        );

        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setCustomer(customer);
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        reservation.setTotalCost(cost);
        reservation.setStatus(ReservationStatus.ACTIVE);

        return reservationRepository.save(reservation);
    }


    public void cancelReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Reservation not found")
                );

        if (!reservation.getStartDate().isAfter(LocalDate.now())) {
            throw new BookingConflictException(
                    "Cannot cancel a started reservation"
            );
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


}

