package com.singleparentlife.app.constants;

public enum Status {

    SUCCESS ("Success"),
    FAIL("Fail"),

    INVALID_INPUT ("Invalid input"),
    PERMISSION_DENIED ("Permission is denied"),
    FETCH_TOKEN_FAILED("Didn't get firebase token"),

    PREFERENCES_PARTIALLY_CREATED("Preferences are not fully created"),
    PREFERENCE_NOT_FOUND("The required preference is not found"),
    ;


    private final String message;

    Status (String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
