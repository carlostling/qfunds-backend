package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.error.EntityDoesNotExistException;
import com.qfunds.qfundsbackend.model.User;
import com.qfunds.qfundsbackend.repository.UserRepository;
import com.qfunds.qfundsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (!user.isPresent()) {
            throw new EntityDoesNotExistException("User not found" + username, User.class);
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.get().getRole().name());
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(), user.get().getPassword(), Collections.singletonList(authority));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
