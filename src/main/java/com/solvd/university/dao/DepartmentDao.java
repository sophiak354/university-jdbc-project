package com.solvd.university.dao;

import com.solvd.university.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {
    Optional<Department> findById(int id);

    List<Department> findAll();

    void save(Department department);

    void update(Department department);

    void deleteById(int id);
}
