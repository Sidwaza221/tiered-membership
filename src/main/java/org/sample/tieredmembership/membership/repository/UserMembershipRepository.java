package org.sample.tieredmembership.membership.repository;

import org.sample.tieredmembership.membership.entity.UserMembership;
import org.sample.tieredmembership.membership.enums.MembershipStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
    boolean existsByUserIdAndStatus(Long userId, MembershipStatus status);

    @EntityGraph(attributePaths = {"offering", "offering.plan", "offering.tier"})
    Optional<UserMembership> findFirstByUserIdAndStatus(Long userId, MembershipStatus status);
}
