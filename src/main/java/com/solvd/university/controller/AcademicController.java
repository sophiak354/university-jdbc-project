package com.solvd.university.controller;

import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Course;
import com.solvd.university.model.Enrollment;
import com.solvd.university.model.Semester;
import com.solvd.university.service.CourseService;
import com.solvd.university.service.EnrollmentService;
import com.solvd.university.service.SemesterService;

import java.time.LocalDate;
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

    public void createSemester() {
        try {
            System.out.print("Enter semester name: ");
            String name = scanner.nextLine();

            System.out.print("Enter start date (yyyy-MM-dd): ");
            String startDateStr = scanner.nextLine();

            System.out.print("Enter end date (yyyy-MM-dd): ");
            String endDateStr = scanner.nextLine();

            System.out.print("Enter academic year (e.g. 2025-2026): ");
            String academicYear = scanner.nextLine();

            Semester semester = new Semester();
            semester.setSemesterName(name);
            semester.setStartDate(LocalDate.parse(startDateStr));
            semester.setEndDate(LocalDate.parse(endDateStr));
            semester.setAcademicYear(academicYear);

            semesterService.create(semester);

            System.out.println("Semester created successfully.");

        } catch (Exception e) {
            System.out.println("Error creating semester: " + e.getMessage());
        }
    }

    public void updateSemester() {
        try {
            System.out.print("Enter semester ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Semester existing = semesterService.getById(id);

            System.out.println("Leave field empty to keep current value.");

            System.out.print("Enter new name (" + existing.getSemesterName() + "): ");
            String name = scanner.nextLine();

            System.out.print("Enter new start date (" + existing.getStartDate() + "): ");
            String startDateStr = scanner.nextLine();

            System.out.print("Enter new end date (" + existing.getEndDate() + "): ");
            String endDateStr = scanner.nextLine();

            System.out.print("Enter new academic year (" + existing.getAcademicYear() + "): ");
            String academicYear = scanner.nextLine();

            if (!name.isBlank()) {
                existing.setSemesterName(name);
            }
            if (!startDateStr.isBlank()) {
                existing.setStartDate(LocalDate.parse(startDateStr));
            }
            if (!endDateStr.isBlank()) {
                existing.setEndDate(LocalDate.parse(endDateStr));
            }
            if (!academicYear.isBlank()) {
                existing.setAcademicYear(academicYear);
            }

            semesterService.update(existing);

            System.out.println("Semester updated successfully.");

        } catch (Exception e) {
            System.out.println("Error updating semester: " + e.getMessage());
        }
    }

    public void deleteSemesterById() {
        try {
            System.out.print("Enter semester ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            semesterService.deleteById(id);

            System.out.println("Semester deleted successfully.");

        } catch (Exception e) {
            System.out.println("Error deleting semester: " + e.getMessage());
        }
    }
}