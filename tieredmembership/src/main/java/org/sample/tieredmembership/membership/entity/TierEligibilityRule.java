package org.sample.tieredmembership.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.sample.tieredmembership.membership.enums.MetricType;
import org.sample.tieredmembership.membership.enums.RuleOperator;

import java.time.Instant;

@Entity
@Table(name = "tier_eligibility_rules")
public class TierEligibilityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private MembershipTier tier;

    @Enumerated(EnumType.STRING)
    private MetricType metricType;

    @Enumerated(EnumType.STRING)
    private RuleOperator operator;

    private String thresholdValue;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    protected TierEligibilityRule() {
    }

    public TierEligibilityRule(MembershipTier tier, MetricType metricType, RuleOperator operator,
                               String thresholdValue, boolean active) {
        this.tier = tier;
        this.metricType = metricType;
        this.operator = operator;
        this.thresholdValue = thresholdValue;
        this.active = active;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public MembershipTier getTier() { return tier; }
    public MetricType getMetricType() { return metricType; }
    public RuleOperator getOperator() { return operator; }
    public String getThresholdValue() { return thresholdValue; }
    public boolean isActive() { return active; }
}
