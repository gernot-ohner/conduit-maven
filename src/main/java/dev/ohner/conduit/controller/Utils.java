package dev.ohner.conduit.controller;

import dev.ohner.conduit.exception.UnauthorizedException;
import dev.ohner.conduit.service.model.EmailRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@Slf4j
public class Utils {

    public static EmailRecord getPrincipalEmail(WebRequest request) throws UnauthorizedException {
        log.info("Handling request: {}", request);
        final var principal = request.getUserPrincipal();
        if (principal == null) {
            throw new UnauthorizedException("No principal found");
        }

        return new EmailRecord(principal.getName());
    }

    public static Optional<EmailRecord> getOptionalPrincipalEmail(WebRequest request) {
        log.info("Handling request: {}", request);
        final var principal = request.getUserPrincipal();
        if (principal == null) {
            return Optional.empty();
        }

        return Optional.of(new EmailRecord(principal.getName()));
    }
}
