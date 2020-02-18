package com.gmail.yauhen2012.controller.validator.validationConstant;

public interface ValidationConstant {

    int STRING_MAX_LENGTH = 16;
    int STRING_MIN_LENGTH = 3;
    String NAME_PATTERN = "[a-zA-Z]+";
    String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-zA-Z])\\w{3,}";

    String ADMIN_ROLE = "ADMIN";
    String USER_ROLE = "USER";

}
