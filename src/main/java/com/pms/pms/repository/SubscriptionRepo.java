package com.pms.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pms.pms.model.Subscription;
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    Subscription findByUserId(long user_id);
}
