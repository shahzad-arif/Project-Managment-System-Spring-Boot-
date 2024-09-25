package com.pms.pms.service;

import com.pms.pms.config.JwtProvider;
import com.pms.pms.model.User;
import com.pms.pms.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("User not found");

        }

        return user.get();
    }

    @Override
    public User updateUserProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize() + number);
         return  userRepo.save(user);

    }
}
