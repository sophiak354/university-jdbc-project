package com.solvd.university.controller;

import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Student;
import com.solvd.university.service.StudentService;

import java.time.LocalDate;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;
    private final Scanner scanner;

    public StudentController(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    public void showStudentById() {
        try {
            System.out.print("Enter student id: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student student = studentService.getById(id);
            System.out.println(student);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id format. Please enter a number.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAllStudents() {
        try {
            var students = studentService.getAll();

            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            for (Student student : students) {
                System.out.println(student);
                System.out.println("----------------------------");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createStudent() {
        try {
            Student student = new Student();

            System.out.print("Enter first name: ");
            student.setFirstName(scanner.nextLine());

            System.out.print("Enter last name: ");
            student.setLastName(scanner.nextLine());

            System.out.print("Enter date of birth (yyyy-mm-dd): ");
            student.setDateOfBirth(LocalDate.parse(scanner.nextLine()));

            System.out.print("Enter gender: ");
            student.setGender(scanner.nextLine());

            System.out.print("Enter email: ");
            student.setEmail(scanner.nextLine());

            System.out.print("Enter phone: ");
            student.setPhone(scanner.nextLine());

            System.out.print("Enter admission date (yyyy-mm-dd): ");
            student.setAdmissionDate(LocalDate.parse(scanner.nextLine()));

            System.out.print("Enter student status: ");
            student.setStudentStatus(scanner.nextLine());

            System.out.print("Enter program id: ");
            student.setProgramId(Integer.parseInt(scanner.nextLine()));

            studentService.create(student);
            System.out.println("Student created successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Failed to create student: " + e.getMessage());
        }
    }

    public void updateStudent() {
        try {
            System.out.print("Enter student id to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student existingStudent = studentService.getById(id);

            System.out.println("Current student data:");
            System.out.println(existingStudent);

            Student updatedStudent = new Student();
            updatedStudent.setStudentId(id);

            System.out.print("Enter first name: ");
            updatedStudent.setFirstName(scanner.nextLine());

            System.out.print("Enter last name: ");
            updatedStudent.setLastName(scanner.nextLine());

            System.out.print("Enter date of birth (yyyy-mm-dd): ");
            updatedStudent.setDateOfBirth(LocalDate.parse(scanner.nextLine()));

            System.out.print("Enter gender: ");
            updatedStudent.setGender(scanner.nextLine());

            System.out.print("Enter email: ");
            updatedStudent.setEmail(scanner.nextLine());

            System.out.print("Enter phone: ");
            updatedStudent.setPhone(scanner.nextLine());

            System.out.print("Enter admission date (yyyy-mm-dd): ");
            updatedStudent.setAdmissionDate(LocalDate.parse(scanner.nextLine()));

            System.out.print("Enter student status: ");
            updatedStudent.setStudentStatus(scanner.nextLine());

            System.out.print("Enter program id: ");
            updatedStudent.setProgramId(Integer.parseInt(scanner.nextLine()));

            studentService.update(updatedStudent);
            System.out.println("Student updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Failed to update student: " + e.getMessage());
        }
    }

    public void deleteStudentById() {
        try {
            System.out.print("Enter student id to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            studentService.deleteById(id);
            System.out.println("Student deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid id format. Please enter a number.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
