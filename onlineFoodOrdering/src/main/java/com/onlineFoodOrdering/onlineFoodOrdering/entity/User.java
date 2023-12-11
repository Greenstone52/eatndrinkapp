package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineFoodOrdering.onlineFoodOrdering.baseClass.CommonUserKnowledge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@Data
public class User extends CommonUserKnowledge {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "details_of_user_id")
    @JsonIgnore
    private DetailsOfUser detailsOfUser;
}
