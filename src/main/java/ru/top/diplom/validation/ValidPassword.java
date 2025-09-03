package ru.top.diplom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.top.diplom.validation.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidPassword{
    String message() default "Пароль должен содержать от 6 до 16 символов, состоять из английских букв, включать минимум одну заглавную букву и одну цифру.";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}