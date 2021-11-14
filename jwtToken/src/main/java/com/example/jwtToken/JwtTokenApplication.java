package com.example.jwtToken;

import com.example.jwtToken.domain.AppUser;
import com.example.jwtToken.domain.Role;
import com.example.jwtToken.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JwtTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtTokenApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
}

	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return (args -> {
			appUserService.saveRole(new Role(null, "ROLE_USER"));
			appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
			appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
			appUserService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			appUserService.saveUser(new AppUser(null, "Patsy", "paddison", "123", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Jim", "jim123", "123", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "James", "jami", "123", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "John", "johnny", "123", new ArrayList<>()));

			appUserService.addRoleToAppUser("paddison", "ROLE_SUPER_ADMIN");
			appUserService.addRoleToAppUser("paddison", "ROLE_MANAGER");
			appUserService.addRoleToAppUser("jim123", "ROLE_USER");
			appUserService.addRoleToAppUser("jami", "ROLE_MANAGER");
			appUserService.addRoleToAppUser("johnny", "ROLE_ADMIN");
		});
	}
}
