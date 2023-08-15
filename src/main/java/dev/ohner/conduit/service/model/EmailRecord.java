package dev.ohner.conduit.service.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@Valid
// TODO make this constructor (or a factory method) throw an illegal argument exception
//  (or UnprocessableEntity exception) if the email is invalid
//  including checked exception
public record EmailRecord(@Email String value) {
}
