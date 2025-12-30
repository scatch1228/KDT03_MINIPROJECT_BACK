package com.ruby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ruby.config.filter.JWTAuthenticationFilter;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfig;
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(cf->cf.disable())
			.authorizeHttpRequests(auth->auth
					.requestMatchers("/review/**").authenticated()
					.anyRequest().permitAll()
					);
		
		http.formLogin(frm->frm.disable());
		http.httpBasic(bs->bs.disable());
		
		http.addFilter(new JWTAuthenticationFilter(authenticationConfig.getAuthenticationManager()));
		
		return http.build();
	}
}
