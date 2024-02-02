package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.onlineFoodOrdering.onlineFoodOrdering.compositeKey.ShareRatioKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shareRatio")
@AllArgsConstructor
@NoArgsConstructor
public class ShareRatio {

    @EmbeddedId
    private ShareRatioKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ownerId")
    @JoinColumn(name = "ownerId")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurant;

    private double shareRatio;
}
