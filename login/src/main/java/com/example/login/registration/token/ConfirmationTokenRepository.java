package com.example.login.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    //@Query("SELECT ct from ConfirmationToken ct where ct.token = ?1")
    Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE ConfirmationToken ct SET ct.confirmedAt = ?2 where ct.token = ?1")
    void setConfirmedAt(String token, LocalDateTime confirmedAt);
}
