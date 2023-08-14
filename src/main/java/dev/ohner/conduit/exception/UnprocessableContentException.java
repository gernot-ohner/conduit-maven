package dev.ohner.conduit.exception;

import dev.ohner.conduit.model.GenericErrorModel;
import lombok.Getter;

@Getter
public class UnprocessableContentException extends RuntimeException {

    private final GenericErrorModel error;

    public UnprocessableContentException(String message, GenericErrorModel error) {
        super(message);
        this.error = error;
    }
}
