package ru.top.diplom.exception.user;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE_FOR_ID = "Пользователь с id %d не найден";
    private static final String MESSAGE_FOR_PHONE = "Пользователь с номером %s не найден";

    public UserNotFoundException(Long id) {
        super(MESSAGE_FOR_ID.formatted(id));
    }

    public UserNotFoundException(String phone){
        super(MESSAGE_FOR_PHONE.formatted(phone));
    }
}
