package cs544.fit.user_service.repository;

import cs544.fit.user_service.entity.CoachProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachProfileRepo extends JpaRepository<CoachProfile, Integer> {
    Optional<CoachProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
