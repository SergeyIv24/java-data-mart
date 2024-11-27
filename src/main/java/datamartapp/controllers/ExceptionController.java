package datamartapp.controllers;

import datamartapp.common.GeneralConstants;
import datamartapp.exceptions.ErrorMessage;
import datamartapp.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(final NotFoundException e) {
        String message = e.getMessage();
        return new ErrorMessage(HttpStatus.NOT_FOUND.getReasonPhrase(), message, prepareResponseTimestamp());
    }

/*    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleThrowable(final Throwable e) {
        String message = e.getMessage();
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                message, prepareResponseTimestamp());
    }*/

    private String prepareResponseTimestamp() {
        return LocalDateTime.now().format(GeneralConstants.DATE_FORMATTER);
    }
}
