package ru.top.diplom.exception.computerSpecification;

public class ComputerSpecificationNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Спецификации для ПК с id %d не существует";

    public ComputerSpecificationNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
