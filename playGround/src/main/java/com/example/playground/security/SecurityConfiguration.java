package com.example.playground.security;

import com.example.playground.appUser.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.servlet.GenericFilter;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity // tell spring this is a web security configuration
@org.springframework.context.annotation.Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    PasswordEncoder passwordEncoder;

    /**
     * Responsible for authorization
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin").hasRole(Role.ADMIN.toString());
        http.authorizeRequests().antMatchers("/user").hasAnyRole(Role.USER.toString(), Role.ADMIN.toString());
        http.authorizeRequests().antMatchers("/**").permitAll().and().addFilterAfter(new HelloFilter(), AuthenticationFilter.class);
        http.formLogin();

    }

    /**
     * Responsible for authentication
     * @param auth builder for the specific auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // create an in memory user
        //PasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("123"))
                .roles(Role.USER.toString())
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("123"))
                .roles(Role.ADMIN.toString());
    }

}
