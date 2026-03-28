package com.solvd.university.service;

import com.solvd.university.model.Instructor;

import java.util.List;

public interface InstructorService {
    Instructor getById(int id);
    List<Instructor> getAll();
    void create(Instructor instructor);
    void update(Instructor instructor);
    void deleteById(int id);
}
