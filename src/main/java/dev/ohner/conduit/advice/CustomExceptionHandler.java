package dev.ohner.conduit.advice;

import dev.ohner.conduit.model.GenericErrorModel;
import dev.ohner.conduit.exception.UnauthorizedException;
import dev.ohner.conduit.exception.UnprocessableContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Void> handleUnauthorized(UnauthorizedException ex) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnprocessableContentException.class)
    public ResponseEntity<GenericErrorModel> handleUnexpectedError(UnprocessableContentException ex) {
        return new ResponseEntity<>(ex.getError(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

