package com.example.notification.queue;

import com.example.notification.component.Send;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestNoti {
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 2000)
    public void autoNoti() {
        notificationService.sendRabbitmqMessage();
    }
}
