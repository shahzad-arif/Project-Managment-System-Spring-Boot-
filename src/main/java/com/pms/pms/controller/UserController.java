package com.pms.pms.controller;

import com.pms.pms.model.User;
import com.pms.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private   UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        if (user == null) throw  new Exception("User not founf");
        return new ResponseEntity<>(user , HttpStatus.OK);
    }
}
