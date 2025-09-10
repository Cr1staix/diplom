package ru.top.diplom.mapper;

import org.mapstruct.Mapper;
import ru.top.diplom.dto.reservationDTO.ReservationCreateDTO;
import ru.top.diplom.dto.reservationDTO.ReservationResponseDTO;
import ru.top.diplom.model.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    Reservation toReservation(ReservationCreateDTO reservationCreateDTO);

    ReservationResponseDTO toResponseDTO(Reservation reservation);
}
