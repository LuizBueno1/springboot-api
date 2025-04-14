package com.example.springboot_startspringio.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.springboot_startspringio.service.DevDojoUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DevDojoUserDetailsService devDojoUserDetailsService;

    /**
     * BasicAuthenticationFilter
     * UsernamePasswordAuthenticationFilter
     * DefaultLoginPageGeneratingFilter
     * DefaultLogoutPageGeneratingFilter
     * FilterSecurityInterceptor
     * Authentication -> Authorization
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/animes/admin/**").hasRole("ADMIN")
                .requestMatchers("/animes/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.permitAll())
            .httpBasic(basic -> {})
            .authenticationManager(authenticationManager());
        
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        
        // In-memory user authentication provider
        DaoAuthenticationProvider inMemoryProvider = new DaoAuthenticationProvider();
        inMemoryProvider.setUserDetailsService(inMemoryUserDetailsManager());
        inMemoryProvider.setPasswordEncoder(passwordEncoder());
        
        // Authentication provider for database users
        DaoAuthenticationProvider databaseProvider = new DaoAuthenticationProvider();
        databaseProvider.setUserDetailsService(devDojoUserDetailsService);
        databaseProvider.setPasswordEncoder(passwordEncoder());
        
        providers.add(inMemoryProvider);
        providers.add(databaseProvider);
        
        return new ProviderManager(providers);
    }
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails william = User.builder()
            .username("william2")
            .password(passwordEncoder().encode("academy"))
            .roles("USER", "ADMIN")
            .build();
            
        UserDetails devdojo = User.builder()
            .username("devdojo2")
            .password(passwordEncoder().encode("academy"))
            .roles("USER")
            .build();
            
        log.info("In-memory users created with encoded passwords");
        
        return new InMemoryUserDetailsManager(william, devdojo);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded example: {}", passwordEncoder.encode("academy"));
        return passwordEncoder;
    }
}