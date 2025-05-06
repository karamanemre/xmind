package com.xmind.config;

import com.xmind.security.entity.UserEntity;
import com.xmind.security.models.RegisterRequest;
import com.xmind.security.models.Roles;
import com.xmind.security.repository.UserRepository;
import com.xmind.security.service.AuthenticationService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineConfig implements CommandLineRunner {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<UserEntity> admin = userRepository.findByEmail("admin@admin.com");
        Optional<UserEntity> user = userRepository.findByEmail("user@user.com");
        if (!admin.isPresent()) {
            authenticationService.register(RegisterRequest.builder()
                    .email("admin@admin.com")
                    .password("123456").build(), List.of(Roles.ADMIN));
        }

        if (!user.isPresent()) {
            authenticationService.register(RegisterRequest.builder()
                    .email("user@user.com")
                    .password("123456").build(), List.of(Roles.USER));
        }

    }
}
