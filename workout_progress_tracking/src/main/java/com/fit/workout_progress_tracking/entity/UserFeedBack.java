package com.fit.workout_progress_tracking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserFeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double weight;
    private String comment;
}
