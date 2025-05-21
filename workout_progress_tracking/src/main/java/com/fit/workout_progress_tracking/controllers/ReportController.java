package com.fit.workout_progress_tracking.controllers;

import com.fit.workout_progress_tracking.dto.UserInfoDTO;
import com.fit.workout_progress_tracking.service.impl.ReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping
    @Operation(summary = "Get User Workout Info", description = "Fetches current user workout progress and related data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user workout information"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserInfoDTO> getUserInfo() {
        return new ResponseEntity<UserInfoDTO>(reportService.getUserInfo(), HttpStatus.OK);
    }
}
