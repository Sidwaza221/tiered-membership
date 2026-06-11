package org.sample.tieredmembership.membership.service;

import org.sample.tieredmembership.membership.dto.OfferingResponse;
import org.sample.tieredmembership.membership.dto.PlanResponse;
import org.sample.tieredmembership.membership.dto.TierResponse;
import org.sample.tieredmembership.membership.repository.MembershipOfferingRepository;
import org.sample.tieredmembership.membership.repository.MembershipPlanRepository;
import org.sample.tieredmembership.membership.repository.MembershipTierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembershipCatalogService {

    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final MembershipOfferingRepository offeringRepository;
    private final MembershipMapper mapper;

    public MembershipCatalogService(MembershipPlanRepository planRepository,
                                    MembershipTierRepository tierRepository,
                                    MembershipOfferingRepository offeringRepository,
                                    MembershipMapper mapper) {
        this.planRepository = planRepository;
        this.tierRepository = tierRepository;
        this.offeringRepository = offeringRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getPlans() {
        return planRepository.findByActiveTrue().stream().map(mapper::toPlanResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<TierResponse> getTiers() {
        return tierRepository.findByActiveTrueOrderByRankAsc().stream().map(mapper::toTierResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<OfferingResponse> getOfferings() {
        return offeringRepository.findByActiveTrue().stream().map(mapper::toOfferingResponse).toList();
    }
}
