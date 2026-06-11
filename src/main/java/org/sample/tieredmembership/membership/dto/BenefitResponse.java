package org.sample.tieredmembership.membership.dto;

import org.sample.tieredmembership.membership.enums.BenefitType;

public record BenefitResponse(BenefitType type, String value) {
}
