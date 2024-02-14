package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "foodDrink")
@Entity
public class FoodDrink {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "menuId")
    @JsonIgnore
    private Menu menu;

    @Column(unique = true)
    private String name;

    private double salesPrice;
    private double costPrice;
    private double profit;

    public FoodDrink(Menu menu, String name, double salesPrice, double costPrice){
        this.menu = menu;
        this.name = name;
        this.salesPrice = salesPrice;
        this.costPrice = costPrice;
        this.profit = this.salesPrice - this.costPrice;
    }
}
