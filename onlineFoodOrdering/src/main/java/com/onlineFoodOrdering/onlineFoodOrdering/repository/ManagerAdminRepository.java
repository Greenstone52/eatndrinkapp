package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.ManagerAdmin;
import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerAdminRepository extends JpaRepository<ManagerAdmin,Long> {
}
