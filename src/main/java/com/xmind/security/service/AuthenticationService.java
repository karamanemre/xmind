package com.xmind.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmind.security.entity.TokenEntity;
import com.xmind.security.entity.TokenType;
import com.xmind.security.entity.UserEntity;
import com.xmind.security.models.AuthenticationRequest;
import com.xmind.security.models.AuthenticationResponse;
import com.xmind.security.models.RegisterRequest;
import com.xmind.security.models.Roles;
import com.xmind.security.repository.TokenRepository;
import com.xmind.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request, List<Roles> roles) {
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        UserEntity savedUser = repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .roles(savedUser.getRoles())
                .email(savedUser.getEmail())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserEntity user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        this.revokeAllUserTokens(user);
        this.saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .roles(user.getRoles())
                .email(user.getEmail())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {

                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
