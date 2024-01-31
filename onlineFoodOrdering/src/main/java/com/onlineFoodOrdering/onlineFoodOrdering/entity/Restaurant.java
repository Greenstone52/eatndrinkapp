package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Restaurant {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @Column(unique = true)
    private String taxNo;

    //@OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    //@JoinColumn(name = "addressId")
    //@JsonIgnore
    //private Address address;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "restaurants")
    @JsonIgnore
    private List<Owner> owners;

    //@OneToOne(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    //@JoinColumn(name = "ibanNo")
    //@JsonIgnore
    //private BankAccount bankAccount;

    private String province;
    private String district;

    @JsonIgnore
    private double netProfit;

    @JsonIgnore
    private double netEndorsement;
    private String password;
}
