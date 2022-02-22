package com.qfunds.qfundsbackend.service;


import com.qfunds.qfundsbackend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {



    User saveUser(User user);

    Optional<User> getUserById(String id);

    List<User> getAll();

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}