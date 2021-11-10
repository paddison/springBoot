package com.example.jwtToken.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data// gives us getters and setters etc.
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = AUTO) // AUTO is usually default
    private Long id;
    private String name;
    private String username;
    private String password;

    // FetchType.EAGER means that each time a user is loaded from db
    // it will always fetch all the roles
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
