USE University;

INSERT INTO Faculty
(faculty_name, dean_name)
VALUES
    ('Engineering', 'Dr. Michael Smith'),
    ('Business', 'Dr. Anna Johnson');

INSERT INTO Department
(department_name, office_phone, faculty_id)
VALUES
    ('Computer Science', '123456', 1),
    ('Software Engineering', '654321', 1),
    ('Management', '111222', 2);

INSERT INTO Program
(program_name, degree_level, duration_years, department_id)
VALUES
    ('Computer Science', 'Bachelor', 4, 1),
    ('Software Engineering', 'Bachelor', 4, 2),
    ('Business Administration', 'Master', 2, 3);

INSERT INTO Student
(first_name, last_name, date_of_birth, gender, email, phone, admission_date, student_status, program_id)
VALUES
    ('Alice', 'Brown', '2002-04-15', 'Female', 'alice.brown@email.com', '111111111', '2022-09-01', 'Active', 1),
    ('David', 'Wilson', '2001-12-10', 'Male', 'david.wilson@email.com', '222222222', '2021-09-01', 'Active', 3),
    ('Emma', 'Taylor', '2003-02-18', 'Female', 'emma.taylor@email.com', '333333333', '2023-09-01', 'Active', 2);

INSERT INTO Student_address
(address_type, country, city, street, postal_code, student_id)
VALUES
    ('Permanent', 'USA', 'Chicago', 'Green St 15', '60007', 1),
    ('Current', 'USA', 'Boston', 'Lake Rd 22', '02101', 2),
    ('Permanent', 'USA', 'Seattle', 'Pine St 7', '98101', 3);

INSERT INTO Instructor
(first_name, last_name, email, phone, hire_date, academic_title, department_id)
VALUES
    ('Robert', 'Miller', 'robert.miller@uni.edu', '444444444', '2018-03-01', 'Professor', 1),
    ('Laura', 'Davis', 'laura.davis@uni.edu', '555555555', '2020-06-10', 'Associate Professor', 2),
    ('Jane', 'Doe', 'jane.doe@uni.edu', '666666666', '2008-05-14', 'Lecturer', 3);

INSERT INTO Course
(course_code, course_name, credits, course_description, department_id)
VALUES
    ('CS101', 'Programming Basics', 5, 'Introduction to programming', 1),
    ('CS201', 'Data Structures', 5, 'Core algorithms and structures', 1),
    ('SE101', 'Software Design', 4, 'Software development principles', 2),
    ('AE101', 'Marketing And Sales', 4, 'Marketing strategies and market analysis.', 3);

INSERT INTO Course_requirement
(requirement_type, requirement_description, is_mandatory, course_id)
VALUES
    ('Self-study', 'Basic programming knowledge recommended', TRUE, 2),
    ('External course', 'Complete online Git tutorial', FALSE, 3);

INSERT INTO Semester
(semester_name, start_date, end_date, academic_year)
VALUES
    ('Autumn', '2024-09-01', '2024-12-20', '2024/2025');

INSERT INTO Building
(building_name, campus_name)
VALUES
    ('Engineering Building', 'Main Campus'),
    ('Business Center', 'Main Campus');

INSERT INTO Room
(room_number, capacity, room_type, building_id)
VALUES
    (101, 50, 'Lecture Hall', 1),
    (202, 30, 'Classroom', 1),
    (301, 40, 'Lecture Hall', 2);

INSERT INTO Course_offering
(section_number, max_students, delivery_mode, course_id, semester_id, instructor_id)
VALUES
    ('A', 40, 'Offline', 1, 1, 1),
    ('B', 35, 'Offline', 3, 1, 2),
    ('C', 30, 'Hybrid', 4, 1, 3);

INSERT INTO Class_schedule
(day_of_week, start_time, end_time, offering_id, room_id)
VALUES
    ('Monday', '09:00:00', '10:30:00', 1, 1),
    ('Wednesday', '11:00:00', '12:30:00', 2, 2),
    ('Friday', '13:00:00', '14:30:00', 3, 3);

INSERT INTO Enrollment
(enrollment_date, enrollment_status, student_id, offering_id)
VALUES
    ('2024-09-02', 'Enrolled', 1, 1),
    ('2024-09-02', 'Enrolled', 2, 2),
    ('2024-09-03', 'Enrolled', 3, 3);

INSERT INTO Attendance
(class_date, attendance_status, enrollment_id)
VALUES
    ('2024-09-05', 'Present', 1),
    ('2024-09-05', 'Absent', 2),
    ('2024-09-06', 'Present', 3);

INSERT INTO Exam_type
(exam_type_name)
VALUES
    ('Midterm'),
    ('Final');

INSERT INTO Exam
(exam_date, max_score, offering_id, exam_type_id)
VALUES
    ('2024-10-20', 100, 1, 1),
    ('2024-12-10', 100, 1, 2),
    ('2024-12-12', 100, 2, 2),
    ('2024-12-11', 100, 3, 2);

INSERT INTO Grade
(score, letter_grade, graded_date, exam_id, student_id)
VALUES
    (85, 'B', '2024-10-21', 1, 1),
    (87, 'B', '2024-12-21', 2, 1),
    (92, 'A', '2024-12-11', 3, 2),
    (78, 'C', '2024-12-13', 4, 3);