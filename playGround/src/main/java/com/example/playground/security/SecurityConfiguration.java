package com.example.playground.security;

import com.example.playground.models.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.playground.models.Role.ADMIN;

@EnableWebSecurity // tell spring this is a web security configuration
@org.springframework.context.annotation.Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    PasswordEncoder passwordEncoder;
    UserDetailsService userDetailsService;

    /**
     * Responsible for authorization
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/admin").hasRole(ADMIN.toString())
                .antMatchers("/user").hasAnyRole(ADMIN.toString(), Role.USER.toString())
                .antMatchers("/").permitAll()
                .and().formLogin();
        /*
        http.authorizeRequests().antMatchers("/admin").hasRole(Role.ADMIN.toString());
        http.authorizeRequests().antMatchers("/user").hasRole(Role.USER.toString());
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.formLogin();
*/
    }

    // insert into recipe_users(id, email, firstname, is_active, lastname, password, role) values(1, '123', '123', true, '123', '{noop}123', 'ADMIN');


    /**
     * Responsible for authentication
     * @param auth builder for the specific auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
