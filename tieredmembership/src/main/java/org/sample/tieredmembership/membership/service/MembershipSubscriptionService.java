package org.sample.tieredmembership.membership.service;

import org.sample.tieredmembership.membership.dto.BenefitResponse;
import org.sample.tieredmembership.membership.dto.MembershipResponse;
import org.sample.tieredmembership.membership.entity.MembershipEvent;
import org.sample.tieredmembership.membership.entity.MembershipOffering;
import org.sample.tieredmembership.membership.entity.UserMembership;
import org.sample.tieredmembership.membership.enums.MembershipEventType;
import org.sample.tieredmembership.membership.enums.MembershipStatus;
import org.sample.tieredmembership.membership.exception.BadRequestException;
import org.sample.tieredmembership.membership.exception.NotFoundException;
import org.sample.tieredmembership.membership.repository.MembershipEventRepository;
import org.sample.tieredmembership.membership.repository.MembershipOfferingRepository;
import org.sample.tieredmembership.membership.repository.UserMembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MembershipSubscriptionService {

    private final MembershipOfferingRepository offeringRepository;
    private final UserMembershipRepository membershipRepository;
    private final MembershipEventRepository eventRepository;
    private final MembershipBenefitService benefitService;
    private final MembershipMapper mapper;

    public MembershipSubscriptionService(MembershipOfferingRepository offeringRepository,
                                         UserMembershipRepository membershipRepository,
                                         MembershipEventRepository eventRepository,
                                         MembershipBenefitService benefitService,
                                         MembershipMapper mapper) {
        this.offeringRepository = offeringRepository;
        this.membershipRepository = membershipRepository;
        this.eventRepository = eventRepository;
        this.benefitService = benefitService;
        this.mapper = mapper;
    }

    @Transactional
    public MembershipResponse subscribe(Long userId, Long offeringId) {
        if (membershipRepository.existsByUserIdAndStatus(userId, MembershipStatus.ACTIVE)) {
            throw new BadRequestException("User already has an active membership");
        }

        MembershipOffering offering = getActiveOffering(offeringId);
        Instant start = Instant.now();
        Instant end = start.plus(offering.getPlan().getDurationInMonths() * 30L, ChronoUnit.DAYS);

        UserMembership membership = membershipRepository.save(new UserMembership(userId, offering, start, end));
        eventRepository.save(new MembershipEvent(membership.getId(), userId, MembershipEventType.SUBSCRIBED,
                null, offering.getId(), "User subscribed"));

        return toResponse(membership);
    }

    @Transactional(readOnly = true)
    public MembershipResponse getCurrent(Long userId) {
        UserMembership membership = getActiveMembership(userId);
        return toResponse(membership);
    }

    @Transactional
    public MembershipResponse changeTier(Long userId, Long newOfferingId) {
        UserMembership membership = getActiveMembership(userId);
        MembershipOffering oldOffering = membership.getOffering();
        MembershipOffering newOffering = getActiveOffering(newOfferingId);

        if (!oldOffering.getPlan().getId().equals(newOffering.getPlan().getId())) {
            throw new BadRequestException("Changing plan is not supported in tier change API");
        }
        if (oldOffering.getId().equals(newOffering.getId())) {
            throw new BadRequestException("User is already on this offering");
        }

        MembershipEventType eventType = newOffering.getTier().getRank() > oldOffering.getTier().getRank()
                ? MembershipEventType.UPGRADED
                : MembershipEventType.DOWNGRADED;

        membership.changeOffering(newOffering);
        eventRepository.save(new MembershipEvent(membership.getId(), userId, eventType,
                oldOffering.getId(), newOffering.getId(), "Tier changed"));

        return toResponse(membership);
    }

    @Transactional
    public MembershipResponse cancel(Long userId) {
        UserMembership membership = getActiveMembership(userId);
        Long oldOfferingId = membership.getOffering().getId();
        membership.cancel();
        eventRepository.save(new MembershipEvent(membership.getId(), userId, MembershipEventType.CANCELLED,
                oldOfferingId, null, "User cancelled membership"));
        return toResponse(membership);
    }

    private UserMembership getActiveMembership(Long userId) {
        UserMembership membership = membershipRepository.findFirstByUserIdAndStatus(userId, MembershipStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("No active membership found for user"));

        if (membership.getEndDate().isBefore(Instant.now())) {
            membership.expire();
            throw new NotFoundException("Membership has expired");
        }
        return membership;
    }

    private MembershipOffering getActiveOffering(Long offeringId) {
        MembershipOffering offering = offeringRepository.findWithPlanAndTierById(offeringId)
                .orElseThrow(() -> new NotFoundException("Membership offering not found"));
        if (!offering.isActive()) {
            throw new BadRequestException("Membership offering is not active");
        }
        return offering;
    }

    private MembershipResponse toResponse(UserMembership membership) {
        List<BenefitResponse> benefits = benefitService.getBenefitsForMembership(membership);
        return mapper.toMembershipResponse(membership, benefits);
    }
}
