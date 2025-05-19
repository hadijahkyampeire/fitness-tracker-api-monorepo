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
