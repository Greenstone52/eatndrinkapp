package com.onlineFoodOrdering.onlineFoodOrdering.security.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Long id;

    @Column(unique = true)
    public String token;

    public boolean expired;
    public boolean revoked;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
