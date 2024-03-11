package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.EmailMessage;

public interface EmailService {
    String sendNewPasswordMail(EmailMessage emailMessage, String type);
    ApiResponse sendReservationStatusMail(EmailMessage emailMessage);
    String createCode();
}
