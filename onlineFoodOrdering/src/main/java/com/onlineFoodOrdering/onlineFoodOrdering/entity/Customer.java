package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineFoodOrdering.onlineFoodOrdering.baseClass.CommonUserKnowledge;
import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends CommonUserKnowledge {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    @JsonIgnore
    private Card card;

    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "details_of_user_id")
    @JsonIgnore
    private DetailsOfUser detailsOfUser;

    @Override
    public void setRole(Role role) {
        super.setRole(Role.CUSTOMER);
    }

    @Transient
    private int totalSpendMoney;

    @Transient
    private int totalNumberOfOrder;
}
