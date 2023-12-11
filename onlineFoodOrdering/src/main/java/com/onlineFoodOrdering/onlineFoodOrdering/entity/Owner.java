package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineFoodOrdering.onlineFoodOrdering.baseClass.CommonUserKnowledge;
import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owner")
@Getter
@Setter
public class Owner extends CommonUserKnowledge {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    @JsonIgnore
    private BankAccount bankAccount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
                    name = "owners_restaurants",
                    joinColumns = @JoinColumn(name = "owner_id"),
                    inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> restaurants;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "details_of_user_id")
    @JsonIgnore
    private DetailsOfUser detailsOfUser;

    @Override
    public void setRole(Role role){
        super.setRole(Role.OWNER);
    }

}
