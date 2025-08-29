// package com.book_micro.profile_service.configuration;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// http.csrf()
// .disable()
// .authorizeHttpRequests(
// auth -> auth.anyRequest().permitAll() // Cho phép tất cả request (tạm thời)
// );
// return http.build();
// }
// }
