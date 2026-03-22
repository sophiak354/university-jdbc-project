package com.solvd.university.service;

import com.solvd.university.model.Student;

import java.util.List;

public interface StudentService {
    Student getById(int id);
    List<Student> getAll();
    void create(Student student);
    void update(Student student);
    void deleteById(int id);
}
