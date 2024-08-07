package pl.slawek.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.slawek.notification.dto.EmailDto;
import pl.slawek.notification.email.EmailSender;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailSender emailSender;

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailDto emailDto) {
        try {
            emailSender.sendEmail(emailDto);
        } catch (Exception e) {
            String message = "Email nie został wysłany do " + emailDto.getTo();
            log.info(message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.ok("Wysłano email do " + emailDto.getTo());
    }
}