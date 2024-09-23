package com.example.notification.controller;

import com.example.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/testRabbitmq")
    public ResponseEntity<String> testRabbitmq(){
        notificationService.sendRabbitmqMessage();
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}