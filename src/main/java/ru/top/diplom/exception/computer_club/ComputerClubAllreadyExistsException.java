package ru.top.diplom.exception.computer_club;

public class ComputerClubAllreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Компьютерный клуб с именем %s уже есть по адресу %s!";

    public ComputerClubAllreadyExistsException(String name, String address) {
        super(MESSAGE.formatted(name, address));
    }
}
