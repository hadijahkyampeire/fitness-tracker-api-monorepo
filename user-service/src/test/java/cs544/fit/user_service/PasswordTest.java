package cs544.fit.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    @Test
    public void testSeedUserPasswords() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Testing Hadijah: " + encoder.matches("user123", "$2a$10$zq1/N44GqWSKdrh37k6S/udnQOiRWTOJ1AyqOEuvI0/E3Jf9HZopS"));
        System.out.println("Testing Mariam: " + encoder.matches("coach123", "$2a$10$WAWxFhRjeBC5zt1AMz7DyeoaWoNo2UF6kFKD.QOxHns7E6o7OhEPC"));
        System.out.println("Testing Jamal: " + encoder.matches("admin123", "$2a$10$hU1Pvxy5kJcb4PZqLy.ZNe5aRzglujL1aZKNg3cNHKkqvJK5iZ3bO"));
    }
}
