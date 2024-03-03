package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_table")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    @JsonIgnore
    @NotNull
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurantId")
    @JsonIgnore
    @NotNull
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menuId")
    @JsonIgnore
    @NotNull
    private Menu menu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foodDrinkId")
    @JsonIgnore
    @NotNull
    private FoodDrink foodDrink;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId")
    @JsonIgnore
    @NotNull
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cardId")
    @JsonIgnore
    @NotNull
    private Card card;


    private LocalDateTime date = LocalDateTime.now();
}
