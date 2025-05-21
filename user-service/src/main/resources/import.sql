-- Insert roles
INSERT INTO role (id, name) VALUES (1, 'USER');
INSERT INTO role (id, name) VALUES (2, 'COACH');
INSERT INTO role (id, name) VALUES (3, 'ADMIN');

-- Insert users (with role_id 1)
INSERT INTO users VALUES (DEFAULT, 1,'hadijah@example.com', '$2a$10$QM3vGBggMvygKHiBrBL39.Lf7owH61D/f7j2RBNsGWXILr0UO1DfO',  'Hadijah'); -- password: user123
INSERT INTO users VALUES (DEFAULT, 1,'omega@example.com', '$2a$10$QM3vGBggMvygKHiBrBL39.Lf7owH61D/f7j2RBNsGWXILr0UO1DfO',  'Hadijah'); -- password: user123

-- Insert users (with role_id 2)
INSERT INTO users VALUES (DEFAULT, 2,'mariam@example.com', '$2a$10$P5rgd08r1WzhACRjgP.ZlujPlox4cGu3WxC/MCIL3ZtZ4xdz05ZGu', 'Mariam'); -- password: coach123
INSERT INTO users VALUES (DEFAULT, 2,'oceane@example.com', '$2a$10$P5rgd08r1WzhACRjgP.ZlujPlox4cGu3WxC/MCIL3ZtZ4xdz05ZGu', 'Mariam'); -- password: coach123
INSERT INTO users VALUES (DEFAULT, 2,'marie@example.com', '$2a$10$P5rgd08r1WzhACRjgP.ZlujPlox4cGu3WxC/MCIL3ZtZ4xdz05ZGu', 'Mariam'); -- password: coach123

-- Insert users (with role_id 3)
INSERT INTO users VALUES (DEFAULT, 3,'jamal@example.com', '$2a$10$Bmt9fki17Yqo90d/nPGu0.UR1vtyLhp6lKg3oaK6UyWSVn3tfLp3S', 'Jamal'); -- password: admin123

-- Insert user profiles (with role as coach)
INSERT INTO coach_profile VALUES ('1989-05-20', NOW(), DEFAULT, NOW(), 2, 'Helping you get stronger every day!', 'MALE', 'Certified Personal Trainer, BSc in Sports Science');
INSERT INTO coach_profile VALUES ('1982-08-10', NOW(), DEFAULT, NOW(), 3, 'Balance body and mind through movement.', 'FEMALE', 'Yoga Instructor, MSc in Physiology');
INSERT INTO coach_profile VALUES ('1995-03-15', NOW(), DEFAULT, NOW(), 4, 'Train smart, eat smarter.', 'MALE', 'CrossFit Level 1, Nutrition Coach');

-- Insert user profiles (with role as user)
INSERT INTO user_profile VALUES ('1989-05-20', 180, 80, NOW(), DEFAULT, NOW(), 1, 'MALE', 'None');
INSERT INTO user_profile VALUES ('1982-08-10', 165, 65, NOW(), DEFAULT, NOW(), 2, 'FEMALE', 'Asthma');

