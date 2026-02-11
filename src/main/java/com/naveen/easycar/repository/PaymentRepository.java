package com.naveen.easycar.repository;

import com.naveen.easycar.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByReservationId(Long reservationId);

    List<Payment> findByReservationCustomerId(Long customerId);
}

