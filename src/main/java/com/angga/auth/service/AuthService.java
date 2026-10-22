package com.angga.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.angga.auth.dto.AuthResponse;
import com.angga.auth.dto.LoginRequest;
import com.angga.auth.dto.RegisterRequest;
import com.angga.auth.dto.UserRegisteredEvent;
import com.angga.auth.entity.User;
import com.angga.auth.messaging.UserEventPublisher;
import com.angga.auth.repository.UserRepository;
import com.angga.auth.util.JwtUtil;

@Service
public class AuthService {

  public static final String DEFAULT_ROLE = "pelanggan";

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserEventPublisher userEventPublisher;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public AuthResponse register(RegisterRequest request) {
    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username sudah dipakai");
    }
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email sudah dipakai");
    }

    User user = new User(
        request.getUsername(),
        passwordEncoder.encode(request.getPassword()),
        request.getEmail(),
        DEFAULT_ROLE);
    user = userRepository.save(user);

    userEventPublisher.publishUserRegistered(new UserRegisteredEvent(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        request.getNama(),
        request.getAlamat(),
        request.getJenis_kelamin()));

    return new AuthResponse(jwtUtil.generateToken(user), user.getUsername(), user.getEmail(), user.getRole());
  }

  public AuthResponse login(LoginRequest request) {
    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah");
    }

    return new AuthResponse(jwtUtil.generateToken(user), user.getUsername(), user.getEmail(), user.getRole());
  }
}
