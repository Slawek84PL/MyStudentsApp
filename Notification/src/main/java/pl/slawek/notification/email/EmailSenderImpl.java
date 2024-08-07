package pl.slawek.notification.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.slawek.notification.dto.EmailDto;
import pl.slawek.notification.dto.NotificationInfoDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmails(NotificationInfoDto notificationInfo) {
        String subject = "Pamiętaj o kursie " + notificationInfo.getCourseName();

        StringBuilder content = buildContent(notificationInfo);

        notificationInfo.getEmails().forEach(email -> {
            try {
                sendEmail(email, subject, content.toString());
            } catch (Exception e) {
                log.error("notyfikacja nie wysłana " + content);
            }
        });

    }

    private StringBuilder buildContent(NotificationInfoDto notificationInfo) {
        StringBuilder content = new StringBuilder();
        content.append("Kurs ");
        content.append(notificationInfo.getCourseName());
        content.append(" rozpoczyna się ");
        content.append(notificationInfo.getCourseStart());
        content.append(". proszę stawić się 15min wczesniej!");
        content.append("\n");
        content.append("Opis kursu:  ");
        content.append(notificationInfo.getCourseDescription());
        content.append("\n");
        content.append("Kurs kończy się ");
        content.append(notificationInfo.getCourseEnd());
        content.append("\n");
        content.append("Cekamy na Ciebie!");
        return content;
    }

    @Override
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, false);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendEmail(EmailDto emailDto) throws MessagingException {
        sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getContent());
    }
}