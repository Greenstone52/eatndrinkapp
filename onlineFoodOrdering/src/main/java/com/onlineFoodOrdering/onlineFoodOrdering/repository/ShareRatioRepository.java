package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.compositeKey.ShareRatioKey;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.ShareRatio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRatioRepository extends JpaRepository<ShareRatio, ShareRatioKey> {
}
