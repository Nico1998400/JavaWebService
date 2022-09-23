package com.example.javawebserviceproject.security;

import com.example.javawebserviceproject.entities.Role;
import com.example.javawebserviceproject.services.AuthServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;
    private final AuthServices authServices;

    public SecurityConfig(
            UserDetailsService userDetailsService,
            JwtRequestFilter jwtRequestFilter,
            AuthServices authServices1) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.authServices = authServices1;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("/api/auth/**","swagger-ui/**","/v3/api-docs/**").permitAll()
                        .antMatchers("/api/users/**").hasRole(Role.ADMIN.toString())
                        .antMatchers("/api/users/**").permitAll()//hasAnyRole(Role.ADMIN.toString(), Role.ADMIN.toString())
                        //.antMatchers("/api/users/{id}").access("@authServices.idMatchesUser(#id)")
                        .anyRequest().authenticated())
                .userDetailsService(userDetailsService)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter,
                UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }


}
