package com.example.jwtToken.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtToken.domain.AppUser;
import com.example.jwtToken.domain.Role;
import com.example.jwtToken.filter.helper.Signer;
import com.example.jwtToken.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static java.util.Arrays.stream;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(appUserService.getAllAppUsers());
    }

    @PostMapping("/users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveUser(user));
    }

    @PostMapping("role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveRole(role));
    }

    @PostMapping("user/addRole")
    public ResponseEntity<?> addRoleToAppUser(@RequestBody UserToRole userToRole) {
        appUserService.addRoleToAppUser(userToRole.getUsername(), userToRole.getRolename());
        return ResponseEntity.ok().build();
    }

    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length()); // remove Bearer from string

                Algorithm algorithm = Signer.signHMAC256();
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser user = appUserService.getUser(username);
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            } catch (Exception e){
                log.error("error loging in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new IllegalStateException("Refresh token missing");
        }
    }
}

@Data
class UserToRole {
    private final String username;
    private final String rolename;
}
