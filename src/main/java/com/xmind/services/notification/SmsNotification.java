package com.xmind.services.notification;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service(value = "SmsNotification")
@Log4j2
public class SmsNotification implements Notification {

    public SmsNotification() {
    }

    @Override
    public void send() {
        log.info("Sms sended...");
    }
}
