package com.pms.pms.service;

import com.pms.pms.model.PlanType;
import com.pms.pms.model.Subscription;
import com.pms.pms.model.User;
import com.pms.pms.repository.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionRepo   subscriptionRepo;
    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        return subscriptionRepo.save(subscription);

    }

    @Override
    public Subscription getSubscription(Long userId) throws Exception {
        User user = userService.findUserById(userId);
        if (user == null) throw new Exception("User not found");

        Subscription subscription = subscriptionRepo.findByUserId(userId);
        if(!isValidSubscription(subscription)) {
            subscription.setPlanType(PlanType.FREE);
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setStartDate(LocalDate.now());

        }
        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {

        Subscription subscription = subscriptionRepo.findByUserId(userId);
        subscription.setPlanType(planType);
        subscriptionRepo.save(subscription);
        subscription.setStartDate(LocalDate.now());
        if (planType == PlanType.ANNUALLY) {
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }
        else {
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }

        return subscriptionRepo.save(subscription);
    }

    @Override
    public boolean isValidSubscription(Subscription subscription) {
        if (subscription.getPlanType() == PlanType.FREE) {
            return true;
        }
        LocalDate startDate = subscription.getStartDate();
        LocalDate endDate = subscription.getGetSubscriptionEndDate();

        return endDate.isAfter(startDate) || endDate.isEqual(startDate);
    }
}
