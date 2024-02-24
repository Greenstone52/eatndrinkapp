package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manager_admin")
@Data
public class ManagerAdmin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "detailsOfUserId")
    @JsonIgnore
    private DetailsOfUser detailsOfUser;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;
}
