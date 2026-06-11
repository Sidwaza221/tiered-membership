package org.sample.tieredmembership.membership.repository;

import org.sample.tieredmembership.membership.entity.TierEligibilityRule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TierEligibilityRuleRepository extends JpaRepository<TierEligibilityRule, Long> {
    @EntityGraph(attributePaths = "tier")
    List<TierEligibilityRule> findByActiveTrue();
}
