package dev.ohner.conduit.service.model;

public record Slug(String value) {
    public static Slug fromTitle(String title) {
        return new Slug(title.toLowerCase().replaceAll("\\s", "-"));
    }
}
