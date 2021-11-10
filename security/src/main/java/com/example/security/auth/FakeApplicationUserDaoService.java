package com.example.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

import static com.example.security.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDAO{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter((applicationUser -> username.equals(applicationUser.getUsername())))
                .findFirst();
    }

    private Set<ApplicationUser> getApplicationUsers() {
        Set<ApplicationUser> applicationUsers = Set.of(
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        "annasmith",
                        passwordEncoder.encode("123"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(ADMIN.getGrantedAuthorities(),
                        "linda",
                        passwordEncoder.encode("123"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(ADMINTRAINEE.getGrantedAuthorities(),
                        "tom",
                        passwordEncoder.encode("123"),
                        true,
                        true,
                        true,
                        true
                )

        );

        return applicationUsers;
    }
}
