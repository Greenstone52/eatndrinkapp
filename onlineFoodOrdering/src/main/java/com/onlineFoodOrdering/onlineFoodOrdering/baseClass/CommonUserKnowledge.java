package com.onlineFoodOrdering.onlineFoodOrdering.baseClass;

import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class CommonUserKnowledge {
    private String email;
    private String password;
    private Role role;
}
