package org.sample.tieredmembership.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "membership_tiers")
public class MembershipTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private Integer rank;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    protected MembershipTier() {
    }

    public MembershipTier(String code, String name, Integer rank, boolean active) {
        this.code = code;
        this.name = name;
        this.rank = rank;
        this.active = active;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public Integer getRank() { return rank; }
    public boolean isActive() { return active; }
}
