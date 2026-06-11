package org.sample.tieredmembership.membership.repository;

import org.sample.tieredmembership.membership.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
    List<MembershipPlan> findByActiveTrue();
    Optional<MembershipPlan> findByCode(String code);
}
