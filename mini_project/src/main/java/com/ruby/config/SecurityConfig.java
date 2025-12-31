package com.ruby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import com.ruby.config.filter.JWTAuthenticationFilter;
import com.ruby.config.filter.JWTAuthorizationFilter;
import com.ruby.persistence.MemberRepo;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfig;
	private final MemberRepo mrepo;
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(cf->cf.disable())
			.httpBasic(bs->bs.disable())
			.formLogin(frm->frm.disable())
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth->auth
					.requestMatchers("/review/**").authenticated()
					.anyRequest().permitAll()
					);
		
		http.addFilter(new JWTAuthenticationFilter(authenticationConfig.getAuthenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFilter(mrepo), AuthorizationFilter.class);
		
		return http.build();
	}
}
