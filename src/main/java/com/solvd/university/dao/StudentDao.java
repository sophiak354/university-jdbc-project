package com.solvd.university.dao;

import com.solvd.university.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Optional<Student> findById(int id);
    List<Student> findAll();
    void save(Student student);
    void update(Student student);
    void deleteById(int id);
}
