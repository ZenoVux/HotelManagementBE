package com.devz.hotelmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devz.hotelmanagement.jwt.JwtFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] SECURED_URLs = { "/api/accounts/**", "/api/service-types/**", "/api/services/**",
			"/api/rooms/types/**", "/api/rooms","/api/invoices/**" }; // Url cần bảo vệ theo quyền ADMIN

	private static final String[] UN_SECURED_URLs = { // URL cho phep
			"/auth/login/**","/reset-password/**" };

	@Autowired
	UserDetailService userDetailService;

	@Autowired
	JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeHttpRequests().requestMatchers(UN_SECURED_URLs).permitAll()
				.requestMatchers(HttpMethod.POST, "/api/rooms/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/rooms/**").hasAuthority("ADMIN").requestMatchers(SECURED_URLs)
				.hasAuthority("ADMIN").anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid role");
				}).accessDeniedHandler((request, response, accessDeniedException) -> {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
				}).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

}
