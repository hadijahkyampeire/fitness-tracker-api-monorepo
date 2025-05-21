package com.fit.workout_progress_tracking.controllers;

import com.fit.workout_progress_tracking.dto.UserInfoDTO;
import com.fit.workout_progress_tracking.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
public class ReportController {
    @Autowired
    private ReportServiceImpl reportService;

    @GetMapping
    public ResponseEntity<UserInfoDTO> getUserInfo() {
        return new ResponseEntity<UserInfoDTO>(reportService.getUserInfo(), HttpStatus.OK);
    }
}
