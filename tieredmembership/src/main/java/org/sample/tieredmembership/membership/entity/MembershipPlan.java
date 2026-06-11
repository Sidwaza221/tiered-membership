package org.sample.tieredmembership.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "membership_plans")
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private Integer durationInMonths;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    protected MembershipPlan() {
    }

    public MembershipPlan(String code, String name, Integer durationInMonths, boolean active) {
        this.code = code;
        this.name = name;
        this.durationInMonths = durationInMonths;
        this.active = active;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public Integer getDurationInMonths() { return durationInMonths; }
    public boolean isActive() { return active; }
}
