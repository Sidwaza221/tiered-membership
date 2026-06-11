package org.sample.tieredmembership.membership.repository;

import org.sample.tieredmembership.membership.entity.MembershipEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipEventRepository extends JpaRepository<MembershipEvent, Long> {
}
