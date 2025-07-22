package ru.top.diplom.exception.computer_club;

public class ComputerClubNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Компьютерный клуб с id %d не найден";

    public ComputerClubNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
