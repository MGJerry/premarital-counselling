package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.request.UpdateRequest;
import com.example.demo.entity.request.UserRegisterRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import com.example.demo.model.PasswordResetToken;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AuthenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    @Autowired
    private EmailService emailService;

    public User register(UserRegisterRequest userRegisterRequest) {
        User user = new User();

        user.setFullName(userRegisterRequest.getFullName());
        user.setUsername(userRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setEmail(userRegisterRequest.getEmail());
        user.setRole(ERole.ROLE_USER);
        user.seteStatus(EStatus.APPROVED);

        return authenticationRepository.save(user);
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
        User user = authenticationRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
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
        return (UserDetails) authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

    public User updateProfile(UpdateRequest updateRequest, long id) {
        User user = authenticationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(updateRequest.getFullName());
        user.setEmail(updateRequest.getEmail());
        user.setPhone(updateRequest.getPhone());
        user.setBirthday(updateRequest.getBirthday());
        user.setGender(updateRequest.isGender());

        return authenticationRepository.save(user);
    }

    public User getCurrentUser() {
        System.out.println(AuthenUtil.getAuthenticatedUser());
        return AuthenUtil.getAuthenticatedUser();
    }

    public ResponseEntity<String> createPasswordResetTokenForUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            return new ResponseEntity<>("The user with this email doesn't exist", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);

        emailService.sendResetPasswordEmail(email, token);

        return new ResponseEntity<>("Reset password email has been sent!", HttpStatus.OK);
    }

    public ResponseEntity<String> resetPassword(String token, String password) {
        if (!passwordTokenRepository.existsByToken(token)) {
            return new ResponseEntity<>("This token doesn't exist", HttpStatus.BAD_REQUEST);
        }

        PasswordResetToken resetToken = passwordTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        if (isPasswordResetTokenExpired(resetToken)) {
            return new ResponseEntity<>("This token is expired.", HttpStatus.BAD_REQUEST);
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        resetToken.setExpired(true);

        return new ResponseEntity<>("Password reset successfully!", HttpStatus.OK);
    }

    public boolean isPasswordResetTokenExpired(PasswordResetToken token) {
        //has token been set to isExpired before?
        boolean isExpired = token.isExpired();

        //if not, check for current date
        if (!isExpired) {
            isExpired = token.getExpiryDate().isBefore(LocalDateTime.now());
        }

        return isExpired;
    }
}
