package org.sample.tieredmembership.membership.controller;

import org.sample.tieredmembership.membership.dto.OfferingResponse;
import org.sample.tieredmembership.membership.dto.PlanResponse;
import org.sample.tieredmembership.membership.dto.TierResponse;
import org.sample.tieredmembership.membership.service.MembershipCatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/membership")
public class MembershipCatalogController {

    private final MembershipCatalogService catalogService;

    public MembershipCatalogController(MembershipCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/plans")
    public List<PlanResponse> plans() {
        return catalogService.getPlans();
    }

    @GetMapping("/tiers")
    public List<TierResponse> tiers() {
        return catalogService.getTiers();
    }

    @GetMapping("/offerings")
    public List<OfferingResponse> offerings() {
        return catalogService.getOfferings();
    }
}
