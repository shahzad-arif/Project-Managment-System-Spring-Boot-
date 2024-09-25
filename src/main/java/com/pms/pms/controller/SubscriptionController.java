package com.pms.pms.controller;

import com.pms.pms.model.PlanType;
import com.pms.pms.model.Subscription;
import com.pms.pms.model.User;
import com.pms.pms.service.SubscriptionService;
import com.pms.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    @Autowired
    private  SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        if (user == null) throw  new Exception("User not found");
        Subscription subscription = subscriptionService.getSubscription(user.getId());
        return new ResponseEntity<>(subscription , HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(
            @RequestHeader("Authorization") String token,
            @RequestParam PlanType planType
            ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        if (user == null) throw  new Exception("User not found");
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);
        return new ResponseEntity<>(subscription , HttpStatus.OK);
    }
}
