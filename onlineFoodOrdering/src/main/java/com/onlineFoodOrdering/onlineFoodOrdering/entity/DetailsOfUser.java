package com.onlineFoodOrdering.onlineFoodOrdering.entity;

import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "details_of_user")
public class DetailsOfUser extends User{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String gsm;
    private String birthdate;
}
