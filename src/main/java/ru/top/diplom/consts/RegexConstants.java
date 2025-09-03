package ru.top.diplom.consts;

public class RegexConstants {

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,30}$";
    public static final String PHONE_REGEX = "^((\\+7|8)9\\d{9})$";
}
