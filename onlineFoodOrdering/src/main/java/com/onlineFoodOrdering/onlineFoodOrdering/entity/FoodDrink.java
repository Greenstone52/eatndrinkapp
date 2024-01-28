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
@Table(name = "food")
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

    private String name;

    private String type;

    private double salesPrice;
    private double costPrice;
    private double profit;

    public FoodDrink(Menu menu, String name, String type, double salesPrice, double costPrice){
        this.menu = menu;
        this.name = name;
        this.type = type;
        this.salesPrice = salesPrice;
        this.costPrice = costPrice;
        this.profit = salesPrice - costPrice;
    }
}
