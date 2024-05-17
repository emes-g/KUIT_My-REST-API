package kuit.server.common.exception_handler;

import jakarta.annotation.Priority;
import kuit.server.common.BaseErrorResponse;
import kuit.server.common.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

import static kuit.server.common.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static kuit.server.common.status.BaseExceptionResponseStatus.LACK_OF_ELEMENT;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class SQLIntegrityConstraintViolationExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public BaseErrorResponse handle_SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("[handle_SQLIntegrityConstraintViolationException]", e);
        return new BaseErrorResponse(LACK_OF_ELEMENT, e.getMessage());
    }

}
