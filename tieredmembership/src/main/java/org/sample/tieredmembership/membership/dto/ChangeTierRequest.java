package org.sample.tieredmembership.membership.dto;

import jakarta.validation.constraints.NotNull;

public record ChangeTierRequest(@NotNull Long offeringId) {
}
