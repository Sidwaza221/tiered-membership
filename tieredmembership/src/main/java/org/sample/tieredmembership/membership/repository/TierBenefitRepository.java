package org.sample.tieredmembership.membership.repository;

import org.sample.tieredmembership.membership.entity.TierBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TierBenefitRepository extends JpaRepository<TierBenefit, Long> {
    List<TierBenefit> findByTierIdAndActiveTrue(Long tierId);
}
