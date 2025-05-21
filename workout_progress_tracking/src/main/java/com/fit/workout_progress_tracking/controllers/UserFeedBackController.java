package com.fit.workout_progress_tracking.controllers;

import com.fit.workout_progress_tracking.dto.UserRequestFeedBackDTO;
import com.fit.workout_progress_tracking.entity.UserFeedBack;
import com.fit.workout_progress_tracking.service.impl.UserFeedBackImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedback")
@Tag(name = "User Feedback API", description = "API for user feedback manipulation and management")
public class UserFeedBackController {
    @Autowired
    private UserFeedBackImpl uFeedBack;

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Submit User Feedback", description = "Saves User Feedback Data.")
    @PostMapping
    public ResponseEntity<UserFeedBack> addFeedBack(@RequestBody UserRequestFeedBackDTO userFeedBackDTO) {
        return new ResponseEntity<>(uFeedBack.addFeedBack(userFeedBackDTO), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Retrieving User Feedback", description = "Retrieves User Feedback Data.")
    @GetMapping
    public ResponseEntity<List<UserFeedBack>> getUserFeedbacks() {
        List<UserFeedBack> feedbackList = uFeedBack.findByUserId();
        return ResponseEntity.ok(feedbackList);
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Editing User Feedback", description = "Edits User Feedback Data.")
    @PutMapping("/{feedbackId}")
    public ResponseEntity<UserFeedBack> updateFeedback(@PathVariable("feedbackId") Long feedbackId, @RequestBody UserRequestFeedBackDTO updatedDTO) {
        UserFeedBack updatedFeedback = uFeedBack.updateFeedback(feedbackId, updatedDTO);
        return ResponseEntity.ok(updatedFeedback);
    }

}
