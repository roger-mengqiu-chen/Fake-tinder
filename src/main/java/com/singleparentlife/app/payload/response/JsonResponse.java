package com.singleparentlife.app.payload.response;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import org.springframework.http.ResponseEntity;

/**
 * This is for wrapping response from service layer
 */
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

    /**
     * This return ResponseEntity according to the Error/Data type
     * If it's server error then return internalServerError
     * If not, then return badRequest
     * @return ResponseEntity
     */
    public ResponseEntity<JsonResponse> toResponseEntity() {
        if (this.status.equals(Status.FAIL)) {
            if (!this.dataType.equals(DataType.SERVER_ERROR)) {
                return ResponseEntity.badRequest().body(this);
            }
            else{
                return ResponseEntity.internalServerError().body(this);
            }
        }
        else {
            return ResponseEntity.ok().body(this);
        }
    }
}
