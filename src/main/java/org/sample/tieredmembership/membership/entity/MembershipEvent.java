package org.sample.tieredmembership.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.sample.tieredmembership.membership.enums.MembershipEventType;

import java.time.Instant;

@Entity
@Table(name = "membership_events")
public class MembershipEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long membershipId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private MembershipEventType eventType;

    private Long oldOfferingId;
    private Long newOfferingId;
    private String reason;
    private Instant createdAt;

    protected MembershipEvent() {
    }

    public MembershipEvent(Long membershipId, Long userId, MembershipEventType eventType,
                           Long oldOfferingId, Long newOfferingId, String reason) {
        this.membershipId = membershipId;
        this.userId = userId;
        this.eventType = eventType;
        this.oldOfferingId = oldOfferingId;
        this.newOfferingId = newOfferingId;
        this.reason = reason;
        this.createdAt = Instant.now();
    }
}
