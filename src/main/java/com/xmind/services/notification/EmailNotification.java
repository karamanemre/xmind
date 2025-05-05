package com.xmind.services.notification;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service(value = "EmailNotification")
@Log4j2
public class EmailNotification implements Notification {

    public EmailNotification() {
    }

    @Override
    public void send() {
        log.info("Mail sended...");
    }
}