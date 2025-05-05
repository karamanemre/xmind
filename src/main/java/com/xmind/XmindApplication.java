package com.xmind;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories
@RequiredArgsConstructor
public class XmindApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmindApplication.class, args);
    }

}
