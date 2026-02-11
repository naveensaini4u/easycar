package com.naveen.easycar.controller;

import com.naveen.easycar.dto.PaymentRequestDto;
import com.naveen.easycar.dto.PaymentResponseDto;
import com.naveen.easycar.entity.Payment;
import com.naveen.easycar.service.PaymentService;
import com.naveen.easycar.utility.PaymentUtility;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/reservation/{reservationId}")
    public ResponseEntity<PaymentResponseDto> makePayment(
            @PathVariable Long reservationId,
            @Valid @RequestBody PaymentRequestDto request
    ) {

        PaymentUtility.validateConditionalFields(request);
        String details = PaymentUtility.extractDetails(request);

        Payment payment = paymentService.makePayment(
                reservationId,
                request.getMethod(),
                details
        );

        return ResponseEntity.ok(mapToDto(payment));
    }

    @GetMapping("/user/{customerId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentHistory(
            @PathVariable Long customerId
    ) {

        List<PaymentResponseDto> history = paymentService
                .getPaymentHistory(customerId)
                .stream()
                .map(this::mapToDto)
                .toList();

        return ResponseEntity.ok(history);
    }

    private PaymentResponseDto mapToDto(Payment payment) {

        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setPaymentId(payment.getId());
        dto.setReservationId(payment.getReservation().getId());
        dto.setMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setAmount(payment.getAmount());
        dto.setTransactionReference(payment.getTransactionReference());
        dto.setCreatedAt(payment.getCreatedAt());

        return dto;
    }
}

