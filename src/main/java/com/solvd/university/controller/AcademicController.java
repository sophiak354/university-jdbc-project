package com.solvd.university.controller;

import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Course;
import com.solvd.university.model.Enrollment;
import com.solvd.university.model.Semester;
import com.solvd.university.service.CourseService;
import com.solvd.university.service.EnrollmentService;
import com.solvd.university.service.SemesterService;

import java.util.List;
import java.util.Scanner;

public class AcademicController {
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final SemesterService semesterService;
    private final Scanner scanner;

    public AcademicController(
            EnrollmentService enrollmentService,
            CourseService courseService,
            SemesterService semesterService,
            Scanner scanner
    ) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.semesterService = semesterService;
        this.scanner = scanner;
    }

    public void enrollStudentIntoOffering() {
        try {
            System.out.print("Enter student id: ");
            int studentId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter course offering id: ");
            int offeringId = Integer.parseInt(scanner.nextLine());

            enrollmentService.enrollStudent(studentId, offeringId);
            System.out.println("Student enrolled successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showStudentEnrollments() {
        try {
            System.out.print("Enter student id: ");
            int studentId = Integer.parseInt(scanner.nextLine());

            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);

            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
                return;
            }

            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment);
                System.out.println("----------------------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAllCourses() {
        try {
            List<Course> courses = courseService.getAll();

            if (courses.isEmpty()) {
                System.out.println("No courses found.");
                return;
            }

            for (Course course : courses) {
                System.out.println(course);
                System.out.println("----------------------------");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCourseById() {
        try {
            System.out.print("Enter course id: ");
            int courseId = Integer.parseInt(scanner.nextLine());

            Course course = courseService.getById(courseId);
            System.out.println(course);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAllSemesters() {
        try {
            List<Semester> semesters = semesterService.getAll();

            if (semesters.isEmpty()) {
                System.out.println("No semesters found.");
                return;
            }

            for (Semester semester : semesters) {
                System.out.println(semester);
                System.out.println("----------------------------");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showSemesterById() {
        try {
            System.out.print("Enter semester id: ");
            int semesterId = Integer.parseInt(scanner.nextLine());

            Semester semester = semesterService.getById(semesterId);
            System.out.println(semester);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}