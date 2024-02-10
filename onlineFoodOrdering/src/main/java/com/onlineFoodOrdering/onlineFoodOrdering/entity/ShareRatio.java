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

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("ownerId")
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private double shareRatio;
}
