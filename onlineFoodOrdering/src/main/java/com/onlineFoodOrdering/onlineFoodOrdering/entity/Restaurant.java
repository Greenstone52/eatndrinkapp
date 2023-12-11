package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String taxNo;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "restaurants")
    @JsonIgnore
    private List<Owner> owners;

}
