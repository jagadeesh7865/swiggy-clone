package com.swiggy.swiggy_backend.service;

import com.swiggy.swiggy_backend.dto.EmailRequest;

public interface NotificationService {

    void sendEmail(EmailRequest request);

}