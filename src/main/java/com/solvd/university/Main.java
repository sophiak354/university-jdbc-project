package com.solvd.university;

import com.solvd.university.config.ConnectionPool;
import com.solvd.university.controller.AcademicController;
import com.solvd.university.controller.DepartmentController;
import com.solvd.university.controller.InstructorController;
import com.solvd.university.controller.StudentController;
import com.solvd.university.dao.*;
import com.solvd.university.dao.impl.*;
import com.solvd.university.dao.impl.jdbc.CourseDaoImpl;
import com.solvd.university.dao.impl.jdbc.CourseOfferingDaoImpl;
import com.solvd.university.dao.impl.jdbc.EnrollmentDaoImpl;
import com.solvd.university.dao.impl.jdbc.StudentDaoImpl;
import com.solvd.university.dao.impl.mybatis.*;
import com.solvd.university.service.*;
import com.solvd.university.service.impl.*;
import com.solvd.university.util.ScriptRunner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            ScriptRunner.runScripts("sql/DDL.sql", "sql/DML.sql");

            boolean useMyBatis = true;

            StudentDao studentDao = useMyBatis
                    ? new StudentMyBatisDaoImpl()
                    : new StudentDaoImpl();

            CourseDao courseDao = useMyBatis
                    ? new CourseMyBatisDaoImpl()
                    : new CourseDaoImpl();

            // Use for XML-based implementation:
            //SemesterDao semesterDao = new SemesterXmlDaoImpl();

            // Use for MyBatis-based implementation:
            SemesterDao semesterDao = new SemesterMyBatisDaoImpl();

            CourseOfferingDao courseOfferingDao = useMyBatis
                    ? new CourseOfferingMyBatisDaoImpl()
                    : new CourseOfferingDaoImpl();

            EnrollmentDao enrollmentDao = useMyBatis
                    ? new EnrollmentMyBatisDaoImpl()
                    : new EnrollmentDaoImpl();

            InstructorDao instructorDao = new InstructorDaoImpl();
            DepartmentDao departmentDao = new DepartmentDaoImpl();

            StudentService studentService = new StudentServiceImpl(studentDao);
            CourseService courseService = new CourseServiceImpl(courseDao);
            SemesterService semesterService = new SemesterServiceImpl(semesterDao);
            EnrollmentService enrollmentService = new EnrollmentServiceImpl(
                    enrollmentDao,
                    studentDao,
                    courseOfferingDao
            );
            InstructorService instructorService = new InstructorServiceImpl(instructorDao);
            DepartmentService departmentService = new DepartmentServiceImpl(departmentDao);

            StudentController studentController = new StudentController(studentService, scanner);
            AcademicController academicController = new AcademicController(
                    enrollmentService,
                    courseService,
                    semesterService,
                    scanner
            );
            InstructorController instructorController = new InstructorController(instructorService, scanner);
            DepartmentController departmentController = new DepartmentController(departmentService, scanner);

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
                    case "12" -> academicController.createSemester();
                    case "13" -> academicController.updateSemester();
                    case "14" -> academicController.deleteSemesterById();
                    case "15" -> instructorController.showAllInstructors();
                    case "16" -> instructorController.showInstructorById();
                    case "17" -> instructorController.createInstructor();
                    case "18" -> instructorController.updateInstructor();
                    case "19" -> instructorController.deleteInstructorById();
                    case "20" -> departmentController.showAllDepartments();
                    case "21" -> departmentController.showDepartmentById();
                    case "22" -> departmentController.createDepartment();
                    case "23" -> departmentController.updateDepartment();
                    case "24" -> departmentController.deleteDepartmentById();
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
                =========================
                6. Enroll student into course offering
                7. Show student enrollments
                =========================
                8. Show all courses
                9. Show course by ID
                =========================
                10. Show all semesters (XML)
                11. Show semester by ID (XML)
                12. Create Semester (XML)
                13. Update Semester (XML)
                14. Delete semester by ID (XML)
                =========================
                15. Show all instructors (Jaxb)
                16. Show instructor by ID (Jaxb)
                17. Create instructor (Jaxb)
                18. Update instructor (Jaxb)
                19. Delete instructor (Jaxb)
                =========================
                20. Show all departments (Jackson)
                21. Show department by ID (Jackson)
                22. Create department (Jackson)
                23. Update department (Jackson)
                24. Delete department (Jackson)
                0. Exit
                """);
        System.out.print("Choose an option: ");
    }
}