package org.sample.tieredmembership.membership.dto;

import jakarta.validation.constraints.NotNull;

public record SubscribeRequest(@NotNull Long offeringId) {
}
