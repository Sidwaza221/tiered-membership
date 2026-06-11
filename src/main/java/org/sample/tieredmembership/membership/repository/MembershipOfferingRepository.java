package org.sample.tieredmembership.membership.repository;

import org.sample.tieredmembership.membership.entity.MembershipOffering;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipOfferingRepository extends JpaRepository<MembershipOffering, Long> {
    @EntityGraph(attributePaths = {"plan", "tier"})
    List<MembershipOffering> findByActiveTrue();

    @EntityGraph(attributePaths = {"plan", "tier"})
    Optional<MembershipOffering> findWithPlanAndTierById(Long id);

    Optional<MembershipOffering> findByPlanIdAndTierIdAndActiveTrue(Long planId, Long tierId);
}
