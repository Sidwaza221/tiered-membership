package org.sample.tieredmembership.membership.dto;

public record PlanResponse(Long id, String code, String name, Integer durationInMonths) {
}
