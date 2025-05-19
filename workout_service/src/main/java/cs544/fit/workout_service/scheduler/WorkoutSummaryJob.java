package cs544.fit.workout_service.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkoutSummaryJob {

    @Scheduled(cron = "0 0 20 * * ?") // Every day at 8 PM
    public void sendDailySummary() {
        // Simulate sending summary or logging
        System.out.println("Sending daily workout summary to users...");
    }
}

