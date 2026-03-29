package com.solvd.university.service;

import com.solvd.university.model.Department;

import java.util.List;

public interface DepartmentService {
    Department getById(int id);
    List<Department> getAll();
    void create(Department department);
    void update(Department department);
    void deleteById(int id);
}