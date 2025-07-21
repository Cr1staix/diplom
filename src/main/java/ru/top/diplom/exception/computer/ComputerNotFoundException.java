package ru.top.diplom.exception.computer;

import java.util.UUID;

public class ComputerNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Компьютер с id %s не найден";

    public ComputerNotFoundException(UUID id) {
        super(MESSAGE.formatted(id));
    }
}
