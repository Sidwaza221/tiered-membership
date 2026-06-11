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
import jakarta.persistence.Version;
import org.sample.tieredmembership.membership.enums.MembershipStatus;

import java.time.Instant;

@Entity
@Table(name = "user_memberships")
public class UserMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offering_id", nullable = false)
    private MembershipOffering offering;

    @Enumerated(EnumType.STRING)
    private MembershipStatus status;

    private Instant startDate;
    private Instant endDate;
    private Instant cancelledAt;

    @Version
    private Long version;

    private Instant createdAt;
    private Instant updatedAt;

    protected UserMembership() {
    }

    public UserMembership(Long userId, MembershipOffering offering, Instant startDate, Instant endDate) {
        this.userId = userId;
        this.offering = offering;
        this.status = MembershipStatus.ACTIVE;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void changeOffering(MembershipOffering newOffering) {
        this.offering = newOffering;
        this.updatedAt = Instant.now();
    }

    public void cancel() {
        this.status = MembershipStatus.CANCELLED;
        this.cancelledAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void expire() {
        this.status = MembershipStatus.EXPIRED;
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public MembershipOffering getOffering() { return offering; }
    public MembershipStatus getStatus() { return status; }
    public Instant getStartDate() { return startDate; }
    public Instant getEndDate() { return endDate; }
    public Instant getCancelledAt() { return cancelledAt; }
    public Long getVersion() { return version; }
}
