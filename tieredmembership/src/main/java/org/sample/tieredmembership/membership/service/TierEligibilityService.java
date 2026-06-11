package org.sample.tieredmembership.membership.service;

import org.sample.tieredmembership.membership.dto.TierEvaluationRequest;
import org.sample.tieredmembership.membership.dto.TierEvaluationResponse;
import org.sample.tieredmembership.membership.entity.MembershipTier;
import org.sample.tieredmembership.membership.entity.TierEligibilityRule;
import org.sample.tieredmembership.membership.enums.MetricType;
import org.sample.tieredmembership.membership.enums.RuleOperator;
import org.sample.tieredmembership.membership.repository.MembershipTierRepository;
import org.sample.tieredmembership.membership.repository.TierEligibilityRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class TierEligibilityService {

    private final TierEligibilityRuleRepository ruleRepository;
    private final MembershipTierRepository tierRepository;
    private final MembershipMapper mapper;

    public TierEligibilityService(TierEligibilityRuleRepository ruleRepository,
                                  MembershipTierRepository tierRepository,
                                  MembershipMapper mapper) {
        this.ruleRepository = ruleRepository;
        this.tierRepository = tierRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public TierEvaluationResponse evaluate(TierEvaluationRequest request) {
        List<TierEligibilityRule> rules = ruleRepository.findByActiveTrue();

        return rules.stream()
                .filter(rule -> matches(rule, request))
                .map(TierEligibilityRule::getTier)
                .max(Comparator.comparing(MembershipTier::getRank))
                .or(() -> tierRepository.findByCode("SILVER"))
                .map(mapper::toTierResponse)
                .map(TierEvaluationResponse::new)
                .orElse(new TierEvaluationResponse(null));
    }

    private boolean matches(TierEligibilityRule rule, TierEvaluationRequest request) {
        String actual = switch (rule.getMetricType()) {
            case MONTHLY_ORDER_COUNT -> String.valueOf(request.monthlyOrderCount());
            case MONTHLY_ORDER_VALUE -> request.monthlyOrderValue().toPlainString();
            case COHORT -> request.cohort() == null ? "" : request.cohort();
        };
        return compare(actual, rule.getThresholdValue(), rule.getOperator(), rule.getMetricType());
    }

    private boolean compare(String actual, String expected, RuleOperator operator, MetricType metricType) {
        if (metricType == MetricType.COHORT) {
            return operator == RuleOperator.EQ && actual.equalsIgnoreCase(expected)
                    || operator == RuleOperator.IN && List.of(expected.split(",")).contains(actual);
        }

        BigDecimal actualNumber = new BigDecimal(actual);
        BigDecimal expectedNumber = new BigDecimal(expected);
        int result = actualNumber.compareTo(expectedNumber);

        return switch (operator) {
            case GTE -> result >= 0;
            case LTE -> result <= 0;
            case EQ -> result == 0;
            case IN -> false;
        };
    }
}
