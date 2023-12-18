package com.gleb.vinnikov.social_network.auth.services;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findFirstByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user with ")); // TODO: 05.06.2023 readable error
    }
}
