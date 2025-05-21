package cs544.fit.user_service.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class PersonalInfo {

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
