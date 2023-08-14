package dev.ohner.conduit.service.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@Valid
public record EmailRecord(@Email String value) {
}
