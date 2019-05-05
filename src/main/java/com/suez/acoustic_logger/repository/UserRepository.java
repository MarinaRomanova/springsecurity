package com.suez.acoustic_logger.repository;

import com.suez.acoustic_logger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
