package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineFoodOrdering.onlineFoodOrdering.baseClass.CommonUserKnowledge;
import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manager_admin")
@Data
public class ManagerAdmin extends CommonUserKnowledge {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "details_of_user_id")
    @JsonIgnore
    private DetailsOfUser detailsOfUser;
}
