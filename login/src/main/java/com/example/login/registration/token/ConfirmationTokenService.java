package com.example.login.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private static final String TOKEN_NOT_FOUND_MSG = "token %s not found";
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token){
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken.isEmpty()) {
            throw new IllegalStateException(String.format(TOKEN_NOT_FOUND_MSG, token));
        }
        return confirmationToken.get();
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.setConfirmedAt(token, LocalDateTime.now());
    }
}
