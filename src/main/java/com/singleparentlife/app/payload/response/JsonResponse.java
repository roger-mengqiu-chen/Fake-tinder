package com.singleparentlife.app.payload.response;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;

public class JsonResponse {

    private final Status status;

    private final DataType dataType;

    private final Object data;

    public JsonResponse(Status status, DataType type, Object data) {
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

    public DataType getDataType() {
        return dataType;
    }
}
