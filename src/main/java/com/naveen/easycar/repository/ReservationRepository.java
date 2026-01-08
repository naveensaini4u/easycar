package com.naveen.easycar.repository;

import com.naveen.easycar.entity.Reservation;
import com.naveen.easycar.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCarIdAndStatus(
            Long carId,
            ReservationStatus status
    );

    List<Reservation> findByCustomerId(Long customerId);
}

