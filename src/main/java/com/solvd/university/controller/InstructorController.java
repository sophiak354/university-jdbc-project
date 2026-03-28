package com.solvd.university.controller;

import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Instructor;
import com.solvd.university.service.InstructorService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class InstructorController {
    private final InstructorService instructorService;
    private final Scanner scanner;

    public InstructorController(
            InstructorService instructorService,
            Scanner scanner
    ) {
        this.instructorService = instructorService;
        this.scanner = scanner;
    }

    public void showAllInstructors() {
        try {
            List<Instructor> instructors = instructorService.getAll();
            if (instructors.isEmpty()) {
                System.out.println("No instructors found.");
                return;
            }

            for (Instructor instructor : instructors) {
                System.out.println(instructor);
                System.out.println("----------------------------");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showInstructorById() {
        try {
            System.out.print("Enter instructor id: ");
            int instructorId = Integer.parseInt(scanner.nextLine());
            Instructor instructor = instructorService.getById(instructorId);
            System.out.println(instructor);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createInstructor() {
        try {
            System.out.print("Enter instructor id: ");
            String instructorId = scanner.nextLine();
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter hire date (yyyy-MM-dd): ");
            String hireDate = scanner.nextLine();
            System.out.print("Enter academic title: ");
            String academicTitle = scanner.nextLine();
            System.out.print("Enter department id: ");
            String departmentId = scanner.nextLine();

            Instructor instructor = new Instructor();
            instructor.setInstructorId(Integer.parseInt(instructorId));
            instructor.setFirstName(firstName);
            instructor.setLastName(lastName);
            instructor.setEmail(email);
            instructor.setPhone(phone);
            instructor.setHireDate(LocalDate.parse(hireDate));
            instructor.setAcademicTitle(academicTitle);
            instructor.setDepartmentId(Integer.parseInt(departmentId));

            instructorService.create(instructor);

            System.out.println("Instructor created successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateInstructor() {
        try {
            System.out.print("Enter instructor ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Instructor existing = instructorService.getById(id);

            System.out.println("Leave field empty to keep current value.");

            System.out.print("Enter new first name (" + existing.getFirstName() + "): ");
            String firstName = scanner.nextLine();
            System.out.print("Enter new last name (" + existing.getLastName() + "): ");
            String lastName = scanner.nextLine();
            System.out.print("Enter new email (" + existing.getEmail() + "): ");
            String email = scanner.nextLine();
            System.out.print("Enter new phone (" + existing.getPhone() + "): ");
            String phone = scanner.nextLine();
            System.out.print("Enter new hire date (yyyy-MM-dd) (" + existing.getHireDate() + "): ");
            String hireDate = scanner.nextLine();
            System.out.print("Enter new academic title (" + existing.getAcademicTitle() + "): ");
            String academicTitle = scanner.nextLine();
            System.out.print("Enter new department id (" + existing.getDepartmentId() + "): ");
            String departmentId = scanner.nextLine();

            if (!firstName.isBlank()) {
                existing.setFirstName(firstName);
            }
            if (!lastName.isBlank()) {
                existing.setLastName(lastName);
            }
            if (!email.isBlank()) {
                existing.setEmail(email);
            }
            if (!phone.isBlank()) {
                existing.setPhone(phone);
            }
            if (!hireDate.isBlank()) {
                existing.setHireDate(LocalDate.parse(hireDate));
            }
            if (!academicTitle.isBlank()) {
                existing.setAcademicTitle(academicTitle);
            }
            if (!departmentId.isBlank()) {
                existing.setDepartmentId(Integer.parseInt(departmentId));
            }

            instructorService.update(existing);

            System.out.println("Instructor updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteInstructorById() {
        try {
            System.out.print("Enter instructor ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            instructorService.deleteById(id);

            System.out.println("Instructor deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
