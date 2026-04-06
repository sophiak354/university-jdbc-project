package com.solvd.university.mapper;

import com.solvd.university.model.Student;

import java.util.List;

public interface StudentMapper {
    Student findById(int id);

    List<Student> findAll();

    void save(Student student);

    void update(Student student);

    void deleteById(int id);
}
