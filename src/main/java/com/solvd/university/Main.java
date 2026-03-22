package com.solvd.university;

import com.solvd.university.config.ConnectionPool;
import com.solvd.university.controller.AcademicController;
import com.solvd.university.controller.StudentController;
import com.solvd.university.dao.CourseDao;
import com.solvd.university.dao.CourseOfferingDao;
import com.solvd.university.dao.EnrollmentDao;
import com.solvd.university.dao.SemesterDao;
import com.solvd.university.dao.StudentDao;
import com.solvd.university.dao.impl.CourseDaoImpl;
import com.solvd.university.dao.impl.CourseOfferingDaoImpl;
import com.solvd.university.dao.impl.EnrollmentDaoImpl;
import com.solvd.university.dao.impl.SemesterDaoImpl;
import com.solvd.university.dao.impl.StudentDaoImpl;
import com.solvd.university.service.CourseService;
import com.solvd.university.service.EnrollmentService;
import com.solvd.university.service.SemesterService;
import com.solvd.university.service.StudentService;
import com.solvd.university.service.impl.CourseServiceImpl;
import com.solvd.university.service.impl.EnrollmentServiceImpl;
import com.solvd.university.service.impl.SemesterServiceImpl;
import com.solvd.university.service.impl.StudentServiceImpl;
import com.solvd.university.util.ScriptRunner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            ScriptRunner.runScripts("sql/DDL.sql", "sql/DML.sql");

            StudentDao studentDao = new StudentDaoImpl();
            CourseDao courseDao = new CourseDaoImpl();
            SemesterDao semesterDao = new SemesterDaoImpl();
            CourseOfferingDao courseOfferingDao = new CourseOfferingDaoImpl();
            EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();

            StudentService studentService = new StudentServiceImpl(studentDao);
            CourseService courseService = new CourseServiceImpl(courseDao);
            SemesterService semesterService = new SemesterServiceImpl(semesterDao);
            EnrollmentService enrollmentService = new EnrollmentServiceImpl(
                    enrollmentDao,
                    studentDao,
                    courseOfferingDao
            );

            StudentController studentController = new StudentController(studentService, scanner);
            AcademicController academicController = new AcademicController(
                    enrollmentService,
                    courseService,
                    semesterService,
                    scanner
            );

            boolean running = true;

            while (running) {
                showMenu();
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> studentController.showStudentById();
                    case "2" -> studentController.showAllStudents();
                    case "3" -> studentController.createStudent();
                    case "4" -> studentController.updateStudent();
                    case "5" -> studentController.deleteStudentById();
                    case "6" -> academicController.enrollStudentIntoOffering();
                    case "7" -> academicController.showStudentEnrollments();
                    case "8" -> academicController.showAllCourses();
                    case "9" -> academicController.showCourseById();
                    case "10" -> academicController.showAllSemesters();
                    case "11" -> academicController.showSemesterById();
                    case "0" -> {
                        running = false;
                        System.out.println("Exiting application.");
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            }
        } finally {
            ConnectionPool.getInstance().shutDown();
            scanner.close();
        }
    }

    private static void showMenu() {
        System.out.println("""
                
                ==== UNIVERSITY MENU ====
                1. Show student by ID
                2. Show all students
                3. Create student
                4. Update student
                5. Delete student by ID
                6. Enroll student into course offering
                7. Show student enrollments
                8. Show all courses
                9. Show course by ID
                10. Show all semesters
                11. Show semester by ID
                0. Exit
                """);
        System.out.print("Choose an option: ");
    }
}