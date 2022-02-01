package com.singleparentlife.app.payload.response;

import com.singleparentlife.app.constants.Status;

public class JsonResponse {

    private final Status status;

    private Object data;

    public JsonResponse(Status status) {
        this.status = status;
    }

    public JsonResponse(Status status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
