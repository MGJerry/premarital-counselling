package com.example.premaritalcounselling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Transactional
    public User deactivateUser(long id) {
        User user = authenticationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.seteStatus(EStatus.INACTIVE);
        return authenticationRepository.save(user);
    }

    @Transactional
    public User activateUser(long id) {
        User user = authenticationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.seteStatus(EStatus.APPROVED);
        return authenticationRepository.save(user);
    }
} 