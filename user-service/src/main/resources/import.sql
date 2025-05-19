-- Insert roles
INSERT INTO role (id, name) VALUES (1, 'USER');
INSERT INTO role (id, name) VALUES (2, 'COACH');
INSERT INTO role (id, name) VALUES (3, 'ADMIN');

-- Insert users (with role_id)
INSERT INTO users VALUES (DEFAULT, 1,'hadijah@example.com', '$2a$10$QM3vGBggMvygKHiBrBL39.Lf7owH61D/f7j2RBNsGWXILr0UO1DfO',  'Hadijah'); -- password: user123

INSERT INTO users VALUES (DEFAULT, 2,'mariam@example.com', '$2a$10$P5rgd08r1WzhACRjgP.ZlujPlox4cGu3WxC/MCIL3ZtZ4xdz05ZGu', 'Mariam'); -- password: coach123
INSERT INTO users VALUES (DEFAULT, 2,'oceane@example.com', '$2a$10$P5rgd08r1WzhACRjgP.ZlujPlox4cGu3WxC/MCIL3ZtZ4xdz05ZGu', 'Mariam'); -- password: coach123
INSERT INTO users VALUES (DEFAULT, 2,'marie@example.com', '$2a$10$P5rgd08r1WzhACRjgP.ZlujPlox4cGu3WxC/MCIL3ZtZ4xdz05ZGu', 'Mariam'); -- password: coach123

INSERT INTO users VALUES (DEFAULT, 3,'jamal@example.com', '$2a$10$Bmt9fki17Yqo90d/nPGu0.UR1vtyLhp6lKg3oaK6UyWSVn3tfLp3S', 'Jamal'); -- password: admin123


