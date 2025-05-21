package com.fit.workout_progress_tracking.controllers;

import com.fit.workout_progress_tracking.dto.ProgressSummaryDTO;
import com.fit.workout_progress_tracking.dto.UserInfoDTO;
import com.fit.workout_progress_tracking.service.impl.ReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
@Tag(name = "Workout Report API", description = "API for user workout progress management")
public class ReportController {
    @Autowired
    private ReportServiceImpl reportService;

    @GetMapping("/report")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Get Progress report", description = "Fetches current user workout progress and related data.")
    public ProgressSummaryDTO getEnrichedProgress() {
        return reportService.getProgressReportForCurrentUser();
    }

}
