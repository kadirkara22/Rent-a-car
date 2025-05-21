package com.kadirkara.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kadirkara.handler.AuthEntryPoint;
import com.kadirkara.jwt.JWTAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public static final String REGISTER = "/register";
	public static final String AUTHENTICATE = "/login";
	public static final String AUTHENTICATE_ADMIN = "/login-admin";
	public static final String REFRESH_TOKEN = "/refreshToken";
	
	private final AuthenticationProvider authenticationProvider;
	
	private final AuthEntryPoint authEntryPoint;
	
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	
	public SecurityConfig(AuthenticationProvider authenticationProvider,
			JWTAuthenticationFilter jwtAuthenticationFilter,AuthEntryPoint authEntryPoint) {
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthenticationFilter=jwtAuthenticationFilter;
		this.authEntryPoint=authEntryPoint;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests(request-> 
		request.requestMatchers(REGISTER , AUTHENTICATE , REFRESH_TOKEN,AUTHENTICATE_ADMIN).permitAll()
		.requestMatchers("/api/customer/**").permitAll()
		.requestMatchers("/api/admin/**").permitAll()
		.anyRequest()
		.authenticated())
		.exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
		.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
