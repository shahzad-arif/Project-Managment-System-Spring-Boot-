package com.pms.pms.repository;

import com.pms.pms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User , Long> {
    User findByEmail(String email);
}
