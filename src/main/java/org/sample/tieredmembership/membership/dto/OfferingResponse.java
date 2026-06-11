package org.sample.tieredmembership.membership.dto;

import java.math.BigDecimal;

public record OfferingResponse(
        Long id,
        PlanResponse plan,
        TierResponse tier,
        BigDecimal price,
        String currency
) {
}
