package kuit.server.common.status;

public interface ResponseStatus {
    int getCode();

    int getStatus();

    String getMessage();
}
