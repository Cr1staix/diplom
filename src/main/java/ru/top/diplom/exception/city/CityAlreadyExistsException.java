package ru.top.diplom.exception.city;

public class CityAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Город с названием %s уже существует";

    public CityAlreadyExistsException(String city) {
        super(MESSAGE.formatted(city));
    }
}
