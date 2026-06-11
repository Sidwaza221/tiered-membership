package org.sample.tieredmembership.membership.dto;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, int status, String message) {
}
