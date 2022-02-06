package com.singleparentlife.app.payload.response;

import com.singleparentlife.app.constants.Status;

public class JsonResponse {

    private final Status status;

    private String dataType;

    private Object data;

    public JsonResponse(Status status) {
        this.status = status;
    }

    public JsonResponse(Status status, String type, Object data) {
        this.status = status;
        this.dataType = type;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getDataType() {
        return dataType;
    }
}
