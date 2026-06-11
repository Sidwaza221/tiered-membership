package org.sample.tieredmembership.membership.config;

import org.sample.tieredmembership.membership.entity.MembershipOffering;
import org.sample.tieredmembership.membership.entity.MembershipPlan;
import org.sample.tieredmembership.membership.entity.MembershipTier;
import org.sample.tieredmembership.membership.entity.TierBenefit;
import org.sample.tieredmembership.membership.entity.TierEligibilityRule;
import org.sample.tieredmembership.membership.enums.BenefitType;
import org.sample.tieredmembership.membership.enums.MetricType;
import org.sample.tieredmembership.membership.enums.RuleOperator;
import org.sample.tieredmembership.membership.repository.MembershipOfferingRepository;
import org.sample.tieredmembership.membership.repository.MembershipPlanRepository;
import org.sample.tieredmembership.membership.repository.MembershipTierRepository;
import org.sample.tieredmembership.membership.repository.TierBenefitRepository;
import org.sample.tieredmembership.membership.repository.TierEligibilityRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DemoDataLoader implements CommandLineRunner {

    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final MembershipOfferingRepository offeringRepository;
    private final TierBenefitRepository benefitRepository;
    private final TierEligibilityRuleRepository ruleRepository;

    public DemoDataLoader(MembershipPlanRepository planRepository,
                          MembershipTierRepository tierRepository,
                          MembershipOfferingRepository offeringRepository,
                          TierBenefitRepository benefitRepository,
                          TierEligibilityRuleRepository ruleRepository) {
        this.planRepository = planRepository;
        this.tierRepository = tierRepository;
        this.offeringRepository = offeringRepository;
        this.benefitRepository = benefitRepository;
        this.ruleRepository = ruleRepository;
    }

    @Override
    public void run(String... args) {
        if (planRepository.count() > 0) {
            return;
        }

        MembershipPlan monthly = planRepository.save(new MembershipPlan("MONTHLY", "Monthly", 1, true));
        MembershipPlan quarterly = planRepository.save(new MembershipPlan("QUARTERLY", "Quarterly", 3, true));
        MembershipPlan yearly = planRepository.save(new MembershipPlan("YEARLY", "Yearly", 12, true));

        MembershipTier silver = tierRepository.save(new MembershipTier("SILVER", "Silver", 1, true));
        MembershipTier gold = tierRepository.save(new MembershipTier("GOLD", "Gold", 2, true));
        MembershipTier platinum = tierRepository.save(new MembershipTier("PLATINUM", "Platinum", 3, true));

        saveOffering(monthly, silver, "199");
        saveOffering(monthly, gold, "299");
        saveOffering(monthly, platinum, "399");
        saveOffering(quarterly, silver, "499");
        saveOffering(quarterly, gold, "799");
        saveOffering(quarterly, platinum, "999");
        saveOffering(yearly, silver, "1499");
        saveOffering(yearly, gold, "2499");
        saveOffering(yearly, platinum, "3499");

        saveBenefits(silver, "false", "5");
        saveBenefits(gold, "true", "10");
        saveBenefits(platinum, "true", "15");

        ruleRepository.save(new TierEligibilityRule(gold, MetricType.MONTHLY_ORDER_COUNT, RuleOperator.GTE, "5", true));
        ruleRepository.save(new TierEligibilityRule(gold, MetricType.MONTHLY_ORDER_VALUE, RuleOperator.GTE, "5000", true));
        ruleRepository.save(new TierEligibilityRule(platinum, MetricType.MONTHLY_ORDER_COUNT, RuleOperator.GTE, "10", true));
        ruleRepository.save(new TierEligibilityRule(platinum, MetricType.MONTHLY_ORDER_VALUE, RuleOperator.GTE, "15000", true));
        ruleRepository.save(new TierEligibilityRule(platinum, MetricType.COHORT, RuleOperator.EQ, "VIP", true));
    }

    private void saveOffering(MembershipPlan plan, MembershipTier tier, String price) {
        offeringRepository.save(new MembershipOffering(plan, tier, new BigDecimal(price), "INR", true));
    }

    private void saveBenefits(MembershipTier tier, String freeDelivery, String discountPercent) {
        benefitRepository.save(new TierBenefit(tier, BenefitType.FREE_DELIVERY, freeDelivery, true));
        benefitRepository.save(new TierBenefit(tier, BenefitType.DISCOUNT_PERCENT, discountPercent, true));
    }
}
