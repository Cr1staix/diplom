package ru.top.diplom.exception.reservation;

import java.time.LocalDateTime;

public class ReservationAlreadyException extends RuntimeException {

    private static final String MESSAGE = "Компьютер забронирован до %s";
    public ReservationAlreadyException(LocalDateTime endDate) {
        super(MESSAGE.formatted(endDate));
    }
}
