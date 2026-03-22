DROP SCHEMA IF EXISTS University;
CREATE SCHEMA IF NOT EXISTS University;
USE University;

CREATE TABLE IF NOT EXISTS Faculty (
    faculty_id INT AUTO_INCREMENT PRIMARY KEY,
    faculty_name VARCHAR(100) NOT NULL UNIQUE,
    dean_name VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS Department (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL,
    office_phone VARCHAR(20),
    faculty_id INT NOT NULL,
    FOREIGN KEY (faculty_id)
    REFERENCES Faculty (faculty_id)
    );

CREATE TABLE IF NOT EXISTS Program (
    program_id INT AUTO_INCREMENT PRIMARY KEY,
    program_name VARCHAR(100) NOT NULL,
    degree_level VARCHAR(20) NOT NULL,
    duration_years INT NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id)
    REFERENCES Department (department_id)
    );

CREATE TABLE IF NOT EXISTS Student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10),
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE,
    admission_date DATE NOT NULL,
    student_status VARCHAR(20) NOT NULL,
    program_id INT NOT NULL,
    FOREIGN KEY (program_id)
    REFERENCES Program (program_id)
    );

CREATE TABLE IF NOT EXISTS Student_address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    address_type VARCHAR(20) NOT NULL,
    country VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    student_id INT NOT NULL,
    FOREIGN KEY (student_id)
    REFERENCES Student (student_id)
    );

CREATE TABLE IF NOT EXISTS Instructor (
    instructor_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE,
    hire_date DATE NOT NULL,
    academic_title VARCHAR(50) NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id)
    REFERENCES Department (department_id)
    );

CREATE TABLE IF NOT EXISTS Course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL,
    course_description VARCHAR(500),
    department_id INT NOT NULL,
    FOREIGN KEY (department_id)
    REFERENCES Department (department_id)
    );

CREATE TABLE IF NOT EXISTS Course_requirement (
    requirement_id INT AUTO_INCREMENT PRIMARY KEY,
    requirement_type VARCHAR(50) NOT NULL,
    requirement_description VARCHAR(255) NOT NULL,
    is_mandatory BOOLEAN NOT NULL DEFAULT TRUE,
    course_id INT NOT NULL,
    FOREIGN KEY (course_id)
    REFERENCES Course (course_id)
    );

CREATE TABLE IF NOT EXISTS Semester (
    semester_id INT AUTO_INCREMENT PRIMARY KEY,
    semester_name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    academic_year VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Building (
    building_id INT AUTO_INCREMENT PRIMARY KEY,
    building_name VARCHAR(100) NOT NULL,
    campus_name VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Room (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_number INT NOT NULL,
    capacity INT NOT NULL,
    room_type VARCHAR(45) NOT NULL,
    building_id INT NOT NULL,
    FOREIGN KEY (building_id)
    REFERENCES Building (building_id)
    );

CREATE TABLE IF NOT EXISTS Course_offering (
    offering_id INT AUTO_INCREMENT PRIMARY KEY,
    section_number VARCHAR(10) NOT NULL,
    max_students INT NOT NULL,
    delivery_mode VARCHAR(20) NOT NULL,
    course_id INT NOT NULL,
    semester_id INT NOT NULL,
    instructor_id INT NOT NULL,
    FOREIGN KEY (course_id)
    REFERENCES Course (course_id),
    FOREIGN KEY (semester_id)
    REFERENCES Semester (semester_id),
    FOREIGN KEY (instructor_id)
    REFERENCES Instructor (instructor_id)
    );

CREATE TABLE IF NOT EXISTS Class_schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    offering_id INT NOT NULL,
    room_id INT NOT NULL,
    FOREIGN KEY (offering_id)
    REFERENCES Course_offering (offering_id),
    FOREIGN KEY (room_id)
    REFERENCES Room (room_id)
    );

CREATE TABLE IF NOT EXISTS Enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_date DATE NOT NULL,
    enrollment_status VARCHAR(20) NOT NULL,
    student_id INT NOT NULL,
    offering_id INT NOT NULL,
    FOREIGN KEY (student_id)
    REFERENCES Student (student_id),
    FOREIGN KEY (offering_id)
    REFERENCES Course_offering (offering_id)
    );

CREATE TABLE IF NOT EXISTS Attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    class_date DATE NOT NULL,
    attendance_status VARCHAR(20) NOT NULL,
    enrollment_id INT NOT NULL,
    FOREIGN KEY (enrollment_id)
    REFERENCES Enrollment (enrollment_id)
    );

CREATE TABLE IF NOT EXISTS Exam_type (
    exam_type_id INT AUTO_INCREMENT PRIMARY KEY,
    exam_type_name VARCHAR(45) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS Exam (
    exam_id INT AUTO_INCREMENT PRIMARY KEY,
    exam_date DATE NOT NULL,
    max_score DECIMAL(3 , 0 ) NOT NULL,
    offering_id INT NOT NULL,
    exam_type_id INT NOT NULL,
    FOREIGN KEY (offering_id)
    REFERENCES Course_offering (offering_id),
    FOREIGN KEY (exam_type_id)
    REFERENCES Exam_type (exam_type_id)
    );

CREATE TABLE IF NOT EXISTS Grade (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    score DECIMAL(3 , 0 ) NOT NULL,
    letter_grade VARCHAR(2) NOT NULL,
    graded_date DATE NOT NULL,
    exam_id INT NOT NULL,
    student_id INT NOT NULL,
    FOREIGN KEY (exam_id)
    REFERENCES Exam (exam_id),
    FOREIGN KEY (student_id)
    REFERENCES Student (student_id)
    );