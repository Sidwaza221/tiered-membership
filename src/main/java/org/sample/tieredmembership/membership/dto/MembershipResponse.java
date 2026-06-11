package org.sample.tieredmembership.membership.dto;

import org.sample.tieredmembership.membership.enums.MembershipStatus;

import java.time.Instant;
import java.util.List;

public record MembershipResponse(
        Long id,
        Long userId,
        MembershipStatus status,
        OfferingResponse offering,
        Instant startDate,
        Instant endDate,
        Instant cancelledAt,
        Long version,
        List<BenefitResponse> benefits
) {
}
