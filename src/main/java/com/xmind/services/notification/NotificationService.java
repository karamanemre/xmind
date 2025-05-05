package com.xmind.services.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Notification mail;
    private final Notification sms;

    public NotificationService(@Qualifier("EmailNotification") Notification mail,
                               @Qualifier("SmsNotification") Notification sms) {
        this.mail = mail;
        this.sms = sms;
    }

    @Async
    public void sendMail(String message) {
        mail.send();
    }

    @Async
    public void sendSms() {
        sms.send();
    }
}
