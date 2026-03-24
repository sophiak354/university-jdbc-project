package com.solvd.university.service;

import com.solvd.university.model.Semester;

import java.util.List;

public interface SemesterService {
    Semester getById(int id);
    List<Semester> getAll();
    void create(Semester semester);
    void update(Semester semester);
    void deleteById(int id);
}