-- 5 workout categories
INSERT INTO workout_categories VALUES (DEFAULT, 'Cardiovascular exercises for endurance and stamina', 'Cardio');
INSERT INTO workout_categories VALUES (DEFAULT, 'Strength training workouts to build muscle', 'Strength');
INSERT INTO workout_categories VALUES (DEFAULT, 'Stretching and flexibility routines', 'Flexibility');
INSERT INTO workout_categories VALUES (DEFAULT, 'Exercises to improve balance and coordination', 'Balance');
INSERT INTO workout_categories VALUES (DEFAULT, 'High-intensity interval training plans', 'HIIT');

-- 3 workout plans for each category (total 15 plans)
INSERT INTO workout_plan VALUES (30, 200, 1, NOW(), DEFAULT, NOW(), 1, 'Light cardio for beginners', 'BEGINNER', 'Beginner Cardio Blast');
INSERT INTO workout_plan VALUES (45, 350, 1, NOW(), DEFAULT, NOW(), 2, 'Moderate intensity cardio workout', 'INTERMEDIATE', 'Intermediate Cardio');
INSERT INTO workout_plan VALUES (60, 500, 1, NOW(), DEFAULT, NOW(), 3, 'High-intensity cardio session', 'ADVANCED', 'Advanced Cardio Challenge');

INSERT INTO workout_plan VALUES (40, 300, 2, NOW(), DEFAULT, NOW(), 1, 'Intro to strength training', 'BEGINNER', 'Strength Basics');
INSERT INTO workout_plan VALUES (50, 400, 2, NOW(), DEFAULT, NOW(), 2, 'Focus on arms and chest', 'INTERMEDIATE', 'Upper Body Strength');
INSERT INTO workout_plan VALUES (60, 550, 2, NOW(), DEFAULT, NOW(), 3, 'Compound lifts for full body', 'ADVANCED', 'Full Body Strength');

INSERT INTO workout_plan VALUES (20, 100, 3, NOW(), DEFAULT, NOW(), 1, 'Gentle stretching routine', 'BEGINNER', 'Morning Stretch');
INSERT INTO workout_plan VALUES (40, 250, 3, NOW(), DEFAULT, NOW(), 2, 'Flexibility with yoga poses', 'INTERMEDIATE', 'Yoga Flow');
INSERT INTO workout_plan VALUES (50, 300, 3, NOW(), DEFAULT, NOW(), 3, 'Advanced flexibility training', 'ADVANCED', 'Deep Stretch');

INSERT INTO workout_plan VALUES (25, 150, 4, NOW(), DEFAULT, NOW(), 1, 'Improve balance with simple moves', 'BEGINNER', 'Balance Basics');
INSERT INTO workout_plan VALUES (40, 275, 4, NOW(), DEFAULT, NOW(), 2, 'Use balance balls and bands', 'INTERMEDIATE', 'Stability Training');
INSERT INTO workout_plan VALUES (55, 350, 4, NOW(), DEFAULT, NOW(), 3, 'Challenging balance exercises', 'ADVANCED', 'Pro Balance Workout');

INSERT INTO workout_plan VALUES (30, 400, 5, NOW(), DEFAULT, NOW(), 1, 'Short bursts for beginners', 'BEGINNER', 'HIIT Beginner');
INSERT INTO workout_plan VALUES (45, 600, 5, NOW(), DEFAULT, NOW(), 2, 'More intense intervals', 'INTERMEDIATE', 'HIIT Intermediate');
INSERT INTO workout_plan VALUES (60, 800, 5, NOW(), DEFAULT, NOW(), 3, 'Max effort intervals', 'ADVANCED', 'HIIT Advanced');

INSERT INTO user_workout_progress VALUES (1, NULL, DEFAULT, '2024-06-01 08:00:00', 1, 1, 'NOT_STARTED');
INSERT INTO user_workout_progress VALUES (2, NULL, DEFAULT, '2024-06-02 09:30:00', 2, 2, 'IN_PROGRESS');
INSERT INTO user_workout_progress VALUES (3, '2024-06-03 10:15:00', DEFAULT, '2024-06-03 09:00:00', 1, 3, 'FINISHED');
INSERT INTO user_workout_progress VALUES (1, NULL, DEFAULT, '2024-06-04 07:45:00', 3, 4, 'NOT_STARTED');
INSERT INTO user_workout_progress VALUES (2, '2024-06-05 11:00:00', DEFAULT, '2024-06-05 10:00:00', 2, 2, 'FINISHED');
INSERT INTO user_workout_progress VALUES (3, NULL, DEFAULT, '2024-06-06 12:30:00', 1, 6, 'IN_PROGRESS');
INSERT INTO user_workout_progress VALUES (1, NULL, DEFAULT, '2024-06-07 13:00:00', 2, 3, 'NOT_STARTED');
INSERT INTO user_workout_progress VALUES (2, '2024-06-08 14:45:00', DEFAULT, '2024-06-08 13:30:00', 3, 5, 'FINISHED');
INSERT INTO user_workout_progress VALUES (3, NULL, DEFAULT, '2024-06-09 15:15:00', 2, 3, 'IN_PROGRESS');
INSERT INTO user_workout_progress VALUES (1, NULL, DEFAULT, '2024-06-10 16:00:00', 1, 10, 'FINISHED');
