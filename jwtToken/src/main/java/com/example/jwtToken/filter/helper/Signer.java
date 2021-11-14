package com.example.jwtToken.filter.helper;

import com.auth0.jwt.algorithms.Algorithm;

public class Signer {

    private static final String SECRET = "secret";

    public static Algorithm signHMAC256() {
        return com.auth0.jwt.algorithms.Algorithm.HMAC256(SECRET.getBytes());
    }
}
