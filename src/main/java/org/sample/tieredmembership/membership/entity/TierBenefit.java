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
import org.sample.tieredmembership.membership.enums.BenefitType;

import java.time.Instant;

@Entity
@Table(name = "tier_benefits")
public class TierBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private MembershipTier tier;

    @Enumerated(EnumType.STRING)
    private BenefitType benefitType;

    private String benefitValue;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    protected TierBenefit() {
    }

    public TierBenefit(MembershipTier tier, BenefitType benefitType, String benefitValue, boolean active) {
        this.tier = tier;
        this.benefitType = benefitType;
        this.benefitValue = benefitValue;
        this.active = active;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public MembershipTier getTier() { return tier; }
    public BenefitType getBenefitType() { return benefitType; }
    public String getBenefitValue() { return benefitValue; }
    public boolean isActive() { return active; }
}
