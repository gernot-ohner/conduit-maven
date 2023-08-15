package dev.ohner.conduit.exception;

import lombok.Getter;

@Getter
public class UnprocessableContentException extends Exception {

    public UnprocessableContentException(String message) {
        super(message);
    }
}
