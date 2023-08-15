package dev.ohner.conduit.exception;

import lombok.Getter;

@Getter
public class UnprocessableContentException extends RuntimeException {

    public UnprocessableContentException(String message) {
        super(message);
    }
}
