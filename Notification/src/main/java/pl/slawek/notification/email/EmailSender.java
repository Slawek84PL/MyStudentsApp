package pl.slawek.notification.email;

import jakarta.mail.MessagingException;
import pl.slawek.notification.dto.EmailDto;
import pl.slawek.notification.dto.NotificationInfoDto;

public interface EmailSender {

    void sendEmails(NotificationInfoDto notificationInfo);

    void sendEmail(String to, String subject, String content) throws MessagingException;

    void sendEmail(EmailDto emailDto) throws MessagingException;
}