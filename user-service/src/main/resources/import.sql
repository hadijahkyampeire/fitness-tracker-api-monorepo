-- Insert roles
INSERT INTO role (id, name) VALUES (1, 'USER');
INSERT INTO role (id, name) VALUES (2, 'COACH');
INSERT INTO role (id, name) VALUES (3, 'ADMIN');

-- Insert users (with role_id 1)
INSERT INTO users VALUES (DEFAULT, 1,'hadijah@example.com', '$2a$10$DOWSDV.KXsAYFhkI/RDPtOmI6E.6U/n2kSLKgyIxDwUjqnvcuK5Fe',  'Hadijah'); -- password: 1234

-- Insert users (with role_id 2)
INSERT INTO users VALUES (DEFAULT, 2,'mariam@example.com', '$2a$10$kQzixNsAKbx3AZaKrbNeYeYyqr0mK0smzIvRRM.qv9Z6fdjOCquvi', 'Mariam'); -- password: password
INSERT INTO users VALUES (DEFAULT, 2,'marie@example.com', '$2a$10$kQzixNsAKbx3AZaKrbNeYeYyqr0mK0smzIvRRM.qv9Z6fdjOCquvi', 'Marie'); -- password: password
INSERT INTO users VALUES (DEFAULT, 2,'oceane@example.com', '$2a$10$kQzixNsAKbx3AZaKrbNeYeYyqr0mK0smzIvRRM.qv9Z6fdjOCquvi', 'Oceane'); -- password: password

-- Insert users (with role_id 3)
INSERT INTO users VALUES (DEFAULT, 3,'jamal@example.com', '$2a$10$4FYVRgxCZHzZzI/nM5j7HuxZHzFM7ytFvME9FSoXc0i/K0vNmEapC', 'Jamal'); -- password: admin123