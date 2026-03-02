-- Insert Users
INSERT INTO users (name, email, role) VALUES ('Alice Johnson', 'alice@example.com', 'ADMIN');
INSERT INTO users (name, email, role) VALUES ('Bob Smith', 'bob@example.com', 'STUDENT');
INSERT INTO users (name, email, role) VALUES ('Carol White', 'carol@example.com', 'STUDENT');

-- Insert Facilities
INSERT INTO facilities (name, location, capacity) VALUES ('Main Auditorium', 'Block A', 500);
INSERT INTO facilities (name, location, capacity) VALUES ('Computer Lab 1', 'Block B', 40);
INSERT INTO facilities (name, location, capacity) VALUES ('Meeting Room 3', 'Block C', 15);

-- Insert Bookings
INSERT INTO bookings (facility_id, user_id, date, start_time, end_time, status) VALUES (1, 2, '2026-02-20', '09:00', '11:00', 'CONFIRMED');
INSERT INTO bookings (facility_id, user_id, date, start_time, end_time, status) VALUES (2, 3, '2026-02-21', '14:00', '16:00', 'PENDING');
INSERT INTO bookings (facility_id, user_id, date, start_time, end_time, status) VALUES (3, 2, '2026-02-22', '10:00', '12:00', 'CANCELLED');