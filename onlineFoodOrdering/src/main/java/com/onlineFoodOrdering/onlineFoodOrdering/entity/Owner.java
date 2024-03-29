package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owner")
@Getter
@Setter
public class Owner {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //@OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "ibanNo")
    //@JsonIgnore
    //private BankAccount bankAccount;

    //@ManyToMany(fetch = FetchType.LAZY)
    //@JoinTable(
    //                name = "owners_restaurants",
    //                joinColumns = @JoinColumn(name = "ownerId"),
    //                inverseJoinColumns = @JoinColumn(name = "restaurantId")
    //)
    //private List<Restaurant> restaurants;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL,mappedBy = "owner")
    @JsonIgnore
    private List<ShareRatio> shareRatios;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "detailsOfUserId")
    @JsonIgnore
    private DetailsOfUser detailsOfUser;

    //@Override
    //public void setRole(Role role){
    //    super.setRole(Role.OWNER);
    //}

    private double balance;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}