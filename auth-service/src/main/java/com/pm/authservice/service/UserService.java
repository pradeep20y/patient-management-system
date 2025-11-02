package com.pm.authservice.service;

import com.pm.authservice.model.User;
import com.pm.authservice.repo.AuthRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final AuthRepo authRepo;
    public UserService(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }
    public Optional<User> findByEmail(String email) {
        return authRepo.findByEmail(email);
    }
}
