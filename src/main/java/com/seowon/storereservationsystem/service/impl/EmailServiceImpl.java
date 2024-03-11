package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.EmailMessage;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static com.seowon.storereservationsystem.type.ErrorCode.SEND_EMAIL_ERROR;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender javaMailSender;

    @Override
    public ApiResponse sendReservationStatusMail(EmailMessage emailMessage) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            LOGGER.info("[sendReservationStatusMail] 이메일 생성 시작");
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(emailMessage.getMessage());
            LOGGER.info("[sendReservationStatusMail] 이메일 생성 완료");
            javaMailSender.send(mimeMessage);
            LOGGER.info("[sendReservationStatusMail] 이메일을 성공적으로 전송함");

        } catch (MessagingException e) {
            LOGGER.error("[sendReservationStatusMail] 이메일 전송 과정 내 오류 발생");
            throw new ReservationSystemException(SEND_EMAIL_ERROR);
        }

        return ApiResponse.builder()
                .success(true)
                .message("이메일을 성공적으로 전송하였습니다.")
                .build();
    }

    @Override
    public String sendNewPasswordMail(EmailMessage emailMessage, String type) {
        String authNum = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        if(type.equals("password")) {
//            userService.setTempPassword(emailMessage.getTo(), authNum);
        }

        try{

            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(authNum, type);
            javaMailSender.send(mimeMessage);
            LOGGER.info("[sendMail] 이메일을 성공적으로 전송함");

        } catch (MessagingException e) {
            LOGGER.error("[sendMail] 이메일 전송 과정 내 오류 발생");
            throw new ReservationSystemException(SEND_EMAIL_ERROR);
        }

        return null;
    }

    @Override
    public String createCode() {
        return null;
    }
}
