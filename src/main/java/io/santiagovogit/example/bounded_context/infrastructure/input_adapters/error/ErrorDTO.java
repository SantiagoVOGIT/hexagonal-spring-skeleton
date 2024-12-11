package io.santiagovogit.example.bounded_context.infrastructure.input_adapters.error;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ErrorDTO(
        LocalDateTime timestamp,
        int status,
        UUID errorId,
        String error,
        String details
) {

}