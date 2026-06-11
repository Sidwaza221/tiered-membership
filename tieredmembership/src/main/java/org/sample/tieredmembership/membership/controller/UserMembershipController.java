package org.sample.tieredmembership.membership.controller;

import jakarta.validation.Valid;
import org.sample.tieredmembership.membership.dto.BenefitResponse;
import org.sample.tieredmembership.membership.dto.ChangeTierRequest;
import org.sample.tieredmembership.membership.dto.MembershipResponse;
import org.sample.tieredmembership.membership.dto.SubscribeRequest;
import org.sample.tieredmembership.membership.dto.TierEvaluationRequest;
import org.sample.tieredmembership.membership.dto.TierEvaluationResponse;
import org.sample.tieredmembership.membership.service.MembershipBenefitService;
import org.sample.tieredmembership.membership.service.MembershipSubscriptionService;
import org.sample.tieredmembership.membership.service.TierEligibilityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/membership")
public class UserMembershipController {

    private final MembershipSubscriptionService subscriptionService;
    private final MembershipBenefitService benefitService;
    private final TierEligibilityService tierEligibilityService;

    public UserMembershipController(MembershipSubscriptionService subscriptionService,
                                    MembershipBenefitService benefitService,
                                    TierEligibilityService tierEligibilityService) {
        this.subscriptionService = subscriptionService;
        this.benefitService = benefitService;
        this.tierEligibilityService = tierEligibilityService;
    }

    @PostMapping("/subscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public MembershipResponse subscribe(@PathVariable Long userId, @Valid @RequestBody SubscribeRequest request) {
        return subscriptionService.subscribe(userId, request.offeringId());
    }

    @GetMapping("/current")
    public MembershipResponse current(@PathVariable Long userId) {
        return subscriptionService.getCurrent(userId);
    }

    @PatchMapping("/tier")
    public MembershipResponse changeTier(@PathVariable Long userId, @Valid @RequestBody ChangeTierRequest request) {
        return subscriptionService.changeTier(userId, request.offeringId());
    }

    @DeleteMapping("/current")
    public MembershipResponse cancel(@PathVariable Long userId) {
        return subscriptionService.cancel(userId);
    }

    @GetMapping("/benefits")
    public List<BenefitResponse> benefits(@PathVariable Long userId) {
        return benefitService.getBenefitsForUser(userId);
    }

    @PostMapping("/tier/evaluate")
    public TierEvaluationResponse evaluateTier(@PathVariable Long userId,
                                               @Valid @RequestBody TierEvaluationRequest request) {
        return tierEligibilityService.evaluate(request);
    }
}
