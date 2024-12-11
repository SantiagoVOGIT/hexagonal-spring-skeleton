package io.santiagovogit.example.shared.domain;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final String errorMessage;
    private final String details;

    public DomainException(String errorMessage) {
        this(errorMessage, null);
    }

    public DomainException(String errorMessage, String details) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.details = details;
    }
}