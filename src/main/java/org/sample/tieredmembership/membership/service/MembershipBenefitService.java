package org.sample.tieredmembership.membership.service;

import org.sample.tieredmembership.membership.dto.BenefitResponse;
import org.sample.tieredmembership.membership.entity.UserMembership;
import org.sample.tieredmembership.membership.enums.MembershipStatus;
import org.sample.tieredmembership.membership.repository.TierBenefitRepository;
import org.sample.tieredmembership.membership.repository.UserMembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class MembershipBenefitService {

    private final UserMembershipRepository membershipRepository;
    private final TierBenefitRepository benefitRepository;
    private final MembershipMapper mapper;

    public MembershipBenefitService(UserMembershipRepository membershipRepository,
                                    TierBenefitRepository benefitRepository,
                                    MembershipMapper mapper) {
        this.membershipRepository = membershipRepository;
        this.benefitRepository = benefitRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<BenefitResponse> getBenefitsForUser(Long userId) {
        return membershipRepository.findFirstByUserIdAndStatus(userId, MembershipStatus.ACTIVE)
                .filter(membership -> membership.getEndDate().isAfter(Instant.now()))
                .map(this::getBenefitsForMembership)
                .orElse(List.of());
    }

    public List<BenefitResponse> getBenefitsForMembership(UserMembership membership) {
        Long tierId = membership.getOffering().getTier().getId();
        return benefitRepository.findByTierIdAndActiveTrue(tierId)
                .stream()
                .map(mapper::toBenefitResponse)
                .toList();
    }
}
