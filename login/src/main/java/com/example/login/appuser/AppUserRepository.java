package com.example.login.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository // not necessary a repo, but considered good practice
@Transactional
public interface AppUserRepository extends JpaRepository<AppUser, Long> { //AppUser -> Table, Long -> Id

    Optional<AppUser> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE AppUser au SET au.enabled = true where au.id = ?1")
    void enableAppUser(Long appUserId);

}
