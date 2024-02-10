package com.onlineFoodOrdering.onlineFoodOrdering.compositeKey;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ShareRatioKey implements Serializable {
    private Long ownerId;
    private Long restaurantId;
}
