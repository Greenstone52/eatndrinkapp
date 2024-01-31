package com.onlineFoodOrdering.onlineFoodOrdering.compositeKey;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ShareRatioKey implements Serializable {
    private Long ownerId;
    private Long restaurantId;
}
