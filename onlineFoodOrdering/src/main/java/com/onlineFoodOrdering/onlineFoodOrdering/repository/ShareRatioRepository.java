package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.compositeKey.ShareRatioKey;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.ShareRatio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRatioRepository extends JpaRepository<ShareRatio, ShareRatioKey> {
    List<ShareRatio> findShareRatioByRestaurantId(Long restaurantId);
}
