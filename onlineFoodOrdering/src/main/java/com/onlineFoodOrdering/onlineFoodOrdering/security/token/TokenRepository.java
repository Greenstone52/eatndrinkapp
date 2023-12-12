package com.onlineFoodOrdering.onlineFoodOrdering.security.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
}
