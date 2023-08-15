package dev.ohner.conduit.advice;

import dev.ohner.conduit.model.GenericErrorModel;
import dev.ohner.conduit.exception.UnauthorizedException;
import dev.ohner.conduit.exception.UnprocessableContentException;
import dev.ohner.conduit.model.GenericErrorModelErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Void> handleUnauthorized(UnauthorizedException ex) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnprocessableContentException.class)
    public ResponseEntity<GenericErrorModel> handleUnexpectedError(UnprocessableContentException ex) {
        final var error = new GenericErrorModel(
            new GenericErrorModelErrors(List.of(ex.getMessage()))
        );
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

