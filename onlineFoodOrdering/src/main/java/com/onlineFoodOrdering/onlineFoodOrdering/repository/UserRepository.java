package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.User;
import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUserDetailsIdAndRole(Long userDetailsId, Role role);
}
