package com.pms.pms.repository;

import com.pms.pms.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<Chat, Long> {

    Chat findByName(String name);
}
