package com.naveen.easycar.service;

import com.naveen.easycar.entity.Payment;
import com.naveen.easycar.entity.Reservation;
import com.naveen.easycar.enums.PaymentMethod;
import com.naveen.easycar.enums.PaymentStatus;
import com.naveen.easycar.enums.ReservationStatus;
import com.naveen.easycar.exception.EntityNotFoundException;
import com.naveen.easycar.repository.PaymentRepository;
import com.naveen.easycar.repository.ReservationRepository;
import com.naveen.easycar.utility.paymentProcessor.PaymentProcessor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private final List<PaymentProcessor> processors;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            ReservationRepository reservationRepository,List<PaymentProcessor> paymentProcessor

    ) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.processors = paymentProcessor;
    }


    public void completePayment(Long paymentId, boolean success) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Payment not found")
                );

        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.getReservation()
                    .setStatus(ReservationStatus.ACTIVE);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }
    }

    public Payment makePayment(
            Long reservationId,
            PaymentMethod method,
            String details
    ) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Reservation not found")
                );

        PaymentProcessor processor = processors.stream()
                .filter(p -> p.getSupportedMethod() == method)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Unsupported payment method")
                );

        processor.validate(details);

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmount(reservation.getTotalCost());
        payment.setPaymentMethod(method);
        payment.setPaymentDetails(processor.mask(details));
        payment.setStatus(processor.process());

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            reservation.setStatus(ReservationStatus.ACTIVE);
        }

        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentHistory(Long customerId) {
        return paymentRepository.findByReservationCustomerId(customerId);
    }

}

