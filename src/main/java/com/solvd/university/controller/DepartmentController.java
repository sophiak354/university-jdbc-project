package com.solvd.university.controller;

import com.solvd.university.exception.ServiceException;
import com.solvd.university.model.Department;
import com.solvd.university.service.DepartmentService;

import java.util.List;
import java.util.Scanner;

public class DepartmentController {
    private final DepartmentService departmentService;
    private final Scanner scanner;

    public DepartmentController(
            DepartmentService departmentService,
            Scanner scanner
    ) {
        this.departmentService = departmentService;
        this.scanner = scanner;
    }

    public void showAllDepartments() {
        try {
            List<Department> departments = departmentService.getAll();
            if (departments.isEmpty()) {
                System.out.println("No departments found.");
                return;
            }

            for (Department department : departments) {
                System.out.println(department);
                System.out.println("----------------------------");
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showDepartmentById() {
        try {
            System.out.print("Enter department id: ");
            int departmentId = Integer.parseInt(scanner.nextLine());
            Department department = departmentService.getById(departmentId);
            System.out.println(department);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createDepartment() {
        try {
            System.out.print("Enter department id: ");
            String departmentId = scanner.nextLine();
            System.out.print("Enter department name: ");
            String departmentName = scanner.nextLine();
            System.out.print("Enter office location: ");
            String officeLocation = scanner.nextLine();

            Department department = new Department();
            department.setDepartmentId(Integer.parseInt(departmentId));
            department.setDepartmentName(departmentName);
            department.setOfficeLocation(officeLocation);

            departmentService.create(department);

            System.out.println("Department created successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateDepartment() {
        try {
            System.out.print("Enter department ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Department existing = departmentService.getById(id);

            System.out.println("Leave field empty to keep current value.");

            System.out.print("Enter new department name (" + existing.getDepartmentName() + "): ");
            String departmentName = scanner.nextLine();
            System.out.print("Enter new office location (" + existing.getOfficeLocation() + "): ");
            String officeLocation = scanner.nextLine();

            if (!departmentName.isBlank()) {
                existing.setDepartmentName(departmentName);
            }
            if (!officeLocation.isBlank()) {
                existing.setOfficeLocation(officeLocation);
            }

            departmentService.update(existing);

            System.out.println("Department updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDepartmentById() {
        try {
            System.out.print("Enter department ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            departmentService.deleteById(id);

            System.out.println("Department deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
