package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "card")
public class Card {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Min(1)
    @Max(12)
    private short month;

    @Min(2025)
    @Max(2040)
    private short year;

    @Length(min = 16,max = 16)
    @Column(unique = true)
    private String cardNumber;

    @Min(100)
    @Max(999)
    private short cvc;
    private String name;

    @JsonIgnore
    private double balance = 1000;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customerId")
    @JsonIgnore
    private Customer customer;
}
