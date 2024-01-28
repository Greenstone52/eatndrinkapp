package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "bank_account")
@Entity
public class BankAccount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String IBAN;
    private String ownerNameAndSurname;
    private double balance;
}
