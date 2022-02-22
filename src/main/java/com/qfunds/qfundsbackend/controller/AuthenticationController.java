package com.qfunds.qfundsbackend.controller;

import com.qfunds.qfundsbackend.config.auth.JwtTokenProvider;
import com.qfunds.qfundsbackend.error.BadLoginException;
import com.qfunds.qfundsbackend.error.UserAlreadyExistsException;
import com.qfunds.qfundsbackend.model.User;
import com.qfunds.qfundsbackend.service.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Validated
@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody AuthRequest req) {
        System.out.println("attempting to sign in with " + req.getEmail() + " and " + req.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            Optional<User> user = userService.findByEmail(req.getEmail());
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("Username " +  req.getEmail() + " not found");
            }

            return ok(getJwtAssignedResponseFromUser(user.get()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    public User createUser(@RequestBody SignupRequest req) {
        if (StringUtils.isBlank(req.getEmail()) || StringUtils.isBlank(req.getPassword())) {
            throw new BadLoginException();
        }
        if (userService.existsByEmail(req.getEmail())) {
            throw new UserAlreadyExistsException(req.getEmail());
        }
        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(User.Role.USER);
        u.setCompany(req.getCompany());

        return userService.saveUser(u);
    }

    public Map<String, Object> getJwtAssignedResponseFromUser(User user) {
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());

        Map<String, Object> res = new HashMap<>();
        res.put("id", user.getId());
        res.put("user", user);
        res.put("token", token);

        return res;
    }

    @Data
    @NoArgsConstructor
    static
    class SignupRequest implements Serializable {
        private String email;
        private String password;
        private String company;
    }

    @Data
    @NoArgsConstructor
    static
    class AuthRequest implements Serializable {
        private String email;
        private String password;
    }


}