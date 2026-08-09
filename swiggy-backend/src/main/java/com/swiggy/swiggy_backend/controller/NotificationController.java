package com.swiggy.swiggy_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.swiggy.swiggy_backend.dto.EmailRequest;
import com.swiggy.swiggy_backend.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/email")
    @PreAuthorize("hasRole('ADMIN')")
    public String sendEmail(@RequestBody EmailRequest request) {

        notificationService.sendEmail(request);
        return "Email sent successfully.";
    }
}