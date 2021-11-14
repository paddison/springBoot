package com.example.jwtToken.repository;


import com.example.jwtToken.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository  extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

}
