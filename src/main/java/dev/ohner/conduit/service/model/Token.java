package dev.ohner.conduit.service.model;

public record Token(String value) {
    public static Token fromPassword(String password) {
        return new Token("{noop}" + password);
    }

    public static Token fromTokenString(String token) {
        return new Token(token);
    }
}
