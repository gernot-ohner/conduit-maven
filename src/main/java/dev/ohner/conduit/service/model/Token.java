package dev.ohner.conduit.service.model;

public record Token(String value) {
    public static Token fromPassword(String password) {

        return new Token(password);
    }
}
