package kuit.server.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kuit.server.common.status.ResponseStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"code", "status", "message", "timestamp"})
public class BaseErrorResponse implements ResponseStatus {
    private final int code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public BaseErrorResponse(ResponseStatus status) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public BaseErrorResponse(ResponseStatus status, String message) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
