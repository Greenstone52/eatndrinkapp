package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "card")
public class Card {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private short month;
    private short year;
    private String cardNumber;
    private short CVC;
    private String name;
    private String Surname;
}
