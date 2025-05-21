package cs544.fit.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonalInfo personalInfo;

    private double height;
    private double weight;

    @Formula("(date_part('year', age(current_date, date_of_birth)))")
    private Integer age;

    private String medicalConditions;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Formula("weight / (height * height)")
    private Double bmi;
}
