package ru.top.diplom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static ru.top.diplom.consts.RegexConstants.PASSWORD_REGEX;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {


    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null){
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }
}
