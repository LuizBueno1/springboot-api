package com.example.springboot_startspringio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springboot_startspringio.repository.DevDojoUserRepository;

@Service
@RequiredArgsConstructor
public class DevDojoUserDetailsService implements UserDetailsService {
    private final DevDojoUserRepository devDojoUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        return devDojoUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("DevDojo User not found with username: " + username));
    }
}
