package com.naveen.easycar.controller;

import com.naveen.easycar.dto.ReservationRequestDto;
import com.naveen.easycar.dto.ReservationResponseDto;
import com.naveen.easycar.entity.Reservation;
import com.naveen.easycar.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(
            @Valid @RequestBody ReservationRequestDto dto
    ) {

        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            throw new IllegalArgumentException(
                    "End date must be after start date"
            );
        }

        Reservation reservation = reservationService.createReservation(
                dto.getCarId(),
                dto.getCustomerId(),
                dto.getStartDate(),
                dto.getEndDate()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToDto(reservation));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(
            @PathVariable Long id
    ) {

        Reservation reservation =
                reservationService.getReservations(id);

        return ResponseEntity.ok(mapToDto(reservation));
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long id
    ) {

        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }


    private ReservationResponseDto mapToDto(
            Reservation reservation
    ) {

        ReservationResponseDto dto =
                new ReservationResponseDto();

        dto.setReservationId(reservation.getId());
        dto.setCarId(reservation.getCar().getId());
        dto.setCustomerId(reservation.getCustomer().getId());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        dto.setTotalCost(reservation.getTotalCost());
        dto.setStatus(reservation.getStatus());
        dto.setCreatedAt(reservation.getCreatedAt());

        return dto;
    }
}

