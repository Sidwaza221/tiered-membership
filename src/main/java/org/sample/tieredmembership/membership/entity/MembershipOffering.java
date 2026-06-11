package org.sample.tieredmembership.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "membership_offerings")
public class MembershipOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private MembershipPlan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private MembershipTier tier;

    private BigDecimal price;
    private String currency;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    protected MembershipOffering() {
    }

    public MembershipOffering(MembershipPlan plan, MembershipTier tier, BigDecimal price, String currency, boolean active) {
        this.plan = plan;
        this.tier = tier;
        this.price = price;
        this.currency = currency;
        this.active = active;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public MembershipPlan getPlan() { return plan; }
    public MembershipTier getTier() { return tier; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public boolean isActive() { return active; }
}
