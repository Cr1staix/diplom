package ru.top.diplom.exception.user;

public class UserAllreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "Пользователь с телефоном %s уже зарегистрирован";

    public UserAllreadyExistsException(String phone) {
        super(MESSAGE.formatted(phone));
    }
}
