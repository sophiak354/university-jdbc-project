package com.solvd.university.service;

import com.solvd.university.model.Semester;

import java.util.List;

public interface SemesterService {
    Semester getById(int id);
    List<Semester> getAll();
}