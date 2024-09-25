package com.pms.pms.service;

import com.pms.pms.model.PlanType;
import com.pms.pms.model.Subscription;
import com.pms.pms.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user) throws Exception;
    Subscription getSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId , PlanType planType) throws Exception;
    boolean isValidSubscription(Subscription subscription) throws Exception;
}
