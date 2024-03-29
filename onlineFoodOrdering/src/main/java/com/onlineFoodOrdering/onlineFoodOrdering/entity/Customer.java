package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //@OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    //@JoinColumn(name = "cardId")
    //@JsonIgnore
    //private Card card;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "detailsOfUserId")
    @JsonIgnore
    private DetailsOfUser detailsOfUser;

    //@Override
    //public void setRole(Role role) {
    //    super.setRole(Role.CUSTOMER);
    //}

    @JsonIgnore
    private double totalSpendMoney;

    @JsonIgnore
    private int totalNumberOfOrder;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
