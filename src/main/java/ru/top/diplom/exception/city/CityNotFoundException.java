package ru.top.diplom.exception.city;

public class CityNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Город с id %d не найден";

    public CityNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
