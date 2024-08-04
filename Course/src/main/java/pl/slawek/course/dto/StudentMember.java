package pl.slawek.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudentMember {

    @NotNull
    private String email;

    @NotNull
    private LocalDateTime registrationDate;

    public StudentMember(@NotNull String email) {
        this.registrationDate = LocalDateTime.now();
        this.email = email;
    }
}