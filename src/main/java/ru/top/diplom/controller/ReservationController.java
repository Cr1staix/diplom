package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.reservationDTO.ReservationCreateDTO;
import ru.top.diplom.dto.reservationDTO.ReservationResponseDTO;
import ru.top.diplom.service.ReservationService;

import java.util.UUID;

@RestController
@RequestMapping("api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@RequestBody ReservationCreateDTO reservationCreateDTO){


        return ResponseEntity.ok(reservationService.create(reservationCreateDTO));
    }

    @GetMapping("/active/{computerId}")
    public ResponseEntity<ReservationResponseDTO> getActiveReservation(
            @PathVariable UUID computerId) {
        ReservationResponseDTO dto = reservationService.findActiveByComputer(computerId);
        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }
}
