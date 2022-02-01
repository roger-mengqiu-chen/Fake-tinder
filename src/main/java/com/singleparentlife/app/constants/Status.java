package com.singleparentlife.app.constants;

public enum Status {

    SUCCESS (0, "Success"),

    INVALID_INPUT (400, "Invalid input"),
    PERMISSION_DENIED (403, "Permission is denied"),
    ;

    private final int code;
    private final String message;

    Status (int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
