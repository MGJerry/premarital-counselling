package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.request.UserRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    public User register(UserRequest userRequest){


        User user = new User();

        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFullName(userRequest.getFullName());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());

        User newAccount = authenticationRepository.save(user);
        return newAccount;
    }





    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            User tempUser = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            tempUser.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new NullPointerException("Wrong email or password");
        }
        User user = authenticationRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        String token = tokenService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail(user.getEmail());
        authenticationResponse.setId(user.getId());
        authenticationResponse.setFullName(user.getFullName());
        authenticationResponse.setUsername(user.getUsername());
        authenticationResponse.setPhone(user.getPhone());
        authenticationResponse.setRole(user.getRole());
        authenticationResponse.setToken(token);
        return authenticationResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) authenticationRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }
}
