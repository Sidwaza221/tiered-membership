package org.sample.tieredmembership.membership.repository;

import org.sample.tieredmembership.membership.entity.MembershipTier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipTierRepository extends JpaRepository<MembershipTier, Long> {
    List<MembershipTier> findByActiveTrueOrderByRankAsc();
    Optional<MembershipTier> findByCode(String code);
}
