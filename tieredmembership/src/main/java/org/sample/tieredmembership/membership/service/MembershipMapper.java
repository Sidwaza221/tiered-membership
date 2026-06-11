package org.sample.tieredmembership.membership.service;

import org.sample.tieredmembership.membership.dto.BenefitResponse;
import org.sample.tieredmembership.membership.dto.MembershipResponse;
import org.sample.tieredmembership.membership.dto.OfferingResponse;
import org.sample.tieredmembership.membership.dto.PlanResponse;
import org.sample.tieredmembership.membership.dto.TierResponse;
import org.sample.tieredmembership.membership.entity.MembershipOffering;
import org.sample.tieredmembership.membership.entity.MembershipPlan;
import org.sample.tieredmembership.membership.entity.MembershipTier;
import org.sample.tieredmembership.membership.entity.TierBenefit;
import org.sample.tieredmembership.membership.entity.UserMembership;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MembershipMapper {

    public PlanResponse toPlanResponse(MembershipPlan plan) {
        return new PlanResponse(plan.getId(), plan.getCode(), plan.getName(), plan.getDurationInMonths());
    }

    public TierResponse toTierResponse(MembershipTier tier) {
        return new TierResponse(tier.getId(), tier.getCode(), tier.getName(), tier.getRank());
    }

    public OfferingResponse toOfferingResponse(MembershipOffering offering) {
        return new OfferingResponse(
                offering.getId(),
                toPlanResponse(offering.getPlan()),
                toTierResponse(offering.getTier()),
                offering.getPrice(),
                offering.getCurrency()
        );
    }

    public BenefitResponse toBenefitResponse(TierBenefit benefit) {
        return new BenefitResponse(benefit.getBenefitType(), benefit.getBenefitValue());
    }

    public MembershipResponse toMembershipResponse(UserMembership membership, List<BenefitResponse> benefits) {
        return new MembershipResponse(
                membership.getId(),
                membership.getUserId(),
                membership.getStatus(),
                toOfferingResponse(membership.getOffering()),
                membership.getStartDate(),
                membership.getEndDate(),
                membership.getCancelledAt(),
                membership.getVersion(),
                benefits
        );
    }
}
